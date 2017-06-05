package ru.itmo.ctddev.Kopitsa.expression.generic;

public class GenericByte extends GenericNumber<Byte> {
    public GenericByte(Type<Byte> value) {
        super(value);
    }

    public GenericByte(Byte value) {
        super(value);
    }

    public GenericByte() {
        super();
    }
    //public GenericNumber<Byte> toGenericNumber(Type<Byte> x){

    //}
    public GenericNumber<Byte> parse(String str) {
        return new GenericByte(Byte.parseByte(str));
    }

    public Type<Byte> add(Type<Byte> y) {
        return new GenericByte((byte)(getValue()+ y.getValue()));
    }

    public Type<Byte> subtract(Type<Byte> y) {
        return new GenericByte((byte)(getValue()- y.getValue()));
    }

    public Type<Byte> multiply(Type<Byte> y) {
        return new GenericByte((byte)(getValue()* y.getValue()));
    }

    public Type<Byte> divide(Type<Byte> y) {
        return new GenericByte((byte)(getValue() / y.getValue()));
    }

    public Type<Byte> negate() {
        return new GenericByte((byte)-(getValue()));
    }

    public Type<Byte> abs() {
        return new GenericByte((byte)Math.abs(getValue()));
    }

    public Type<Byte> sqrt() {
        return new GenericByte(getSqrt(getValue()));
    }

    private Byte getSqrt(byte x) {
        boolean decreased = false;
        byte result = 1, nx;
        for (; ; ) {
            if (result == 0) {
                break;
            }
            nx = (byte)((result + x / result) >> 1);
            if (result == nx || nx > result && decreased) {
                break;
            }
            decreased = nx < result;
            result = nx;
        }
        return result;
    }

    public Type<Byte> mod(Type<Byte> y) {
        return new GenericByte((byte)(getValue() % y.getValue()));
    }

    public Type<Byte> square() {
        return new GenericByte((byte)(getValue() * getValue()));
    }
}