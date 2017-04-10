package ru.itmo.ctddev.Kopitsa.expression;

/**
 * Created by Anton on 29.03.2017.
 */
public class Abs extends Operation {
    public Abs(SuperExpression x, SuperExpression y) {
        super(x, y);
    }

    public int over(int x, int y) {
        if (x >= 0) {
            return x;
        } else {
            return -x;
        }
    }

    @Override
    public double over(double x, double y) {
        if (x >= 0) {
            return x;
        } else {
            return -x;
        }
    }
}
