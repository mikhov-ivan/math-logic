package task3;

import java.util.ArrayList;

public class Lexer {

    private String spart;

    public String[] determineLex(String t) {
        this.spart = t.replaceAll("\\s+", "");
        ArrayList<String> result = new ArrayList<>();
        int l = 0;
        int r = 0;
        boolean f;
        while (r < spart.length()) {
            ++r;
            f = true;
            String temp = spart.substring(l, r);
            for (Lexeme lex : Lexeme.values()) {
                if (temp.equals(lex.s)) {
                    result.add(temp);
                    l = r;
                    f = false;
                    break;
                }
            }
            if (f && Helper.isVariable(temp)) {
                while (r < spart.length() && Helper.isVariable(spart.substring(l, r + 1))) {
                    ++r;
                }
                temp = spart.substring(l, r);
                result.add(temp);
                l = r;
            }
        }
        return result.toArray(new String[result.size()]);
    }
}
