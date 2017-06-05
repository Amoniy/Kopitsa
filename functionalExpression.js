"use strict";
function binaryFunction(f) {
    return function (left, right) {
        return function () {
            return f(left.apply(null, arguments), right.apply(null, arguments));
        }//////////call(this, [a,b,c])
    }
}
function unaryFunction(f) {
    return function (left) {
        return function () {
            return f(left.apply(null, arguments));
        }
    }
}
var pi = function () {
    return Math.PI;
}
var e = function () {
    return Math.E;
}
var x = function () {
    return arguments[0];
}
var y = function () {
    return arguments[1];
}
var z = function () {
    return arguments[2];
}
var cnst = function (x) {
    return function () {
        return x;
    }
}
var indexes = ["x", "y", "z", "u", "v", "w"];
var variable = function (str) {
    var i = indexes.indexOf(str);
    return function () {
        return arguments[i];
    }
}
var add = binaryFunction(function (x, y) {
    return x + y;
});
var subtract = binaryFunction(function (x, y) {
    return x - y;
});
var multiply = binaryFunction(function (x, y) {
    return x * y;
});
var divide = binaryFunction(function (x, y) {
    return x / y;
});
var negate = unaryFunction(function (x) {
    return -x;
});
var min3 = function (a, b, c) {
    return function () {
        return Math.min(a.apply(null, arguments), b.apply(null, arguments), c.apply(null, arguments));
    }
};
var max5 = function (a, b, c, d, e) {
    return function () {
        return Math.max(a.apply(null, arguments), b.apply(null, arguments), c.apply(null, arguments), d.apply(null, arguments), e.apply(null, arguments));
    }
};
var isOperation = function (stringToCheck) {
    return !(!isNaN(stringToCheck) || indexes.indexOf(stringToCheck) != -1 || stringToCheck == "pi" || stringToCheck == "e");
};
var parse = function (input) {
    //var regex = /\s*(\-?\d+|[a-zA-Z]+|[^\s]+)/ig;
    //var lexems = [];

    var lexems = input.split(" ");

    //var s;
    //while ((s = regex.exec(input)) != null) {
    //    lexems.push(s[1]);
    //}

    var processedLexems = [];
    var answer = function () {
        for (var i = 0; i < lexems.length; i++) {
            if (lexems[i] == "") {
                continue;
            }
            if (isOperation(lexems[i])) {//operation
                switch (lexems[i]) {
                    case "+": {
                        var right = processedLexems.pop();
                        var left = processedLexems.pop();
                        processedLexems.push(add(left, right));
                        break;
                    }
                    case "*": {
                        var right = processedLexems.pop();
                        var left = processedLexems.pop();
                        processedLexems.push(multiply(left, right));
                        break;
                    }
                    case "/": {
                        var right = processedLexems.pop();
                        var left = processedLexems.pop();
                        processedLexems.push(divide(left, right));
                        break;
                    }
                    case "-": {
                        var right = processedLexems.pop();
                        var left = processedLexems.pop();
                        processedLexems.push(subtract(left, right));
                        break;
                    }
                    case "negate": {
                        var right = processedLexems.pop();
                        processedLexems.push(negate(right));
                        break;
                    }
                    case "min3": {
                        var right = processedLexems.pop();
                        var middle = processedLexems.pop();
                        var left = processedLexems.pop();
                        processedLexems.push(min3(left, middle, right));
                        break;
                    }
                    case "max5": {
                        var a = processedLexems.pop();
                        var b = processedLexems.pop();
                        var c = processedLexems.pop();
                        var d = processedLexems.pop();
                        var E = processedLexems.pop();
                        processedLexems.push(max5(a, b, c, d, E));
                        break;
                    }
                    default: {
                    }
                }
            }
            else {
                if (!isNaN(lexems[i])) {
                    processedLexems.push(cnst(parseInt(lexems[i])));
                }
                else if (lexems[i] == "pi") {
                    processedLexems.push(pi);
                }
                else if (lexems[i] == "e") {
                    processedLexems.push(e);
                }
                else {
                    processedLexems.push(variable(lexems[i]));
                }
            }
        }
        return processedLexems.pop();
    };
    return answer();
};