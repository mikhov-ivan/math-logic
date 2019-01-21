package task5;

import java.util.*;

public abstract class Quantifier implements Expression {

    protected Variable variable;
    protected Expression expression;

    protected Quantifier(Variable v, Expression e) {
        this.variable = v;
        this.expression = e;
    }

    @Override
    public String toString() {
        return variable.toString() + "(" + expression.toString() + ")";
    }

    public Variable getVariable() {
        return variable;
    }

    public Expression getExpression() {
        return expression;
    }

    @Override
    public ArrayList<Variable> freeVars(ArrayList<Variable> used) {
        ArrayList<Variable> tmp = new ArrayList<>();
        boolean isUsed = used.contains(variable);
        if (!isUsed) {
            used.add(variable);
        }
        tmp.addAll(expression.freeVars(used));
        if (!isUsed) {
            used.remove(variable);
        }
        return tmp;
    }

    @Override
    public ArrayList<Variable> used() {
        ArrayList<Variable> tmp = new ArrayList<>();
        tmp.add(variable);
        return tmp;
    }

    @Override
    public ArrayList<Variable> allVars() {
        ArrayList<Variable> tmp = new ArrayList<>();
        tmp.add(variable);
        tmp.addAll(expression.allVars());
        return tmp;
    }

    @Override
    public Pair<Expression, Expression> diff(Expression expression) {
        if (expression instanceof Quantifier) {
            Pair<Expression, Expression> diff = this.getVariable().diff(((Quantifier) expression).getVariable());
            if (diff != null) {
                return diff;
            }
            return this.getExpression().diff(((Quantifier) expression).getExpression());
        }
        return new Pair(this, expression);
    }

    @Override
    public boolean equals(Object object) {
        if (object instanceof Quantifier) {
            Quantifier q = (Quantifier) object;
            return expression.equals(q.getExpression()) && variable.equals(q.getVariable());
        }
        return false;
    }
}
