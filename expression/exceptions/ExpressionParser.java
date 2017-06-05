package ru.itmo.ctddev.Kopitsa.expression.exceptions;

import ru.itmo.ctddev.Kopitsa.expression.*;

public class ExpressionParser implements Parser {
    private int index;
    private String input;
    private int constant;
    private char variable;
    private char operation;
    private int bracketBalance = 0;

    public TripleExpression parse(String input) throws UnknownSymbolException, SyntaxException, InvalidNumberException {
        index = 0;
        constant = 0;
        operation = 0;
        variable = 0;
        bracketBalance = 0;
        this.input = input;
        /*TripleExpression answer = tryAdding();
        while (index<input.length()-1) {
            if (operation == 'm') {
                answer = new CheckedMin(answer, tryAdding());
            }
            if(operation=='M'){
                answer=new CheckedMax(answer,tryAdding());
            }
        }*/
        TripleExpression answer = tryMinMax();

        if (index + 1 < this.input.length()) {
            throw new SyntaxException("Wrong syntax");
        }
        if (bracketBalance != 0) {
            throw new SyntaxException("Bracket exception");
        }
        return answer;
    }

    private TripleExpression tryMinMax() throws SyntaxException, InvalidNumberException, UnknownSymbolException {
        TripleExpression left = tryAdding();
        while (true) {
            switch (operation) {
                case 'm':
                    left = new CheckedMin(left, tryAdding());
                    break;
                case 'M':
                    left = new CheckedMax(left, tryAdding());
                    break;
                default:
                    return left;
            }
        }
    }

    private TripleExpression tryAdding() throws SyntaxException, InvalidNumberException, UnknownSymbolException {
        TripleExpression left = tryMultiplying();
        while (true) {
            switch (operation) {
                case '-':
                    left = new CheckedSubtract(left, tryMultiplying());
                    break;
                case '+':
                    left = new CheckedAdd(left, tryMultiplying());
                    break;
                default:
                    return left;
            }
        }
    }

    private TripleExpression tryMultiplying() throws SyntaxException, InvalidNumberException, UnknownSymbolException {
        TripleExpression left = tryBrackets();
        while (true) {
            switch (operation) {
                case '*':
                    left = new CheckedMultiply(left, tryBrackets());
                    break;
                case '/':
                    left = new CheckedDivide(left, tryBrackets());
                    break;
                default:
                    return left;
            }
        }
    }

    private TripleExpression tryBrackets() throws SyntaxException, InvalidNumberException, UnknownSymbolException {
        singleParse();
        TripleExpression answer;
        switch (operation) {
            case 'o':
                answer = new Const(constant);
                singleParse();
                break;
            case 'v':
                answer = new Variable(Character.toString(variable));
                singleParse();
                break;
            case '-'://unary minus
                answer = new CheckedNegate(tryBrackets());
                break;
            case '(':
                answer = tryMinMax();
                singleParse();
                break;
            case 'a':
                answer = new CheckedAbs(tryBrackets());
                break;
            case 's':
                answer = new CheckedSqrt(tryBrackets());
                break;
            default://я сюда никогда не зайду
                throw new SyntaxException("Expression starts with " + Character.toString(operation));
        }

        return answer;
    }

    private char getOperand() {
        if (index < input.length()) {
            index++;
            char ret = input.charAt(index - 1);
            return ret;
        } else {
            return '#';
        }
    }

    private void singleParse() throws UnknownSymbolException, InvalidNumberException, SyntaxException {
        char character = getOperand();
        while (Character.isWhitespace(character)) {
            character = getOperand();
        }
        boolean flag = false;
        if (character == '-') {
            boolean wasAtLeastOneSpace = false;
            int spaceMarker = 0;
            while (Character.isWhitespace(input.charAt(index))) {
                index++;
                wasAtLeastOneSpace = true;
            }
            if (wasAtLeastOneSpace) {
                index--;
                spaceMarker = 1;
            }
            if (index + 9 + spaceMarker < input.length()) {

                if (input.substring(index + spaceMarker, index + 10 + spaceMarker).equals("2147483648")) {
                    operation = 'o';
                    character = ' ';
                    index += 10 + spaceMarker;
                    constant = Integer.MIN_VALUE;
                    flag = true;
                }

            }
        }
        if (!flag) {
            if (Character.isDigit(character) && !flag) {
                StringBuilder str = new StringBuilder();
                while (Character.isDigit(character)) {
                    str.append(character);
                    character = getOperand();
                }
                index--;
                try {
                    constant = Integer.parseInt(str.toString());
                } catch (NumberFormatException e) {
                    throw new InvalidNumberException(str.toString());
                }
                operation = 'o';
            } else if (character == 'x' || character == 'y' || character == 'z') {
                operation = 'v';
                variable = character;
            } else if (character == '#') {

            } else {
                switch (character) {
                    case '+':
                        operation = '+';
                        break;
                    case '-':
                        operation = '-';
                        break;
                    case '*':
                        operation = '*';
                        break;
                    case 'a': {
                        String oper = "a";
                        for (int i = 0; i < 2; i++) {
                            oper += getOperand();
                        }
                        if (oper.equals("abs")) {
                            operation = 'a';
                            if (index == input.length()) {
                                throw new SyntaxException("Expression needed");
                            }

                            if (!(input.charAt(index) == ' ' || input.charAt(index) == '(' || input.charAt(index) == '-')) {
                                throw new UnknownSymbolException("abs" + Character.toString(input.charAt(index)));
                            }
                            break;
                        } else {
                            throw new UnknownSymbolException(oper);
                        }
                    }
                    case 'm': {
                        String oper = "m";
                        for (int i = 0; i < 2; i++) {
                            oper += getOperand();
                        }
                        if (oper.equals("min")) {
                            operation = 'm';
                            if (index == input.length()) {
                                throw new SyntaxException("Expression needed");
                            }

                            if (!(input.charAt(index) == ' ' || input.charAt(index) == '(' || input.charAt(index) == '-')) {
                                throw new UnknownSymbolException("min" + Character.toString(input.charAt(index)));
                            }
                            break;
                        } else if (oper.equals("max")) {
                            operation = 'M';
                            if (index == input.length()) {
                                throw new SyntaxException("Expression needed");
                            }

                            if (!(input.charAt(index) == ' ' || input.charAt(index) == '(' || input.charAt(index) == '-')) {
                                throw new UnknownSymbolException("max" + Character.toString(input.charAt(index)));
                            }
                            break;
                        } else {
                            throw new UnknownSymbolException(oper);
                        }
                    }
                    case 's': {
                        String oper = "s";
                        for (int i = 0; i < 3; i++) {
                            oper += getOperand();
                        }

                        if (oper.equals("sqrt")) {
                            operation = 's';
                            if (index == input.length()) {
                                throw new SyntaxException("Expression needed");
                            }
                            if (!(input.charAt(index) == ' ' || input.charAt(index) == '(' || input.charAt(index) == '-')) {
                                throw new UnknownSymbolException("sqrt" + Character.toString(input.charAt(index)));
                            }
                            break;
                        } else {
                            throw new UnknownSymbolException(oper);
                        }
                    }
                    case '/':
                        operation = '/';
                        break;
                    case '(':
                        bracketBalance++;
                        operation = '(';
                        break;
                    case ')':
                        bracketBalance--;
                        if (bracketBalance < 0) {
                            throw new SyntaxException("Bracket exception");
                        }
                        operation = ')';
                        break;
                    default:
                        throw new UnknownSymbolException(Character.toString(character));
                }
            }
        }
    }
}
