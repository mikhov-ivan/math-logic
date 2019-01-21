package task3;

import java.io.*;
import java.util.*;

public class Helper {

    public static BufferedReader in;
    public static PrintWriter out;
    protected static Lexer lexer = new Lexer();
    private static final Parser parser = new Parser();

    public static Expression parse(String s) {
        Expression expression = parseExcept(s);
        return expression;
    }

    public static Expression parseExcept(String s) {
        Expression expression;
        String[] lexems = lexer.determineLex(s);
        parser.s = s;
        expression = parser.parse(lexems);
        return expression;
    }

    public static List<Expression> proofAA(Expression alpha) {
        List<Expression> result = new ArrayList<>();
        for (AA e : AA.values()) {
            result.add(e.replace(alpha));
        }
        return result;
    }

    public static void toMps(Map<Expression, ArrayList<Expression>> mps, Expression e) {
        if (e instanceof Then) {
            if (!mps.containsKey(((Then) e).getRight())) {
                mps.put(((Then) e).getRight(), new ArrayList<Expression>(3));
            }
            List<Expression> exprs = mps.get(((Then) e).getRight());
            exprs.add(((Then) e).getLeft());
        }
    }

    public static boolean lowV(String temp) {
        return Character.isLowerCase(temp.charAt(0)) && cpm(temp);
    }

    public static boolean upV(String temp) {
        return Character.isUpperCase(temp.charAt(0)) && cpm(temp);
    }

    private static boolean cpm(String temp) {
        if (temp.length() > 1) {
            try {
                Integer.parseInt(temp.substring(1, temp.length()));
                return true;
            } catch (NumberFormatException e) {
                return false;
            }
        }
        return true;
    }

    public static boolean isVariable(String temp) {
        return Character.isLetter(temp.charAt(0)) && cpm(temp);
    }
}
