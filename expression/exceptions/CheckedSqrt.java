package ru.itmo.ctddev.Kopitsa.expression.exceptions;

import ru.itmo.ctddev.Kopitsa.expression.Const;
import ru.itmo.ctddev.Kopitsa.expression.TripleExpression;

public class CheckedSqrt extends CheckedOperation {
    public CheckedSqrt(TripleExpression x) {
        super(x, new Const(0));
    }

    @Override
    protected int over(int x, int y) {
        if (x < 0) {
            throw new SqrtArithmeticException(Integer.toString(x));
        }

        return sqrt(x);//(int) Math.sqrt(x);
    }

    private int sqrt(int x) {
        boolean decreased = false;
        int result = 1, nx;
        for (;;) {
            if (result == 0) {
                break;
            }
            nx = (result + x / result) >> 1;
            if (result == nx || nx > result && decreased) {
                break;
            }
            decreased = nx < result;
            result = nx;
        }
        return result;
    }
}
