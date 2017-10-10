package ru.itmo.ctddev.kopitsa.expression.generic;

public class GenericMod<T extends Number> extends GenericOperation<T> {
    public GenericMod(GenericExpression<T> x, GenericExpression<T> y) {
        super(x, y);
    }

    protected Type<T> over(Type<T> x, Type<T> y) {
        return x.mod(y);
    }
}