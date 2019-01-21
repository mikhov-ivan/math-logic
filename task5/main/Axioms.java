package task5;

public class Axioms {

    static int isAxiom(Expression expression) {
        if (ax1(expression)) {
            return 1;
        }
        if (ax2(expression)) {
            return 2;
        }
        if (ax3(expression)) {
            return 3;
        }
        if (ax4(expression)) {
            return 4;
        }
        if (ax5(expression)) {
            return 5;
        }
        if (ax6(expression)) {
            return 6;
        }
        if (ax7(expression)) {
            return 7;
        }
        if (ax8(expression)) {
            return 8;
        }
        if (ax9(expression)) {
            return 9;
        }
        if (ax10(expression)) {
            return 10;
        }
        if (ax11(expression)) {
            return 11;
        }
        if (ax12(expression)) {
            return 12;
        }
        if (a1(expression)) {
            return 13;
        }
        if (a2(expression)) {
            return 14;
        }
        if (a3(expression)) {
            return 15;
        }
        if (a4(expression)) {
            return 16;
        }
        if (a5(expression)) {
            return 17;
        }
        if (a6(expression)) {
            return 19;
        }
        if (a7(expression)) {
            return 20;
        }
        if (a8(expression)) {
            return 21;
        }
        if (a9(expression)) {
            return 22;
        }
        return -1;
    }

    private static boolean ax1(Expression expression) {
        String s = "#->(/->#)";
        if (expression instanceof Implication && ((Implication) expression).getRight() instanceof Implication) {
            s = s.replaceAll("#", "(" + ((Implication) expression).getLeft().toString() + ")");
            s = s.replaceAll("/", "(" + ((Implication) ((Implication) expression).getRight()).getLeft().toString() + ")");
            return expression.equals(new Parser(s).parse());
        }
        return false;
    }

    private static boolean ax2(Expression expression) {
        String s = "(#->/)->(#->/->%)->(#->%)";
        if (expression instanceof Implication && ((Implication) expression).getRight() instanceof Implication) {
            Expression expr1 = ((Implication) expression).getLeft();
            Expression expr2 = ((Implication) ((Implication) expression).getRight()).getRight();
            if (expr1 instanceof Implication && expr2 instanceof Implication) {
                s = s.replaceAll("#", "(" + ((Implication) expr1).getLeft().toString() + ")");
                s = s.replaceAll("/", "(" + ((Implication) expr1).getRight().toString() + ")");
                s = s.replaceAll("%", "(" + ((Implication) expr2).getRight().toString() + ")");
                return expression.equals(new Parser(s).parse());
            }
            return false;
        }
        return false;
    }

    private static boolean ax3(Expression expression) {
        String s = "#->/->#&/";
        if (expression instanceof Implication && ((Implication) expression).getRight() instanceof Implication) {
            s = s.replaceAll("#", "(" + ((Implication) expression).getLeft().toString() + ")");
            s = s.replaceAll("/", "(" + ((Implication) ((Implication) expression).getRight()).getLeft().toString() + ")");
            return expression.equals(new Parser(s).parse());
        }
        return false;
    }

    private static boolean ax4(Expression expression) {
        String s = "#&/->#";
        if (expression instanceof Implication && ((Implication) expression).getLeft() instanceof Conjunction) {
            s = s.replaceAll("#", "(" + ((Implication) expression).getRight().toString() + ")");
            s = s.replaceAll("/", "(" + ((Conjunction) ((Implication) expression).getLeft()).getRight().toString() + ")");
            return expression.equals(new Parser(s).parse());
        }
        return false;
    }

    private static boolean ax5(Expression expression) {
        String s = "#&/->/";
        if (expression instanceof Implication && ((Implication) expression).getLeft() instanceof Conjunction) {
            s = s.replaceAll("#", "(" + ((Conjunction) ((Implication) expression).getLeft()).getLeft().toString() + ")");
            s = s.replaceAll("/", "(" + ((Implication) expression).getRight().toString() + ")");
            return expression.equals(new Parser(s).parse());
        }
        return false;
    }

    private static boolean ax6(Expression expression) {
        String s = "#->#|/";
        if (expression instanceof Implication && ((Implication) expression).getRight() instanceof Disjunction) {
            s = s.replaceAll("#", "(" + ((Implication) expression).getLeft().toString() + ")");
            s = s.replaceAll("/", "(" + ((Disjunction) ((Implication) expression).getRight()).getRight().toString() + ")");
            return expression.equals(new Parser(s).parse());
        }
        return false;
    }

