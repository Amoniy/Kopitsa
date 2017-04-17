package ru.itmo.ctddev.Kopitsa.expression.generic;

public class GenericDivide<T extends Number> extends GenericOperation<T> {
    public GenericDivide(GenericExpression<T> x, GenericExpression<T> y) {
        super(x, y);
    }

    public Type<T> over(Type<T> x, Type<T> y) {
        return x.divide(y);
    }
}
