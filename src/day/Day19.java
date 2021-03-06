package day;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Day19 {

    static class Rule {
        String value = "";
        List<List<Integer>> otherRule;

        public Rule(String value) {
            this.value = value;
        }

        public Rule(List<List<Integer>> rules) {
            this.otherRule = rules;
        }
    }

    public static void solve() {
        try {
            File myObj = new File("/home/pierre/work/perso/advent.input");
            Scanner myReader = new Scanner(myObj);

            boolean readingRules = true;
            List<String> messages = new ArrayList<>();
            Map<Integer, Rule> rules = new HashMap<>();

            while (myReader.hasNextLine()) {
                String data = myReader.nextLine().trim();

                if (data.equals("")) {
                    readingRules = false;
                }
                else {
                    if (readingRules) {
                        Pattern pattern = Pattern.compile("^(\\d+):\\s(.+)$");
                        Matcher matcher = pattern.matcher(data);

                        if (matcher.find()) {
                            int ruleId = Integer.parseInt(matcher.group(1));
                            String ruleData = matcher.group(2);
                            if (!ruleData.contains("\"")) {
                                List<String> rulesAsString = Arrays.asList(ruleData.split("\\|"));
                                List<List<Integer>> innerRules = new ArrayList<>();
                                rulesAsString.forEach(r -> {
                                    innerRules.add(Arrays.stream(r.trim().split(" ")).map(Integer::parseInt).collect(Collectors.toList()));
                                });
                                rules.put(ruleId, new Rule(innerRules));
                            }
                            else {
                                rules.put(ruleId, new Rule(ruleData.substring(1, ruleData.length() - 1)));
                            }
                        }
                    }
                    else {
                        messages.add(data);
                    }
                }
            }

            String regex = "^" + evaluateRule(rules, 0) + "$";
            Pattern pattern = Pattern.compile(regex);

            messages.stream().filter(pattern.asPredicate()).forEach(System.out::println);
            System.out.println("matches: " + messages.stream().filter(pattern.asPredicate()).count());

            System.out.println("regex: " + regex);

        }
        catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    private static String evaluateRule(Map<Integer, Rule> rules, int ruleId) {
        Rule rule = rules.get(ruleId);
        if (!rule.value.equals("")) {
            return rule.value;
        }
        else {
            if (ruleId != 11) {
                String res = "(";
                res += rule.otherRule.stream().map(innerRules -> evaluateOtherRules(innerRules, rules)).collect(Collectors.joining("|"));
                res += ")";
                if (ruleId == 8) {
                    res += "+";
                }

                return res;
            }
            else {
                String res = "(";
                res += evaluateOtherRules(Arrays.asList(42, 31), rules);
                res += "|";
                res += "(";
                for (int i = 1; i < 50; i++) {
                    res += evaluateOtherRules(Arrays.asList(42),rules) + "{" + i + "}";
                    res += evaluateOtherRules(Arrays.asList(31), rules) + "{" + i + "}";
                    res += "|";
                }
                res = removeLastCharacter(res);
                res += ")";
                res += ")";

                return res;
            }
        }
    }

    private static String evaluateOtherRules(List<Integer> otherRules, Map<Integer, Rule> rules) {
        String res = "";
        for (Integer ruleId : otherRules) {
            res += evaluateRule(rules, ruleId);
        }

        return res;
    }
    public static String removeLastCharacter(String str) {
        String result = Optional.ofNullable(str)
                .filter(sStr -> sStr.length() != 0)
                .map(sStr -> sStr.substring(0, sStr.length() - 1))
                .orElse(str);
        return result;
    }
}
