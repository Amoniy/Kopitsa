package ru.itmo.ctddev.kopitsa.expression.exceptions;

import ru.itmo.ctddev.kopitsa.expression.TripleExpression;

public class CheckedMin extends CheckedOperation {
    public CheckedMin(TripleExpression x, TripleExpression y) {
        super(x, y);
    }

    @Override
    public int over(int x, int y) {
        int r = Math.min(x, y);
        return r;
    }
}
