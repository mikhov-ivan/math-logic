package task4;

import java.util.*;

public class NumExpr extends ImplExpression {

    public int number;

    public NumExpr(int number) {
        this.number = number;
    }

    @Override
    public boolean equalTrees(Expression other) {
        return true;
    }

    @Override
    public boolean isAxiom(Expression expr, Map<Integer, Expression> known) {
        if (known.containsKey(number)) {
            return known.get(number).equalTrees(expr);
        } else {
            known.put(number, expr);
            return true;
        }
    }

    @Override
    public Expression subcp(Map<String, ? extends Expression> variables) {
        return variables.containsKey(Integer.toString(number)) ? variables.get(Integer.toString(number)) : this;
    }

    @Override
    public Expression subst(Map<String, ? extends Expression> variables) {
        return subcp(variables);
    }

    @Override
    public boolean compareTypes(Expression other) {
        return true;
    }

    @Override
    public boolean calc() {
        return false;
    }

    @Override
    public StringBuilder asString() {
        return new StringBuilder(number).append("}").insert(0, "{");
    }

    @Override
    public StringBuilder asJavaExpr() {
        return new StringBuilder("new NumExpr(").append(number).append(")");
    }

    @Override
    public List<Expression> curProof(List<? extends Expression> hypos) throws MyException {
        return null;
    }

    @Override
    public HashMap<String, Variable> getVars() {
        return null;
    }

    @Override
    public Set<String> getFreeVars() {
        return null;
    }

    @Override
    public void setQuantifiers(Set<String> quantifiers) {

    }

    @Override
    public int markVars(String variableName) {
        return 0;
    }

    @Override
    public Set<Pair<Term, Term>> replaceVars(Expression originalExpr) throws MyException {
        return null;
    }
}
