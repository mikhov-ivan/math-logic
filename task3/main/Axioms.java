package task3;

import java.util.HashMap;

public enum Axioms {

    a1(new Then(new NumExpr(1), new Then(new NumExpr(2), new NumExpr(1)))),
    a2(new Then(new Then(new NumExpr(1), new NumExpr(2)), new Then(new Then(new NumExpr(1), new Then(new NumExpr(2), new NumExpr(3))), new Then(new NumExpr(1), new NumExpr(3))))),
    a3(new Then(new NumExpr(1), new Then(new NumExpr(2), new And(new NumExpr(1), new NumExpr(2))))),
    a4(new Then(new And(new NumExpr(1), new NumExpr(2)), new NumExpr(1))),
    a5(new Then(new And(new NumExpr(1), new NumExpr(2)), new NumExpr(2))),
    a6(new Then(new NumExpr(1), new Or(new NumExpr(1), new NumExpr(2)))),
    a7(new Then(new NumExpr(2), new Or(new NumExpr(1), new NumExpr(2)))),
    a8(new Then(new Then(new NumExpr(1), new NumExpr(3)), new Then(new Then(new NumExpr(2), new NumExpr(3)), new Then(new Or(new NumExpr(1), new NumExpr(2)), new NumExpr(3))))),
    a9(new Then(new Then(new NumExpr(1), new NumExpr(2)), new Then(new Then(new NumExpr(1), new Not(new NumExpr(2))), new Not(new NumExpr(1))))),
    a10(new Then(new Not(new Not(new NumExpr(1))), new NumExpr(1)));

    private final Expression expr;

    public Expression getExpr() {
        return expr;
    }

    private Axioms(Expression expr) {
        this.expr = expr;
    }

    public boolean compare(Expression expr) {
        return this.expr.isAxiom(expr, new HashMap<Integer, Expression>());
    }

    public Expression substitute(HashMap<String, Expression> map) {
        return this.expr.subAndCopy(map);
    }
}
