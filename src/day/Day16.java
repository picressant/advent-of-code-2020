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

            List<List<Integer>> validTickets = new ArrayList<>();
            validTickets.add(myTicket);
            for (List<Integer> ticket : otherTickets) {
                boolean valid = true;
                for (Integer val : ticket) {
                    if (!isAnyRuleValid(val, rules)) {
                        valid = false;
                        break;
                    }
                }

                if (valid) {
                    validTickets.add(ticket);
                }
            }

            System.out.println("validTickets: " + validTickets.size());

            Map<String, List<Integer>> ruleOrder = new HashMap<>();


            for (Map.Entry<String, List<Pair<Integer, Integer>>> entry : rules.entrySet()) {
                String ruleKey = entry.getKey();
                List<Pair<Integer, Integer>> rulePairs = entry.getValue();
                int pos = 0;
                ruleOrder.put(ruleKey, new ArrayList<>());
                System.out.println("testing " + ruleKey);
                while (pos < myTicket.size()) {
                    boolean allValid = true;
                    for (List<Integer> validOtherTicket : validTickets) {
                        if (!isRuleValid(validOtherTicket.get(pos), rulePairs)) {
                            System.out.println(validOtherTicket.get(pos) + " not valid for " + rulePairs);
                            allValid = false;
                        }
                    }

                    if (allValid) {
                        ruleOrder.get(ruleKey).add(pos);
                    }

                    pos++;

                }
            }
            System.out.println(ruleOrder);

            while (!allAtOne(ruleOrder)) {
                for (Map.Entry<String, List<Integer>> entry : ruleOrder.entrySet()) {
                    if (entry.getValue().size() == 1) {
                        System.out.println("cleaning " + entry.getValue());
                        for (Map.Entry<String, List<Integer>> entryIn : ruleOrder.entrySet()) {
                            if (!entry.getKey().equals(entryIn.getKey())) {
                                entryIn.getValue().remove(entry.getValue().get(0));
                            }
                        }
                    }
                }
            }

            System.out.println(ruleOrder);
            long res = 1;
            for (Map.Entry<String, List<Integer>> entry : ruleOrder.entrySet()) {
                String key = entry.getKey();
                List<Integer> value = entry.getValue();
                if (key.startsWith("departure")) {
                    res *= myTicket.get(value.get(0));
                }
            }

            System.out.println("res: " + res);
        }
        catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

    }

    private static boolean allAtOne(Map<String, List<Integer>> ruleOrder) {
        return ruleOrder.keySet().stream().noneMatch(key -> ruleOrder.get(key).size() > 1);
    }

    private static boolean isAnyRuleValid(int val, Map<String, List<Pair<Integer, Integer>>> rules) {
        for (String rule : rules.keySet()) {
            if (isRuleValid(val, rules.get(rule))) {
                return true;
            }
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