    private static boolean ax7(Expression expression) {
        String s = "/->#|/";
        if (expression instanceof Implication && ((Implication) expression).getRight() instanceof Disjunction) {
            s = s.replaceAll("#", "(" + ((Disjunction) ((Implication) expression).getRight()).getLeft().toString() + ")");
            s = s.replaceAll("/", "(" + ((Implication) expression).getLeft().toString() + ")");
            return expression.equals(new Parser(s).parse());
        }
        return false;
    }

    private static boolean ax8(Expression expression) {
        String s = "(#->%)->(/->%)->(#|/->%)";
        if (expression instanceof Implication && ((Implication) expression).getRight() instanceof Implication) {
            Expression expr1 = ((Implication) expression).getLeft();
            Expression expr2 = ((Implication) ((Implication) expression).getRight()).getLeft();
            if (expr1 instanceof Implication && expr2 instanceof Implication) {
                s = s.replaceAll("#", "(" + ((Implication) expr1).getLeft().toString() + ")");
                s = s.replaceAll("/", "(" + ((Implication) expr2).getLeft().toString() + ")");
                s = s.replaceAll("%", "(" + ((Implication) expr1).getRight().toString() + ")");
                return expression.equals(new Parser(s).parse());
            }
        }
        return false;
    }

    private static boolean ax9(Expression expression) {
        String s = "(#->/)->(#->!/)->!#";
        if (expression instanceof Implication && ((Implication) expression).getLeft() instanceof Implication) {
            s = s.replaceAll("#", "(" + ((Implication) ((Implication) expression).getLeft()).getLeft().toString() + ")");
            s = s.replaceAll("/", "(" + ((Implication) ((Implication) expression).getLeft()).getRight().toString() + ")");
            return expression.equals(new Parser(s).parse());
        }
        return false;
    }

    private static boolean ax10(Expression expression) {
        String s = "!!#->#";
        if (expression instanceof Implication) {
            s = s.replaceAll("#", "(" + ((Implication) expression).getRight().toString() + ")");
            return expression.equals(new Parser(s).parse());
        }
        return false;
    }

    private static boolean ax11(Expression expression) {
        if (expression instanceof Implication) {
            Implication implication = (Implication) expression;
            if (implication.getLeft() instanceof ForAll) {
                Pair<Expression, Expression> difference = ((ForAll) implication.getLeft()).getExpression().diff(implication.getRight());
                if (difference == null) {
                    return true;
                }
                if (difference.getFirst().equals(((ForAll) implication.getLeft()).getVariable())) {
                    Expression subst = ((ForAll) implication.getLeft()).getExpression().replace(difference.getFirst(), difference.getSecond());
                    return subst.equals(implication.getRight());
                }
            }
        }
        return false;
    }

    private static boolean ax12(Expression expression) {
        if (expression instanceof Implication) {
            Implication implication = (Implication) expression;
            if (implication.getRight() instanceof Exists) {
                Pair<Expression, Expression> difference = ((Exists) implication.getRight()).getExpression().diff(implication.getLeft());
                if (difference == null) {
                    return true;
                }
                if (difference.getFirst().equals(((Exists) implication.getRight()).getVariable())) {
                    Expression subst = ((Exists) implication.getRight()).getExpression().replace(difference.getFirst(), difference.getSecond());
                    return subst.equals(implication.getLeft());
                }
            }
        }
        return false;
    }

    private static boolean a1(Expression expression) {
        String s = "#=%->#'=%'";
        if (expression instanceof Implication) {
            Implication implication = (Implication) expression;
            if (implication.getLeft() instanceof Equal) {
                s = s.replaceAll("#", ((Equal) implication.getLeft()).getLeft().toString());
                s = s.replaceAll("%", ((Equal) implication.getLeft()).getRight().toString());
                return new Parser(s).parse().equals(expression);
            }
        }
        return false;
    }

