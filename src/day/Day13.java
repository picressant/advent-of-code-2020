package day;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Day13 {

    public static void solve() {
        try {
            File myObj = new File("/home/pierre/work/perso/advent.input");
            Scanner myReader = new Scanner(myObj);

            List<Integer> buses = new ArrayList<>();
            int timestamp = 0;

            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                if (timestamp == 0) {
                    timestamp = Integer.parseInt(data);
                }
                else {
                    buses = Arrays.stream(data.split(",")).filter(d -> !d.equals("x")).map(Integer::parseInt).collect(Collectors.toList());
                }
            }

            int nextBus = 0;
            int nextTimeStamp = 0;

            for (Integer bus : buses) {
                int busNext = Math.floorDiv(timestamp, bus) * bus + bus;
                if (nextBus == 0 || nextTimeStamp > busNext) {
                    nextBus = bus;
                    nextTimeStamp = busNext;
                }
            }

            System.out.println("Next bus: " + nextBus + " - timestamp: " + nextTimeStamp);
            System.out.println("Res: " + ((nextTimeStamp - timestamp) * nextBus));

            myReader.close();

        }
        catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}
