package ru.itmo.ctddev.Kopitsa.counter;

import ru.itmo.ctddev.Kopitsa.counter.DoubleExpression;
import ru.itmo.ctddev.Kopitsa.counter.Expression;
import ru.itmo.ctddev.Kopitsa.counter.TripleExpression;

public interface SuperExpression extends Expression, DoubleExpression, TripleExpression {
}
