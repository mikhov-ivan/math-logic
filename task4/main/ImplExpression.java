package task4;

public abstract class ImplExpression implements Expression {

    protected Lexeme lpart;

    @Override
    public String toString() {
        return asString().toString();
    }

    @Override
    public int hashCode() {
        return toString().hashCode();
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof ImplExpression && this.toString().equals(o.toString());
    }

    @Override
    public boolean compare(Expression other) {
        return this.toString().equals(other.toString());
    }

    @Override
    public boolean compareTypes(Expression other) {
        return this.getClass().getSimpleName().equals(other.getClass().getSimpleName());
    }
}
