package ru.itmo.ctddev.Kopitsa.counter;

import ru.itmo.ctddev.Kopitsa.counter.SuperExpression;

public class Variable implements SuperExpression {
    private final char variable;

    public Variable(String str) {
        this.variable = str.charAt(0);
    }

    @Override
    public int evaluate(int x) {
        return x;
    }

    @Override
    public double evaluate(double x) {
        return x;
    }

    @Override
    public int evaluate(int x, int y, int z) {
        switch (variable) {
            case 'x':
                return x;
            case 'y':
                return y;
            case 'z':
                return z;
        }
        return 0;
    }
}
