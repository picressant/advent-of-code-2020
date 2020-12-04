package day;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Day3 {

    public static void solve() {
        long res1 = solveFor(1, 1);
        long res2 = solveFor(3, 1);
        long res3 = solveFor(5, 1);
        long res4 = solveFor(7, 1);
        long res5 = solveFor(1, 2);

        long total = res1 * res2 * res3 * res4 * res5;

        System.out.println("all: " + total);
    }

    private static long solveFor(int right, int down) {

        int trees = 0;

        try {
            File myObj = new File("C:\\Workspaces\\advent.input");
            Scanner myReader = new Scanner(myObj);
            int pos = 0;
            int ignoringCount = 0;

            boolean ignoreFirstLine = true;

            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();

                if (ignoreFirstLine) {
                    ignoreFirstLine = false;
                    continue;
                }
                ignoringCount++;

                if (ignoringCount != down) {
                    continue;
                }

                ignoringCount = 0;
                pos = pos + right;

                while (pos >= data.length())
                    data = data + data;

                if (data.charAt(pos) == '#')
                    trees++;


            }
            myReader.close();

            System.out.println("total (" + right + ", " + down + "): " + trees);
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        return trees;
    }
}
