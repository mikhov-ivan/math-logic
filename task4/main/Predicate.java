package task4;

import java.util.*;

public class Predicate extends ImplExpression {

    protected String name;
    protected Term[] arguments;

    public Predicate(String name) {
        this.arguments = new Term[0];
        this.name = name;
    }

    public Predicate(String name, Term... terms) {
        this.arguments = terms;
        this.name = name;
    }

    public Predicate(String name, ArrayList<Term> list) {
        this.arguments = list.toArray(new Term[list.size()]);
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Term[] getArgs() {
        return arguments;
    }

    public void setArgs(Term[] arguments) {
        this.arguments = arguments;
    }

    @Override
    public boolean equalTrees(Expression e) {
        if (compareTypes(e)) {
            Predicate pred = (Predicate) e;
            if (name.equals(pred.name) && arguments.length == pred.arguments.length) {
                boolean f = false;
                for (int i = 0; i < arguments.length; i++) {
                    if (!arguments[i].equalTrees(pred.arguments[i])) {
                        f = true;
                        break;
                    }
                }
                if (!f) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean compare(Expression e) {
        return compareTypes(e) && ((Predicate) e).getName().equals(name) && ((Predicate) e).arguments.length == arguments.length;
    }

    @Override
    public boolean isAxiom(Expression e, Map<Integer, Expression> m) {
        return false;
    }

    @Override
    public Expression subcp(Map<String, ? extends Expression> variables) {
        Predicate pred = new Predicate(this.name);
        Term[] args = new Term[arguments.length];
        for (int i = 0; i < arguments.length; i++) {
            args[i] = (Term) arguments[i].subst(variables);
        }
        pred.setArgs(args);
        return pred;
    }

    @Override
    public Expression subst(Map<String, ? extends Expression> variables) {
        for (int i = 0; i < arguments.length; i++) {
            arguments[i] = (Term) arguments[i].subst(variables);
        }
        return this;
    }

    @Override
    public boolean calc() {
        return false;
    }

    @Override
    public StringBuilder asString() {
        StringBuilder sb = new StringBuilder();
        sb.append(name);
        if (arguments.length != 0) {
            sb.append("(");
            for (int i = 0; i < arguments.length; i++) {
                sb.append(arguments[i].asString()).append(i == arguments.length - 1 ? "" : ",");
            }
            sb.append(")");
        }
        return sb;
    }

    @Override
    public StringBuilder asJavaExpr() {
        StringBuilder sb = new StringBuilder("new Predicate(").append("\"" + name + "\"");
        for (int i = 0; i < arguments.length; i++) {
            sb.append(",").append(arguments[i].asJavaExpr());
        }
        sb.append(")");
        return sb;
    }

    @Override
    public List<Expression> curProof(List<? extends Expression> hypos) throws MyException {
        return null;
    }

    @Override
    public Map<String, Variable> getVars() {
        return null;
    }

    @Override
    public Set<String> getFreeVars() {
        Set<String> set = new HashSet<>();
        for (Term t : arguments) {
            set.addAll(t.getFreeVars());
        }
        return set;
    }

    @Override
    public void setQuantifiers(Set<String> quantifiers) {
        for (Term t : arguments) {
            t.setQuantifiers(quantifiers);
        }
    }

    @Override
    public int markVars(String variableName) {
        int result = 0;
        for (Term t : arguments) {
            result += t.markVars(variableName);
        }
        return result;
    }

    @Override
    public Set<Pair<Term, Term>> replaceVars(Expression originalExpr) throws MyException {
        Set<Pair<Term, Term>> set = new HashSet<>();
        if (!compare(originalExpr)) {
            throw new MyException();
        }
        for (int i = 0; i < arguments.length; i++) {
            Term t = arguments[i];
            set.addAll(t.replaceVars(((Predicate) originalExpr).arguments[i]));
        }
        return set;
    }
}
