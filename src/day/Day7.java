package day;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day7 {

    static class ContainingBag {
        public String bagColor;
        public int number;

        public ContainingBag(String color, int number) {
            this.bagColor = color;
            this.number = number;
        }
    }

    public static void solve() {
        try {
            File myObj = new File("C:\\Workspaces\\advent.input");
            Scanner myReader = new Scanner(myObj);

            Map<String, List<ContainingBag>> mapBags = new HashMap<>();

            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                String pattern = "^(.+)\\sbags\\scontain\\s(.+)\\.$";
                Pattern r = Pattern.compile(pattern);

                Matcher matcher = r.matcher(data);
                if (matcher.find()) {
                    String color = matcher.group(1);
                    String containing = matcher.group(2);
                    List<ContainingBag> bags = new ArrayList<>();

                    System.out.println("color: " + color);

                    if (!containing.equals("no other bags")) {
                        List<String> splitted = Arrays.asList(containing.split(", "));
                        splitted.forEach(s -> {
                            String bagPattern = "^(\\d+)\\s(.+)\\sbag.*$";
                            Pattern r1 = Pattern.compile(bagPattern);

                            Matcher matcherBag = r1.matcher(s);
                            if (matcherBag.find()) {
                                int number = Integer.parseInt(matcherBag.group(1));
                                String bagColor = matcherBag.group(2);

                                bags.add(new ContainingBag(bagColor, number));

                            }
                        });
                    }

                    mapBags.put(color, bags);
                }

            }
            myReader.close();

            int total = 0;
            // Part One
//            for (String color : mapBags.keySet()) {
//                if (canContain("shiny gold", color, mapBags))
//                    total++;
//            }

            total = countInnerBags("shiny gold", mapBags) - 1;

            System.out.println("total: " + total);


        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    private static int countInnerBags(String color, Map<String, List<ContainingBag>> mapBags) {
        int count = 1;
        if (mapBags.get(color).size() == 0) {
            return count;
        }
        else {
            for (ContainingBag containingBag : mapBags.get(color)) {
                count += containingBag.number * countInnerBags(containingBag.bagColor, mapBags);
            }

            return count;
        }
    }

    private static boolean canContain(String searchColor, String color, Map<String, List<ContainingBag>> mapBags) {
        if (mapBags.get(color).size() == 0) {
            return false;
        } else {
            boolean canContain = false;
            for (ContainingBag containingBag : mapBags.get(color)) {
                if (containingBag.bagColor.equals(searchColor))
                    canContain = true;
                else {
                    if (canContain(searchColor, containingBag.bagColor, mapBags))
                        canContain = true;
                }
            }

            return canContain;
        }
    }
}
