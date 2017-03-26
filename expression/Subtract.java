package ru.itmo.ctddev.Kopitsa.expression;

public class Subtract extends Operation {
    public Subtract(SuperExpression x, SuperExpression y) {
        super(x, y);
    }

    @Override
    public int over(int x, int y) {
        return x - y;
    }

    @Override
    public double over(double x, double y) {
        return x - y;
    }
}
