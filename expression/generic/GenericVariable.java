package ru.itmo.ctddev.Kopitsa.expression.generic;

public class GenericVariable<T extends Number> implements GenericExpression<T> {
    char variable;

    public GenericVariable(String var) {
        this.variable = var.charAt(0);
    }

    @Override
    public Type<T> evaluate(Type<T> x, Type<T> y, Type<T> z) {
        switch (variable) {
            case 'x':
                return x;
            case 'y':
                return y;
            case 'z':
                return z;
            default:
                return null;
        }
    }
}