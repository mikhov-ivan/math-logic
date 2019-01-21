package task3;

import java.util.*;

public class Not extends UnaryOperator {

    public Not(Expression operand) {
        super(operand);
        this.lpart = Lexeme.NOT;
    }

    @Override
    public boolean equalTrees(Expression other) {
        return hasSameType(other) && ((Not) other).operand.equalTrees(operand);
    }

    @Override
    public boolean calc() {
        return !operand.calc();
    }

    @Override
    public List<Expression> curProof(List<? extends Expression> hypos) {
        List<Expression> result = super.curProof(hypos);
        Expression a = operand;
        if (operand.calc()) {
            result.add(new Then(new Then(new Not(a), a), new Then(new Then(new Not(a), new Not(a)), new Not(new Not(a)))));
            result.add(new Then(a, new Then(new Not(a), a)));
            result.add(a);
            result.add(new Then(new Not(a), a));
            result.add(new Then(new Then(new Not(a), new Not(a)), new Not(new Not(a))));
            result.add(new Then(new Not(a), new Then(new Not(a), new Not(a))));
            result.add(new Then(new Then(new Not(a), new Then(new Not(a), new Not(a))), new Then(new Then(new Not(a), new Then(new Then(new Not(a), new Not(a)), new Not(a))), new Then(new Not(a), new Not(a)))));
            result.add(new Then(new Then(new Not(a), new Then(new Then(new Not(a), new Not(a)), new Not(a))), new Then(new Not(a), new Not(a))));
            result.add(new Then(new Not(a), new Then(new Then(new Not(a), new Not(a)), new Not(a))));
            result.add(new Then(new Not(a), new Not(a)));
            result.add(new Not(new Not(a)));
        } else if (!operand.calc()) {
            result.add(new Not(a));
        }
        return result;
    }

    @Override
    public Expression subAndCopy(Map<String, ? extends Expression> variables) {
        return new Not(operand.subAndCopy(variables));
    }
}
