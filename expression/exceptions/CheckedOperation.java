package ru.itmo.ctddev.Kopitsa.expression.exceptions;

import ru.itmo.ctddev.Kopitsa.expression.TripleExpression;

public abstract class CheckedOperation implements TripleExpression {
    private TripleExpression left, right;

    protected CheckedOperation(TripleExpression x, TripleExpression y) {
        left = x;
        right = y;
    }

    public int evaluate(int x, int y, int z) {
        return over(left.evaluate(x, y, z), right.evaluate(x, y, z));
    }

    protected abstract int over(int x, int y);
}
