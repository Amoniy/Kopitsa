package ru.itmo.ctddev.Kopitsa.expression;

import ru.itmo.ctddev.Kopitsa.expression.exceptions.*;
import ru.itmo.ctddev.Kopitsa.expression.parser.Parser;

public class ExpressionParser implements Parser {
    private int index;
    private String input;
    private int constant;
    private char variable;
    private char operation;

    public TripleExpression parse(String input) throws UnknownSymbolException, SyntaxException, InvalidNumberException{
        index = 0;
        constant = 0;
        operation = 0;
        variable = 0;
        this.input = input.replaceAll("\\s", "");
        return tryShifts();
    }

    private SuperExpression tryShifts() throws SyntaxException, InvalidNumberException, UnknownSymbolException{
        SuperExpression left = tryOr();
        while (true) {
            switch (operation) {
                case '>':
                    left = new ShiftRight(left, tryOr());
                    break;
                case '<':
                    left = new ShiftLeft(left, tryOr());
                    break;
                default:
                    return left;
            }
        }
    }

    private SuperExpression tryOr() throws SyntaxException, InvalidNumberException, UnknownSymbolException{
        SuperExpression left = tryXor();
        while (true) {
            switch (operation) {
                case '|':
                    left = new Or(left, tryXor());
                    break;
                default:
                    return left;
            }
        }
    }

    private SuperExpression tryXor() throws SyntaxException, InvalidNumberException, UnknownSymbolException{
        SuperExpression left = tryAnd();
        while (true) {
            switch (operation) {
                case '^':
                    left = new Xor(left, tryAnd());
                    break;
                default:
                    return left;
            }
        }
    }

    private SuperExpression tryAnd() throws SyntaxException, InvalidNumberException, UnknownSymbolException{
        SuperExpression left = tryAdding();
        while (true) {
            switch (operation) {
                case '&':
                    left = new And(left, tryAdding());
                    break;
                default:
                    return left;
            }
        }
    }

    private SuperExpression tryAdding() throws SyntaxException, InvalidNumberException, UnknownSymbolException{
        SuperExpression left = tryMultiplying();
        while (true) {
            switch (operation) {
                case '-':
                    left = new Subtract(left, tryMultiplying());
                    break;
                case '+':
                    left = new Add(left, tryMultiplying());
                    break;
                default:
                    return left;
            }
        }
    }

    private SuperExpression tryMultiplying() throws SyntaxException, InvalidNumberException, UnknownSymbolException{
        SuperExpression left = tryBrackets();
        while (true) {
            switch (operation) {
                case '*':
                    left = new Multiply(left, tryBrackets());
                    break;
                case '/':
                    left = new Divide(left, tryBrackets());
                    break;
                default:
                    return left;
            }
        }
    }

    private SuperExpression tryBrackets() throws SyntaxException, InvalidNumberException, UnknownSymbolException{
        singleParse();
        SuperExpression answer;
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
                answer = new Subtract(new Const(0), tryBrackets());
                break;
            case 'a':
                answer = new Abs(tryBrackets(), new Const(0));
                break;
            case 's':
                answer = new Square(tryBrackets(), new Const(0));
                break;
            case '(':
                answer = tryShifts();
                singleParse();
                break;
            default://я сюда никогда не зайду
                answer = new Const(0);
        }
        return answer;
    }

    private char getOperand() {
        if (index < input.length()) {
            index++;
            return input.charAt(index - 1);
        } else {
            return ' ';
        }
    }

    private void singleParse() throws UnknownSymbolException, InvalidNumberException {
        char character = getOperand();
        if (Character.isDigit(character)) {
            StringBuilder str = new StringBuilder();
            while (Character.isDigit(character)) {
                str.append(character);
                character = getOperand();
            }
            index--;
            constant = Integer.parseUnsignedInt(str.toString());
            operation = 'o';
        } else if (character == 'x' || character == 'y' || character == 'z') {
            operation = 'v';
            variable = character;
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
                case '/':
                    operation = '/';
                    break;
                case '(':
                    operation = '(';
                    break;
                case ')':
                    operation = ')';
                    break;
                case '^':
                    operation = '^';
                    break;
                case '&':
                    operation = '&';
                    break;
                case '|':
                    operation = '|';
                    break;
                case 'a':
                    operation = 'a';
                    index += 2;
                    break;
                case 's':
                    operation = 's';
                    index += 5;
                    break;
                case '>':
                    operation = '>';
                    index += 1;
                    break;
                case '<':
                    operation = '<';
                    index += 1;
                    break;
            }
        }
    }
}
