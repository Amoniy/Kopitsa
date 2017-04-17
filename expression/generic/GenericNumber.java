package ru.itmo.ctddev.Kopitsa.expression.generic;

public abstract class GenericNumber<T extends Number> implements Type<T> {
    T value;
    public GenericNumber(){}
    public GenericNumber(Type<T> value) {
        this.value = getValue();
    }

    public GenericNumber(T value) {
        this.value = value;
    }

    public T getValue() {
        return value;
    }
}
