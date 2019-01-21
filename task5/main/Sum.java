package task5;

public class Sum extends BinaryOperator {

    public Sum(Expression left, Expression right) {
        super(left, right);
    }

    @Override
    protected boolean calc(boolean a, boolean b) {
        return true;
    }

    @Override
    public String toString() {
        return "(" + left.toString() + "+" + right.toString() + ")";
    }

    @Override
    public boolean equals(Object object) {
        if (object instanceof Sum) {
            return (((Sum) object).getLeft().equals(left)) && ((Sum) object).getRight().equals(right);
        }
        return false;
    }

    @Override
    public Expression replace(Expression e, Expression sub) {
        if (this.equals(e)) {
            return sub;
        }
        return new Sum(left.replace(e, sub), right.replace(e, sub));
    }

    @Override
    public int hashCode() {
        int hash = 3;
        return hash;
    }
}
