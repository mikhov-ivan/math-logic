package task3;

import java.util.*;

public class Then extends BinaryOperator {

    public Then(Expression left, Expression right) {
        super(left, right);
        this.lpart = Lexeme.THEN;
    }

    @Override
    public boolean calc() {
        return !left.calc() || right.calc();
    }

    @Override
    public List<Expression> curProof(List<? extends Expression> hypos) {
        List<Expression> result = super.curProof(hypos);
        boolean l = left.calc();
        boolean r = right.calc();
        Expression a = left;
        Expression b = right;
        if (l & r) {
            result.add(new Then(b, new Then(a, b)));
            result.add(b);
            result.add(new Then(a, b));
        } else if (!l & r) {
            result.add(new Then(b, new Then(a, b)));
            result.add(b);
            result.add(new Then(a, b));
        } else if (l & !r) {
            result.add(new Then(new Then(a, b), new Then(new Then(a, b), new Then(a, b))));
            result.add(new Then(new Then(new Then(a, b), new Then(new Then(a, b), new Then(a, b))), new Then(new Then(new Then(a, b), new Then(new Then(new Then(a, b), new Then(a, b)), new Then(a, b))), new Then(new Then(a, b), new Then(a, b)))));
            result.add(new Then(new Then(new Then(a, b), new Then(new Then(new Then(a, b), new Then(a, b)), new Then(a, b))), new Then(new Then(a, b), new Then(a, b))));
            result.add(new Then(new Then(a, b), new Then(new Then(new Then(a, b), new Then(a, b)), new Then(a, b))));
            result.add(new Then(new Then(a, b), new Then(a, b)));
            result.add(a);
            result.add(new Then(a, new Then(new Then(a, b), a)));
            result.add(new Then(new Then(a, b), a));
            result.add(new Then(new Then(new Then(a, b), a), new Then(new Then(new Then(a, b), new Then(a, b)), new Then(new Then(a, b), b))));
            result.add(new Then(new Then(new Then(a, b), new Then(a, b)), new Then(new Then(a, b), b)));
            result.add(new Then(new Then(a, b), b));
            result.add(new Then(b, new Or(new Not(a), b)));
            result.add(new Then(new Then(b, new Or(new Not(a), b)), new Then(new Then(a, b), new Then(b, new Or(new Not(a), b)))));
            result.add(new Then(new Then(a, b), new Then(b, new Or(new Not(a), b))));
            result.add(new Then(new Then(new Then(a, b), b), new Then(new Then(new Then(a, b), new Then(b, new Or(new Not(a), b))), new Then(new Then(a, b), new Or(new Not(a), b)))));
            result.add(new Then(new Then(new Then(a, b), new Then(b, new Or(new Not(a), b))), new Then(new Then(a, b), new Or(new Not(a), b))));
            result.add(new Then(new Then(a, b), new Or(new Not(a), b)));

            result.addAll(new Not(new Not(a)).curProof(hypos));
            result.addAll(new Or(new Not(a), b).curProof(hypos));

            result.add(new Then(new Not(new Or(new Not(a), b)), new Then(new Then(a, b), new Not(new Or(new Not(a), b)))));
            result.add(new Then(new Then(a, b), new Not(new Or(new Not(a), b))));
            result.add(new Then(new Then(new Then(a, b), new Or(new Not(a), b)), new Then(new Then(new Then(a, b), new Not(new Or(new Not(a), b))), new Not(new Then(a, b)))));
            result.add(new Then(new Then(new Then(a, b), new Not(new Or(new Not(a), b))), new Not(new Then(a, b))));
            result.add(new Not(new Then(a, b)));
        } else if (!l & !r) {
            result.add(new Then(new Then(new Not(b), a), new Then(new Then(new Not(b), new Not(a)), new Not(new Not(b)))));
            result.add(new Then(new Then(new Then(new Not(b), a), new Then(new Then(new Not(b), new Not(a)), new Not(new Not(b)))), new Then(a, new Then(new Then(new Not(b), a), new Then(new Then(new Not(b), new Not(a)), new Not(new Not(b)))))));
            result.add(new Then(a, new Then(new Then(new Not(b), a), new Then(new Then(new Not(b), new Not(a)), new Not(new Not(b))))));
            result.add(new Then(new Not(a), new Then(new Not(b), new Not(a))));
            result.add(new Then(new Then(new Not(a), new Then(new Not(b), new Not(a))), new Then(a, new Then(new Not(a), new Then(new Not(b), new Not(a))))));
            result.add(new Then(a, new Then(new Not(a), new Then(new Not(b), new Not(a)))));
            result.add(new Then(a, new Then(new Not(b), a)));
            result.add(new Then(new Then(a, new Then(new Not(b), a)), new Then(a, new Then(a, new Then(new Not(b), a)))));
            result.add(new Then(a, new Then(a, new Then(new Not(b), a))));
            result.add(new Then(a, new Then(a, a)));
            result.add(new Then(new Then(a, new Then(a, a)), new Then(new Then(a, new Then(new Then(a, a), a)), new Then(a, a))));
            result.add(new Then(new Then(a, new Then(new Then(a, a), a)), new Then(a, a)));
            result.add(new Then(a, new Then(new Then(a, a), a)));
            result.add(new Then(a, a));
            result.add(new Not(a));
            result.add(new Then(new Not(a), new Then(a, new Not(a))));
            result.add(new Then(a, new Not(a)));
            result.add(new Then(new Then(a, a), new Then(new Then(a, new Then(a, new Then(new Not(b), a))), new Then(a, new Then(new Not(b), a)))));
            result.add(new Then(new Then(a, new Then(a, new Then(new Not(b), a))), new Then(a, new Then(new Not(b), a))));
            result.add(new Then(a, new Then(new Not(b), a)));
            result.add(new Then(new Then(a, new Not(a)), new Then(new Then(a, new Then(new Not(a), new Then(new Not(b), new Not(a)))), new Then(a, new Then(new Not(b), new Not(a))))));
            result.add(new Then(new Then(a, new Then(new Not(a), new Then(new Not(b), new Not(a)))), new Then(a, new Then(new Not(b), new Not(a)))));
            result.add(new Then(a, new Then(new Not(b), new Not(a))));
            result.add(new Then(new Then(a, new Then(new Not(b), a)), new Then(new Then(a, new Then(new Then(new Not(b), a), new Then(new Then(new Not(b), new Not(a)), new Not(new Not(b))))), new Then(a, new Then(new Then(new Not(b), new Not(a)), new Not(new Not(b)))))));
            result.add(new Then(new Then(a, new Then(new Then(new Not(b), a), new Then(new Then(new Not(b), new Not(a)), new Not(new Not(b))))), new Then(a, new Then(new Then(new Not(b), new Not(a)), new Not(new Not(b))))));
            result.add(new Then(a, new Then(new Then(new Not(b), new Not(a)), new Not(new Not(b)))));
            result.add(new Then(new Then(a, new Then(new Not(b), new Not(a))), new Then(new Then(a, new Then(new Then(new Not(b), new Not(a)), new Not(new Not(b)))), new Then(a, new Not(new Not(b))))));
            result.add(new Then(new Then(a, new Then(new Then(new Not(b), new Not(a)), new Not(new Not(b)))), new Then(a, new Not(new Not(b)))));
            result.add(new Then(a, new Not(new Not(b))));
            result.add(new Then(new Not(new Not(b)), b));
            result.add(new Then(new Then(new Not(new Not(b)), b), new Then(a, new Then(new Not(new Not(b)), b))));
            result.add(new Then(a, new Then(new Not(new Not(b)), b)));
            result.add(new Then(new Then(a, new Not(new Not(b))), new Then(new Then(a, new Then(new Not(new Not(b)), b)), new Then(a, b))));
            result.add(new Then(new Then(a, new Then(new Not(new Not(b)), b)), new Then(a, b)));
            result.add(new Then(a, b));
        }
        return result;
    }

    @Override
    public Expression subAndCopy(Map<String, ? extends Expression> variables) {
        return new Then(left.subAndCopy(variables), right.subAndCopy(variables));
    }
}
