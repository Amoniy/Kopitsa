package ru.itmo.ctddev.Kopitsa.expression.generic;

public class GenericSubtract<T extends Number> extends GenericOperation<T> {
    public GenericSubtract(GenericExpression<T> x, GenericExpression<T> y) {
        super(x, y);
    }

    public Type<T> over(Type<T> x, Type<T> y) {
        return x.subtract(y);
    }
}
