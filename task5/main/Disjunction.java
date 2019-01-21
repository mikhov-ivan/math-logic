package task5;

public class Disjunction extends BinaryOperator {

    public Disjunction(Expression left, Expression right) {
        super(left, right);
    }

    @Override
    protected boolean calc(boolean left, boolean right) {
        return left | right;
    }

    @Override
    public String toString() {
        String lefStr = left.toString();
        String rightStr = right.toString();

        if (left instanceof Implication || left instanceof Disjunction) {
            lefStr = "(" + lefStr + ")";
        }
        if (right instanceof Implication || right instanceof Disjunction) {
            rightStr = "(" + rightStr + ")";
        }

        return lefStr + "|" + rightStr;
    }

    @Override
    public Expression replace(Expression e, Expression newE) {
        if (this.equals(e)) {
            return newE;
        }
        return new Disjunction(left.replace(e, newE), right.replace(e, newE));
    }

    @Override
    public boolean equals(Object object) {
        if (object instanceof Disjunction) {
            return (this.getLeft().equals(((Disjunction) object).getLeft()) && this.getRight().equals(((Disjunction) object).getRight()));
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        int hash = 3;
        return hash;
    }
}
