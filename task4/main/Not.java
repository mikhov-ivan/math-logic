package task4;

import java.util.*;

public class Not extends UnaryOperator {

    public Not(Expression operand) {
        super(operand);
        this.lpart = Lexeme.NOT;
    }

    @Override
    public boolean equalTrees(Expression other) {
        return compareTypes(other) && ((Not) other).operand.equalTrees(operand);
    }

    @Override
    public boolean calc() {
        return !operand.calc();
    }

    @Override
    public List<Expression> curProof(List<? extends Expression> sup) throws MyException {
        List<Expression> result = super.curProof(sup);
        Expression a = operand;
        if (operand.calc()) {
            result.add(new Implication(new Implication(new Not(a), a), new Implication(new Implication(new Not(a), new Not(a)), new Not(new Not(a)))));
            result.add(new Implication(a, new Implication(new Not(a), a)));
            result.add(a);
            result.add(new Implication(new Not(a), a));
            result.add(new Implication(new Implication(new Not(a), new Not(a)), new Not(new Not(a))));
            result.add(new Implication(new Not(a), new Implication(new Not(a), new Not(a))));
            result.add(new Implication(new Implication(new Not(a), new Implication(new Not(a), new Not(a))), new Implication(new Implication(new Not(a), new Implication(new Implication(new Not(a), new Not(a)), new Not(a))), new Implication(new Not(a), new Not(a)))));
            result.add(new Implication(new Implication(new Not(a), new Implication(new Implication(new Not(a), new Not(a)), new Not(a))), new Implication(new Not(a), new Not(a))));
            result.add(new Implication(new Not(a), new Implication(new Implication(new Not(a), new Not(a)), new Not(a))));
            result.add(new Implication(new Not(a), new Not(a)));
            result.add(new Not(new Not(a)));
        } else if (!operand.calc()) {
            result.add(new Not(a));
        } else {
            throw new MyException();
        }
        return result;
    }

    @Override
    public Expression subcp(Map<String, ? extends Expression> variables) {
        return new Not(operand.subcp(variables));
    }
}
