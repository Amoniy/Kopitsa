package ru.itmo.ctddev.kopitsa.expression;

/**
 * Created by Anton on 29.03.2017.
 */
public class Square extends Operation {
    public Square(SuperExpression x, SuperExpression y) {
        super(x, y);
    }

    public int over(int x, int y) {
        return x * x;
    }

    @Override
    public double over(double x, double y) {
        return x * x;
    }
}
