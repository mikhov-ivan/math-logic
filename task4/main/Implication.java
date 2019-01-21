package task4;

import java.util.*;

public class Implication extends BinaryOperator {
    public Implication(Expression left, Expression right) {
        super(left, right);
        this.lpart = Lexeme.THEN;
    }

    @Override
    public boolean calc() {
        return !left.calc() || right.calc();
    }

    @Override
    public List<Expression> curProof(List<? extends Expression> sup) throws MyException {
        List<Expression> result = super.curProof(sup);
        boolean l = left.calc();
        boolean r = right.calc();
        Expression a = left;
        Expression b = right;
        if (l & r) {
            result.add(new Implication(b, new Implication(a, b)));
            result.add(b);
            result.add(new Implication(a, b));
        } else if (!l & r) {
            result.add(new Implication(b, new Implication(a, b)));
            result.add(b);
            result.add(new Implication(a, b));
        } else if (l & !r) {
            result.add(new Implication(new Implication(a, b), new Implication(new Implication(a, b), new Implication(a, b))));
            result.add(new Implication(new Implication(new Implication(a, b), new Implication(new Implication(a, b), new Implication(a, b))), new Implication(new Implication(new Implication(a, b), new Implication(new Implication(new Implication(a, b), new Implication(a, b)), new Implication(a, b))), new Implication(new Implication(a, b), new Implication(a, b)))));
            result.add(new Implication(new Implication(new Implication(a, b), new Implication(new Implication(new Implication(a, b), new Implication(a, b)), new Implication(a, b))), new Implication(new Implication(a, b), new Implication(a, b))));
            result.add(new Implication(new Implication(a, b), new Implication(new Implication(new Implication(a, b), new Implication(a, b)), new Implication(a, b))));
            result.add(new Implication(new Implication(a, b), new Implication(a, b)));
            result.add(a);
            result.add(new Implication(a, new Implication(new Implication(a, b), a)));
            result.add(new Implication(new Implication(a, b), a));
            result.add(new Implication(new Implication(new Implication(a, b), a), new Implication(new Implication(new Implication(a, b), new Implication(a, b)), new Implication(new Implication(a, b), b))));
            result.add(new Implication(new Implication(new Implication(a, b), new Implication(a, b)), new Implication(new Implication(a, b), b)));
            result.add(new Implication(new Implication(a, b), b));
            result.add(new Implication(b, new Or(new Not(a), b)));
            result.add(new Implication(new Implication(b, new Or(new Not(a), b)), new Implication(new Implication(a, b), new Implication(b, new Or(new Not(a), b)))));
            result.add(new Implication(new Implication(a, b), new Implication(b, new Or(new Not(a), b))));
            result.add(new Implication(new Implication(new Implication(a, b), b), new Implication(new Implication(new Implication(a, b), new Implication(b, new Or(new Not(a), b))), new Implication(new Implication(a, b), new Or(new Not(a), b)))));
            result.add(new Implication(new Implication(new Implication(a, b), new Implication(b, new Or(new Not(a), b))), new Implication(new Implication(a, b), new Or(new Not(a), b))));
            result.add(new Implication(new Implication(a, b), new Or(new Not(a), b)));
            result.addAll(new Not(new Not(a)).curProof(sup));
            result.addAll(new Or(new Not(a), b).curProof(sup));
            result.add(new Implication(new Not(new Or(new Not(a), b)), new Implication(new Implication(a, b), new Not(new Or(new Not(a), b)))));
            result.add(new Implication(new Implication(a, b), new Not(new Or(new Not(a), b))));
            result.add(new Implication(new Implication(new Implication(a, b), new Or(new Not(a), b)), new Implication(new Implication(new Implication(a, b), new Not(new Or(new Not(a), b))), new Not(new Implication(a, b)))));
            result.add(new Implication(new Implication(new Implication(a, b), new Not(new Or(new Not(a), b))), new Not(new Implication(a, b))));
            result.add(new Not(new Implication(a, b)));
        } else if (!l & !r) {
            result.add(new Implication(new Implication(new Not(b), a), new Implication(new Implication(new Not(b), new Not(a)), new Not(new Not(b)))));
            result.add(new Implication(new Implication(new Implication(new Not(b), a), new Implication(new Implication(new Not(b), new Not(a)), new Not(new Not(b)))), new Implication(a, new Implication(new Implication(new Not(b), a), new Implication(new Implication(new Not(b), new Not(a)), new Not(new Not(b)))))));
            result.add(new Implication(a, new Implication(new Implication(new Not(b), a), new Implication(new Implication(new Not(b), new Not(a)), new Not(new Not(b))))));
            result.add(new Implication(new Not(a), new Implication(new Not(b), new Not(a))));
            result.add(new Implication(new Implication(new Not(a), new Implication(new Not(b), new Not(a))), new Implication(a, new Implication(new Not(a), new Implication(new Not(b), new Not(a))))));
            result.add(new Implication(a, new Implication(new Not(a), new Implication(new Not(b), new Not(a)))));
            result.add(new Implication(a, new Implication(new Not(b), a)));
            result.add(new Implication(new Implication(a, new Implication(new Not(b), a)), new Implication(a, new Implication(a, new Implication(new Not(b), a)))));
            result.add(new Implication(a, new Implication(a, new Implication(new Not(b), a))));
            result.add(new Implication(a, new Implication(a, a)));
            result.add(new Implication(new Implication(a, new Implication(a, a)), new Implication(new Implication(a, new Implication(new Implication(a, a), a)), new Implication(a, a))));
            result.add(new Implication(new Implication(a, new Implication(new Implication(a, a), a)), new Implication(a, a)));
            result.add(new Implication(a, new Implication(new Implication(a, a), a)));
            result.add(new Implication(a, a));
            result.add(new Not(a));
            result.add(new Implication(new Not(a), new Implication(a, new Not(a))));
            result.add(new Implication(a, new Not(a)));
            result.add(new Implication(new Implication(a, a), new Implication(new Implication(a, new Implication(a, new Implication(new Not(b), a))), new Implication(a, new Implication(new Not(b), a)))));
            result.add(new Implication(new Implication(a, new Implication(a, new Implication(new Not(b), a))), new Implication(a, new Implication(new Not(b), a))));
            result.add(new Implication(a, new Implication(new Not(b), a)));
            result.add(new Implication(new Implication(a, new Not(a)), new Implication(new Implication(a, new Implication(new Not(a), new Implication(new Not(b), new Not(a)))), new Implication(a, new Implication(new Not(b), new Not(a))))));
            result.add(new Implication(new Implication(a, new Implication(new Not(a), new Implication(new Not(b), new Not(a)))), new Implication(a, new Implication(new Not(b), new Not(a)))));
            result.add(new Implication(a, new Implication(new Not(b), new Not(a))));
            result.add(new Implication(new Implication(a, new Implication(new Not(b), a)), new Implication(new Implication(a, new Implication(new Implication(new Not(b), a), new Implication(new Implication(new Not(b), new Not(a)), new Not(new Not(b))))), new Implication(a, new Implication(new Implication(new Not(b), new Not(a)), new Not(new Not(b)))))));
            result.add(new Implication(new Implication(a, new Implication(new Implication(new Not(b), a), new Implication(new Implication(new Not(b), new Not(a)), new Not(new Not(b))))), new Implication(a, new Implication(new Implication(new Not(b), new Not(a)), new Not(new Not(b))))));
            result.add(new Implication(a, new Implication(new Implication(new Not(b), new Not(a)), new Not(new Not(b)))));
            result.add(new Implication(new Implication(a, new Implication(new Not(b), new Not(a))), new Implication(new Implication(a, new Implication(new Implication(new Not(b), new Not(a)), new Not(new Not(b)))), new Implication(a, new Not(new Not(b))))));
            result.add(new Implication(new Implication(a, new Implication(new Implication(new Not(b), new Not(a)), new Not(new Not(b)))), new Implication(a, new Not(new Not(b)))));
            result.add(new Implication(a, new Not(new Not(b))));
            result.add(new Implication(new Not(new Not(b)), b));
            result.add(new Implication(new Implication(new Not(new Not(b)), b), new Implication(a, new Implication(new Not(new Not(b)), b))));
            result.add(new Implication(a, new Implication(new Not(new Not(b)), b)));
            result.add(new Implication(new Implication(a, new Not(new Not(b))), new Implication(new Implication(a, new Implication(new Not(new Not(b)), b)), new Implication(a, b))));
            result.add(new Implication(new Implication(a, new Implication(new Not(new Not(b)), b)), new Implication(a, b)));
            result.add(new Implication(a, b));
        } else throw new MyException();
        return result;
    }

    @Override
    public Expression subcp(Map<String, ? extends Expression> variables) {
        return new Implication(left.subcp(variables), right.subcp(variables));
    }
}
