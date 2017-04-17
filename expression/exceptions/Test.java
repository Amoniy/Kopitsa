package ru.itmo.ctddev.Kopitsa.expression.exceptions;


public class Test {
    public static void main(String[] args) throws Exception {
        String input = "1+sqrt 4";
        System.out.println(input);
        System.out.println(new ExpressionParser().parse(input).evaluate(1, 1, 0));

    }
}