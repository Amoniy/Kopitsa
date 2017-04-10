package ru.itmo.ctddev.Kopitsa.expression.exceptions;


public class AbsArithmeticException extends ArithmeticException{
    AbsArithmeticException(String message) {
        super("AbsArithmeticException: " + message);
    }
}
