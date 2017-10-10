package ru.itmo.ctddev.kopitsa.expression.exceptions;

import ru.itmo.ctddev.kopitsa.expression.TripleExpression;

public class CheckedMax extends CheckedOperation {
    public CheckedMax(TripleExpression x, TripleExpression y) {
        super(x, y);
    }

    @Override
    public int over(int x, int y) {
        int r = Math.max(x, y);
        return r;
    }
}
