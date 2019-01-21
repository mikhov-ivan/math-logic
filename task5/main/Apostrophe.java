package task5;

import java.util.ArrayList;
import java.util.Objects;

public class Apostrophe implements Expression {

    private final Expression expression;

    public Apostrophe(Expression expression) {
        this.expression = expression;
    }

    public Expression getExpression() {
        return expression;
    }

    @Override
    public boolean calc(ArrayList<String> trueV) {
        return trueV.contains(expression);
    }

    @Override
    public ArrayList<Variable> allVars() {
        return this.getExpression().allVars();
    }

    @Override
    public ArrayList<Variable> freeVars(ArrayList<Variable> used) {
        ArrayList<Variable> tmp = new ArrayList<>();
        tmp.addAll(this.getExpression().freeVars(used));
        return tmp;
    }

    @Override
    public ArrayList<Variable> used() {
        return this.getExpression().used();
    }

    @Override
    public Pair<Expression, Expression> diff(Expression expression) {
        if (expression instanceof Apostrophe) {
            return this.getExpression().diff(((Apostrophe) expression).getExpression());
        }
        return new Pair(this, expression);
    }

    @Override
    public String toString() {
        return expression.toString() + "'";
    }

    @Override
    public boolean equals(Object object) {
        if (object instanceof Apostrophe) {
            return ((Apostrophe) object).getExpression().equals(expression);
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 79 * hash + Objects.hashCode(this.expression);
        return hash;
    }

    @Override
    public Expression replace(Expression e, Expression sub) {
        if (this.equals(e)) {
            return sub;
        }
        return new Apostrophe(expression.replace(e, sub));
    }
}
