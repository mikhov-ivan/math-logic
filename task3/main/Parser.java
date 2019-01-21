package task3;

import java.util.*;

public class Parser {

    protected int pos = 0;
    protected String[] parts;
    public String s;

    public Expression parse(String[] tokens) {
        this.parts = tokens;
        this.pos = 0;
        return expr();
    }

    protected Expression expr() {
        Expression result = disjunction();
        ArrayList<Expression> arr = new ArrayList<>();
        arr.add(result);
        while (pos < parts.length && parts[pos].equals(Lexeme.THEN.s)) {
            pos++;
            Expression expr = disjunction();
            arr.add(expr);
        }
        if (arr.size() > 1) {
            result = arr.get(arr.size() - 1);
            int i = arr.size() - 2;
            while (i >= 0) {
                result = new Then(arr.get(i), result);
                i--;
            }
        }
        return result;
    }

    protected Expression disjunction() {
        Expression result = conjunction();
        while (pos < parts.length && parts[pos].equals(Lexeme.OR.s)) {
            pos++;
            result = new Or(result, conjunction());
        }
        return result;
    }

    protected Expression conjunction() {
        Expression result = unaryOperator();
        while (pos < parts.length && parts[pos].equals(Lexeme.AND.s)) {
            pos++;
            result = new And(result, unaryOperator());
        }
        return result;
    }

    protected Variable variable() {
        Variable result = new Variable(parts[pos]);
        pos++;
        return result;
    }

    protected Expression unaryOperator() {
        if (Helper.upV(parts[pos])) {
            return variable();
        }
        if (parts[pos].equals(Lexeme.LEFT_B.s)) {
            pos++;

            Expression result = expr();
            if (!parts[pos].equals(Lexeme.RIGHT_B.s)) {
                StringBuilder sb = new StringBuilder();
                for (String st : parts) {
                    sb.append(st);
                }
            } else {
                pos++;
            }
            return result;
        }

        if (parts[pos].equals(Lexeme.NOT.s)) {
            pos++;
            return new Not(unaryOperator());
        }
        return null;
    }

}
