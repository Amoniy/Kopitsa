package ru.itmo.ctddev.Kopitsa.expression.generic;

public class GenericDouble extends GenericNumber<Double> {
    public GenericDouble(Double value) {
        super(value);
    }
    public GenericDouble(Type<Double> value) {
        super(value);
    }
    public GenericDouble(){super();}

    public GenericNumber<Double> parse(String str) {
        return new GenericDouble(Double.parseDouble(str));
    }

    public Type<Double> add(Type<Double> y) {
        return new GenericDouble(getValue() + y.getValue());
    }

    public Type<Double> subtract(Type<Double> y) {
        return new GenericDouble(getValue() - y.getValue());
    }

    public Type<Double> multiply(Type<Double> y) {
        return new GenericDouble(getValue() * y.getValue());
    }

    public Type<Double> divide(Type<Double> y) {
        return new GenericDouble(getValue() / y.getValue());
    }

    public Type<Double> negate() {
        return new GenericDouble(-getValue());
    }

    public Type<Double> abs() {
        return new GenericDouble(Math.abs(getValue()));
    }

    public Type<Double> sqrt() {
        return new GenericDouble(Math.sqrt(getValue()));
    }
    public Type<Double> mod(Type<Double> y) {
        return new GenericDouble(getValue() % y.getValue());
    }
    public Type<Double> square(){
        return new GenericDouble(getValue()*getValue());
    }
}
