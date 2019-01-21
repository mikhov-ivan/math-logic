package task5;

import java.util.*;

public class Exists extends Quantifier {

    public Exists(Variable v, Expression e) {
        super(v, e);
    }

    @Override
    public String toString() {
        return "?" + super.toString();
    }

    @Override
    public boolean calc(ArrayList<String> trueE) {
        return trueE.contains(this.toString());
    }

    @Override
    public Expression replace(Expression e, Expression newE) {
        if (this.equals(e)) {
            return newE;
        }
        return new Exists((Variable) variable.replace(e, newE), expression.replace(e, newE));
    }

    @Override
    public boolean equals(Object object) {
        return (object instanceof Exists) && super.equals(object);
    }

    @Override
    public int hashCode() {
        int hash = 5;
        return hash;
    }
}
