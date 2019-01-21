package task5;

public class Mul extends BinaryOperator {

    public Mul(Expression left, Expression right) {
        super(left, right);
    }

    @Override
    protected boolean calc(boolean a, boolean b) {
        return true;
    }

    @Override
    public String toString() {
        return "(" + left.toString() + "*" + right.toString() + ")";
    }

    @Override
    public boolean equals(Object object) {
        if (object instanceof Mul) {
            return (((Mul) object).getLeft().equals(left)) && ((Mul) object).getRight().equals(right);
        }
        return false;
    }

    @Override
    public Expression replace(Expression e, Expression sub) {
        if (this.equals(e)) {
            return sub;
        }
        return new Mul(left.replace(e, sub), right.replace(e, sub));
    }

    @Override
    public int hashCode() {
        int hash = 7;
        return hash;
    }
}
