package task4;

import java.util.*;

public class ForAll extends Quantifier {

    public ForAll(Term var, Expression operand) {
        super(var, operand);
        lpart = Lexeme.FOR_ALL;
    }

    @Override
    public boolean equalTrees(Expression e) {
        return compareTypes(e) && var.equalTrees(((ForAll) e).var) && operand.equalTrees(((ForAll) e).operand);
    }

    @Override
    public boolean isAxiom(Expression e, Map<Integer, Expression> m) {
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

}
