package ru.itmo.ctddev.Kopitsa.expression.generic;

public class GenericAbs<T extends Number> extends GenericOperation<T> {
    public GenericAbs(GenericExpression<T> x) {
        super(x, x);
    }

    protected Type<T> over(Type<T> x, Type<T> y) {
        return x.abs();
    }
}