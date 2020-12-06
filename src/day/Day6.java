package day;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Day6 {

    public static void solve() {
        try {
            File myObj = new File("/home/pierre/work/perso/advent.input");
            Scanner myReader = new Scanner(myObj);

            List<String> currentLetters = new ArrayList<>();
            int count = 0;
            boolean addAll = true;

            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();

                if (data.trim().equals("")) {
                    count = count + currentLetters.size();
                    System.out.println(currentLetters + " count " + count);
                    currentLetters = new ArrayList<>();
                    addAll = true;
                }
                else {
                    List<String> splitted = Arrays.asList(data.trim().split(""));
                    if (addAll) {
                        currentLetters.addAll(splitted);
                        addAll = false;
                    }
                   else {
                        currentLetters.removeIf(s -> !splitted.contains(s));
                    }
                }

            }
            myReader.close();

            count = count + currentLetters.size();
            System.out.println(currentLetters + " count " + count);
            System.out.println("total: " + count);
        }
        catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}
