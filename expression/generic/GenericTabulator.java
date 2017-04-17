package ru.itmo.ctddev.Kopitsa.expression.generic;

public class GenericTabulator implements Tabulator {
    public Object[][][] tabulate(String mode, String expression, int x1, int x2, int y1, int y2, int z1, int z2) throws Exception {
        GenericParser parser = new GenericParser(mode);
        GenericExpression exp = parser.parse(expression);
        Object[][][] ans = new Object[x2 - x1 + 1][y2 - y1 + 1][z2 - z1 + 1];
        for (int i = 0; i <= x2 - x1; i++) {
            for (int j = 0; j <= y2 - y1; j++) {
                for (int k = 0; k <= z2 - z1; k++) {
                    Type x = parser.getType().parse(Integer.toString(x1 + i));
                    Type y = parser.getType().parse(Integer.toString(y1 + j));
                    Type z = parser.getType().parse(Integer.toString(z1 + k));
                    try {
                        ans[i][j][k] = exp.evaluate(x, y, z).getValue();
                    } catch (ArithmeticException ex) {
                        ans[i][j][k] = null;
                    }
                }
            }
        }
        return ans;
    }
}
