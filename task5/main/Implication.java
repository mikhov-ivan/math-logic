package task5;

public class Implication extends BinaryOperator {

    public Implication(Expression left, Expression right) {
        super(left, right);
    }

    @Override
    protected boolean calc(boolean left, boolean right) {
        return !left | right;
    }

    @Override
    public String toString() {
        String leftStr = left.toString();
        String rightStr = right.toString();

        if (left instanceof Implication) {
            leftStr = "(" + leftStr + ")";
        }
        if (right instanceof Implication) {
            rightStr = "(" + rightStr + ")";
        }

        return leftStr + "->" + rightStr;
    }

    @Override
    public Expression replace(Expression e, Expression newE) {
        if (this.equals(e)) {
            return newE;
        }
        return new Implication(left.replace(e, newE), right.replace(e, newE));
    }

    @Override
    public boolean equals(Object object) {
        if (object instanceof Implication) {
            return (this.getLeft().equals(((Implication) object).getLeft()) && this.getRight().equals(((Implication) object).getRight()));
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        int hash = 7;
        return hash;
    }
}
