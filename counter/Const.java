package ru.itmo.ctddev.Kopitsa.counter;

import ru.itmo.ctddev.Kopitsa.counter.SuperExpression;

public class Const implements SuperExpression {
    private final double value;

    public Const(int value) {
        this.value = value;
    }

    public Const(double value) {
        this.value = value;
    }


    @Override
    public double evaluate(double x) {
        return value;
    }

    @Override
    public int evaluate(int x) {
        return (int) value;
    }

    @Override
    public int evaluate(int x, int y, int z) {
        return (int) value;
    }
}
