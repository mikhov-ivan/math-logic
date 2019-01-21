package task4;

import java.util.*;

public class Lexer {

    private String curl;

    public String[] lex(String t) throws MyException {
        this.curl = t.replaceAll("\\s+", "");
        ArrayList<String> result = new ArrayList<>();
        int l = 0;
        int r = 0;
        boolean f;
        while (r < curl.length()) {
            ++r;
            f = true;
            String temp = curl.substring(l, r);
            for (Lexeme lex : Lexeme.values()) {
                if (temp.equals(lex.s)) {
                    result.add(temp);
                    l = r;
                    f = false;
                    break;
                }
            }
            if (f && Helper.isVariable(temp)) {
                while (r < curl.length() && Helper.isVariable(curl.substring(l, r + 1))) {
                    ++r;
                }
                temp = curl.substring(l, r);
                result.add(temp);
                l = r;
            }
        }
        if (l - r > 0) {
            throw new MyException();
        }
        return result.toArray(new String[result.size()]);
    }
}
