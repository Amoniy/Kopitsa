package ru.itmo.ctddev.kopitsa.expression.exceptions;

import ru.itmo.ctddev.kopitsa.expression.TripleExpression;

public class CheckedSubtract extends CheckedOperation {
    public CheckedSubtract(TripleExpression x, TripleExpression y) {
        super(x, y);
    }

    @Override
    public int over(int x, int y) {
        int r = x - y;

        if (((x ^ y) & (x ^ r)) < 0) {
            throw new OverflowException(Integer.toString(x) + " - " + Integer.toString(y));
        }
        return r;
    }

}
