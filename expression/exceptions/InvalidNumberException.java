package ru.itmo.ctddev.Kopitsa.expression.exceptions;

public class InvalidNumberException extends ParserException {
    public InvalidNumberException(String message) {
        super("Invalid number: " + message);
    }
}
