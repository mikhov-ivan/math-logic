package task4;

import java.util.*;

public class Quantifier extends ImplExpression {

    public Term var;
    public Expression operand;

    public Quantifier(Term var, Expression predicate) {
        this.var = var;
        this.operand = predicate;
    }

    @Override
    public boolean equalTrees(Expression other) {
        return compareTypes(other) && var.equalTrees(((ForAll) other).var) && operand.equalTrees(((ForAll) other).operand);
    }

    @Override
    public boolean isAxiom(Expression expr, Map<Integer, Expression> known) {
        return false;
    }

    @Override
    public Expression subcp(Map<String, ? extends Expression> variables) {
        return null;
    }

    @Override
    public Expression subst(Map<String, ? extends Expression> variables) {
        return null;
    }

    @Override
    public Set<String> getFreeVars() {
        Set<String> vars = operand.getFreeVars();
        vars.remove(var.getName());
        return vars;
    }

    @Override
    public boolean calc() {
        return false;
    }

    @Override
    public StringBuilder asString() {
        return new StringBuilder(lpart.s).append(var.toString()).append("(").append(operand.toString()).append(")");
    }

    @Override
    public StringBuilder asJavaExpr() {
        return new StringBuilder("new ForAll(").append(var.asJavaExpr()).append(",").append(operand.asJavaExpr()).append(")");
    }

    @Override
    public List<Expression> curProof(List<? extends Expression> hypos) throws MyException {
        return null;
    }

    @Override
    public Map<String, Variable> getVars() {
        return null;
    }

    @Override
    public void setQuantifiers(Set<String> quantifiers) {
        boolean f = quantifiers.contains(var.name);
        quantifiers.add(var.name);
        operand.setQuantifiers(quantifiers);
        if (!f) {
            quantifiers.remove(var.name);
        }
    }

    @Override
    public int markVars(String variableName) {
        return operand.markVars(variableName);
    }

    @Override
    public Set<Pair<Term, Term>> replaceVars(Expression originalExpr) throws MyException {
        if (!compareTypes(originalExpr) || !this.var.name.equals(((Quantifier) originalExpr).var.name)) {
            throw new MyException();
        }
        return operand.replaceVars(((Quantifier) originalExpr).operand);
    }

    public Expression getOperand() {
        return operand;
    }
}
