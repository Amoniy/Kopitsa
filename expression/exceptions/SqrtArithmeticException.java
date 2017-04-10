package ru.itmo.ctddev.Kopitsa.expression.exceptions;

public class SqrtArithmeticException extends ArithmeticException{
    SqrtArithmeticException(String message) {
        super("SqrtArithmeticException: " + message);
    }
}
