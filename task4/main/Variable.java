package task4;

import java.util.*;

public class Variable extends Predicate {

    protected Boolean currentValue;

    public Variable(String name) {
        super(name);
    }

    public Boolean getCurrentValue() {
        return currentValue;
    }

    public void setCurrentValue(Boolean currentValue) {
        this.currentValue = currentValue;
    }

    @Override
    public boolean equalTrees(Expression other) {
        return compareTypes(other) && (name != null && ((Variable) other).name.equals(name));
    }

    @Override
    public boolean compare(Expression other) {
        return true;
    }

    @Override
    public boolean isAxiom(Expression expr, Map<Integer, Expression> known) {
        return false;
    }

    @Override
    public Expression subcp(Map<String, ? extends Expression> variables) {
        return variables.containsKey(name) ? variables.get(name) : new Variable(name);
    }

    @Override
    public Expression subst(Map<String, ? extends Expression> variables) {
        return subcp(variables);
    }

    @Override
    public boolean calc() {

        if (currentValue == null) {
            System.out.println("variable is not evaluated");
        }
        return currentValue;
    }

    @Override
    public StringBuilder asString() {
        return new StringBuilder(name);
    }

    @Override
    public StringBuilder asJavaExpr() {
        return new StringBuilder("new Variable(\"").append(name).append("\")");
    }

    @Override
    public List<Expression> curProof(List<? extends Expression> hypos) throws MyException {
        boolean f = false;
        List<Expression> list = new ArrayList<>();
        if (hypos.contains(this)) {
            currentValue = true;
            if (!list.contains(this)) {
                list.add(this);
            }
            f = true;
        }
        if (!f && hypos.contains(new Not(this))) {
            currentValue = false;
            if (!list.contains(new Not(this))) {
                list.add(new Not(this));
            }
            f = true;
        }
        if (!f) {
            throw new MyException();
        }
        return list;
    }

    @Override
    public HashMap<String, Variable> getVars() {
        HashMap<String, Variable> vars = new HashMap<>();
        vars.put(name, this);
        return vars;
    }
}
