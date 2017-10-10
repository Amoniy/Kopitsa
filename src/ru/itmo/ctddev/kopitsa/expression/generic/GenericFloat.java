package ru.itmo.ctddev.kopitsa.expression.generic;


public class GenericFloat extends GenericNumber<Float> {
    public GenericFloat(Type<Float> value) {
        super(value);
    }

    public GenericFloat(Float value) {
        super(value);
    }

    public GenericFloat() {
        super();
    }
    //public GenericNumber<Float> toGenericNumber(Type<Float> x){

    //}
    public GenericNumber<Float> parse(String str) {
        return new GenericFloat(Float.parseFloat(str));
    }

    public Type<Float> add(Type<Float> y) {
        return new GenericFloat((getValue() + y.getValue()));
    }

    public Type<Float> subtract(Type<Float> y) {
        return new GenericFloat((getValue() - y.getValue()));
    }

    public Type<Float> multiply(Type<Float> y) {
        return new GenericFloat((getValue() * y.getValue()));
    }

    public Type<Float> divide(Type<Float> y) {
        return new GenericFloat((getValue() / y.getValue()));
    }

    public Type<Float> negate() {
        return new GenericFloat(-(getValue()));
    }

    public Type<Float> abs() {
        return new GenericFloat(Math.abs(getValue()));
    }

    public Type<Float> sqrt() {

        return new GenericFloat((float) Math.sqrt(getValue()));
    }

    /*private Float getSqrt(float x) {
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
    }*/

    public Type<Float> mod(Type<Float> y) {
        return new GenericFloat(getValue() % y.getValue());
    }

    public Type<Float> square() {
        return new GenericFloat(getValue() * getValue());
    }
}
