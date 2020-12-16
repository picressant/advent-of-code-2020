package day;

import javafx.util.Pair;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Day16 {

    public static void solve() {
        try {
            File myObj = new File("/home/pierre/work/perso/advent.input");
            Scanner myReader = new Scanner(myObj);

            boolean isReadingTicket = false;
            boolean isReadingOtherTickets = false;

            List<Integer> myTicket = new ArrayList<>();
            List<List<Integer>> otherTickets = new ArrayList<>();
            Map<String, List<Pair<Integer, Integer>>> rules = new HashMap<>();
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();

                if ("your ticket:".equals(data.trim())) {
                    isReadingTicket = true;
                }
                else if ("nearby tickets:".equals(data.trim())) {
                    isReadingOtherTickets = true;
                    isReadingTicket = false;
                }
                else if (!data.trim().equals("")) {
                    if (isReadingTicket) {
                        myTicket = Arrays.stream(data.split(",")).map(Integer::parseInt).collect(Collectors.toList());
                    }
                    else if (isReadingOtherTickets) {
                        otherTickets.add(Arrays.stream(data.split(",")).map(Integer::parseInt).collect(Collectors.toList()));
                    }
                    else {
                        String pattern = "(.+):\\s(\\d+)-(\\d+)\\D+(\\d+)-(\\d+)$";
                        Pattern r = Pattern.compile(pattern);

                        Matcher matcher = r.matcher(data);
                        if (matcher.find()) {
                            String ruleKey = matcher.group(1);
                            Pair<Integer, Integer> fRule = new Pair<>(Integer.parseInt(matcher.group(2)), Integer.parseInt(matcher.group(3)));
                            Pair<Integer, Integer> sRule = new Pair<>(Integer.parseInt(matcher.group(4)), Integer.parseInt(matcher.group(5)));

                            rules.put(ruleKey, Arrays.asList(fRule, sRule));
                        }
                    }
                }
            }
            myReader.close();

            System.out.println("rules: " + rules);
            System.out.println("ticket: " + myTicket);
            System.out.println("others: " + otherTickets);

            int diff = 0;
            for (List<Integer> ticket : otherTickets) {
                for (Integer val : ticket) {
                    if (!isAnyRuleValid(val, rules))
                        diff += val;
                }
            }

            System.out.println("res: " + diff);
        }
        catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    private static  boolean isAnyRuleValid(int val, Map<String, List<Pair<Integer, Integer>>> rules) {
        for (String rule : rules.keySet()) {
            if (isRuleValid(val, rules.get(rule)))
                return true;
        }

        return false;
    }

    private static boolean isRuleValid(int val, List<Pair<Integer, Integer>> rules) {
        boolean res = false;
        for (Pair<Integer, Integer> rule : rules) {
            if (val >= rule.getKey() && val <= rule.getValue()) {
                res = true;
            }
        }

        return res;
    }
}
