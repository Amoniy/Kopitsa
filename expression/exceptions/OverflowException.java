package ru.itmo.ctddev.Kopitsa.expression.exceptions;

public class OverflowException extends ArithmeticException{
    protected OverflowException(String input){
        super("Overflow in: "+input);
    }
}
