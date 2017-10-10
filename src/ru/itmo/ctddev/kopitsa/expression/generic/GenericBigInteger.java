package ru.itmo.ctddev.kopitsa.expression.generic;

import java.math.BigInteger;

public class GenericBigInteger extends GenericNumber<BigInteger> {
    public GenericBigInteger(BigInteger value) {
        super(value);
    }

    public GenericBigInteger(Type<BigInteger> value) {
        super(value);
    }

    public GenericBigInteger() {
        super();
    }

    public GenericNumber<BigInteger> parse(String str) {
        return new GenericBigInteger(new BigInteger(str));
    }

    public Type<BigInteger> add(Type<BigInteger> y) {
        return new GenericBigInteger(getValue().add(y.getValue()));
    }

    public Type<BigInteger> subtract(Type<BigInteger> y) {
        return new GenericBigInteger(getValue().subtract(y.getValue()));
    }

    public Type<BigInteger> multiply(Type<BigInteger> y) {
        return new GenericBigInteger(getValue().multiply(y.getValue()));
    }

    public Type<BigInteger> divide(Type<BigInteger> y) {
        return new GenericBigInteger(getValue().divide(y.getValue()));
    }

    public Type<BigInteger> negate() {
        return new GenericBigInteger(getValue().negate());
    }

    public Type<BigInteger> abs() {
        return new GenericBigInteger(getValue().abs());
    }

    public Type<BigInteger> sqrt() {
        return new GenericBigInteger(getSqrt(getValue()));
    }

    private BigInteger getSqrt(BigInteger x) {
        BigInteger a = BigInteger.ONE;
        BigInteger b = new BigInteger(x.shiftRight(5).add(new BigInteger("8")).toString());
        while (b.compareTo(a) >= 0) {
            BigInteger mid = new BigInteger(a.add(b).shiftRight(1).toString());
            if (mid.multiply(mid).compareTo(x) > 0) b = mid.subtract(BigInteger.ONE);
            else a = mid.add(BigInteger.ONE);
        }
        return a.subtract(BigInteger.ONE);
    }

    public Type<BigInteger> mod(Type<BigInteger> y) {
        return new GenericBigInteger(BigInteger.valueOf(new GenericInteger(getValue().intValue()).mod(new GenericInteger(y.getValue().intValue())).getValue()));
    }

    public Type<BigInteger> square() {
        return new GenericBigInteger(getValue().multiply(getValue()));
    }
}
