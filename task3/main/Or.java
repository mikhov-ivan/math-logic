package task3;

import java.util.*;

public class Or extends BinaryOperator {
    public Or(Expression left, Expression right) {
        super(left, right);
        this.lpart = Lexeme.OR;
    }

    @Override
    public boolean calc() {
        return left.calc() || right.calc();
    }

    @Override
    public List<Expression> curProof(List<? extends Expression> hypos) {
        List<Expression> result = super.curProof(hypos);
        boolean l = left.calc();
        boolean r = right.calc();
        Expression a = left;
        Expression b = right;
        if (l & r) {
            result.add(new Then(a, new Or(a, b)));
            result.add(a);
            result.add(new Or(a, b));
        } else if (!l & r) {
            result.add(new Then(b, new Or(a, b)));
            result.add(b);
            result.add(new Or(a, b));
        } else if (l & !r) {
            result.add(new Then(a, new Or(a, b)));
            result.add(a);
            result.add(new Or(a, b));
        } else if (!l & !r) {
            result.add(new Then(a, new Then(a, a)));
            result.add(new Then(new Then(a, new Then(a, a)), new Then(new Then(a, new Then(new Then(a, a), a)), new Then(a, a))));
            result.add(new Then(new Then(a, new Then(new Then(a, a), a)), new Then(a, a)));
            result.add(new Then(a, new Then(new Then(a, a), a)));
            result.add(new Then(a, a));
            result.add(new Then(new Then(new And(new Not(a), new Not(b)), a), new Then(new Then(new And(new Not(a), new Not(b)), new Not(a)), new Not(new And(new Not(a), new Not(b))))));
            result.add(new Then(new Then(new Then(new And(new Not(a), new Not(b)), a), new Then(new Then(new And(new Not(a), new Not(b)), new Not(a)), new Not(new And(new Not(a), new Not(b))))), new Then(a, new Then(new Then(new And(new Not(a), new Not(b)), a), new Then(new Then(new And(new Not(a), new Not(b)), new Not(a)), new Not(new And(new Not(a), new Not(b))))))));
            result.add(new Then(a, new Then(new Then(new And(new Not(a), new Not(b)), a), new Then(new Then(new And(new Not(a), new Not(b)), new Not(a)), new Not(new And(new Not(a), new Not(b)))))));
            result.add(new Then(a, new Then(new And(new Not(a), new Not(b)), a)));
            result.add(new Then(new Then(a, new Then(new And(new Not(a), new Not(b)), a)), new Then(a, new Then(a, new Then(new And(new Not(a), new Not(b)), a)))));
            result.add(new Then(a, new Then(a, new Then(new And(new Not(a), new Not(b)), a))));
            result.add(new Then(new Then(a, a), new Then(new Then(a, new Then(a, new Then(new And(new Not(a), new Not(b)), a))), new Then(a, new Then(new And(new Not(a), new Not(b)), a)))));
            result.add(new Then(new Then(a, new Then(a, new Then(new And(new Not(a), new Not(b)), a))), new Then(a, new Then(new And(new Not(a), new Not(b)), a))));
            result.add(new Then(a, new Then(new And(new Not(a), new Not(b)), a)));
            result.add(new Then(new Then(a, new Then(new And(new Not(a), new Not(b)), a)), new Then(new Then(a, new Then(new Then(new And(new Not(a), new Not(b)), a), new Then(new Then(new And(new Not(a), new Not(b)), new Not(a)), new Not(new And(new Not(a), new Not(b)))))), new Then(a, new Then(new Then(new And(new Not(a), new Not(b)), new Not(a)), new Not(new And(new Not(a), new Not(b))))))));
            result.add(new Then(new Then(a, new Then(new Then(new And(new Not(a), new Not(b)), a), new Then(new Then(new And(new Not(a), new Not(b)), new Not(a)), new Not(new And(new Not(a), new Not(b)))))), new Then(a, new Then(new Then(new And(new Not(a), new Not(b)), new Not(a)), new Not(new And(new Not(a), new Not(b)))))));
            result.add(new Then(a, new Then(new Then(new And(new Not(a), new Not(b)), new Not(a)), new Not(new And(new Not(a), new Not(b))))));
            result.add(new Then(new And(new Not(a), new Not(b)), new Not(a)));
            result.add(new Then(new Then(new And(new Not(a), new Not(b)), new Not(a)), new Then(a, new Then(new And(new Not(a), new Not(b)), new Not(a)))));
            result.add(new Then(a, new Then(new And(new Not(a), new Not(b)), new Not(a))));
            result.add(new Then(new Then(a, new Then(new And(new Not(a), new Not(b)), new Not(a))), new Then(new Then(a, new Then(new Then(new And(new Not(a), new Not(b)), new Not(a)), new Not(new And(new Not(a), new Not(b))))), new Then(a, new Not(new And(new Not(a), new Not(b)))))));
            result.add(new Then(new Then(a, new Then(new Then(new And(new Not(a), new Not(b)), new Not(a)), new Not(new And(new Not(a), new Not(b))))), new Then(a, new Not(new And(new Not(a), new Not(b))))));
            result.add(new Then(a, new Not(new And(new Not(a), new Not(b)))));
            result.add(new Then(b, new Then(b, b)));
            result.add(new Then(new Then(b, new Then(b, b)), new Then(new Then(b, new Then(new Then(b, b), b)), new Then(b, b))));
            result.add(new Then(new Then(b, new Then(new Then(b, b), b)), new Then(b, b)));
            result.add(new Then(b, new Then(new Then(b, b), b)));
            result.add(new Then(b, b));
            result.add(new Then(new Then(new And(new Not(a), new Not(b)), b), new Then(new Then(new And(new Not(a), new Not(b)), new Not(b)), new Not(new And(new Not(a), new Not(b))))));
            result.add(new Then(new Then(new Then(new And(new Not(a), new Not(b)), b), new Then(new Then(new And(new Not(a), new Not(b)), new Not(b)), new Not(new And(new Not(a), new Not(b))))), new Then(b, new Then(new Then(new And(new Not(a), new Not(b)), b), new Then(new Then(new And(new Not(a), new Not(b)), new Not(b)), new Not(new And(new Not(a), new Not(b))))))));
            result.add(new Then(b, new Then(new Then(new And(new Not(a), new Not(b)), b), new Then(new Then(new And(new Not(a), new Not(b)), new Not(b)), new Not(new And(new Not(a), new Not(b)))))));
            result.add(new Then(b, new Then(new And(new Not(a), new Not(b)), b)));
            result.add(new Then(new Then(b, new Then(new And(new Not(a), new Not(b)), b)), new Then(b, new Then(b, new Then(new And(new Not(a), new Not(b)), b)))));
            result.add(new Then(b, new Then(b, new Then(new And(new Not(a), new Not(b)), b))));
            result.add(new Then(new Then(b, b), new Then(new Then(b, new Then(b, new Then(new And(new Not(a), new Not(b)), b))), new Then(b, new Then(new And(new Not(a), new Not(b)), b)))));
            result.add(new Then(new Then(b, new Then(b, new Then(new And(new Not(a), new Not(b)), b))), new Then(b, new Then(new And(new Not(a), new Not(b)), b))));
            result.add(new Then(b, new Then(new And(new Not(a), new Not(b)), b)));
            result.add(new Then(new Then(b, new Then(new And(new Not(a), new Not(b)), b)), new Then(new Then(b, new Then(new Then(new And(new Not(a), new Not(b)), b), new Then(new Then(new And(new Not(a), new Not(b)), new Not(b)), new Not(new And(new Not(a), new Not(b)))))), new Then(b, new Then(new Then(new And(new Not(a), new Not(b)), new Not(b)), new Not(new And(new Not(a), new Not(b))))))));
            result.add(new Then(new Then(b, new Then(new Then(new And(new Not(a), new Not(b)), b), new Then(new Then(new And(new Not(a), new Not(b)), new Not(b)), new Not(new And(new Not(a), new Not(b)))))), new Then(b, new Then(new Then(new And(new Not(a), new Not(b)), new Not(b)), new Not(new And(new Not(a), new Not(b)))))));
            result.add(new Then(b, new Then(new Then(new And(new Not(a), new Not(b)), new Not(b)), new Not(new And(new Not(a), new Not(b))))));
            result.add(new Then(new And(new Not(a), new Not(b)), new Not(b)));
            result.add(new Then(new Then(new And(new Not(a), new Not(b)), new Not(b)), new Then(b, new Then(new And(new Not(a), new Not(b)), new Not(b)))));
            result.add(new Then(b, new Then(new And(new Not(a), new Not(b)), new Not(b))));
            result.add(new Then(new Then(b, new Then(new And(new Not(a), new Not(b)), new Not(b))), new Then(new Then(b, new Then(new Then(new And(new Not(a), new Not(b)), new Not(b)), new Not(new And(new Not(a), new Not(b))))), new Then(b, new Not(new And(new Not(a), new Not(b)))))));
            result.add(new Then(new Then(b, new Then(new Then(new And(new Not(a), new Not(b)), new Not(b)), new Not(new And(new Not(a), new Not(b))))), new Then(b, new Not(new And(new Not(a), new Not(b))))));
            result.add(new Then(b, new Not(new And(new Not(a), new Not(b)))));
            result.add(new Then(new Then(a, new Not(new And(new Not(a), new Not(b)))), new Then(new Then(b, new Not(new And(new Not(a), new Not(b)))), new Then(new Or(a, b), new Not(new And(new Not(a), new Not(b)))))));
            result.add(new Then(new Then(b, new Not(new And(new Not(a), new Not(b)))), new Then(new Or(a, b), new Not(new And(new Not(a), new Not(b))))));
            result.add(new Then(new Or(a, b), new Not(new And(new Not(a), new Not(b)))));
            result.add(new Then(new Not(a), new Then(new Not(b), new And(new Not(a), new Not(b)))));
            result.add(new Then(new Not(b), new And(new Not(a), new Not(b))));
            result.add(new And(new Not(a), new Not(b)));
            result.add(new Then(new And(new Not(a), new Not(b)), new Then(new Or(a, b), new And(new Not(a), new Not(b)))));
            result.add(new Then(new Or(a, b), new And(new Not(a), new Not(b))));
            result.add(new Then(new Then(new Or(a, b), new And(new Not(a), new Not(b))), new Then(new Then(new Or(a, b), new Not(new And(new Not(a), new Not(b)))), new Not(new Or(a, b)))));
            result.add(new Then(new Then(new Or(a, b), new Not(new And(new Not(a), new Not(b)))), new Not(new Or(a, b))));
            result.add(new Not(new Or(a, b)));
        }
        return result;
    }

    @Override
    public Expression subAndCopy(Map<String, ? extends Expression> variables) {
        return new Or(left.subAndCopy(variables), right.subAndCopy(variables));
    }
}