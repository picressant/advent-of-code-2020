package day;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day14 {

    public static void solve() {
        try {
            File myObj = new File("/home/pierre/work/perso/advent.input");
            Scanner myReader = new Scanner(myObj);

            Map<String, Long> memories = new HashMap<>();
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

                        System.out.println("memory:" + memoryPlace + " = " + memoryPlace + " (" + Long.toBinaryString(memoryPlace) + ")");
                        String binary = Long.toBinaryString(memoryPlace);
                        binary = String.format("%1$" + currentMask.length() + "s", binary).replace(' ', '0');


                        applyMask(binary, currentMask).forEach(address -> memories.put(address, value));

                    }
                }

            }
            myReader.close();

            long total = memories.values().stream()
                    .mapToLong(val -> val).sum();
            System.out.println("total: " + total);

        }
        catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    private static HashSet<String> applyMask(String value, String mask) {
        HashSet<String> values = new HashSet<>();
        values.add(value);
        System.out.println("Writting " + value);
        return applyMaskPos(0, mask, values);
    }

    private static String changeChar(String value, char c, int pos) {
        StringBuilder sb = new StringBuilder(value);
        sb.setCharAt(pos, c);
        return sb.toString();
    }

    private static HashSet<String> applyMaskPos(int pos, String mask, HashSet<String> values) {
        HashSet<String> newValues = new HashSet<>();
        for (String value : values) {
            if (mask.charAt(pos) == '1') {
                newValues.add(changeChar(value, '1', pos));
            }
            else if (mask.charAt(pos) == 'X') {
                newValues.add(changeChar(value, '1', pos));
                newValues.add(changeChar(value, '0', pos));
            }
            else {
                newValues.add(value);
            }
        }

        pos++;
        if (pos < mask.length()) {
            return applyMaskPos(pos, mask, newValues);
        }
        else {
            return newValues;
        }


    }
}
