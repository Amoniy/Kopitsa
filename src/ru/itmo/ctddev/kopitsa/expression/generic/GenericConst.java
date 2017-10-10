package ru.itmo.ctddev.kopitsa.expression.generic;

public class GenericConst<T extends Number> implements GenericExpression<T> {
    Type<T> value;

    public GenericConst(Type<T> value) {
        this.value = value;
    }

    @Override
    public Type<T> evaluate(Type<T> x, Type<T> y, Type<T> z) {
        return value;
    }
}