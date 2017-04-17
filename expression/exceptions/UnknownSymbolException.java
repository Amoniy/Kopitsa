package ru.itmo.ctddev.Kopitsa.expression.exceptions;

public class UnknownSymbolException extends ParserException{
    public UnknownSymbolException(String input){
        super("Unknown symbol: "+input);
    }
}
