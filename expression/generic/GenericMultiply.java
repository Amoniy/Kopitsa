package ru.itmo.ctddev.Kopitsa.expression.generic;

public class GenericMultiply<T extends Number> extends GenericOperation<T> {
    public GenericMultiply(GenericExpression<T> x, GenericExpression<T> y) {
        super(x, y);
    }

    public Type<T> over(Type<T> x, Type<T> y) {
        return x.multiply(y);
    }
}
