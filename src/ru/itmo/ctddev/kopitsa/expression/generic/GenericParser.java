package ru.itmo.ctddev.kopitsa.expression.generic;

import ru.itmo.ctddev.kopitsa.expression.exceptions.InvalidNumberException;
import ru.itmo.ctddev.kopitsa.expression.exceptions.SyntaxException;
import ru.itmo.ctddev.kopitsa.expression.exceptions.UnknownSymbolException;

import java.math.BigInteger;

public class GenericParser<T extends Number> {
    public GenericParser(String mode) throws UnknownModeException {
        this.mode = mode;
        switch (mode) {

            case "i":
                this.mode = "Integer";
                //constant=new Integer(0);
                //хотели так. так нельзя. требует Type<T> вместо инта. но тайп т создать нельзя. так что создадим абстрактный тип наследующий тайп т.
                constant = new GenericInteger(0);
                break;
            case "d":
                this.mode = "Double";
                constant = new GenericDouble(0D);
                break;
            case "bi":
                this.mode = "BigInteger";
                constant = new GenericBigInteger(BigInteger.ZERO);
                break;
            case "u":
                this.mode = "Int";
                constant = new GenericInt(0);
                break;
            case "b":
                this.mode = "Byte";
                constant = new GenericByte((byte) 0);
                break;
            case "f":
                this.mode = "Float";
                constant = new GenericFloat(0f);
                break;
            default:
                throw new UnknownModeException(mode);
        }
    }

    public Type<T> getType() {
        return constant;
    }

    private int index;
    private String input;
    private GenericNumber constant;
    private char variable;
    private char operation;
    private int bracketBalance = 0;
    String mode;

    public GenericExpression<T> parse(String input) throws UnknownSymbolException, SyntaxException, InvalidNumberException, UnknownModeException {
        index = 0;
        operation = 0;
        variable = 0;
        bracketBalance = 0;
        this.input = input;
        GenericExpression<T> answer = tryAdding();
        if (index + 1 < this.input.length()) {
            throw new SyntaxException("Wrong syntax");
        }
        if (bracketBalance != 0) {
            throw new SyntaxException("Wrong syntax");
        }
        return answer;
    }


    private GenericExpression<T> tryAdding() throws SyntaxException, InvalidNumberException, UnknownSymbolException {
        GenericExpression<T> left = tryMultiplying();
        while (true) {
            switch (operation) {
                case '-':
                    left = new GenericSubtract<>(left, tryMultiplying());
                    break;
                case '+':
                    left = new GenericAdd<>(left, tryMultiplying());
                    break;
                default:
                    return left;
            }
        }
    }

    private GenericExpression<T> tryMultiplying() throws SyntaxException, InvalidNumberException, UnknownSymbolException {
        GenericExpression<T> left = tryBrackets();
        while (true) {
            switch (operation) {
                case '*':
                    left = new GenericMultiply<>(left, tryBrackets());
                    break;
                case '/':
                    left = new GenericDivide<>(left, tryBrackets());
                    break;
                case 'm':
                    left = new GenericMod<>(left, tryBrackets());
                    break;
                default:
                    return left;
            }
        }
    }

    private GenericExpression<T> tryBrackets() throws SyntaxException, InvalidNumberException, UnknownSymbolException {
        singleParse();
        GenericExpression<T> answer;
        switch (operation) {
            case 'o':
                answer = new GenericConst<>(constant);
                singleParse();
                break;
            case 'v':
                answer = new GenericVariable<>(Character.toString(variable));
                singleParse();
                break;
            case '-'://unary minus
                answer = new GenericNegate<>(tryBrackets());
                break;
            case '(':
                answer = tryAdding();
                singleParse();
                break;
            case 'a':
                answer = new GenericAbs(tryBrackets());
                break;
            case 's':
                answer = new GenericSquare(tryBrackets());
                break;
            default:
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
        if (character == '-' && mode.equals("Integer")) {
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
                    constant = new GenericInteger(Integer.MIN_VALUE);
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
                    switch (mode) {
                        case "Integer":
                            constant = new GenericInteger();
                            constant = (GenericNumber) constant.parse(str.toString());
                            break;
                        case "Double":
                            constant = new GenericDouble();
                            constant = (GenericNumber) constant.parse(str.toString());
                            break;
                        case "BigInteger":
                            constant = new GenericBigInteger();
                            constant = (GenericNumber) constant.parse(str.toString());
                            break;
                        case "Int":
                            constant = new GenericInt();
                            constant = (GenericNumber) constant.parse(str.toString());
                            break;
                        case "Float":
                            constant = new GenericFloat();
                            constant = (GenericNumber) constant.parse(str.toString());
                            break;
                        case "Byte":
                            constant = new GenericByte();
                            constant = (GenericNumber) constant.parse(str.toString());
                            break;
                    }
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
                        if (oper.equals("mod")) {
                            operation = 'm';
                            if (index == input.length()) {
                                throw new SyntaxException("Expression needed");
                            }

                            if (!(input.charAt(index) == ' ' || input.charAt(index) == '(' || input.charAt(index) == '-')) {
                                throw new UnknownSymbolException("mod" + Character.toString(input.charAt(index)));
                            }
                            break;
                        } else {
                            throw new UnknownSymbolException(oper);
                        }
                    }
                    case 's': {
                        String oper = "s";
                        for (int i = 0; i < 5; i++) {
                            oper += getOperand();
                        }
                        if (oper.equals("square")) {
                            operation = 's';
                            if (index == input.length()) {
                                throw new SyntaxException("Expression needed");
                            }

                            if (!(input.charAt(index) == ' ' || input.charAt(index) == '(' || input.charAt(index) == '-')) {
                                throw new UnknownSymbolException("square" + Character.toString(input.charAt(index)));
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
