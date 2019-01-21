package task3;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;
import java.util.regex.Pattern;

public class Deduction extends Solution {

    public PrintWriter output;
    private Expression alpha;
    private final Map<String, Expression> map = new HashMap<>();
    private List<Expression> supposes = new ArrayList<>();
    private Map<String, Expression> trueSt = new HashMap<>();
    private final Map<Expression, ArrayList<Expression>> mps = new HashMap<>();

    public Deduction() throws FileNotFoundException {
        output = new PrintWriter("task3.bck");
    }

    public Deduction(List<Expression> hypos, Expression alpha) {
        this.supposes = hypos;
        this.supposes.add(alpha);
    }

    public void setSupposes(List<Expression> hypos) {
        this.supposes = new ArrayList<>(hypos);
        this.alpha = this.supposes.remove(hypos.size() - 1);
    }

    public void setTrue(List<Expression> proofed) {
        this.trueSt = new HashMap<>();
        for (Expression e : proofed) {
            this.trueSt.put(e.toString(), e);
        }
    }

    public List<Expression> deductionTheorem(List<Expression> proof) {
        List<Expression> result = new ArrayList<>();
        mps.clear();
        for (Expression e : trueSt.values()) {
            result.add(e);
            result.add(new Then(e, new Then(alpha, e)));
            Expression temp = new Then(alpha, e);
            result.add(temp);
            Helper.toMps(mps, e);
            Helper.toMps(mps, temp);
        }

        for (Expression expr : proof) {
            boolean flag = false;
            for (Expression e : supposes) {
                if (e.equalTrees(expr)) {
                    flag = true;
                    break;
                }
            }

            if (!flag) {
                for (Axioms a : Axioms.values()) {
                    if (a.compare(expr)) {
                        flag = true;
                        break;
                    }
                }
            }
            if (flag) {
                result.add(expr);
                result.add(new Then(expr, new Then(alpha, expr)));
                Expression temp = new Then(alpha, expr);
                result.add(temp);
            }
            if (!flag && expr.equalTrees(alpha)) {
                result.addAll(Helper.proofAA(alpha));
                flag = true;
            }
            if (trueSt.containsKey(expr.toString())) {
                flag = true;
            }
            if (!flag) {
                if (mps.get(expr) != null) {
                    for (Expression e : mps.get(expr)) {
                        if (trueSt.get(e.toString()) != null) {
                            map.put("1", alpha);
                            map.put("2", e);
                            map.put("3", expr);
                            result.add(new Then(new Then(new NumExpr(1), new NumExpr(2)), new Then(new Then(new NumExpr(1), new Then(new NumExpr(2), new NumExpr(3))), new Then(new NumExpr(1), new NumExpr(3)))).subst(map));
                            result.add(new Then(new Then(new NumExpr(1), new Then(new NumExpr(2), new NumExpr(3))), new Then(new NumExpr(1), new NumExpr(3))).subst(map));
                            result.add(new Then(alpha, expr));
                            flag = true;
                            break;
                        }
                    }
                }
            }

            if (!flag) {
                for (Expression e : trueSt.values()) {
                    Helper.out.println(e.asString());
                }
                Helper.out.println(expr.asString());
            } else {
                trueSt.put(expr.toString(), expr);
                Helper.toMps(mps, expr);
                Helper.toMps(mps, new Then(alpha, expr));
            }
        }

        output.println();
        return result;
    }

    @Override
    public void solve() {
        String[] temp = null;
        try {
            temp = Helper.in.readLine().split(Pattern.quote("|-"));
        } catch (IOException ex) {
        }
        String[] s = temp[0].split(",");
        for (String value : s) {
            supposes.add(Helper.parse(value));
        }
        alpha = supposes.remove(supposes.size() - 1);
        List<Expression> proof = new ArrayList<>();
        String s1 = "";
        try {
            s1 = Helper.in.readLine();
        } catch (IOException ex) {
        }
        while (s1 != null && !s1.replace("\\s+", "").isEmpty()) {
            proof.add(Helper.parse(s1));
            try {
                s1 = Helper.in.readLine();
            } catch (IOException ex) {
            }
        }
        List<Expression> newProof = deductionTheorem(proof);
        for (Expression e : newProof) {
            Helper.out.println(e.asString());
        }
    }
}
