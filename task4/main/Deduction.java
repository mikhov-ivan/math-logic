package task4;

import java.io.*;
import java.util.*;
import java.util.regex.Pattern;

public class Deduction extends Solution {

    public PrintWriter tout;
    private Expression alpha;
    private List<Expression> suppose = new ArrayList<>();
    private Map<String, Expression> trueSt = new HashMap<>();
    private final Set<String> supVars = new HashSet<>();
    private final Map<String, Expression> map = new HashMap<>();
    private final Map<Expression, ArrayList<Expression>> mps = new HashMap<>();
    private boolean success;

    public Deduction() throws FileNotFoundException {
        tout = new PrintWriter("task4.out");
    }

    public Deduction(List<Expression> sup, Expression alpha) {
        this.suppose = sup;
        this.suppose.add(alpha);
    }

    public void setHypos(List<Expression> sup) {
        this.suppose = new ArrayList<>(sup);
        this.alpha = this.suppose.remove(sup.size() - 1);
    }

    public void setProofed(List<Expression> tSt) {
        this.trueSt = new HashMap<>();
        for (Expression e : tSt) {
            this.trueSt.put(e.toString(), e);
        }
    }

    public List<Expression> deductionTheorem(List<Expression> proof) throws MyException {
        List<Expression> result = new ArrayList<>();
        mps.clear();
        success = false;
        for (Expression e : trueSt.values()) {
            result.add(e);
            result.add(new Implication(e, new Implication(alpha, e)));
            Expression temp = new Implication(alpha, e);
            result.add(temp);
            Helper.toMps(mps, e);
            Helper.toMps(mps, temp);
        }
        supVars.addAll(alpha.getFreeVars());
        for (Expression e : suppose) {
            supVars.addAll(e.getFreeVars());
        }

        for (int l = 0; l < proof.size(); l++) {
            Expression expr = proof.get(l);
            boolean f = false;
            for (Expression e : suppose) {
                if (e.compare(expr)) {
                    f = true;
                    break;
                }
            }
            if (!f) {
                if (expr instanceof Implication && ((Implication) expr).getLeft() instanceof ForAll) {
                    Term var = ((ForAll) ((Implication) expr).getLeft()).var;
                    try {
                        ((ForAll) ((Implication) expr).getLeft()).getOperand().setQuantifiers(new HashSet<String>());
                        ((Implication) expr).getRight().setQuantifiers(new HashSet<String>());
                        int freeCount = ((ForAll) ((Implication) expr).getLeft()).getOperand().markVars(var.getName());
                        Set<Pair<Term, Term>> replaced = ((Implication) expr).getRight().replaceVars(((ForAll) ((Implication) expr).getLeft()).getOperand());
                        boolean cond = true;
                        if (freeCount == 0) {
                            cond = false;
                            if (((ForAll) ((Implication) expr).getLeft()).getOperand().equalTrees(((Implication) expr).getRight())) {
                                f = true;
                            }
                        }
                        Term temp = null;
                        if (cond) {
                            for (Pair<Term, Term> pair : replaced) {
                                if (temp == null) {
                                    temp = pair.getValue();
                                } else {
                                    if (!(temp.compare(pair.getValue()))) {
                                        cond = false;
                                        break;
                                    }
                                }
                            }
                        }
                        if (temp == null) {
                            cond = false;
                        }
                        if (cond) {
                            for (Pair<Term, Term> pair : replaced) {
                                Term t = pair.getValue();
                                List<String> names = t.getTerms();
                                for (String s : names) {
                                    if (t.quantifiers.contains(s)) {
                                        cond = false;
                                        incorrectSt(l + 1);
                                        break;
                                    }
                                }
                            }
                        }
                        if (cond) {
                            if (supVars.contains(var.getName())) {
                                incorrectSt(l + 1);
                            }
                            f = true;
                        }
                    } catch (MyException e) {
                    }
                }
            }
            if (!f) {
                if (expr instanceof Implication && ((Implication) expr).getRight() instanceof Exists) {
                    Term var = ((Exists) ((Implication) expr).getRight()).var;
                    try {
                        ((Exists) ((Implication) expr).getRight()).getOperand().setQuantifiers(new HashSet<String>());
                        ((Implication) expr).getLeft().setQuantifiers(new HashSet<String>());
                        int freeCount = ((Exists) ((Implication) expr).getRight()).getOperand().markVars(var.getName());
                        Set<Pair<Term, Term>> replaced = ((Implication) expr).getLeft().replaceVars(((Exists) ((Implication) expr).getRight()).getOperand());
                        boolean cond = true;
                        if (freeCount == 0) {
                            cond = false;
                            if (((Exists) ((Implication) expr).getRight()).getOperand().equalTrees(((Implication) expr).getLeft())) {
                                f = true;
                            }
                        }
                        Term temp = null;
                        if (cond) {
                            for (Pair<Term, Term> pair : replaced) {
                                if (temp == null) {
                                    temp = pair.getValue();
                                } else {
                                    if (!(temp.compare(pair.getValue()))) {
                                        cond = false;
                                        break;
                                    }
                                }
                            }
                        }
                        if (temp == null) {
                            cond = false;
                        }
                        if (cond) {
                            for (Pair<Term, Term> pair : replaced) {
                                Term t = pair.getValue();
                                List<String> names = t.getTerms();
                                for (String s : names) {
                                    if (t.quantifiers.contains(s)) {
                                        cond = false;
                                        incorrectSt(l + 1);
                                        break;
                                    }
                                }
                            }
                        }
                        if (cond) {
                            if (supVars.contains(var.getName())) {
                                incorrectSt(l + 1);
                            }
                            f = true;
                        }
                    } catch (MyException e) {
                    }
                }
            }
            if (!f) {
                for (Axioms scheme : Axioms.values()) {
                    if (scheme.compare(expr)) {
                        f = true;
                        break;
                    }
                }
            }
            if (f) {
                result.add(expr);
                result.add(new Implication(expr, new Implication(alpha, expr)));
                Expression temp = new Implication(alpha, expr);
                result.add(temp);
            }
            if (!f && expr.equalTrees(alpha)) {
                result.addAll(Helper.proofAA(alpha));
                f = true;
            }
            if (trueSt.containsKey(expr.toString())) {
                f = true;
            }

            if (!f) {
                if (mps.get(expr) != null) {
                    for (Expression e : mps.get(expr)) {
                        if (trueSt.get(e.toString()) != null) {
                            map.put("1", alpha);
                            map.put("2", e);
                            map.put("3", expr);
                            result.add(new Implication(new Implication(new NumExpr(1), new NumExpr(2)), new Implication(new Implication(new NumExpr(1), new Implication(new NumExpr(2), new NumExpr(3))), new Implication(new NumExpr(1), new NumExpr(3)))).subst(map));
                            result.add(new Implication(new Implication(new NumExpr(1), new Implication(new NumExpr(2), new NumExpr(3))), new Implication(new NumExpr(1), new NumExpr(3))).subst(map));
                            result.add(new Implication(alpha, expr));
                            f = true;
                            break;
                        }
                    }
                }
            }
            if (!f) {
                if (expr instanceof Implication && ((Implication) expr).getRight() instanceof ForAll) {
                    Expression prev = trueSt.get(new Implication(((Implication) expr).getLeft(), ((ForAll) ((Implication) expr).getRight()).getOperand()).toString());
                    Term var = ((ForAll) ((Implication) expr).getRight()).var;
                    boolean cond = (prev != null);
                    if (cond) {
                        cond = cond && !((Implication) prev).getLeft().getFreeVars().contains(var.getName());
                        if (!cond) {
                            incorrectSt(l + 1);
                        }
                        cond = cond && !supVars.contains(var.getName());
                        if (!cond) {
                            incorrectSt(l + 1);
                        }
                        if (cond) {
                            for (ForAllRule rule : ForAllRule.values()) {
                                if (rule == ForAllRule.expr80) {
                                    boolean k = true;
                                }
                                result.add(rule.replace(alpha,
                                        ((Implication) expr).getLeft(),
                                        ((ForAll) ((Implication) expr).getRight()).getOperand()));
                            }
                            f = true;
                        }
                    }
                }
            }

            if (!f) {
                if (expr instanceof Implication && ((Implication) expr).getLeft() instanceof Exists) {
                    Expression prev = trueSt.get(new Implication(((Exists) ((Implication) expr).getLeft()).getOperand(), ((Implication) expr).getRight()).toString());
                    Term var = ((Exists) ((Implication) expr).getLeft()).var;
                    boolean cond = (prev != null);
                    if (cond) {
                        cond = cond && !((Implication) prev).getRight().getFreeVars().contains(var.getName());
                        if (!cond) {
                            incorrectSt(l + 1);
                        }
                        cond = cond && !supVars.contains(var.getName());
                        if (!cond) {
                            incorrectSt(l + 1);
                        }
                        if (cond) {
                            for (ExistsRule rule : ExistsRule.values()) {
                                result.add(rule.replace(alpha, ((Exists) ((Implication) expr).getLeft()).getOperand(), ((Implication) expr).getRight()));
                            }
                            f = true;
                        }
                    }
                }
            }

            if (!f) {
                incorrectSt("" + expr.asString());
                break;
            } else {
                trueSt.put(expr.toString(), expr);
                Helper.toMps(mps, expr);
                Helper.toMps(mps, new Implication(alpha, expr));
            }
            success = true;
        }
        return result;
    }

