package ru.itmo.ctddev.kopitsa.expression.generic;

public abstract class GenericOperation<T extends Number> implements GenericExpression<T> {
    private GenericExpression<T> left, right;

    protected GenericOperation(GenericExpression<T> x, GenericExpression<T> y) {
        left = x;
        right = y;
    }

    public Type<T> evaluate(Type<T> x, Type<T> y, Type<T> z) {
        return over(left.evaluate(x, y, z), right.evaluate(x, y, z));
    }

    protected abstract Type<T> over(Type<T> x, Type<T> y);
}
