package task3;

import java.util.*;

public class And extends BinaryOperator {

    public And(Expression left, Expression right) {
        super(left, right);
        this.lpart = Lexeme.AND;
    }

    @Override
    public boolean equalTrees(Expression other) {
        return other instanceof BinaryOperator && left.equalTrees(((BinaryOperator) other).left) && right.equalTrees(((BinaryOperator) other).right);
    }

    @Override
    public Expression subAndCopy(Map<String, ? extends Expression> variables) {
        return new And(left.subAndCopy(variables), right.subAndCopy(variables));
    }

    @Override
    public boolean calc() {
        return left.calc() && right.calc();
    }

    @Override
    public List<Expression> curProof(List<? extends Expression> hypos) {
        List<Expression> result = super.curProof(hypos);

        boolean l = left.calc();
        boolean r = right.calc();
        Expression a = left;
        Expression b = right;
        if (l & r) {
            result.add(new Then(a, new Then(b, new And(a, b))));
            result.add(a);
            result.add(b);
            result.add(new Then(b, new And(a, b)));
            result.add(new And(a, b));
        } else if (!l & r) {
            result.add(new Then(new Then(new And(a, b), a), new Then(new Then(new And(a, b), new Not(a)), new Not(new And(a, b)))));
            result.add(new Then(new And(a, b), a));
            result.add(new Then(new Then(new And(a, b), new Not(a)), new Not(new And(a, b))));
            result.add(new Then(new Not(a), new Then(new And(a, b), new Not(a))));
            result.add(new Not(a));
            result.add(new Then(new And(a, b), new Not(a)));
            result.add(new Not(new And(a, b)));
        } else if (l & !r) {
            result.add(new Then(new Then(new And(a, b), b), new Then(new Then(new And(a, b), new Not(b)), new Not(new And(a, b)))));
            result.add(new Then(new And(a, b), b));
            result.add(new Then(new Then(new And(a, b), new Not(b)), new Not(new And(a, b))));
            result.add(new Then(new Not(b), new Then(new And(a, b), new Not(b))));
            result.add(new Not(b));
            result.add(new Then(new And(a, b), new Not(b)));
            result.add(new Not(new And(a, b)));
        } else if (!l & !r) {
            result.add(new Then(new Then(new And(a, b), a), new Then(new Then(new And(a, b), new Not(a)), new Not(new And(a, b)))));
            result.add(new Then(new And(a, b), a));
            result.add(new Then(new Then(new And(a, b), new Not(a)), new Not(new And(a, b))));
            result.add(new Then(new Not(a), new Then(new And(a, b), new Not(a))));
            result.add(new Not(a));
            result.add(new Then(new And(a, b), new Not(a)));
            result.add(new Not(new And(a, b)));
        }
        return result;
    }
}
