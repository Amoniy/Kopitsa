package ru.itmo.ctddev.Kopitsa.expression;

public abstract class Operation implements SuperExpression {
    private SuperExpression left, right;

    protected Operation(SuperExpression x, SuperExpression y) {
        left = x;
        right = y;
    }

    public int evaluate(int x) {
        return over(left.evaluate(x), right.evaluate(x));
    }

    public double evaluate(double x) {
        return over(left.evaluate(x), right.evaluate(x));
    }

    public int evaluate(int x, int y, int z) {
        return over(left.evaluate(x, y, z), right.evaluate(x, y, z));
    }

    protected abstract int over(int x, int y);

    protected abstract double over(double x, double y);
}
