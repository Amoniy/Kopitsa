package ru.itmo.ctddev.kopitsa.expression;

public class ShiftLeft extends Operation {
    public ShiftLeft(SuperExpression x, SuperExpression y) {
        super(x, y);
    }

    public int over(int x, int y) {
        return x << y;
    }

    @Override
    public double over(double x, double y) {
        return ((int) x << (int) y);
    }

}
