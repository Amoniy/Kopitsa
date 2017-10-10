package ru.itmo.ctddev.kopitsa.expression.exceptions;

public class SyntaxException extends ParserException {
    public SyntaxException(String message) {
        super("SyntaxException : " + message);
    }
}
