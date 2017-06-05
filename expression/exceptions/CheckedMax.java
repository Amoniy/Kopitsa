package ru.itmo.ctddev.Kopitsa.expression.exceptions;

import ru.itmo.ctddev.Kopitsa.expression.TripleExpression;

public class CheckedMax extends CheckedOperation {
    public CheckedMax(TripleExpression x, TripleExpression y) {
        super(x, y);
    }

    @Override
    public int over(int x, int y) {
        int r = Math.max(x,y);
        return r;
    }
}
