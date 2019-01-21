package task4;

import java.util.*;

public class And extends BinaryOperator {
    public And(Expression left, Expression right) {
        super(left, right);
        this.lpart = Lexeme.AND;
    }

    @Override
    public boolean equalTrees(Expression other) {
        return other instanceof BinaryOperator &&
                left.equalTrees(((BinaryOperator) other).left) && right.equalTrees(((BinaryOperator) other).right);
    }

    @Override
    public Expression subcp(Map<String, ? extends Expression> variables) {
        return new And(left.subcp(variables), right.subcp(variables));
    }

    @Override
    public boolean calc() {
        return left.calc() && right.calc();
    }

    @Override
    public List<Expression> curProof(List<? extends Expression> hypos) throws MyException {
        List<Expression> result = super.curProof(hypos);
        boolean l = left.calc();
        boolean r = right.calc();
        Expression a = left;
        Expression b = right;
        if (l & r) {
            result.add(new Implication(a, new Implication(b, new And(a, b))));
            result.add(a);
            result.add(b);
            result.add(new Implication(b, new And(a, b)));
            result.add(new And(a, b));
        } else if (!l & r) {
            result.add(new Implication(new Implication(new And(a, b), a), new Implication(new Implication(new And(a, b), new Not(a)), new Not(new And(a, b)))));
            result.add(new Implication(new And(a, b), a));
            result.add(new Implication(new Implication(new And(a, b), new Not(a)), new Not(new And(a, b))));
            result.add(new Implication(new Not(a), new Implication(new And(a, b), new Not(a))));
            result.add(new Not(a));
            result.add(new Implication(new And(a, b), new Not(a)));
            result.add(new Not(new And(a, b)));
        } else if (l & !r) {
            result.add(new Implication(new Implication(new And(a, b), b), new Implication(new Implication(new And(a, b), new Not(b)), new Not(new And(a, b)))));
            result.add(new Implication(new And(a, b), b));
            result.add(new Implication(new Implication(new And(a, b), new Not(b)), new Not(new And(a, b))));
            result.add(new Implication(new Not(b), new Implication(new And(a, b), new Not(b))));
            result.add(new Not(b));
            result.add(new Implication(new And(a, b), new Not(b)));
            result.add(new Not(new And(a, b)));
        } else if (!l & !r) {
            result.add(new Implication(new Implication(new And(a, b), a), new Implication(new Implication(new And(a, b), new Not(a)), new Not(new And(a, b)))));
            result.add(new Implication(new And(a, b), a));
            result.add(new Implication(new Implication(new And(a, b), new Not(a)), new Not(new And(a, b))));
            result.add(new Implication(new Not(a), new Implication(new And(a, b), new Not(a))));
            result.add(new Not(a));
            result.add(new Implication(new And(a, b), new Not(a)));
            result.add(new Not(new And(a, b)));
        } else throw new MyException();
        return result;
    }
}
