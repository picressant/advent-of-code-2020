package day;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Day10 {

    static class Differences {
        int diff1 = 0;
        int diff2 = 0;
        int diff3 = 0;
    }

    static long count = 0;

    public static void solve() {
        try {
            File myObj = new File("/home/pierre/work/perso/advent.input");
            Scanner myReader = new Scanner(myObj);

            List<Long> numbers = new ArrayList<>();

            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                numbers.add(Long.parseLong(data.trim()));
            }
            myReader.close();

            numbers.add(0L);


            long max = numbers.stream().mapToLong(Long::longValue).max().getAsLong();
            long min = numbers.stream().mapToLong(Long::longValue).min().getAsLong();

            Map<Long, Long> possibilities = new HashMap<>();
            numbers.forEach(n -> possibilities.put(n, 0L));

            /* algo propre pour la partie 2 */
            possibilities.keySet().stream().sorted(Collections.reverseOrder()).forEach(number -> {
                if (number != max) {
                    if (number + 1 == max) {
                        possibilities.put(number, 1L);
                    }
                    else if (number + 2 == max) {
                        long toAdd = 1L;
                        if (possibilities.containsKey(number + 1)) {
                            toAdd += possibilities.get(number + 1);
                        }

                        possibilities.put(number, toAdd);

                    }
                    else if (number + 3 == max) {
                        long toAdd = 1L;
                        if (possibilities.containsKey(number + 1)) {
                            toAdd += possibilities.get(number + 1);
                        }

                        if (possibilities.containsKey(number + 2)) {
                            toAdd += possibilities.get(number + 2);
                        }

                        possibilities.put(number, toAdd);
                    }
                    else {
                        long toAdd = 0L;
                        if (possibilities.containsKey(number + 1)) {
                            toAdd += possibilities.get(number + 1);
                        }

                        if (possibilities.containsKey(number + 2)) {
                            toAdd += possibilities.get(number + 2);
                        }

                        if (possibilities.containsKey(number + 3)) {
                            toAdd += possibilities.get(number + 3);
                        }

                        possibilities.put(number, toAdd);
                    }
                }
            });

            System.out.println("Arrangements: " + possibilities.get(min));

        }
        catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    /* Algo brutal */
    private static void countPath(long value, List<Long> numbers, long max) {

        if (value != max) {
            if (numbers.contains(value + 1)) {
                countPath(value + 1, numbers, max);
            }
            if (numbers.contains(value + 2)) {
                countPath(value + 2, numbers, max);
            }
            if (numbers.contains(value + 3)) {
                countPath(value + 3, numbers, max);
            }
        }
        else {
            //System.out.println("path abouti");
            count++;
        }

    }


    private static void searchDifferences(long value, Differences differences, List<Long> numbers) {
        if (value != numbers.stream().mapToLong(l -> l).max().getAsLong()) {
            if (numbers.contains(value + 1)) {
                value++;
                differences.diff1++;
            }
            else if (numbers.contains(value + 2)) {
                value += 2;
                differences.diff2++;
            }
            else {
                value += 3;
                differences.diff3++;
            }

            searchDifferences(value, differences, numbers);
        }
    }
}
