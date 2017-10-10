package ru.itmo.ctddev.kopitsa.expression.exceptions;

public class InvalidNumberException extends ParserException {
    public InvalidNumberException(String message) {
        super("Invalid number: " + message);
    }
}
