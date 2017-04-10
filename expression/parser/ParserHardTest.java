package ru.itmo.ctddev.Kopitsa.expression.parser;

import java.util.Arrays;

import static ru.itmo.ctddev.Kopitsa.expression.Util.*;

import ru.itmo.ctddev.Kopitsa.expression.Either;
import ru.itmo.ctddev.Kopitsa.expression.ExpressionParser;
import ru.itmo.ctddev.Kopitsa.expression.TripleExpression;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;
import java.util.function.UnaryOperator;

/**
 * @author Georgiy Korneev
 */
public class ParserHardTest extends ParserShiftsTest {
    protected ParserHardTest() {
        unary.add(op(" abs ", Math::abs));
        unary.add(op(" square ", a -> a * a));

        tests.addAll(Arrays.asList(
                op("abs -5", (x, y, z) -> 5L),
                op("abs (x - y)", (x, y, z) -> Math.abs(x - y)),
                op("x - abs -y", (x, y, z) -> x - Math.abs(-y)),
                op("abs -x", (x, y, z) -> Math.abs(-x)),
                op("abs(x+y)", (x, y, z) -> Math.abs(x + y)),
                op("square -5", (x, y, z) -> 25L),
                op("square (x - y)", (x, y, z) -> (x - y) * (x - y)),
                op("x - square y", (x, y, z) -> x - y * y),
                op("square -x", (x, y, z) -> x * x),
                op("square(x+y)", (x, y, z) -> (x + y) * (x + y))
        ));
    }

    public static void main(final String[] args) {
        checkAssert(ParserHardTest.class);
        new ParserHardTest().run();
    }
}