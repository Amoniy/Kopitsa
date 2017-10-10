package ru.itmo.ctddev.kopitsa.expression;

/**
 * Created by Anton on 29.03.2017.
 */
public class ShiftRight extends Operation {
    public ShiftRight(SuperExpression x, SuperExpression y) {
        super(x, y);
    }

    public int over(int x, int y) {
        return x >> y;
    }

    @Override
    public double over(double x, double y) {
        return ((int) x >> (int) y);
    }


}
