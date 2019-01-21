package task3;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

public class Proof extends Solution {

    Map<String, Variable> vars = new HashMap<>();
    Map<String, Expression> subst = new HashMap<>();
    List<Expression> exProofs = new ArrayList<>();
    List<Expression> exRules = new ArrayList<>();
    Deduction deduction;

    public Proof() throws FileNotFoundException {
        this.deduction = new Deduction();
    }

    private List<Expression> getProof(Expression st, List<Expression> sups, int curPos) {
        ArrayList<Expression> proof = new ArrayList<>();
        Expression v = sups.get(curPos);
        Expression notV = new Not(v);

        List<Expression> proof1 = new ArrayList<>();
        List<Expression> proof2 = new ArrayList<>();

        if (curPos == sups.size() - 1) {
            deduction.setSupposes(sups);
            deduction.setTrue(exRules);
            proof1.addAll(exRules);
            proof1.addAll(st.curProof(sups));
            proof1 = deduction.deductionTheorem(proof1);
            sups.set(curPos, notV);
            deduction.setSupposes(sups);
            deduction.setTrue(exRules);
            proof2.addAll(exRules);
            proof2.addAll(st.curProof(sups));
            proof2 = deduction.deductionTheorem(proof2);
        } else {
            proof1 = getProof(st, sups, curPos + 1);
            deduction.setSupposes(sups.subList(0, curPos + 1));
            deduction.setTrue(exRules);
            proof1 = deduction.deductionTheorem(proof1);
            sups.set(curPos, notV);
            proof2 = getProof(st, sups, curPos + 1);
            deduction.setSupposes(sups.subList(0, curPos + 1));
            deduction.setTrue(exRules);
            proof2 = deduction.deductionTheorem(proof2);
        }
        proof.addAll(proof1);
        proof.addAll(proof2);
        sups.set(curPos, v);
        HashMap<String, Expression> map = new HashMap<>();
        map.put("1", v);
        map.put("2", notV);
        map.put("3", st);
        proof.add(Axioms.a8.substitute(map));
        proof.add(new Then(new Then(notV, st), new Then(new Or(v, notV), st)));
        proof.add(new Then(new Or(v, notV), st));
        proof.add(st);
        return proof;
    }

    @Override
    public void solve() {
        Expression theorem = null;
        try {
            theorem = Helper.parse(Helper.in.readLine());
        } catch (IOException ex) {
        }
        vars = theorem.getVars();
        int n = (int) Math.pow(2, vars.size());
        List<Variable> variables = new ArrayList<>(vars.values());
        for (int i = 0; i < n; i++) {
            int k = i;
            for (Expression v : vars.values()) {
                ((Variable) v).setValue(k % 2 == 1);
                k /= 2;
            }
            theorem = theorem.subAndCopy(vars);
            boolean f = theorem.calc();
            if (!f) {
                StringBuilder sb = new StringBuilder("Statement is false with ");
                for (int j = 0; j < variables.size(); j++) {
                    sb.append(variables.get(j).getName()).append("=").append(variables.get(j).getCurrentValue() ? "True" : "False");
                    if (j != variables.size() - 1) {
                        sb.append("; ");
                    }
                }
                Helper.out.println(sb);
                Helper.out.close();
                System.exit(0);
            }
        }

        for (Expression v : vars.values()) {
            exProofs.addAll(exRule((Variable) v));
            Expression tnd = new Or(v, new Not(v));
            exRules.add(tnd);
        }
        List<Expression> hypothesis = new ArrayList<Expression>(variables);
        List<Expression> proof = getProof(theorem, hypothesis, 0);

        for (Expression e : exProofs) {
            Helper.out.println(e.toString());
        }
        for (Expression e : exRules) {
            Helper.out.println(e.toString());
        }
        for (Expression e : proof) {
            Helper.out.println(e.toString());
        }
        deduction.output.close();
    }

    private ArrayList<Expression> exRule(Variable v) {
        subst.put("1", v);
        Not notV = new Not(v);
        ArrayList<Expression> result = new ArrayList<>();
        result.add(new Then(new NumExpr(1), new Or(new NumExpr(1), new Not(new NumExpr(1)))).subst(subst));
        for (Ctrpstn s : Ctrpstn.values()) {
            result.add(s.replace(v, new Or(v, notV)));
        }
        result.add(new Then(new Not(new Or(new NumExpr(1), new Not(new NumExpr(1)))), new Not(new NumExpr(1))).subst(subst));
        result.add(new Then(new Not(new NumExpr(1)), new Or(new NumExpr(1), new Not(new NumExpr(1)))).subst(subst));
        for (Ctrpstn s : Ctrpstn.values()) {
            result.add(s.replace(new Not(v), new Or(v, notV)));
        }
        result.add(new Then(new Not(new Or(new NumExpr(1), new Not(new NumExpr(1)))), new Not(new Not(new NumExpr(1)))).subst(subst));
        result.add(new Then(new Then(new Not(new Or(new NumExpr(1), new Not(new NumExpr(1)))), new Not(new NumExpr(1))), new Then(new Then(new Not(new Or(new NumExpr(1), new Not(new NumExpr(1)))), new Not(new Not(new NumExpr(1)))), new Not(new Not(new Or(new NumExpr(1), new Not(new NumExpr(1))))))).subst(subst));
        result.add(new Then(new Then(new Not(new Or(new NumExpr(1), new Not(new NumExpr(1)))), new Not(new Not(new NumExpr(1)))), new Not(new Not(new Or(new NumExpr(1), new Not(new NumExpr(1)))))).subst(subst));
        result.add(new Not(new Not(new Or(new NumExpr(1), new Not(new NumExpr(1))))).subst(subst));
        result.add(new Then(new Not(new Not(new Or(new NumExpr(1), new Not(new NumExpr(1))))), new Or(new NumExpr(1), new Not(new NumExpr(1)))).subst(subst));
        result.add(new Or(new NumExpr(1), new Not(new NumExpr(1))).subst(subst));
        return result;
    }
}
