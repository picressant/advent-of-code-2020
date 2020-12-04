package day;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day4 {

    private static List<String> requiredFields = Arrays.asList("byr", "iyr", "eyr", "hgt", "hcl", "ecl", "pid");

    public static void solve() {
        int valid = 0;
        try {
            File myObj = new File("C:\\Workspaces\\advent.input");
            Scanner myReader = new Scanner(myObj);

            List<String> currentData = new ArrayList<>();


            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();

                if (data.trim().equals("")) {
                    if (isPassportValid(currentData)) {
                        valid++;
                    }
                    currentData = new ArrayList<>();
                } else {
                    List<String> couples = Arrays.asList(data.split(" "));
                    currentData.addAll(couples);
                }
            }
            myReader.close();

            if (isPassportValid(currentData)) {
                valid++;
            }

        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        System.out.println("valid: " + valid);
    }

    private static boolean isPassportValid(List<String> data) {

        System.out.println(data);
        Map<String, String> passportData = new HashMap<>();

        data.forEach(couple -> {
            passportData.put(couple.split(":")[0], couple.split(":")[1]);
        });


        for (String requiredKey : requiredFields) {
            if (!passportData.containsKey(requiredKey))
                return false;
            else if (!isDataValid(requiredKey, passportData.get(requiredKey)))
                return false;
        }

        return true;
    }

    private static boolean isDataValid(String key, String value) {
        if (key.equals("byr")) {
            return checkYear(value, 1920, 2002);
        } else if (key.equals("iyr")) {
            return checkYear(value, 2010, 2020);
        } else if (key.equals("eyr")) {
            return checkYear(value, 2020, 2030);
        } else if (key.equals("hgt")) {
            return checkHeight(value);
        } else if (key.equals("hcl")) {
            return checkHair(value);
        } else if (key.equals("ecl")) {
            return checkEye(value);
        } else if (key.equals("pid")) {
            return checkPassportId(value);
        } else if (key.equals("cid")) {
            return true;
        }

        return false;
    }

    private static boolean checkPassportId(String data) {
        String pattern = "^\\d{9}$";
        Pattern r = Pattern.compile(pattern);

        return r.matcher(data).matches();
    }

    private static boolean checkEye(String data) {
        List<String> valid = Arrays.asList("amb", "blu", "brn", "gry", "grn", "hzl", "oth");
        return valid.contains(data);
    }

    private static boolean checkHair(String data) {
        String pattern = "^#[0-9a-f]{6}$";
        Pattern r = Pattern.compile(pattern);

        return r.matcher(data).matches();
    }

    private static boolean checkYear(String data, int min, int max) {
        String pattern = "^\\d\\d\\d\\d$";
        Pattern r = Pattern.compile(pattern);

        if (!r.matcher(data).matches()) {
            return false;
        }

        int value = Integer.parseInt(data);
        return (value <= max && value >= min);
    }

    private static boolean checkHeight(String data) {
        String pattern = "^(\\d+)(\\S+)$";
        Pattern r = Pattern.compile(pattern);

        Matcher matcher = r.matcher(data);
        if (matcher.find()) {
            int size = Integer.parseInt(matcher.group(1));
            String unit = matcher.group(2);

            if (unit.equals("cm"))
                return (size >= 150 && size <= 193);
            else
                return (size >= 59 && size <= 76);
        }

        return false;
    }
}
