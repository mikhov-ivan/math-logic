package task4;

import java.util.*;

public class Term extends ImplExpression {

    public Set<String> quantifiers;
    public boolean isFree = false;
    public String name;
    protected Term[] arguments;

    public Term(String token) {
        this.arguments = new Term[0];
        quantifiers = new HashSet<>(3);
        this.name = token;
    }

    public Term(String token, Term... terms) {
        this.arguments = terms;
        this.name = token;
    }

    public Term(String part, ArrayList<Term> list) {
        this.name = part;
        this.arguments = list.toArray(new Term[list.size()]);
        quantifiers = new HashSet<>(3);
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
    public boolean equalTrees(Expression other) {
        if (compareTypes(other)) {
            Term pred = (Term) other;
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
    public boolean compare(Expression other) {
        return compareTypes(other) && ((Term) other).getName().equals(name) && ((Term) other).arguments.length == arguments.length;
    }

    @Override
    public boolean isAxiom(Expression expr, Map<Integer, Expression> known) {
        return false;
    }

    @Override
    public Expression subcp(Map<String, ? extends Expression> variables) {
        Term term = new Term(this.name);
        Term[] args = new Term[arguments.length];
        for (int i = 0; i < arguments.length; i++) {
            args[i] = (Term) arguments[i].subst(variables);
        }
        term.setArgs(args);
        return term;
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
        StringBuilder sb = new StringBuilder("new Term(").append("\"" + name + "\"");
        for (Term argument : arguments) {
            sb.append(",").append(argument.asJavaExpr());
        }
        sb.append(")");
        return sb;
    }

    @Override
    public List<Expression> curProof(List<? extends Expression> hypos) throws MyException {
        return null;
    }

    public boolean compare(Term t) {
        return (t.name.equals(name) && numArgs(t));
    }

    public boolean numArgs(Term t) {
        return t.arguments.length == arguments.length;
    }

    @Override
    public Map<String, Variable> getVars() {
        return null;
    }

    @Override
    public Set<String> getFreeVars() {
        HashSet<String> vars = new HashSet<>();
        for (Term t : arguments) {
            vars.addAll(t.getFreeVars());
        }
        if (!this.quantifiers.contains(this.name)) {
            vars.add(name);
        }
        return vars;
    }

    @Override
    public void setQuantifiers(Set<String> quantifiers) {
        Set<String> set = new HashSet<>();
        for (Term t : arguments) {
            t.setQuantifiers(quantifiers);
        }
        for (String s : quantifiers) {
            set.add(s);
        }
        this.quantifiers = set;
    }

    @Override
    public int markVars(String variableName) {
        int result = 0;
        for (Term t : arguments) {
            result += t.markVars(variableName);
        }
        if (this.name.equals(variableName)) {
            boolean f = true;
            for (String s : quantifiers) {
                if (s.equals(variableName)) {
                    f = false;
                    break;
                }
            }
            if (f) {
                isFree = true;
                result++;
            }
        }
        return result;
    }

    @Override
    public boolean equals(Object o) {
        return this == o;
    }

    @Override
    public int hashCode() {
        return System.identityHashCode(this);
    }

    public List<String> getTerms() {
        List<String> result;
        if (arguments.length == 0) {
            result = new ArrayList<>(10);
        } else {
            result = arguments[0].getTerms();
            for (int i = 1; i < arguments.length; i++) {
                result.addAll(arguments[i].getTerms());
            }
        }
        result.add(name);
        return result;
    }

    @Override
    public Set<Pair<Term, Term>> replaceVars(Expression originalExpr) throws MyException {
        Set<Pair<Term, Term>> set = new HashSet<>();
        if (!(originalExpr instanceof Term)) {
            throw new MyException();
        }
        if (((Term) originalExpr).isFree) {
            set.add(new Pair<>((Term) originalExpr, this));
        }
        if (numArgs((Term) originalExpr)) {
            for (int i = 0; i < arguments.length; i++) {
                Term t = arguments[i];
                set.addAll(t.replaceVars(((Term) originalExpr).arguments[i]));
            }
        }
        return set;
    }
}
