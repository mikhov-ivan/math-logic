package task5;

import java.util.ArrayList;

public interface Expression {

    public abstract boolean calc(ArrayList<String> trueVariables);

    @Override
    public String toString();

    @Override
    public boolean equals(Object object);

    public ArrayList<Variable> freeVars(ArrayList<Variable> used);

    public ArrayList<Variable> used();

    public ArrayList<Variable> allVars();

    public Pair<Expression, Expression> diff(Expression e);

    public Expression replace(Expression e, Expression newE);
}
