package ru.itmo.ctddev.Kopitsa.expression.exceptions;

public class UnknownSymbolException extends ParserException{
    UnknownSymbolException(String input){
        super("Unknown symbol: "+input);
    }
}
