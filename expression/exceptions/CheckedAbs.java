package ru.itmo.ctddev.Kopitsa.expression.exceptions;

import ru.itmo.ctddev.Kopitsa.expression.Const;
import ru.itmo.ctddev.Kopitsa.expression.TripleExpression;

public class CheckedAbs extends CheckedOperation {
    public CheckedAbs(TripleExpression x) {
        super(x, new Const(0));
    }

    @Override
    protected int over(int x, int y) {
        if (x == Integer.MIN_VALUE) {
            throw new AbsArithmeticException(Integer.toString(x));
        }

        return x < 0 ? -x : x;
    }
}
