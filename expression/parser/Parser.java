package ru.itmo.ctddev.Kopitsa.expression.parser;

import ru.itmo.ctddev.Kopitsa.expression.TripleExpression;

/**
 * @author Georgiy Korneev (kgeorgiy@kgeorgiy.info)
 */
public interface Parser {
    TripleExpression parse(String expression) throws /* Change me */ Exception;
}