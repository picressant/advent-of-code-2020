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

            String regex = "^" + evaluateRule(rules, rules.get(0)) + "$";
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

    private static String evaluateRule(Map<Integer, Rule> rules, Rule rule) {
        if (!rule.value.equals("")) {
            return rule.value;
        }
        else {
            String res = "(";
            res += rule.otherRule.stream().map(innerRules -> evaluateOtherRules(innerRules, rules)).collect(Collectors.joining("|"));
            res += ")";

            return res;
        }
    }

    private static String evaluateOtherRules(List<Integer> otherRules, Map<Integer, Rule> rules) {
        String res = "";
        for (Integer ruleId : otherRules) {
            res += evaluateRule(rules, rules.get(ruleId));
        }

        return res;
    }

    // Rule --> soit une liste d'autre numéro
    // --> Soit une lettre

    //pour construire la rule 0
    // Si on a une lettre

    //Pour chaque rule avec un choix, on ajoute des parenthèses. A la fin on doit avoir une regex
}
