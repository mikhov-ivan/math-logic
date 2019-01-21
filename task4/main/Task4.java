package task4;

import java.io.*;

public class Task4 {

    public static void main(String[] args) throws FileNotFoundException, IOException, MyException {
        Helper.setParser(new Parser());
        Solution ans = new Deduction();
        Helper.input = new BufferedReader(new FileReader("task4.in"));
        Helper.output = new PrintWriter(new FileWriter("task4.out"));
        ans.solve();
        Helper.input.close();
        Helper.output.println();
        Helper.output.close();
    }
}
