package task4;

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
    public List<Expression> curProof(List<? extends Expression> sup) throws MyException {
        List<Expression> result = super.curProof(sup);
        boolean l = left.calc();
        boolean r = right.calc();
        Expression a = left;
        Expression b = right;
        if (l & r) {
            result.add(new Implication(a, new Or(a, b)));
            result.add(a);
            result.add(new Or(a, b));
        } else if (!l & r) {
            result.add(new Implication(b, new Or(a, b)));
            result.add(b);
            result.add(new Or(a, b));
        } else if (l & !r) {
            result.add(new Implication(a, new Or(a, b)));
            result.add(a);
            result.add(new Or(a, b));
        } else if (!l & !r) {
            result.add(new Implication(a, new Implication(a, a)));
            result.add(new Implication(new Implication(a, new Implication(a, a)), new Implication(new Implication(a, new Implication(new Implication(a, a), a)), new Implication(a, a))));
            result.add(new Implication(new Implication(a, new Implication(new Implication(a, a), a)), new Implication(a, a)));
            result.add(new Implication(a, new Implication(new Implication(a, a), a)));
            result.add(new Implication(a, a));
            result.add(new Implication(new Implication(new And(new Not(a), new Not(b)), a), new Implication(new Implication(new And(new Not(a), new Not(b)), new Not(a)), new Not(new And(new Not(a), new Not(b))))));
            result.add(new Implication(new Implication(new Implication(new And(new Not(a), new Not(b)), a), new Implication(new Implication(new And(new Not(a), new Not(b)), new Not(a)), new Not(new And(new Not(a), new Not(b))))), new Implication(a, new Implication(new Implication(new And(new Not(a), new Not(b)), a), new Implication(new Implication(new And(new Not(a), new Not(b)), new Not(a)), new Not(new And(new Not(a), new Not(b))))))));
            result.add(new Implication(a, new Implication(new Implication(new And(new Not(a), new Not(b)), a), new Implication(new Implication(new And(new Not(a), new Not(b)), new Not(a)), new Not(new And(new Not(a), new Not(b)))))));
            result.add(new Implication(a, new Implication(new And(new Not(a), new Not(b)), a)));
            result.add(new Implication(new Implication(a, new Implication(new And(new Not(a), new Not(b)), a)), new Implication(a, new Implication(a, new Implication(new And(new Not(a), new Not(b)), a)))));
            result.add(new Implication(a, new Implication(a, new Implication(new And(new Not(a), new Not(b)), a))));
            result.add(new Implication(new Implication(a, a), new Implication(new Implication(a, new Implication(a, new Implication(new And(new Not(a), new Not(b)), a))), new Implication(a, new Implication(new And(new Not(a), new Not(b)), a)))));
            result.add(new Implication(new Implication(a, new Implication(a, new Implication(new And(new Not(a), new Not(b)), a))), new Implication(a, new Implication(new And(new Not(a), new Not(b)), a))));
            result.add(new Implication(a, new Implication(new And(new Not(a), new Not(b)), a)));
            result.add(new Implication(new Implication(a, new Implication(new And(new Not(a), new Not(b)), a)), new Implication(new Implication(a, new Implication(new Implication(new And(new Not(a), new Not(b)), a), new Implication(new Implication(new And(new Not(a), new Not(b)), new Not(a)), new Not(new And(new Not(a), new Not(b)))))), new Implication(a, new Implication(new Implication(new And(new Not(a), new Not(b)), new Not(a)), new Not(new And(new Not(a), new Not(b))))))));
            result.add(new Implication(new Implication(a, new Implication(new Implication(new And(new Not(a), new Not(b)), a), new Implication(new Implication(new And(new Not(a), new Not(b)), new Not(a)), new Not(new And(new Not(a), new Not(b)))))), new Implication(a, new Implication(new Implication(new And(new Not(a), new Not(b)), new Not(a)), new Not(new And(new Not(a), new Not(b)))))));
            result.add(new Implication(a, new Implication(new Implication(new And(new Not(a), new Not(b)), new Not(a)), new Not(new And(new Not(a), new Not(b))))));
            result.add(new Implication(new And(new Not(a), new Not(b)), new Not(a)));
            result.add(new Implication(new Implication(new And(new Not(a), new Not(b)), new Not(a)), new Implication(a, new Implication(new And(new Not(a), new Not(b)), new Not(a)))));
            result.add(new Implication(a, new Implication(new And(new Not(a), new Not(b)), new Not(a))));
            result.add(new Implication(new Implication(a, new Implication(new And(new Not(a), new Not(b)), new Not(a))), new Implication(new Implication(a, new Implication(new Implication(new And(new Not(a), new Not(b)), new Not(a)), new Not(new And(new Not(a), new Not(b))))), new Implication(a, new Not(new And(new Not(a), new Not(b)))))));
            result.add(new Implication(new Implication(a, new Implication(new Implication(new And(new Not(a), new Not(b)), new Not(a)), new Not(new And(new Not(a), new Not(b))))), new Implication(a, new Not(new And(new Not(a), new Not(b))))));
            result.add(new Implication(a, new Not(new And(new Not(a), new Not(b)))));
            result.add(new Implication(b, new Implication(b, b)));
            result.add(new Implication(new Implication(b, new Implication(b, b)), new Implication(new Implication(b, new Implication(new Implication(b, b), b)), new Implication(b, b))));
            result.add(new Implication(new Implication(b, new Implication(new Implication(b, b), b)), new Implication(b, b)));
            result.add(new Implication(b, new Implication(new Implication(b, b), b)));
            result.add(new Implication(b, b));
            result.add(new Implication(new Implication(new And(new Not(a), new Not(b)), b), new Implication(new Implication(new And(new Not(a), new Not(b)), new Not(b)), new Not(new And(new Not(a), new Not(b))))));
            result.add(new Implication(new Implication(new Implication(new And(new Not(a), new Not(b)), b), new Implication(new Implication(new And(new Not(a), new Not(b)), new Not(b)), new Not(new And(new Not(a), new Not(b))))), new Implication(b, new Implication(new Implication(new And(new Not(a), new Not(b)), b), new Implication(new Implication(new And(new Not(a), new Not(b)), new Not(b)), new Not(new And(new Not(a), new Not(b))))))));
            result.add(new Implication(b, new Implication(new Implication(new And(new Not(a), new Not(b)), b), new Implication(new Implication(new And(new Not(a), new Not(b)), new Not(b)), new Not(new And(new Not(a), new Not(b)))))));
            result.add(new Implication(b, new Implication(new And(new Not(a), new Not(b)), b)));
            result.add(new Implication(new Implication(b, new Implication(new And(new Not(a), new Not(b)), b)), new Implication(b, new Implication(b, new Implication(new And(new Not(a), new Not(b)), b)))));
            result.add(new Implication(b, new Implication(b, new Implication(new And(new Not(a), new Not(b)), b))));
            result.add(new Implication(new Implication(b, b), new Implication(new Implication(b, new Implication(b, new Implication(new And(new Not(a), new Not(b)), b))), new Implication(b, new Implication(new And(new Not(a), new Not(b)), b)))));
            result.add(new Implication(new Implication(b, new Implication(b, new Implication(new And(new Not(a), new Not(b)), b))), new Implication(b, new Implication(new And(new Not(a), new Not(b)), b))));
            result.add(new Implication(b, new Implication(new And(new Not(a), new Not(b)), b)));
            result.add(new Implication(new Implication(b, new Implication(new And(new Not(a), new Not(b)), b)), new Implication(new Implication(b, new Implication(new Implication(new And(new Not(a), new Not(b)), b), new Implication(new Implication(new And(new Not(a), new Not(b)), new Not(b)), new Not(new And(new Not(a), new Not(b)))))), new Implication(b, new Implication(new Implication(new And(new Not(a), new Not(b)), new Not(b)), new Not(new And(new Not(a), new Not(b))))))));
            result.add(new Implication(new Implication(b, new Implication(new Implication(new And(new Not(a), new Not(b)), b), new Implication(new Implication(new And(new Not(a), new Not(b)), new Not(b)), new Not(new And(new Not(a), new Not(b)))))), new Implication(b, new Implication(new Implication(new And(new Not(a), new Not(b)), new Not(b)), new Not(new And(new Not(a), new Not(b)))))));
            result.add(new Implication(b, new Implication(new Implication(new And(new Not(a), new Not(b)), new Not(b)), new Not(new And(new Not(a), new Not(b))))));
            result.add(new Implication(new And(new Not(a), new Not(b)), new Not(b)));
            result.add(new Implication(new Implication(new And(new Not(a), new Not(b)), new Not(b)), new Implication(b, new Implication(new And(new Not(a), new Not(b)), new Not(b)))));
            result.add(new Implication(b, new Implication(new And(new Not(a), new Not(b)), new Not(b))));
            result.add(new Implication(new Implication(b, new Implication(new And(new Not(a), new Not(b)), new Not(b))), new Implication(new Implication(b, new Implication(new Implication(new And(new Not(a), new Not(b)), new Not(b)), new Not(new And(new Not(a), new Not(b))))), new Implication(b, new Not(new And(new Not(a), new Not(b)))))));
            result.add(new Implication(new Implication(b, new Implication(new Implication(new And(new Not(a), new Not(b)), new Not(b)), new Not(new And(new Not(a), new Not(b))))), new Implication(b, new Not(new And(new Not(a), new Not(b))))));
            result.add(new Implication(b, new Not(new And(new Not(a), new Not(b)))));
            result.add(new Implication(new Implication(a, new Not(new And(new Not(a), new Not(b)))), new Implication(new Implication(b, new Not(new And(new Not(a), new Not(b)))), new Implication(new Or(a, b), new Not(new And(new Not(a), new Not(b)))))));
            result.add(new Implication(new Implication(b, new Not(new And(new Not(a), new Not(b)))), new Implication(new Or(a, b), new Not(new And(new Not(a), new Not(b))))));
            result.add(new Implication(new Or(a, b), new Not(new And(new Not(a), new Not(b)))));
            result.add(new Implication(new Not(a), new Implication(new Not(b), new And(new Not(a), new Not(b)))));
            result.add(new Implication(new Not(b), new And(new Not(a), new Not(b))));
            result.add(new And(new Not(a), new Not(b)));
            result.add(new Implication(new And(new Not(a), new Not(b)), new Implication(new Or(a, b), new And(new Not(a), new Not(b)))));
            result.add(new Implication(new Or(a, b), new And(new Not(a), new Not(b))));
            result.add(new Implication(new Implication(new Or(a, b), new And(new Not(a), new Not(b))), new Implication(new Implication(new Or(a, b), new Not(new And(new Not(a), new Not(b)))), new Not(new Or(a, b)))));
            result.add(new Implication(new Implication(new Or(a, b), new Not(new And(new Not(a), new Not(b)))), new Not(new Or(a, b))));
            result.add(new Not(new Or(a, b)));
        } else {
            throw new MyException();
        }
        return result;
    }

    @Override
    public Expression subcp(Map<String, ? extends Expression> variables) {
        return new Or(left.subcp(variables), right.subcp(variables));
    }
}
