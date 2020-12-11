package day;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

public class Day11 {

    public static void solve() {
        try {
            File myObj = new File("C:\\Workspaces\\advent.input");
            Scanner myReader = new Scanner(myObj);

            List<List<String>> seats = new ArrayList<>();
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                seats.add(Arrays.asList(data.split("")));
            }
            myReader.close();

            int seatTaken;
            int currentSeatTaken = 0;
            System.out.println("Default");
            showSeats(seats);

            do {
                System.out.println("");
                seatTaken = currentSeatTaken;
                seats = updateSeats(seats);
                currentSeatTaken = countTakenSeats(seats);
                showSeats(seats);
                System.out.println("currentSeatTaken: " + currentSeatTaken);
            } while (seatTaken != currentSeatTaken);

        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    private static void showSeats(List<List<String>> seats) {
        seats.forEach(row -> {
            System.out.println(String.join("", row));
        });
    }

    private static List<List<String>> updateSeats(List<List<String>> seats) {
        List<List<String>> newSeats = new ArrayList<>();

        for (int i = 0; i < seats.size(); i++) {
            List<String> row = new ArrayList<>();
            for (int j = 0; j < seats.get(i).size(); j++) {
                row.add(getNewSeatStatus(seats, i, j));
            }
            newSeats.add(row);
        }

        return newSeats;
    }

    private static String getNewSeatStatus(List<List<String>> seats, int x, int y) {
        List<String> seatsArround = new ArrayList<>();
        String currentSeat = getSeat(seats, x, y);
        if (currentSeat.equals("."))
            return currentSeat;
        else {
            seatsArround.add(getSeatInDirection(seats, x - 1, y - 1, x, y));
            seatsArround.add(getSeatInDirection(seats, x - 1, y, x, y));
            seatsArround.add(getSeatInDirection(seats, x - 1, y + 1, x, y));
            seatsArround.add(getSeatInDirection(seats, x, y - 1, x, y));
            seatsArround.add(getSeatInDirection(seats, x, y + 1, x, y));
            seatsArround.add(getSeatInDirection(seats, x + 1, y - 1, x, y));
            seatsArround.add(getSeatInDirection(seats, x + 1, y, x, y));
            seatsArround.add(getSeatInDirection(seats, x + 1, y + 1, x, y));

            if (currentSeat.equals("L")) {
                if (seatsArround.stream().noneMatch(s -> s.equals("#")))
                    return "#";
                else
                    return "L";
            } else {
                if (seatsArround.stream().filter(s -> s.equals("#")).count() >= 5)
                    return "L";
                else
                    return "#";
            }
        }

    }

    private static String getSeatInDirection(List<List<String>> seats, int x, int y, int xOrigin, int yOrigin) {
        if (x < 0 || x == seats.size() || y < 0 || y == seats.get(x).size())
            return ".";

        String seat = getSeat(seats, x, y);
        if (seat.equals(".")) {
            if (x < xOrigin)
                x--;
            else if (x > xOrigin)
                x++;

            if (y < yOrigin)
                y--;
            else if (y > yOrigin)
                y++;

            return getSeatInDirection(seats, x, y, xOrigin, yOrigin);
        } else {
            return seat;
        }
    }

    private static String getSeat(List<List<String>> seats, int x, int y) {
        if (x < 0 || x >= seats.size())
            return ".";
        else {
            List<String> row = seats.get(x);
            if (y < 0 || y >= row.size())
                return ".";
            else
                return row.get(y);
        }

    }

    private static int countTakenSeats(List<List<String>> seats) {
        AtomicInteger count = new AtomicInteger();
        seats.forEach(row -> row.forEach(seat -> {
            if (seat.equals("#"))
                count.getAndIncrement();
        }));

        return count.get();

    }

}
