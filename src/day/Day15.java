package day;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Day15 {

    public static void solve() {
        try {
            File myObj = new File("/home/pierre/work/perso/advent.input");
            Scanner myReader = new Scanner(myObj);

            int lastSpoken = 0;
            int turn = 0;
            Map<Integer, Integer> spoken = new HashMap<>();
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                String[] split = data.split(",");
                for (int i = 0, splitLength = split.length; i < splitLength - 1; i++) {
                    int n = Integer.parseInt(split[i]);
                    spoken.put(n, i + 1);
                }
                lastSpoken = Integer.parseInt(split[split.length-1]);

                turn = split.length;
            }
            myReader.close();

            while (turn < 30000000) {
                turn++;
                lastSpoken = getNextNumber(turn, lastSpoken, spoken);
                //System.out.println("turn: " + turn + " / spoken: " + lastSpoken);
            }

            System.out.println("turn: " + turn + " / lastSpoken: " + lastSpoken);



        }
        catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    private static int getNextNumber(int turn, int lastSpoken, Map<Integer, Integer> spoken) {
        int spokenNumber;
        if (spoken.containsKey(lastSpoken)) {
            if (spoken.get(lastSpoken) == turn - 1)
                spokenNumber = 1;
            else
                spokenNumber = turn - 1 - spoken.get(lastSpoken);
        }
        else {
            spokenNumber = 0;
        }

        spoken.put(lastSpoken, turn-1);
        return spokenNumber;
    }
}
