package ru.itmo.ctddev.kopitsa.expression.exceptions;

public class OverflowException extends ArithmeticException {
    protected OverflowException(String input) {
        super("Overflow in: " + input);
    }
}
