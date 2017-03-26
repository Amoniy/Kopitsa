package ru.itmo.ctddev.Kopitsa.counter;

import ru.itmo.ctddev.Kopitsa.counter.*;

import java.sql.Connection;

public class Main {
    public static Register singleParse(String s) {
        int i = 0;
        boolean negative = false;
        if (s.charAt(0) == '-') {
            negative = true;
            s = s.substring(1);
        }
        while (i < s.length() && (Character.isDigit(s.charAt(i)) || s.charAt(i) == '.')) {
            i++;
        }
        double value = Double.parseDouble(s.substring(0, i));
        if (negative) {
            value = -value;
        }
        String restPart = s.substring(i);

        return new Register(value, restPart);
    }

    public static SuperExpression solve(String input, int x) {
        input = input.trim();
        int i = 0;
        if(input.charAt(i)=='-'){
            i++;
            if (input.charAt(i) != 'x') {
                while (i < input.length() && Character.isDigit(input.charAt(i))) {
                    i++;
                }
                if(i==input.length()){
                    return new Const(1);//nonono
                }
                String left;
                String right = input.substring(i);
                left = Integer.toString(new Const(Integer.parseInt(input.substring(0, i))*(-1)).evaluate(x));
                return solve(left + right, x);
            } else {//"x"
                i++;
                if(i==input.length()){
                    return new Variable("x");
                }
                String left;
                String right = input.substring(i );
                left = Integer.toString(new Variable("x").evaluate(x)*(-1));
                return solve(left + right, x);
            }
        }
        SuperExpression leftOperand = new Const(0);
        while (i < input.length()) {
            char first = input.charAt(i);
            switch (first) {
                case 'x': {
                    leftOperand = new Variable("x");
                    i++;
                    break;
                }
                case '-': {
                    return (new Add(leftOperand, solve(input.substring(i), x)));//if a[0]=='-' => unary minus
                }
                case '+': {
                    return (new Add(leftOperand, solve(input.substring(i + 1), x)));
                }
                case '/': {
                    i++;
                    input = input.substring(i);
                    input = input.trim();
                    i = 0;
                    if (input.charAt(i) != 'x') {
                        while (i < input.length() && Character.isDigit(input.charAt(i))) {
                            i++;
                        }
                        String left;
                        String right = input.substring(i);
                        left = Integer.toString(new Divide(leftOperand, new Const(Integer.parseInt(input.substring(0, i)))).evaluate(x));
                        return solve(left + right, x);
                    } else {//"x"
                        String left;
                        String right = input.substring(i + 1);
                        left = Integer.toString(new Divide(leftOperand, new Variable("x")).evaluate(x));
                        return solve(left + right, x);
                    }

                }
                case '*': {
                    return (new Multiply(leftOperand, solve(input.substring(i + 1), x)));
                }
                case '(': {
                    int balance = 1;
                    i++;
                    while (i < input.length() && balance > 0) {
                        if (input.charAt(i) == '(') {
                            balance++;
                        } else if (input.charAt(i) == ')') {
                            balance--;
                        }
                        i++;
                    }
                    leftOperand = solve(input.substring(1, i - 1), x);
                    if(i==input.length()){
                        return leftOperand;
                    }
                    input = input.substring(i);
                    input = input.trim();
                    Character operation=input.charAt(0);
                    switch (operation){
                        case '-': {
                            return (new Add(leftOperand, solve(input, x)));//if a[0]=='-' => unary minus
                        }
                        case '+': {
                            return (new Add(leftOperand, solve(input.substring(1), x)));
                        }
                        case '/': {
                            i=1;
                            input = input.substring(i);
                            input = input.trim();
                            i = 0;
                            if (input.charAt(i) != 'x') {
                                while (i < input.length() && Character.isDigit(input.charAt(i))) {
                                    i++;
                                }
                                String left;
                                String right = input.substring(i);
                                left = Integer.toString(new Divide(leftOperand, new Const(Integer.parseInt(input.substring(0, i)))).evaluate(x));
                                return solve(left + right, x);
                            } else {//"x"
                                String left;
                                String right = input.substring(i + 1);
                                left = Integer.toString(new Divide(leftOperand, new Variable("x")).evaluate(x));
                                return solve(left + right, x);
                            }

                        }
                        case '*': {
                            return (new Multiply(leftOperand, solve(input.substring(1), x)));
                        }
                    }
                }

                default: {//operand
                    while (i < input.length() && Character.isDigit(input.charAt(i))) {
                        i++;
                    }
                    leftOperand = new Const(Integer.parseInt(input.substring(0, i)));
                    break;
                }
            }
        }


        return leftOperand;//nonono
    }

    public static void main(String[] args) {
        /*System.out.println(new Subtract(new Multiply(new Const(2), new Variable("x")), new Const(3)).evaluate(5));
        System.out.println(new Variable("x").evaluate(1));
        System.out.println(new Subtract(new Const(7), new Const(5)).evaluate(99999));
        System.out.println(new Const(3).evaluate(1293213123));*/
        System.out.println(new Add(new Const(5), new Const(3)).evaluate(1));
        //System.out.println(new Multiply(new Variable("x"), new Const(-1000000000)).evaluate(x));

        System.out.println(new Add(new Add(new Const(5), new Variable("x")), new Variable("x")).evaluate(2));

        String test = "1+2+3+42+2*3*2+(2+3)";
        System.out.println(solve(test, 1).evaluate(2));
        String toParse = "";
        while (toParse.length() > 0) {
            //Register start=singleParse(input);
            //input=start.getString();
            toParse = toParse.trim();
            Const begin = new Const(0);


        }
    }
}
