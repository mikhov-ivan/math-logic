package task3;

import java.util.*;

public interface Expression {

    boolean equalTrees(Expression other);

    boolean compare(Expression other);

    boolean isAxiom(Expression expr, Map<Integer, Expression> known);

    Expression subAndCopy(Map<String, ? extends Expression> variables);

    Expression subst(Map<String, ? extends Expression> variables);

    boolean hasSameType(Expression other);

    boolean calc();

    StringBuilder asString();

    StringBuilder asJavaExpr();

    List<Expression> curProof(List<? extends Expression> hypos);

    Map<String, Variable> getVars();

    Set<String> getFreeVars();

    void setQuantifiers(Set<String> quantifiers);

    int markVars(String variableName);

    Set<Pair<Term, Term>> replaceVars(Expression originalExpr);

}
