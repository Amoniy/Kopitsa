package ru.itmo.ctddev.kopitsa.expression.exceptions;


public class AbsArithmeticException extends ArithmeticException {
    AbsArithmeticException(String message) {
        super("AbsArithmeticException: " + message);
    }
}
