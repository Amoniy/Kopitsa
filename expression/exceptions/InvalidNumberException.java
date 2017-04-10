package ru.itmo.ctddev.Kopitsa.expression.exceptions;

public class InvalidNumberException extends ParserException {
    InvalidNumberException(String message) {
        super("Invalid number: " + message);
    }
}
