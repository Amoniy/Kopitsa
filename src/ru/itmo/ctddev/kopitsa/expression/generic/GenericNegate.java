package ru.itmo.ctddev.kopitsa.expression.generic;

public class GenericNegate<T extends Number> extends GenericOperation<T> {
    public GenericNegate(GenericExpression<T> x) {
        super(x, x);
    }

    public Type<T> over(Type<T> x, Type<T> y) {
        return x.negate();
    }
}
