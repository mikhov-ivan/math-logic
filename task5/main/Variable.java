package task5;

import java.util.*;

public class Variable implements Expression {

    private final String name;

    public Variable(String name) {
        this.name = name;
    }

    @Override
    public boolean calc(ArrayList<String> trueV) {
        return trueV.contains(name);
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public ArrayList<Variable> freeVars(ArrayList<Variable> used) {
        ArrayList<Variable> tmp = new ArrayList<>();
        if (!used.contains(this)) {
            tmp.add(this);
        }
        return tmp;
    }

    @Override
    public ArrayList<Variable> used() {
        ArrayList<Variable> tmp = new ArrayList<>();
        return tmp;
    }

    @Override
    public ArrayList<Variable> allVars() {
        ArrayList<Variable> tmp = new ArrayList<>();
        tmp.add(this);
        return tmp;
    }

    @Override
    public Pair<Expression, Expression> diff(Expression expression) {
        if (expression.equals(this)) {
            return null;
        }
        return new Pair(this, expression);
    }

    @Override
    public Expression replace(Expression e, Expression newE) {
        if (this.equals(e)) {
            return newE;
        } else {
            return this;
        }
    }

    @Override
    public boolean equals(Object object) {
        if (object instanceof Variable) {
            return this.toString().equals(object.toString());
        }
        return false;
    }
}
