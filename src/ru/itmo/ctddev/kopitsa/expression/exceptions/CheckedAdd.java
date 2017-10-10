package ru.itmo.ctddev.kopitsa.expression.exceptions;

import ru.itmo.ctddev.kopitsa.expression.TripleExpression;

public class CheckedAdd extends CheckedOperation {
    public CheckedAdd(TripleExpression x, TripleExpression y) {
        super(x, y);
    }

    public int over(int x, int y) {
        int r = x + y;
        if (((x ^ r) & (y ^ r)) < 0) {
            throw new OverflowException(Integer.toString(x) + " + " + Integer.toString(y));
        }
        return r;
    }
}
