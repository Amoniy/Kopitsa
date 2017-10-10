package ru.itmo.ctddev.kopitsa.expression.generic;

import ru.itmo.ctddev.kopitsa.expression.exceptions.ParserException;

public class UnknownModeException extends ParserException {
    public UnknownModeException(String message) {
        super("Unknown mode : " + message);
    }
}
