package ru.itmo.ctddev.Kopitsa.expression.exceptions;

public class DBZException extends ArithmeticException {
    public DBZException(int x, int y) {
        super("Division by zero: " + x + " / " + y);
    }
}
