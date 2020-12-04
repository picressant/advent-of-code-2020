package day;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day2 {

    public static void solve() {
        try {
            File myObj = new File("C:\\Workspaces\\advent.input");
            Scanner myReader = new Scanner(myObj);

            int total = 0;
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                String pattern = "(\\d+)-(\\d+)\\s(\\S):\\s(\\S+)";
                Pattern r = Pattern.compile(pattern);

                Matcher matcher = r.matcher(data);
                if (matcher.find()) {
                    int pos1 = Integer.parseInt(matcher.group(1));
                    int pos2 = Integer.parseInt(matcher.group(2));
                    char letter = matcher.group(3).charAt(0);
                    String password = matcher.group(4);

                    if (password.charAt(pos1-1) == letter && password.charAt(pos2-1) != letter)
                        total++;
                    else if (password.charAt(pos2-1) == letter && password.charAt(pos1-1) != letter)
                        total++;
                }
            }
            myReader.close();

            System.out.println("total: " + total);
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}
