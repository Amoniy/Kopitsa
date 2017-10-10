package ru.itmo.ctddev.kopitsa.expression.generic;

public class GenericInteger extends GenericNumber<Integer> {
    public GenericInteger(Type<Integer> value) {
        super(value);
    }

    public GenericInteger(Integer value) {
        super(value);
    }

    public GenericInteger() {
        super();
    }
    //public GenericNumber<Integer> toGenericNumber(Type<Integer> x){

    //}
    public GenericNumber<Integer> parse(String str) {
        return new GenericInteger(Integer.parseInt(str));
    }

    public Type<Integer> add(Type<Integer> y) {
        return new GenericInteger(Math.addExact(getValue(), y.getValue()));
    }

    public Type<Integer> subtract(Type<Integer> y) {
        return new GenericInteger(Math.subtractExact(getValue(), y.getValue()));
    }

    public Type<Integer> multiply(Type<Integer> y) {
        return new GenericInteger(Math.multiplyExact(getValue(), y.getValue()));
    }

    public Type<Integer> divide(Type<Integer> y) {
        return new GenericInteger((getValue() / y.getValue()));
    }

    public Type<Integer> negate() {
        return new GenericInteger(Math.negateExact(getValue()));
    }

    public Type<Integer> abs() {
        return new GenericInteger(Math.abs(getValue()));
    }

    public Type<Integer> sqrt() {
        return new GenericInteger(getSqrt(getValue()));
    }

    private Integer getSqrt(int x) {
        boolean decreased = false;
        int result = 1, nx;
        for (; ; ) {
            if (result == 0) {
                break;
            }
            nx = (result + x / result) >> 1;
            if (result == nx || nx > result && decreased) {
                break;
            }
            decreased = nx < result;
            result = nx;
        }
        return result;
    }

    public Type<Integer> mod(Type<Integer> y) {
        return new GenericInteger(getValue() % y.getValue());
    }

    public Type<Integer> square() {
        return new GenericInteger(getValue() * getValue());
    }
}
