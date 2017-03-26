package ru.itmo.ctddev.Kopitsa.expression;

public class Add extends Operation {
    public Add(SuperExpression x, SuperExpression y) {
        super(x, y);
    }

    public int over(int x, int y) {
        return x + y;
    }

    @Override
    public double over(double x, double y) {
        return x + y;
    }
}