    private static boolean a2(Expression expression) {
        String s = "#=%->#=/->%=/";
        if (expression instanceof Implication) {
            Implication implication = (Implication) expression;
            if (implication.getRight() instanceof Implication) {
                Implication implicationRight = (Implication) implication.getRight();
                if (implicationRight.getLeft() instanceof Equal && implicationRight.getRight() instanceof Equal) {
                    s = s.replaceAll("#", ((Equal) implicationRight.getLeft()).getLeft().toString());
                    s = s.replaceAll("%", ((Equal) implicationRight.getRight()).getLeft().toString());
                    s = s.replaceAll("/", ((Equal) implicationRight.getLeft()).getRight().toString());
                    return new Parser(s).parse().equals(expression);
                }
            }
        }
        return false;
    }

    private static boolean a3(Expression expression) {
        String s = "#'=%'->#=%";
        if (expression instanceof Implication) {
            Implication implication = (Implication) expression;
            if (implication.getRight() instanceof Equal) {
                s = s.replaceAll("#", ((Equal) implication.getRight()).getLeft().toString());
                s = s.replaceAll("%", ((Equal) implication.getRight()).getRight().toString());
                return new Parser(s).parse().equals(expression);
            }
        }
        return false;
    }

    private static boolean a4(Expression expression) {
        String s = "!#'=0";
        if (expression instanceof Equal) {
            Expression expr1 = (Expression) ((Equal) expression).getLeft();
            if (expr1 instanceof Apostrophe) {
                if (((Apostrophe) expr1).getExpression() instanceof Not) {
                    s = s.replaceAll("#", ((Not) ((Apostrophe) expr1).getExpression()).getExpression().toString());
                    return new Parser(s).parse().equals(expression);
                }
            }
        }
        return false;
    }

    private static boolean a5(Expression expression) {
        String s = "#+%'=(#+%)'";
        if (expression instanceof Equal) {
            Expression expr1 = ((Equal) expression).getLeft();
            if (expr1 instanceof Sum) {
                if (((Sum) expr1).getRight() instanceof Apostrophe) {
                    s = s.replaceAll("#", ((Sum) expr1).getLeft().toString());
                    s = s.replaceAll("%", ((Apostrophe) ((Sum) expr1).getRight()).getExpression().toString());
                    return new Parser(s).parse().equals(expression);
                }
            }
        }
        return false;
    }

    private static boolean a6(Expression expression) {
        String s = "#+0=#";
        if (expression instanceof Equal) {
            s = s.replaceAll("#", ((Equal) expression).getRight().toString());
            return new Parser(s).parse().equals(expression);
        }
        return false;
    }

    private static boolean a7(Expression expression) {
        String s = "/->#|/";
        if (expression instanceof Equal) {
            if (((Equal) expression).getLeft() instanceof Mul) {
                s = s.replaceAll("#", ((Mul) ((Equal) expression).getLeft()).getLeft().toString());
                return new Parser(s).parse().equals(expression);
            }
        }
        return false;
    }

    private static boolean a8(Expression expression) {
        String s = "#*%'=#*%+#";
        if (expression instanceof Equal) {
            if (((Equal) expression).getLeft() instanceof Mul) {
                if (((Mul) ((Equal) expression).getLeft()).getRight() instanceof Apostrophe) {
                    s = s.replaceAll("#", ((Mul) ((Equal) expression).getLeft()).getLeft().toString());
                    s = s.replaceAll("%", ((Apostrophe) ((Mul) ((Equal) expression).getLeft()).getRight()).getExpression().toString());
                    return new Parser(s).parse().equals(expression);
                }
            }
        }
        return false;
    }

    private static boolean a9(Expression expression) {
        if (expression instanceof Implication) {
            Implication implication = (Implication) expression;
            if (implication.getLeft() instanceof Conjunction) {
                Conjunction conjunction = (Conjunction) implication.getLeft();
                if (conjunction.getRight() instanceof ForAll) {
                    ForAll universal = (ForAll) conjunction.getRight();
                    Variable x = universal.getVariable();
                    if (universal.getExpression() instanceof Implication) {
                        Implication universalImplication = (Implication) universal.getExpression();
                        boolean flag1 = conjunction.getLeft().equals(implication.getRight().replace(x, new Zero()));
                        boolean flag2 = implication.getRight().equals(universalImplication.getLeft());
                        boolean flag3 = universalImplication.getRight().equals(implication.getRight().replace(x, new Apostrophe(x)));
                        return flag1 && flag2 && flag3;
                    }
                }
            }
        }
        return false;
    }
}
