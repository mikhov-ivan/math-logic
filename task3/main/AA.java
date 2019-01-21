package task3;

import java.util.*;

public enum AA {

    expr1(new Then(new NumExpr(1), new Then(new NumExpr(1), new NumExpr(1)))),
    expr2(new Then(new NumExpr(1), new Then(new Then(new NumExpr(1), new NumExpr(1)), new NumExpr(1)))),
    expr3(new Then(new Then(new NumExpr(1), new Then(new NumExpr(1), new NumExpr(1))), new Then(new Then(new NumExpr(1), new Then(new Then(new NumExpr(1), new NumExpr(1)), new NumExpr(1))), new Then(new NumExpr(1), new NumExpr(1))))),
    expr4(new Then(new Then(new NumExpr(1), new Then(new Then(new NumExpr(1), new NumExpr(1)), new NumExpr(1))), new Then(new NumExpr(1), new NumExpr(1)))),
    expr5(new Then(new NumExpr(1), new NumExpr(1)));

    private final Expression expression;

    AA(Expression expression) {
        this.expression = expression;
    }

    public Expression getExpression() {
        return expression;
    }

    public Expression replace(Expression e) {
        Map<String, Expression> map = new HashMap<>();
        map.put("1", e);
        return expression.subAndCopy(map);
    }

    public String replace(String e) {
        return replace(Helper.parse(e)).toString();
    }
}