    private Expression findSup(Term v) {
        if (alpha.getFreeVars().contains(v.getName())) {
            return alpha;
        }
        for (Expression e : suppose) {
            if (e.getFreeVars().contains(v.getName())) {
                return e;
            }
        }
        return null;
    }

    @Override
    public void solve() throws IOException, MyException {
        String[] temp = Helper.input.readLine().split(Pattern.quote("|-"));
        String s = temp[0];
        int l = 0;
        int r = 0;
        while (l < s.length()) {
            r++;
            Expression tempExpr;
            try {
                String ss = s.substring(l, r);
                tempExpr = Helper.parseExcept(ss);
            } catch (Exception e) {
                continue;
            }
            if (r == s.length() || s.charAt(r) == ',') {
                l = r + 1;
                r = l;
                suppose.add(tempExpr);
            }
        }
        alpha = suppose.remove(suppose.size() - 1);
        List<Expression> proof = new ArrayList<>();
        String s1 = Helper.input.readLine();
        while (s1 != null && !s1.replace("\\s+", "").isEmpty()) {
            Expression e = Helper.parse(s1);
            proof.add(e);
            s1 = Helper.input.readLine();
        }
        List<Expression> newProof = deductionTheorem(proof);
        if (success) {
            for (Expression e : newProof) {
                Helper.output.println(e.asString());
            }
        }
    }

    public void incorrectSt(int row) {
        if (row > -1) {
            Helper.output.println("[INCORRECT] Proof failed on (" + row + ")");
        } else {
            Helper.output.println("[INCORRECT] Proof failed");
        }
        Helper.output.close();
        System.exit(0);
    }

    public void incorrectSt(String s) {
        Helper.output.println("[INCORRECT] Proof failed on (" + s + ")"); 
        Helper.output.close();
        System.exit(0);
    }
}
