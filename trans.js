var variables = ['x', 'y', 'z'];

function operation(h, opString, diff) {
    function Cons() {
        this.ops = Array.prototype.slice.call(arguments, 0);
    }

    Cons.prototype.toString = function () {
        return this.ops.map(function (fun) { return fun.toString(); }).join(" ") + " " + opString;
    };
    Cons.prototype.evaluate = function () {
        var args = arguments;
        return h.apply(null, this.ops.map(function(fun) { return fun.evaluate.apply(fun, args); }));
    };
    Cons.prototype.prefix = function () {
        return "(" + opString + " " + this.ops.map(function (fun) { return fun.prefix(); }).join(" ") + ")";
    };
    Cons.prototype.diff = function (arg) {
        return diff.call(this, arg);
    };
    return Cons;
}

function Const(arg) {
    this.cnst = arg;
}
Const.prototype.toString = function () {
    return this.cnst.toString();
};
Const.prototype.evaluate = function () {
    return this.cnst;
};
Const.prototype.prefix = function () {
    return this.cnst.toString();
};
Const.prototype.diff = function () {
    return new Const(0);
};


function Variable(arg) {
    this.v = arg;
}
Variable.prototype.toString = function () {
    return this.v;
};
Variable.prototype.evaluate = function () {
    return arguments[variables.indexOf(this.v)];
};
Variable.prototype.prefix = function () {
    return this.v;
};
Variable.prototype.diff = function (arg) {
    return new Const(this.v === arg ? 1 : 0);
};

function addDiff(arg) {
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
function expDiff(arg) {
    return new Multiply(this.ops[0].diff(arg), new Exp(this.ops[0]))
}
function atanDiff(arg) {
    return new Multiply(this.ops[0].diff(arg), new Divide(new Const(1), new Add(new Multiply(this.ops[0], this.ops[0]), new Const(1))))
}

var Add = operation(function (x, y) {
    return x + y
}, "+", addDiff);
var Subtract = operation(function (x, y) {
    return x - y
}, "-", subDiff);
var Multiply = operation(function (x, y) {
    return x * y
}, "*", mulDiff);
var Divide = operation(function (x, y) {
    return x / y
}, "/", divDiff);

var Negate = operation(function (x) { return -x }, "negate", negDiff);
var Sin = operation(Math.sin, "sin", sinDiff);
var Cos = operation(Math.cos, "cos", cosDiff);
var Exp = operation(Math.exp, "exp", expDiff);
var ArcTan = operation(Math.atan, "atan", atanDiff);

var ops = {};
ops['+'] = {Op: Add, Args: 2};
ops['-'] = {Op: Subtract, Args: 2};
ops['*'] = {Op: Multiply, Args: 2};
ops['/'] = {Op: Divide, Args: 2};

ops['negate'] = {Op: Negate, Args: 1};
ops['sin'] = {Op: Sin, Args: 1};
ops['cos'] = {Op: Cos, Args: 1};
ops['exp'] = {Op: Exp, Args: 1};
ops['atan'] = {Op: ArcTan, Args: 1};

/*var parse = function (expr) {
 var regex = /\s*(\-?\d+|[a-zA-Z]+|[^\s]+)/ig;
 var tokens = [];
 var s;
 while ((s = regex.exec(expr)) != null) {
 tokens.push(s[1]);
 }
 //println("#" + stack.toString() + "#");
 var expression = function() {
 var arg = tokens.pop();
 if (arg.match(/\-?\d+/)) {
 return new Const(Number(arg));
 }
 if (variables.indexOf(arg) >= 0) {
 return new Variable(arg);
 }
 var arg1, arg2;
 switch (arg) {
 case "+":
 case "-":
 case "*":
 case "/":
 case "%":
 case "**":
 arg2 = expression();
 arg1 = expression();
 return new ops[arg].Op(arg1, arg2);
 case "negate":
 return new Negate(expression());
 case "abs":
 //return new Abs(expression());
 case "log":
 //return new Log(expression());
 default:
 return undefined;
 }
 };
 return expression();
 };*/

function ParsePrefixError(msg) {
    this.message = msg;
    this.name = "ParsePrefixError";
}
ParsePrefixError.prototype = Object.create(Error.prototype);

function ParsePrefixInternalError(msg) {
    this.message = msg;
    this.name = "ParsePrefixInternalError";
}

function parsePrefix(expr) {
    var regex = /\s*([\(\)]|\-?[A-Za-z]+|\-?\d+|[-!"#$%&'*+,./:;<=>?@[\\\]_`{|}~]+)/g;
    var lastArg = null;
    var lastIndex = 0;

    if (expr === "") {
        throw new ParsePrefixError("Empty input");
    }

    function expression(leftBracket) {
        var lIndex = regex.lastIndex + 1;
        var s = regex.exec(expr);

        if (lastArg == null && s[1] != "(") {
            lastArg = s[1];
            lastIndex = lIndex;
        }

        if (s == null) {
            throw new ParsePrefixError("Unexpected end of string");
        }
        var arg = s[1];

        if (arg.match(/\-?\d+/)) {
            return new Const(Number(arg));
        }

        if (variables.indexOf(arg) >= 0) {
            return new Variable(arg);
        }

        switch (arg) {
            case "(":
                var result = expression(true, lastArg);

                s = regex.exec(expr);
                if (s == null || s[1] !== ')') {
                    throw new ParsePrefixError("Too many arguments after " + lastArg + " at " + lastIndex);
                }
                return result;

            case ")":
                throw new ParsePrefixError("Too few arguments after " + lastArg + " at " + lastIndex);  //TODO
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