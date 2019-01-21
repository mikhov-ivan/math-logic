package task4;

public enum Lexeme {

    AND("&"), OR("|"), NOT("!"), THEN("->"), EXISTS("?"), FOR_ALL("@"), LEFT_B("("), RIGHT_B(")"), COMMA(",");

    public final String s;

    @Override
    public String toString() {
        return s;
    }

    private Lexeme(final String s) {
        this.s = s;
    }
}
