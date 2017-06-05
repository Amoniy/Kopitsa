package ru.itmo.ctddev.Kopitsa.expression.exceptions;

import ru.itmo.ctddev.Kopitsa.expression.TripleExpression;

public class CheckedMin extends CheckedOperation {
    public CheckedMin(TripleExpression x, TripleExpression y) {
        super(x, y);
    }

    @Override
    public int over(int x, int y) {
        int r = Math.min(x,y);
        return r;
    }
}
