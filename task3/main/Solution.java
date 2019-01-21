package task3;

abstract class Solution {
    protected int row;

    public static boolean mps(Expression A, Expression AB, Expression B) {
        return AB instanceof Then && A.equalTrees(((Then) AB).getLeft()) && B.equalTrees(((Then) AB).getRight());
    }

    public abstract void solve();
}
