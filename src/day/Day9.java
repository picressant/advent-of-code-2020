package day;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Day9 {

    public static void solve() {
        try {
            File myObj = new File("C:\\Workspaces\\advent.input");
            Scanner myReader = new Scanner(myObj);

            List<Long> numbers = new ArrayList<>();

            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                numbers.add(Long.parseLong(data.trim()));
            }
            myReader.close();

            int startSearchingIndex = 25;
            int index = startSearchingIndex;

            while (index < numbers.size()) {
                if (!isPreviousPair(startSearchingIndex, index, numbers))
                    break;
                index++;
            }

            System.out.println("Weakness: " + numbers.get(index));

            int maxIndex = -1;
            int searchIndex = -1;
            while (maxIndex == -1) {
                searchIndex++;
                maxIndex = findMaxRangeIndex(searchIndex, numbers, numbers.get(index));
            }

            System.out.println("Indexes: " + searchIndex + " / " + maxIndex);

            long min = numbers.subList(searchIndex, maxIndex).stream().mapToLong(value -> value).min().getAsLong();
            long max = numbers.subList(searchIndex, maxIndex).stream().mapToLong(value -> value).max().getAsLong();

            System.out.println("Min: " + min + " / max: " + max + " -- sum: " + (min + max));
            
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    private static int findMaxRangeIndex(int minIndex, List<Long> numbers, long weakness) {
        int maxIndex = minIndex;
        long sum = 0;
        while (sum < weakness) {
            maxIndex++;
            sum = numbers.subList(minIndex, maxIndex).stream().mapToLong(value -> value).sum();
        }

        if (sum == weakness)
            return maxIndex;
        else
            return -1;
    }

    private static boolean isPreviousPair(int previous, int index, List<Long> numbers) {
        long val = numbers.get(index);

        for (int i = index - previous; i < index; i++) {
            for (int j = index - previous; j < index; j++) {
                long a = numbers.get(i);
                long b = numbers.get(j);

                if (a != b) {
                    if (a + b == val)
                        return true;
                }
            }

        }

        return false;
    }
}
