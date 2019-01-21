package task5;

import java.util.*;

public class Check {

    private final String s;
    private final ArrayList<Expression> inputE;

    private int inputSize;

    public Check(String s) {
        this.s = s;
        inputSize = 0;
        inputE = new ArrayList<>();
    }

    public String getString() {
        return s;
    }

    public String add(Expression expression) {
        inputSize++;
        inputE.add(expression);
        if (isAxiom(expression) != -1) {
            return process(expression);
        }
        if (mps(expression)) {
            return "true";
        }
        String st = isRule(expression);
        if (!st.equals("next")) {
            return st;
        }
        String st2 = isExRule(expression);
        if (!st2.equals("next")) {
            return st2;
        }
        return "[INCORRECT] Statement (" + inputSize + ") is incorrect";
    }

    private String process(Expression expression) {
        int axiom = isAxiom(expression);
        if (axiom == 11) {
            ForAll st = (ForAll) ((Implication) expression).getLeft();
            Expression term = ((Implication) expression).getRight();
            Pair<Expression, Expression> diff = st.getExpression().diff(term);
            if (diff == null) {
                term = null;
            } else {
                term = diff.getSecond();
            }
            ArrayList<Variable> used = st.getExpression().used();
            ArrayList<Variable> tVars = (term != null) ? term.allVars() : null;

            if (term != null && !st.getVariable().equals(term)) {
                for (Variable variable : tVars) {
                    if (used.contains(variable)) {
                        return "[INCORRECT] Statement (" + inputSize + ") is incorrect";
                    }
                }
            }
        }
        if (axiom == 12) {
            Exists st = (Exists) ((Implication) expression).getRight();
            Expression term = ((Implication) expression).getLeft();
            Pair<Expression, Expression> diff = st.getExpression().diff(term);
            if (diff == null) {
                term = null;
            } else {
                term = diff.getSecond();
            }
            ArrayList<Variable> used = st.getExpression().used();
            used.add(st.getVariable());
            ArrayList<Variable> variables = (term != null) ? term.allVars() : null;

            if (term != null && !st.getVariable().equals(term)) {
                for (Variable x : variables) {
                    if (used.contains(x)) {
                        return "[INCORRECT] Statement (" + inputSize + ") is incorrect";
                    }
                }
            }
        }
        if (axiom == 109) {
            Implication implication = (Implication) expression;
            Conjunction st = (Conjunction) implication.getLeft();
            ForAll forAll = (ForAll) st.getRight();
            Variable variable = forAll.getVariable();
            ArrayList<Variable> variables = implication.getRight().used();
            if (variables.contains(variable)) {
                return "[INCORRECT] Statement (" + inputSize + ") is incorrect";
            }
        }
        return "true";
    }

    private boolean mps(Expression expression) {
        for (int i = inputE.size() - 1; i >= 0; --i) {
            if (inputE.get(i) instanceof Implication) {
                Implication st = (Implication) inputE.get(i);
                if (st.getRight().equals(expression)) {
                    for (int j = inputE.size() - 1; j >= 0; --j) {
                        Expression left = inputE.get(j);
                        if (left.equals(st.getLeft())) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    private String isRule(Expression expression) {
        if (expression instanceof Implication) {
            Implication st = (Implication) expression;
            if (st.getRight() instanceof ForAll) {
                ForAll universal = (ForAll) st.getRight();
                for (int j = inputSize - 1; j >= 0; --j) {
                    if (inputE.get(j) instanceof Implication) {
                        Implication ii = (Implication) inputE.get(j);
                        if (st.getLeft().equals(ii.getLeft())
                                && universal.getExpression().equals(ii.getRight())) {
                            if (st.getLeft().allVars().contains(universal.getVariable())
                                    && st.getLeft().freeVars(new ArrayList<>()).contains(universal.getVariable())) {
                                return "[INCORRECT] Statement (" + inputSize + ") is incorrect";
                            }
                            return "true";
                        }
                    }
                }
            }
        }
        return "next";
    }

    private String isExRule(Expression expression) {
        if (expression instanceof Implication) {
            Implication st = (Implication) expression;
            if (st.getLeft() instanceof Exists) {
                Exists exists = (Exists) st.getLeft();
                for (int j = inputSize - 2; j >= 0; --j) {
                    if (inputE.get(j) instanceof Implication) {
                        Implication impl2 = (Implication) inputE.get(j);
                        if (st.getRight().equals(impl2.getRight()) && exists.getExpression().equals(impl2.getLeft())) {
                            if (st.getRight().allVars().contains(exists.getVariable()) && st.getRight().freeVars(new ArrayList<>()).contains(exists.getVariable())) {
                                return "[INCORRECT] Statement (" + inputSize + ") is incorrect";
                            }
                            return "true";
                        }
                    }
                }
            }
        }
        return "next";
    }

    private int isAxiom(Expression expression) {
        return Axioms.isAxiom(expression);
    }
}
