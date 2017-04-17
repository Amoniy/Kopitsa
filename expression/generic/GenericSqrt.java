package ru.itmo.ctddev.Kopitsa.expression.generic;

public class GenericSqrt<T extends Number> extends GenericOperation<T> {
    public GenericSqrt(GenericExpression<T> x) {
        super(x, x);
    }

    protected Type<T> over(Type<T> x, Type<T> y) {
        return x.sqrt();
    }
}