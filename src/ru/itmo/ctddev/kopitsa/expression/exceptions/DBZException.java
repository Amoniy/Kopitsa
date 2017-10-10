package ru.itmo.ctddev.kopitsa.expression.exceptions;

public class DBZException extends ArithmeticException {
    public DBZException(int x, int y) {
        super("Division by zero: " + x + " / " + y);
    }
}
