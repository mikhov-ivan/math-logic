package task3;

import java.util.*;

public class Variable extends Predicate {

    protected Boolean val;

    public Variable(String name) {
        super(name);
    }

    public Boolean getCurrentValue() {

        return val;
    }

    public void setValue(Boolean currentValue) {
        this.val = currentValue;
    }

    @Override
    public boolean equalTrees(Expression other) {
        return hasSameType(other) && (name != null && ((Variable) other).name.equals(name));
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
    public Expression subAndCopy(Map<String, ? extends Expression> variables) {
        return variables.containsKey(name) ? variables.get(name) : new Variable(name);
    }

    @Override
    public Expression subst(Map<String, ? extends Expression> variables) {
        return subAndCopy(variables);
    }

    @Override
    public boolean calc() {
        return val;
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
    public List<Expression> curProof(List<? extends Expression> hypos) {
        boolean f = false;
        List<Expression> list = new ArrayList<>();
        if (hypos.contains(this)) {
            val = true;
            if (!list.contains(this)) {
                list.add(this);
            }
            f = true;
        }
        if (!f && hypos.contains(new Not(this))) {
            val = false;
            if (!list.contains(new Not(this))) {
                list.add(new Not(this));
            }
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
