package ru.itmo.ctddev.Kopitsa.expression;

public class Const implements SuperExpression {
    private final int value;

    public Const(int value) {
        this.value = value;
    }

    @Override
    public double evaluate(double x) {
        return value;
    }

    @Override
    public int evaluate(int x) {
        return value;
    }

    @Override
    public int evaluate(int x, int y, int z) {
        return value;
    }
}
