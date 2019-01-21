package task5;

public class Equal extends BinaryOperator {

    public Equal(Expression left, Expression right) {
        super(left, right);
    }

    @Override
    public boolean calc(boolean a, boolean b) {
        return a == b;
    }

    @Override
    public String toString() {
        return "(" + left.toString() + "=" + right.toString() + ")";
    }

    @Override
    public boolean equals(Object object) {
        if (object instanceof Equal) {
            return (((Equal) object).getLeft().equals(left)) && (((Equal) object).getRight().equals(right));
        }
        return false;
    }

    @Override
    public Expression replace(Expression e, Expression sub) {
        if (this.equals(e)) {
            return sub;
        }
        return new Equal(left.replace(e, sub), right.replace(e, sub));
    }

    @Override
    public int hashCode() {
        int hash = 3;
        return hash;
    }
}
