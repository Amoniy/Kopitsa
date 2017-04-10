package ru.itmo.ctddev.Kopitsa.expression.exceptions;

import ru.itmo.ctddev.Kopitsa.expression.SuperExpression;
import ru.itmo.ctddev.Kopitsa.expression.TripleExpression;
import ru.itmo.ctddev.Kopitsa.expression.*;
public class CheckedNegate extends CheckedOperation {
    public CheckedNegate(TripleExpression x) {
        super(x, new Const(0));
    }

    @Override
    protected int over(int x, int y) {
        if (x == Integer.MIN_VALUE) {
            throw new OverflowException("-"+Integer.toString(x));
        }
        return -x;
    }
}
