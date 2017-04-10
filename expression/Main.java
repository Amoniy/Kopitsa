package ru.itmo.ctddev.Kopitsa.expression;

public class Main {
    public static void main(String[] args) {
        /*System.out.println(new Subtract(new Multiply(new Const(2), new Variable("x")), new Const(3)).evaluate(5));
        System.out.println(new Variable("x").evaluate(1));
        System.out.println(new Subtract(new Const(7), new Const(5)).evaluate(99999));
        System.out.println(new Const(3).evaluate(1293213123));*/

        System.out.println(new Add(new Const(5),new Const(3)).evaluate(1));
        //System.out.println(new Multiply(new Variable("x"), new Const(-1000000000)).evaluate(x));
        //int x = Integer.parseInt(args[0]);
        int x=3;
        System.out.println(new Add(new Subtract(new Multiply(new Variable("x"), new Variable("x")), new Multiply(new Const(2), new Variable("x"))), new Const(1)).evaluate(x));
    }
}
