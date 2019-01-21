package task4;

import java.io.*;

public abstract class Solution {
    protected int row;

    public static boolean modusPonens(Expression A, Expression aThenB, Expression B) {
        return aThenB instanceof Implication && A.equalTrees(((Implication) aThenB).getLeft()) && B.equalTrees(((Implication) aThenB).getRight());
    }

    public abstract void solve() throws IOException, MyException;
}
