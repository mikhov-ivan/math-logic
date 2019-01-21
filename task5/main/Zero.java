package task5;

import java.util.*;

public class Zero implements Expression {

    public Zero() {

    }

    @Override
    public boolean calc(ArrayList<String> trueV) {
        return false;
    }

    @Override
    public ArrayList<Variable> freeVars(ArrayList<Variable> used) {
        ArrayList<Variable> tmp = new ArrayList<>();
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
    public String toString() {
        return "0";
    }

    @Override
    public boolean equals(Object object) {
        return (object instanceof Zero);
    }

    @Override
    public Expression replace(Expression e, Expression sub) {
        if (this.equals(e)) {
            return sub;
        }
        return this;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        return hash;
    }
}
