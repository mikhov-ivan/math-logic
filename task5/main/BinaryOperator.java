package task5;

import java.util.*;

public abstract class BinaryOperator implements Expression {
    protected Expression left;
    protected Expression right;

    protected BinaryOperator(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }

    protected Expression getLeft() {
        return left;
    }

    protected Expression getRight() {
        return right;
    }

    @Override
    public ArrayList<Variable> freeVars(ArrayList<Variable> used) {
        ArrayList<Variable> temporaryArrayList = new ArrayList<>();
        temporaryArrayList.addAll(left.freeVars(used));
        temporaryArrayList.addAll(right.freeVars(used));
        return temporaryArrayList;
    }

    @Override
    public ArrayList<Variable> used() {
        ArrayList<Variable> tmp = new ArrayList<>();
        tmp.addAll(left.used());
        tmp.addAll(right.used());
        return tmp;
    }

    @Override
    public ArrayList<Variable> allVars() {
        ArrayList<Variable> tmp = new ArrayList<>();
        tmp.addAll(left.allVars());
        tmp.addAll(right.allVars());
        return tmp;
    }

    @Override
    public Pair<Expression, Expression> diff(Expression e) {
        if (e instanceof BinaryOperator) {
            Pair<Expression, Expression> diff = this.getLeft().diff(((BinaryOperator) e).getLeft());
            if (diff != null)
                return diff;
            return this.getRight().diff(((BinaryOperator) e).getRight());
        }
        return new Pair(this, e);
    }

    @Override
    public boolean calc(ArrayList<String> trueV) {
        return calc(left.calc(trueV), right.calc(trueV));
    }

    protected abstract boolean calc(boolean left, boolean right);
}
