package ru.itmo.ctddev.Kopitsa.counter;

import ru.itmo.ctddev.Kopitsa.counter.SuperExpression;

public class Divide extends Operation {
    public Divide(SuperExpression x, SuperExpression y) {
        super(x, y);
    }

    @Override
    public int over(int x, int y) {
        return x / y;
    }

    @Override
    public double over(double x, double y) {
        return x / y;
    }
}
