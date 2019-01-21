package task3;

public enum Lexeme {

    AND("&"), OR("|"), NOT("!"), THEN("->"), LEFT_B("("), RIGHT_B(")");

    public final String s;

    @Override
    public String toString() {
        return s;
    }

    private Lexeme(final String s) {
        this.s = s;
    }
}
