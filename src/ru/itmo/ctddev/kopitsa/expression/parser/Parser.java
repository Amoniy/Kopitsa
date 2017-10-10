package ru.itmo.ctddev.kopitsa.expression.parser;

import ru.itmo.ctddev.kopitsa.expression.TripleExpression;

/**
 * @author Georgiy Korneev (kgeorgiy@kgeorgiy.info)
 */
public interface Parser {
    TripleExpression parse(String expression) throws /* Change me */ Exception;
}