package task4;

import java.io.*;
import java.util.*;

public class Helper {
    public static BufferedReader input;
    public static PrintWriter output;
    protected static Lexer lexer = new Lexer();
    private static Parser parser = new Parser();

    public static void setParser(Parser parser) {
        Helper.parser = parser;
    }

    public static Expression parse(String s) {
        Expression expression = null;
        try {
            expression = parseExcept(s);
        } catch (Exception e) {
            System.out.println("error");
            System.exit(1);
        }
        return expression;
    }

    public static Expression parseExcept(String s) throws Exception {
        Expression expression;
        String[] lexems = lexer.lex(s);
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
        if (e instanceof Implication) {
            if (!mps.containsKey(((Implication) e).getRight())) {
                mps.put(((Implication) e).getRight(), new ArrayList<Expression>(3));
            }
            List<Expression> exprs = mps.get(((Implication) e).getRight());
            exprs.add(((Implication) e).getLeft());
        }
    }

    public static boolean lowV(String tmp) {
        return Character.isLowerCase(tmp.charAt(0)) && similarTo(tmp);
    }

    public static boolean upV(String tmp) {
        return Character.isUpperCase(tmp.charAt(0)) && similarTo(tmp);
    }

    public static boolean isVariable(String tmp) {
        return Character.isLetter(tmp.charAt(0)) && similarTo(tmp);
    }

    private static boolean similarTo(String tmp) {
        if (tmp.length() > 1) {
            try {
                Integer.parseInt(tmp.substring(1, tmp.length()));
                return true;
            } catch (NumberFormatException e) {
                return false;
            }
        }
        return true;
    }

    public static void exitWithMessage(String s) {
        output.println(s);
        output.close();
        System.exit(1);
    }
}
