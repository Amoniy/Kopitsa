package ru.itmo.ctddev.kopitsa.expression.generic;

public class GenericAdd<T extends Number> extends GenericOperation<T> {
    public GenericAdd(GenericExpression<T> x, GenericExpression<T> y) {
        super(x, y);
    }

    public Type<T> over(Type<T> x, Type<T> y) {
        return x.add(y);
    }
}
