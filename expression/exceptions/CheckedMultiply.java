package ru.itmo.ctddev.Kopitsa.expression.exceptions;

import ru.itmo.ctddev.Kopitsa.expression.Operation;
import ru.itmo.ctddev.Kopitsa.expression.SuperExpression;
import ru.itmo.ctddev.Kopitsa.expression.TripleExpression;

public class CheckedMultiply extends CheckedOperation {
    public CheckedMultiply(TripleExpression x, TripleExpression y) {
        super(x, y);
    }

    @Override
    public int over(int x, int y) {
        int r = x * y;

        if (y > 0) {
            if (x > Integer.MAX_VALUE / y || x < Integer.MIN_VALUE / y) {
                throw new OverflowException(Integer.toString(x) + " * " + Integer.toString(y));
            }
        } else if (y < -1) {
            if (x > Integer.MIN_VALUE / y || x < Integer.MAX_VALUE / y) {
                throw new OverflowException(Integer.toString(x) + " * " + Integer.toString(y));
            }
        } else if (y == -1 && x == Integer.MIN_VALUE) {
            throw new OverflowException(Integer.toString(x) + " * " + Integer.toString(y));
        }
        return r;
    }
}
