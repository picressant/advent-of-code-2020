package day;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day18 {

    public static void solve() {
        try {
            File myObj = new File("/home/pierre/work/perso/advent.input");
            Scanner myReader = new Scanner(myObj);

            List<String> lines = new ArrayList<>();
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                lines.add(data.trim());
            }

            long res = 0;
            for (String line : lines) {
                res += evaluate(line);
            }

            System.out.println("sum: " + res);


        }
        catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    private static long evaluate(String line) {
        while (line.contains("(")) {
            Pattern pattern = Pattern.compile("(\\([^\\(\\)]+\\))");
            Matcher matcher = pattern.matcher(line);

            if (matcher.find()) {
                String data = matcher.group(1);
                long value = evaluate(data.substring(1, data.length() - 1));
                line = line.substring(0, matcher.start()) + value + line.substring(matcher.end());
            }

        }

        while (line.contains("+")) {
            //Je trouve les *
            // Je prend avant et après et je remplace
            Pattern pattern = Pattern.compile("(\\d+\\s\\+\\s\\d+)");
            Matcher matcher = pattern.matcher(line);

            if (matcher.find()) {
                List<String> data = Arrays.asList(matcher.group(1).split(" "));
                long a = Long.parseLong(data.get(0));
                long b = Long.parseLong(data.get(2));
                char operator = data.get(1).charAt(0);

                long value = calculate(a, b, operator);
                line = line.substring(0, matcher.start()) + value + line.substring(matcher.end());
            }
        }

        while (line.contains(" ")) {
            List<String> elements = Arrays.asList(line.split(" "));

            long a = Long.parseLong(elements.get(0));
            long b = Long.parseLong(elements.get(2));
            char operator = elements.get(1).charAt(0);

            long value = calculate(a, b, operator);
            line = value + line.substring(elements.get(0).length() + elements.get(2).length() + 3); //two spaces and an operator
            line = line.trim();

        }

        return Long.parseLong(line);
    }


    private static long calculate(long a, long b, char operator) {
        if (operator == '-') {
            return a - b;
        }
        else if (operator == '+') {
            return a + b;
        }
        else if (operator == '/') {
            return a / b;
        }
        else {
            return a * b;
        }
    }

}
