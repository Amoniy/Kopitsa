package ru.itmo.ctddev.Kopitsa.expression.exceptions;

import ru.itmo.ctddev.Kopitsa.expression.*;
import ru.itmo.ctddev.Kopitsa.expression.parser.Parser;

public class ExpressionParser implements Parser {
    private void tryCorrectness(String input) throws UnknownSymbolException {
        if (input.contains("absx") || input.contains("absy") || input.contains("absz") || input.contains("sqrtx") || input.contains("sqrty") || input.contains("sqrtz")) {
            throw new UnknownSymbolException("Wrong input");
        }
    }

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
        //this.input = input.replaceAll("\\s", "");
        this.input = input;
        tryCorrectness(input);
        TripleExpression answer = tryAdding();
        if (index + 1 < this.input.length()) {
            throw new SyntaxException("Wrong syntax");
        }
        if (bracketBalance != 0) {
            throw new SyntaxException("Wrong syntax");
        }
        return answer;
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
                answer = tryAdding();
                singleParse();
                break;
            case 'a':
                answer = new CheckedAbs(tryBrackets());
                //singleParse();
                break;
            case 's':
                answer = new CheckedSqrt(tryBrackets());
                //singleParse();
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
            /*while(Character.isWhitespace(ret)){
                index++;
                ret=input.charAt(index-1);
            }*/
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
                    case 'a':
                        character = getOperand();
                        if (character == 'b') {
                            character = getOperand();
                            if (character == 's') {
                                operation = 'a';
                                if (index == input.length()) {
                                    throw new SyntaxException("Expression needed");
                                }
                                //if (input.charAt(index)!=' '||input.charAt(index)!='(')
                                break;
                            } else {
                                throw new UnknownSymbolException("ab" + Character.toString(character));
                            }
                        } else {
                            throw new UnknownSymbolException(Character.toString('a') + Character.toString(character));
                        }
                    case 's':
                        character = getOperand();
                        if (character == 'q') {
                            character = getOperand();
                            if (character == 'r') {
                                character = getOperand();
                                if (character == 't') {
                                    operation = 's';
                                    if (index == input.length()) {
                                        throw new SyntaxException("Expression needed");
                                    }
                                    break;
                                } else {
                                    throw new UnknownSymbolException("sqr" + Character.toString(character));
                                }
                            } else {
                                throw new UnknownSymbolException("sq" + Character.toString(character));
                            }
                        } else {
                            throw new UnknownSymbolException(Character.toString('s') + Character.toString(character));
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
