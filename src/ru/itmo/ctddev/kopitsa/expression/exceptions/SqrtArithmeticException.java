package ru.itmo.ctddev.kopitsa.expression.exceptions;

public class SqrtArithmeticException extends ArithmeticException {
    SqrtArithmeticException(String message) {
        super("SqrtArithmeticException: " + message);
    }
}
