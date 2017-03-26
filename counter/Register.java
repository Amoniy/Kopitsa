package ru.itmo.ctddev.Kopitsa.counter;

public class Register {
    private double value;
    private String holder;

    public void setValue(double value) {
        this.value = value;
    }

    public void setString(String string) {
        this.holder = string;
    }

    public String getString() {
        return holder;
    }

    public double getValue() {
        return value;
    }

    public Register(double value, String input) {
        this.holder = input;
        this.value = value;
    }
}
