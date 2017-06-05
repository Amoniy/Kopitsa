"use strict";
function BinaryFunction(operation, string/*, derivative*/) {
    function Cons() {
        this.ops = Array.prototype.slice.call(arguments, 0);
    }

    Cons.prototype.toString = function () {
        return this.ops.map(function (fun) {
                return fun.toString();
            }).join(" ") + " " + string;
    };
    Cons.prototype.evaluate = function () {
        var args = arguments;
        return operation.apply(null, this.ops.map(function (fun) {
            return fun.evaluate.apply(fun, args);
        }));
    };
    Cons.prototype.prefix = function () {
        return "(" + string + " " + this.ops.map(function (fun) {
                return fun.prefix();
            }).join(" ") + ")";
    };
    /*Cons.prototype.diff = function (arg) {
        return derivative.call(this, arg);
    };*/
    return Cons;
}
var Add = BinaryFunction(function (x, y) {
    return x + y
}, "+"/*, addDiff()*/);
var Subtract = BinaryFunction(function (x, y) {
    return x - y
}, "-"/*, subDiff()*/);
var Multiply = BinaryFunction(function (x, y) {
    return x * y
}, "*"/*, mulDiff()*/);
var Divide = BinaryFunction(function (x, y) {
    return x / y
}, "/"/*, divDiff()*/);
var Power = BinaryFunction(function (x, y) {
    return Math.pow(x, y)
}, "pow"/*,powDiff()*/);
var Log = BinaryFunction(function (x, y) {
    return Math.log(Math.abs(y)) / Math.log(Math.abs(x))
}, "log"/*,logDiff()*/);
var Square = BinaryFunction(function (x) {
    return Math.pow(x, 2)
}, "square"/*,squareDiff()*/);
var Sqrt = BinaryFunction(function (x) {
    return Math.sqrt(Math.abs(x))
}, "sqrt"/*,sqrtDiff()*/);
var Negate = BinaryFunction(function (x) {
    return -x
}, "negate"/*,negDiff()*/);
var Sinh = BinaryFunction(function (x) {
    return (Math.exp(x) - Math.exp(-x)) / 2
}, "sinh"/*,sinhDiff()*/);
var Cosh = BinaryFunction(function (x) {
    return (Math.exp(x) + Math.exp(-x)) / 2
}, "cosh"/*,coshDiff()*/);
var Sin = BinaryFunction(function (x) {
    return Math.sin(x)
}, "sin"/*,sinDiff()*/);
var Cos = BinaryFunction(function (x) {
    return Math.cos(x)
}, "cos"/*,cosDiff()*/);

function Const(value) {
    this.value = value;
    this.evaluate = function () {
        return this.value;
    }
    this.toString = function () {
        return this.value.toString();
    }
    this.prefix = function () {
        return this.value.toString();
    }
    /*this.diff = function () {
        return new Const(0);
    };*/
}
var indexes = ["x", "y", "z", "u", "v", "w"];
function Variable(name) {
    this.name = name;
    this.evaluate = function () {
        return arguments[indexes.indexOf(this.name)];
    }
    this.toString = function () {
        return this.name;
    }
    this.prefix = function () {
        return this.name;
    }
    /*this.diff = function (arg) {
        return new Const(this.name === arg ? 1 : 0);
    };*/
}
/*function addDiff(arg) {
    return new Add(this.ops[0].diff(arg), this.ops[1].diff(arg))
}
function subDiff(arg) {
    return new Subtract(this.ops[0].diff(arg), this.ops[1].diff(arg))
}
function mulDiff(arg) {
    return new Add(new Multiply(this.ops[0].diff(arg), this.ops[1]), new Multiply(this.ops[0], this.ops[1].diff(arg)))
}
function divDiff(arg) {
    return new Divide(new Subtract(new Multiply(this.ops[0].diff(arg), this.ops[1]), new Multiply(this.ops[0], this.ops[1].diff(arg))), new Multiply(this.ops[1], this.ops[1]))
}
function negDiff(arg) {
    return new Negate(this.ops[0].diff(arg))
}
function sinDiff(arg) {
    return new Multiply(this.ops[0].diff(arg), new Cos(this.ops[0]))
}
function cosDiff(arg) {
    return new Negate(new Multiply(this.ops[0].diff(arg), new Sin(this.ops[0])))
}
function logDiff(arg) {

}
function expDiff(arg) {

}
function sinhDiff(arg) {

}
function coshDiff(arg) {

}
function powDiff(arg) {

}
function logDiff(arg) {

}
function squareDiff(arg) {

}
function sqrtDiff(arg) {

}*/
var ops = {};
ops['+'] = {Op: Add, Args: 2};
ops['-'] = {Op: Subtract, Args: 2};
ops['*'] = {Op: Multiply, Args: 2};
ops['/'] = {Op: Divide, Args: 2};
ops['negate'] = {Op: Negate, Args: 1};
ops['sin'] = {Op: Sin, Args: 1};
ops['cos'] = {Op: Cos, Args: 1};
function ParsePrefixError(msg) {
    this.message = msg;
    this.name = "ParsePrefixError";
}
ParsePrefixError.prototype = Object.create(Error.prototype);

function parsePrefix(expr) {
    var regex = /\(|\)|[^\s\(\))]+/g;
    var lastArg = null;
    var lastIndex = 0;
    if (expr === "") {
        throw new ParsePrefixError("Empty input");
    }
    function expression(leftBracket) {
        var lIndex = regex.lastIndex + 1;
        var s = regex.exec(expr);
        if (lastArg == null && s[0] != "(") {
            lastArg = s[0];
            lastIndex = lIndex;
        }
        if (s == null) {
            throw new ParsePrefixError("Unexpected end of string");
        }
        var arg = s[0];

        if (!isNaN(arg)) {
            return new Const(Number(arg));
        }

        if (indexes.indexOf(arg) >= 0) {
            return new Variable(arg);
        }
        switch (arg) {
            case "(":
                var result = expression(true, lastArg);
                s = regex.exec(expr);
                if (s == null || s[0] !== ')') {
                    throw new ParsePrefixError("Too many arguments after " + lastArg + " at " + lastIndex);
                }
                return result;
            case ")":
                throw new ParsePrefixError("Too few arguments after " + lastArg + " at " + lastIndex);
        }
        if (!ops.hasOwnProperty(arg)) {
            throw new ParsePrefixError("Unknown symbol: " + arg + " at " + lastIndex);
        }
        if (leftBracket) {
            var args = [];
            for (var i = 0; i < ops[arg].Args; i++) {
                args.push(expression(false, arg));
            }
            var obj = Object.create(ops[arg].Op.prototype);
            ops[arg].Op.apply(obj, args);
            return obj;
        } else {
            throw new ParsePrefixError("Expression expected, found " + arg + " at " + lastIndex)
        }
    }

    var result = expression(false, null);
    if (regex.exec(expr) !== null) {
        throw new ParsePrefixError("Excessive info");
    }
    return result;
}