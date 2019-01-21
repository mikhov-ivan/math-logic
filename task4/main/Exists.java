package task4;

import java.util.*;

public class Exists extends Quantifier {

    public Exists(Term var, Expression operand) {
        super(var, operand);
        lpart = Lexeme.EXISTS;
    }

    @Override
    public boolean equalTrees(Expression other) {
        return compareTypes(other) && var.equalTrees(((Exists) other).var) && operand.equalTrees(((Exists) other).operand);
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
    public boolean calc() {
        return false;
    }

    @Override
    public StringBuilder asString() {
        return new StringBuilder(lpart.s).append(var.asString()).append("(").append(operand.asString()).append(")");
    }

    @Override
    public StringBuilder asJavaExpr() {
        return new StringBuilder("new Exists(").append(var.asJavaExpr()).append(",").append(operand.asJavaExpr()).append(")");
    }

    @Override
    public List<Expression> curProof(List<? extends Expression> hypos) throws MyException {
        return null;
    }
}
