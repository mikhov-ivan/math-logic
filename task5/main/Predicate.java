package task5;

import java.util.*;

public class Predicate implements Expression {

    private final String name;
    private final ArrayList<Expression> terms;

    public Predicate(String name, ArrayList<Expression> terms) {
        this.name = name;
        this.terms = terms;
    }

    @Override
    public boolean calc(ArrayList<String> trueV) {
        return true;
    }

    @Override
    public String toString() {
        String s = name;
        if (terms != null && !terms.isEmpty()) {
            s += "(";
        }
        if (terms != null) {
            for (int i = 0; i < terms.size(); i++) {
                s += terms.get(i);
                if (i < terms.size() - 1) {
                    s += ",";
                }
            }
        }
        if (terms != null && !terms.isEmpty()) {
            s += ")";
        }
        return s;
    }

    @Override
    public ArrayList<Variable> freeVars(ArrayList<Variable> used) {
        ArrayList<Variable> tmp = new ArrayList<>();
        for (Expression term : terms) {
            tmp.addAll(term.freeVars(used));
        }
        return tmp;
    }

    @Override
    public ArrayList<Variable> used() {
        ArrayList<Variable> tmp = new ArrayList<>();
        for (Expression term : terms) {
            tmp.addAll(term.used());
        }
        return tmp;
    }

    @Override
    public ArrayList<Variable> allVars() {
        ArrayList<Variable> tmp = new ArrayList<>();
        for (Expression term : terms) {
            tmp.addAll(term.allVars());
        }
        return tmp;
    }

    @Override
    public Pair<Expression, Expression> diff(Expression expression) {
        if (expression instanceof Predicate) {
            if (name.equals(((Predicate) expression).name) == false) {
                new Pair(this, expression);
            }
            ArrayList<Expression> expressionTerms = ((Predicate) expression).getTerms();
            if (terms.size() != expressionTerms.size()) {
                return new Pair(this, expression);
            }
            for (int i = 0; i < terms.size(); i++) {
                Pair<Expression, Expression> difference = terms.get(i).diff(expressionTerms.get(i));
                if (difference != null) {
                    return difference;
                }
            }
            return null;
        }
        return new Pair(this, expression);
    }

    @Override
    public Expression replace(Expression e, Expression newE) {
        if (this.equals(e)) {
            return newE;
        }
        ArrayList<Expression> expressionTerms = new ArrayList<>();
        for (Expression term : terms) {
            expressionTerms.add(term.replace(e, newE));
        }
        return new Predicate(name, expressionTerms);
    }

    @Override
    public boolean equals(Object object) {
        if (object instanceof Predicate) {
            Predicate predicate = (Predicate) object;
            boolean flag = name.equals(predicate.getName()) && (terms.size() == predicate.getTerms().size());
            for (int i = 0; i < terms.size() && flag; i++) {
                if (!terms.get(i).equals(predicate.getTerms().get(i))) {
                    flag = false;
                }
            }
            return flag;
        }
        return false;
    }

    public ArrayList<Expression> getTerms() {
        return terms;
    }

    public String getName() {
        return name;
    }
}
