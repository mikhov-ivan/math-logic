package task4;

import java.util.*;

public interface Expression {

    boolean equalTrees(Expression other);

    boolean compare(Expression other);

    boolean isAxiom(Expression expr, Map<Integer, Expression> known);

    Expression subcp(Map<String, ? extends Expression> variables);

    Expression subst(Map<String, ? extends Expression> variables);

    boolean compareTypes(Expression other);

    boolean calc();

    StringBuilder asString();

    StringBuilder asJavaExpr();

    List<Expression> curProof(List<? extends Expression> hypos) throws MyException;

    Map<String, Variable> getVars();

    Set<String> getFreeVars();

    void setQuantifiers(Set<String> quantifiers);

    int markVars(String variableName);

    Set<Pair<Term, Term>> replaceVars(Expression originalExpr) throws MyException;

}
