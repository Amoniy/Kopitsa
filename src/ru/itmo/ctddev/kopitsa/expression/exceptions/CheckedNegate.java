package ru.itmo.ctddev.kopitsa.expression.exceptions;

import ru.itmo.ctddev.kopitsa.expression.Const;
import ru.itmo.ctddev.kopitsa.expression.TripleExpression;

public class CheckedNegate extends CheckedOperation {
    public CheckedNegate(TripleExpression x) {
        super(x, new Const(0));
    }

    @Override
    protected int over(int x, int y) {
        if (x == Integer.MIN_VALUE) {
            throw new OverflowException("-" + Integer.toString(x));
        }
        return -x;
    }
}
