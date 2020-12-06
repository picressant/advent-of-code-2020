package day;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Day5 {


    static class Row {
        public int min;
        public int max;

        public Row(int min, int max) {
            this.min = min;
            this.max = max;
        }
    }

    public static void solve() {
        try {
            File myObj = new File("/home/pierre/work/perso/advent.input");
            Scanner myReader = new Scanner(myObj);

            List<Integer> ids = new ArrayList<>();

            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                Row row = resolveRow(new Row(0, 127), data);
                System.out.println("res -> " + row.min + " " + row.max);

                Row col = resolveCol(new Row(0, 7), data.substring(7));
                System.out.println("res -> " + col.min + " " + col.max);

                int id = row.max * 8 + col.max;
                System.out.println("id -> " + id);

                ids.add(id);

            }
            myReader.close();

            ids.forEach(id -> {
                boolean searched = ids.stream().anyMatch(i -> i.equals(id + 1));
                boolean max = ids.stream().anyMatch(i -> i.equals(id + 2));

                if (!searched && max)
                    System.out.println("found " + (id + 1));
            });


        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }


    private static Row resolveRow(Row row, String letters) {
        String letter = letters.substring(0, 1);
        int diff = (row.max - row.min) / 2 + 1;
        if (letter.equals("F")) {
            row.max = row.max - diff;
        }
        else {
            row.min = row.min + diff;
        }

        System.out.println(letter + " -> " + row.min + " " + row.max);

        if (row.max != row.min)
            return resolveRow(row, letters.substring(1));
        else
            return row;
    }

    private static Row resolveCol(Row row, String letters) {
        String letter = letters.substring(0, 1);
        int diff = (row.max - row.min) / 2 + 1;
        if (letter.equals("L")) {
            row.max = row.max - diff;
        }
        else {
            row.min = row.min + diff;
        }

        System.out.println(letter + " -> " + row.min + " " + row.max);

        if (row.max != row.min)
            return resolveCol(row, letters.substring(1));
        else
            return row;
    }
}
