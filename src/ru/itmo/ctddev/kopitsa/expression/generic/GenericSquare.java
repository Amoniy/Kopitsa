package ru.itmo.ctddev.kopitsa.expression.generic;

public class GenericSquare<T extends Number> extends GenericOperation<T> {
    public GenericSquare(GenericExpression<T> x) {
        super(x, x);
    }

    protected Type<T> over(Type<T> x, Type<T> y) {
        return x.square();
    }
}
