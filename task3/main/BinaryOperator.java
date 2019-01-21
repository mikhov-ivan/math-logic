package task3;

import java.util.*;

public abstract class BinaryOperator extends ImplExpression {

    protected Expression left;
    protected Expression right;

    protected BinaryOperator(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }

    public Lexeme getPart() {
        return lpart;
    }

    public Expression getRight() {
        return right;
    }

    public Expression getLeft() {
        return left;
    }

    @Override
    public boolean isAxiom(Expression a, Map<Integer, Expression> m) {
        return hasSameType(a) && left.isAxiom(((BinaryOperator) a).left, m) && right.isAxiom(((BinaryOperator) a).right, m);
    }

    @Override
    public StringBuilder asString() {
        StringBuilder s = left.asString();
        StringBuilder s2 = right.asString();
        if (left instanceof BinaryOperator) {
            s.insert(0, Lexeme.LEFT_B.s);
            s.append(Lexeme.RIGHT_B.s);
        }
        if (right instanceof BinaryOperator) {
            s2.insert(0, Lexeme.LEFT_B.s);
            s2.append(Lexeme.RIGHT_B.s);
        }
        return s.append(lpart.s).append(s2);
    }

    @Override
    public Map<String, Variable> getVars() {
        Map<String, Variable> h = left.getVars();
        h.putAll(right.getVars());
        return h;
    }

    @Override
    public Set<String> getFreeVars() {
        Set<String> h = left.getFreeVars();
        h.addAll(right.getFreeVars());
        return h;
    }

    @Override
    public StringBuilder asJavaExpr() {
        return new StringBuilder("new ").append(getClass().getSimpleName()).append("(").append(left.asJavaExpr()).append(",").append(right.asJavaExpr()).append(")");
    }

    @Override
    public boolean equalTrees(Expression other) {
        return hasSameType(other) && ((BinaryOperator) other).left.equalTrees(left) && ((BinaryOperator) other).right.equalTrees(right);
    }

    @Override
    public List<Expression> curProof(List<? extends Expression> hypos) {
        List<Expression> result = left.curProof(hypos);
        result.addAll(right.curProof(hypos));
        return result;
    }

    @Override
    public void setQuantifiers(Set<String> quantifiers) {
        left.setQuantifiers(quantifiers);
        right.setQuantifiers(quantifiers);
    }

    @Override
    public int markVars(String variableName) {
        int result = left.markVars(variableName);
        result += right.markVars(variableName);
        return result;
    }

    @Override
    public Set<Pair<Term, Term>> replaceVars(Expression originalExpr) {
        Set<Pair<Term, Term>> set = left.replaceVars(((BinaryOperator) originalExpr).left);
        set.addAll(right.replaceVars(((BinaryOperator) originalExpr).right));
        return set;
    }

    @Override
    public Expression subst(Map<String, ? extends Expression> variables) {
        left = left.subst(variables);
        right = right.subst(variables);
        return this;
    }
}
