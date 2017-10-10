package ru.itmo.ctddev.kopitsa.expression.generic;

public interface GenericExpression<T extends Number> {
    Type<T> evaluate(Type<T> x, Type<T> y, Type<T> z);
}