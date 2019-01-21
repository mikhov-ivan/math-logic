package task4;

import java.util.*;

public class Parser {
    protected int lookBack; 
    protected String[] parts;
    public String s;
    protected int pos = 0;

    public Expression parse(String[] parts) throws MyException {
        this.parts = parts;
        this.pos = 0;
        return expr();
    }

    public Expression parse(String s) throws MyException {
        return null;
    }

    protected Expression expr() throws MyException {
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
                result = new Implication(arr.get(i), result);
                i--;
            }
        }
        return result;
    }

    protected Expression disjunction() throws MyException {
        Expression result = conjunction();
        while (pos < parts.length && parts[pos].equals(Lexeme.OR.s)) {
            pos++;
            result = new Or(result, conjunction());
        }
        return result;
    }

    protected Expression conjunction() throws MyException {
        Expression result = unaryOperator();
        while (pos < parts.length && parts[pos].equals(Lexeme.AND.s)) {
            pos++;
            result = new And(result, unaryOperator());
        }
        return result;
    }

    protected Variable var() {
        Variable result = new Variable(parts[pos]);
        pos++;
        return result;
    }
    
    protected Expression unaryOperator() throws MyException {
        Expression result;
        if (parts[pos].equals(Lexeme.NOT.s)) {
            pos++;
            result = new Not(unaryOperator());
            return result;
        }
        if (parts[pos].equals(Lexeme.FOR_ALL.s)) {
            pos++;
            Term var = null;
            if (Helper.lowV(parts[pos])) {
                var = new Term(parts[pos]);
                pos++;
            } else {
                throw new MyException();
            }
            result = new ForAll(var, unaryOperator());
            return result;
        }
        if (parts[pos].equals(Lexeme.EXISTS.s)) {
            pos++;
            Term var;
            if (Helper.lowV(parts[pos])) {
                var = term();
            } else {
                throw new MyException();
            }
            result = new Exists(var, unaryOperator());
            return result;
        }
        if (parts[pos].equals(Lexeme.LEFT_B.s)) {
            int backupPos = pos;
            try {
                pos++;

                result = expr();
                if (!parts[pos].equals(Lexeme.RIGHT_B.s)) {
                    StringBuilder sb = new StringBuilder();
                    for (String s : parts) {
                        sb.append(s);
                    }
                    throw new MyException();
                } else {
                    pos++;
                }
                return result;
            } catch (MyException e) {
                pos = backupPos;
                return predicate();
            }
        }
        return predicate();
    }

    protected Predicate predicate() throws MyException {
        if (Helper.upV(parts[pos])) {
            Predicate result;
            result = new Predicate(parts[pos]);
            pos++;
            if (pos < parts.length && parts[pos].equals(Lexeme.LEFT_B.s)) {
                pos++;
                List<Term> arguments = new ArrayList<>(3);
                arguments.add(term());
                while (parts[pos].equals(Lexeme.COMMA.s)) {
                    pos++;
                    arguments.add(term());
                }
                pos++;
                result.setArgs(arguments.toArray(new Term[arguments.size()]));
            } else {
                result = new Variable(parts[pos - 1]);
            }
            return result;
        } else {
            throw new MyException();
        }
    }

    protected Term term() throws MyException {
        Term result;
        boolean f = false;
        if (parts[pos].equals(Lexeme.LEFT_B.s)) {
            pos++;
            result = term();
        } else if (Helper.lowV(parts[pos])) {
            result = new Term(parts[pos]);
            pos++;
            f = true;
            if (pos < parts.length && parts[pos].equals(Lexeme.LEFT_B.s)) {
                lookBack = pos;
                pos++;
                List<Term> arguments = new ArrayList<>(3);
                boolean success = true;
                try {
                    arguments.add(term());
                } catch (MyException e) {
                    pos = lookBack;
                    success = false;
                }
                if (success) {
                    while (parts[pos].equals(Lexeme.COMMA.s)) {
                        pos++;
                        arguments.add(term());
                    }
                    result.setArgs(arguments.toArray(new Term[arguments.size()]));
                    pos++;
                }
            }
        } else {
            throw new MyException();
        }

        if (!f) {
            pos++;
        }
        return result;
    }
}
