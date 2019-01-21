package task5;

import java.io.*;

public class Task5 {

    public static void main(String[] args) throws FileNotFoundException, IOException {
        BufferedReader input = new BufferedReader(new FileReader("task5.in"));
        boolean flag = true;
        String s;
        Check deductionWork = new Check("");
        while ((s = input.readLine()) != null) {
            if (s.length() == 0) {
                continue;
            }
            String result = deductionWork.add(new Parser(s).parse());
            if (result.equals("true")) {
                continue;
            }
            System.out.println(result + "\n");
            flag = false;
            break;
        }

        if (flag) {
            System.out.println("[CORRECT] Each statement is correct");
        }
        
        input.close();
    }
}
