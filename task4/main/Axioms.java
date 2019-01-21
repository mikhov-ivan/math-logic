package task4;

import java.util.*;

public enum Axioms {

    a1(new Implication(new NumExpr(1), new Implication(new NumExpr(2), new NumExpr(1)))),
    a2(new Implication(new Implication(new NumExpr(1), new NumExpr(2)), new Implication(new Implication(new NumExpr(1), new Implication(new NumExpr(2), new NumExpr(3))), new Implication(new NumExpr(1), new NumExpr(3))))),
    a3(new Implication(new NumExpr(1), new Implication(new NumExpr(2), new And(new NumExpr(1), new NumExpr(2))))),
    a4(new Implication(new And(new NumExpr(1), new NumExpr(2)), new NumExpr(1))),
    a5(new Implication(new And(new NumExpr(1), new NumExpr(2)), new NumExpr(2))),
    a6(new Implication(new NumExpr(1), new Or(new NumExpr(1), new NumExpr(2)))),
    a7(new Implication(new NumExpr(2), new Or(new NumExpr(1), new NumExpr(2)))),
    a8(new Implication(new Implication(new NumExpr(1), new NumExpr(3)), new Implication(new Implication(new NumExpr(2), new NumExpr(3)), new Implication(new Or(new NumExpr(1), new NumExpr(2)), new NumExpr(3))))),
    a9(new Implication(new Implication(new NumExpr(1), new NumExpr(2)), new Implication(new Implication(new NumExpr(1), new Not(new NumExpr(2))), new Not(new NumExpr(1))))),
    a10(new Implication(new Not(new Not(new NumExpr(1))), new NumExpr(1)));

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

    public Expression subst(HashMap<String, Expression> map) {
        return this.expr.subcp(map);
    }
}
