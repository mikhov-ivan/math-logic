package task5;

public class Conjunction extends BinaryOperator {

    public Conjunction(Expression left, Expression right) {
        super(left, right);
    }

    @Override
    protected boolean calc(boolean left, boolean right) {
        return left & right;
    }

    @Override
    public String toString() {
        String lefStr = left.toString();
        String rightStr = right.toString();
        if (left instanceof Implication || left instanceof Disjunction || left instanceof Conjunction) {
            lefStr = "(" + lefStr + ")";
        }
        if (right instanceof Implication || right instanceof Disjunction || right instanceof Conjunction) {
            rightStr = "(" + rightStr + ")";
        }
        return lefStr + "&" + rightStr;
    }

    @Override
    public Expression replace(Expression e, Expression newE) {
        if (this.equals(e)) {
            return newE;
        }
        return new Conjunction(left.replace(e, newE), right.replace(e, newE));
    }

    @Override
    public boolean equals(Object object) {
        if (object instanceof Conjunction) {
            return (this.getLeft().equals(((Conjunction) object).getLeft()) && this.getRight().equals(((Conjunction) object).getRight()));
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        int hash = 5;
        return hash;
    }
}
