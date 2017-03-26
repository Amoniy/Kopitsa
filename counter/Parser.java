package ru.itmo.ctddev.Kopitsa.counter;

public class Parser  {

   /* private static Register tryAdding(Register input) {
        Register result = tryMultiplying(input);


        return new Register(0, "");
    }

    private static Register tryMultiplying(Register input) {
        Register result = tryOpenBracket(input);


        return new Register(0, "");
    }

    private static Register tryOpenBracket(Register input) {
        Register result = tryCloseBracket(input);
        if (input.getString().charAt(0) == '(') {
            //tryUnaryMinus(input);
            result.setString(result.getString().substring(1));
            Register r = tryAdding(result);
            return r;
        }
        return singleParse(input);
    }

    private static Register tryUnaryMinus(String input) {
        input = input.trim();
        if (input.charAt(0) == '-') {

        }


        return new Register(0, "");
    }

    private static Register tryCloseBracket(Register input) {
        //Register result = tryUnaryMinus(input);


        if (input.getString().charAt(0) == ')') {
            input.setString(input.getString().substring(1));
        }
        return input;
    }

    private double value = 0;

    public double evaluate(double x) {
        return value;
    }

    public int evaluate(int x) {
        return (int) value;
    }

    public int evaluate(int x, int y, int z) {
        return (int) value;
    }

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
        double value;
        if(i>0) {
            value = Double.parseDouble(s.substring(0, i));
        }
        else {
            value=0;
        }
        if (negative) {
            value = -value;
        }
        String restPart = s.substring(i);

        return new Register(value, restPart);
    }

    public double parse(String input) {
        Register start = singleParse(input);
        input = start.getString();
        value = start.getValue();
        Const begin=new Const(value);
        if(!(input.length()==0)){
            begin=tryAdding(input,)
        }

        //0 - begin
        //1 - +
        //2 - *
        //3 - (

        value=begin.evaluate(1);
        return value;
    }*/
}
