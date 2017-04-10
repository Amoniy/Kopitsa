package ru.itmo.ctddev.Kopitsa.expression.exceptions;

public class SyntaxException extends ParserException {
    SyntaxException(String message) {
        super("SyntaxException : " + message);
    }
}
