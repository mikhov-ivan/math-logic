package task3;

import java.io.*;

public class Task3 {

    private static Solution ans;

    public static void main(String[] args) throws FileNotFoundException, IOException {
        ans = new Proof();
        Helper.in = new BufferedReader(new FileReader("task3.in"));
        Helper.out = new PrintWriter(new FileWriter("task3.out"));
        ans.solve();
        Helper.in.close();
        Helper.out.println();
        Helper.out.close();
    }
}
