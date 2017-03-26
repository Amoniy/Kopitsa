package ru.itmo.ctddev.Kopitsa.counter;

import static ru.itmo.ctddev.Kopitsa.counter.Util.assertEquals;
import static ru.itmo.ctddev.Kopitsa.counter.Util.checkAssert;

/**
 * @author Georgiy Korneev (kgeorgiy@kgeorgiy.info)
 */
public class DoubleExpressionTest {
    public static void main(final String[] args) {
        checkAssert(DoubleExpressionTest.class);
        testExpression("10", new Const(10), x -> 10);
        testExpression("x", new Variable("x"), x -> x);
        testExpression("x+2", new Add(new Variable("x"), new Const(2)), x -> x + 2);
        testExpression("2-x", new Subtract(new Const(2), new Variable("x")), x -> 2 - x);
        testExpression("3*x", new Multiply(new Const(3), new Variable("x")), x -> 3 * x);
        testExpression("x/-2", new Divide(new Variable("x"), new Const(-2)), x -> -x / 2);
        testExpression(
                "x*x+(x-1)/10",
                new Add(
                        new Multiply(new Variable("x"), new Variable("x")),
                        new Divide(new Subtract(new Variable("x"), new Const(1)), new Const(10))
                ),
                x -> x * x + (x - 1) / 10
        );
        testExpression("x*-1_000_000_000", new Multiply(new Variable("x"), new Const(-1_000_000_000)), x -> x * -1_000_000_000.0);
        testExpression("10/x", new Divide(new Const(10), new Variable("x")), x -> 10.0 / x);
        testExpression("x/x", new Divide(new Variable("x"), new Variable("x")), x -> x / x);
        System.out.println("OK");
    }

    private static void testExpression(final String description, final DoubleExpression actual, final DoubleExpression expected) {
        System.out.println("Testing " + description);
        for (int i = 0; i < 10; i++) {
            assertEquals(String.format("f(%d)", i), actual.evaluate(i), expected.evaluate(i));
        }
    }
}