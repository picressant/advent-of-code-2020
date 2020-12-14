package day;

import javafx.util.Pair;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day14 {

    public static void solve() {
        try {
            File myObj = new File("/home/pierre/work/perso/advent.input");
            Scanner myReader = new Scanner(myObj);

            Map<Integer, String> memories = new HashMap<>();
            String currentMask = "";

            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                if (data.startsWith("mask = ")) {
                    currentMask = data.substring("mask = ".length());
                }
                else {
                    String pattern = ".+\\[(\\d+)\\]\\D+(\\d+)$";
                    Pattern r = Pattern.compile(pattern);

                    Matcher matcher = r.matcher(data);
                    if (matcher.find()) {
                        int memoryPlace = Integer.parseInt(matcher.group(1));
                        long value = Long.parseLong(matcher.group(2));

                        String binary = Long.toBinaryString(value);
                        binary = String.format("%1$" + currentMask.length() + "s", binary).replace(' ', '0');

                        memories.put(memoryPlace, applyMask(binary, currentMask));

                    }
                }

            }
            myReader.close();

            System.out.println(memories);
            long total = memories.entrySet().stream().mapToLong(integerStringEntry -> Long.parseLong(integerStringEntry.getValue(), 2)).sum();
            System.out.println("total: " + total);

        }
        catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    private static String applyMask(String value, String mask) {
        //System.out.println("applying " + mask + " to " + value);
        String newValue = "";
        for(int i = 0; i < mask.length(); i++) {
            if (mask.charAt(i) == 'X')
                newValue += value.substring(i, i+1);
            else
                newValue += mask.substring(i, i+1);
        }

        return newValue;
    }
}
