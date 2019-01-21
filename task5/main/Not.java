package task5;

import java.util.*;

public class Not implements Expression {

    private final Expression expr;

    public Not(Expression expr) {
        this.expr = expr;
    }

    public Expression getExpression() {
        return expr;
    }

    @Override
    public boolean calc(ArrayList<String> trueV) {
        return !expr.calc(trueV);
    }

    @Override
    public String toString() {
        if (expr instanceof Variable || expr instanceof Exists || expr instanceof ForAll || expr instanceof Predicate) {
            return "!" + expr.toString();
        } else {
            return "!(" + expr.toString() + ")";
        }
    }

    @Override
    public ArrayList<Variable> freeVars(ArrayList<Variable> used) {
        ArrayList<Variable> tmp = new ArrayList<>();
        tmp.addAll(expr.freeVars(used));
        return tmp;
    }

    @Override
    public ArrayList<Variable> used() {
        ArrayList<Variable> tmp = new ArrayList<>();
        tmp.addAll(expr.used());
        return tmp;
    }

    @Override
    public ArrayList<Variable> allVars() {
        ArrayList<Variable> tmp = new ArrayList<>();
        tmp.addAll(expr.allVars());
        return tmp;
    }

    @Override
    public Pair<Expression, Expression> diff(Expression e) {
        if (e instanceof Not) {
            return this.getExpression().diff(((Not) e).getExpression());
        }
        return new Pair(this, e);
    }

    @Override
    public Expression replace(Expression instead, Expression newExpr) {
        if (this.equals(instead)) {
            return newExpr;
        }
        return new Not(expr.replace(instead, newExpr));
    }

    @Override
    public boolean equals(Object object) {
        if (object instanceof Not) {
            return this.getExpression().equals(((Not) object).getExpression());
        } else {
            return false;
        }
    }
}
