package task4;

import java.util.*;

public abstract class UnaryOperator extends ImplExpression {

    protected Expression operand;

    public UnaryOperator(Expression operand) {
        this.operand = operand;
    }

    public Expression getOperand() {
        return operand;
    }

    public Lexeme getToken() {
        return lpart;
    }

    @Override
    public boolean isAxiom(Expression expr, Map<Integer, Expression> known) {
        return compareTypes(expr) && operand.isAxiom(((UnaryOperator) expr).operand, known);
    }

    @Override
    public StringBuilder asString() {
        StringBuilder s = operand.asString();
        if (operand instanceof BinaryOperator) {
            s.insert(0, Lexeme.LEFT_B.s);
            s.append(Lexeme.RIGHT_B.s);
        }
        return s.insert(0, lpart.s);
    }

    @Override
    public StringBuilder asJavaExpr() {
        return new StringBuilder("new ").append(getClass().getSimpleName()).append("(").append(operand.asJavaExpr()).append(")");
    }

    @Override
    public Map<String, Variable> getVars() {
        return operand.getVars();
    }

    @Override
    public Set<String> getFreeVars() {
        return operand.getFreeVars();
    }

    @Override
    public void setQuantifiers(Set<String> quantifiers) {
        operand.setQuantifiers(quantifiers);
    }

    @Override
    public int markVars(String variableName) {
        return operand.markVars(variableName);
    }

    @Override
    public Set<Pair<Term, Term>> replaceVars(Expression originalExpr) throws MyException {
        if (!compareTypes(originalExpr)) {
            throw new MyException();
        }
        return operand.replaceVars(((UnaryOperator) originalExpr).operand);
    }

    @Override
    public List<Expression> curProof(List<? extends Expression> hypos) throws MyException {
        return operand.curProof(hypos);
    }

    @Override
    public Expression subst(Map<String, ? extends Expression> variables) {
        operand = operand.subst(variables);
        return this;
    }
}
