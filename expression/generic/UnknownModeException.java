package ru.itmo.ctddev.Kopitsa.expression.generic;

import ru.itmo.ctddev.Kopitsa.expression.exceptions.ParserException;

public class UnknownModeException extends ParserException {
    public UnknownModeException(String message) {
        super("Unknown mode : " + message);
    }
}
