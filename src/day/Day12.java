package day;

import javafx.util.Pair;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Day12 {

    static class Ship {
        int northSouth;
        int eastWest;

        int wayPointNorthSouth;
        int wayPointEastWest;

        public Ship(int wayPointNorthSouth, int wayPointEastWest) {
            this.northSouth = 0;
            this.eastWest = 0;

            this.wayPointNorthSouth = wayPointNorthSouth;
            this.wayPointEastWest = wayPointEastWest;
        }

        public void forward(int value) {
            this.northSouth += value * this.wayPointNorthSouth;
            this.eastWest += value * this.wayPointEastWest;
        }

        public void move(String direction, int value) {
            switch (direction) {
                case "N":
                    this.wayPointNorthSouth += value;
                    break;
                case "E":
                    this.wayPointEastWest += value;
                    break;
                case "S":
                    this.wayPointNorthSouth -= value;
                    break;
                case "W":
                    this.wayPointEastWest -= value;
                    break;
            }
        }

        public void changeDirection(String direction, int value) {
            if (direction.equals("L")) {
                value = 360 - value;
            }

            int moveInt = value;
            while (moveInt > 0) {
                int oldNorthSouth = this.wayPointNorthSouth;
                int oldEastWest = this.wayPointEastWest;

                this.wayPointEastWest = oldNorthSouth;
                this.wayPointNorthSouth = -oldEastWest;
                moveInt -= 90;
            }
        }
    }

    public static void solve() {
        try {
            File myObj = new File("/home/pierre/work/perso/advent.input");
            Scanner myReader = new Scanner(myObj);

            List<Pair<String, Integer>> actions = new ArrayList<>();

            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                Pair<String, Integer> action = new Pair<>(data.substring(0, 1), Integer.parseInt(data.substring(1)));
                actions.add(action);
            }
            myReader.close();

            Ship ship = new Ship(1, 10);
            actions.forEach(action -> {
                if (action.getKey().equals("F")) {
                    ship.forward(action.getValue());
                }
                else if (action.getKey().equals("R") || action.getKey().equals("L")) {
                    ship.changeDirection(action.getKey(), action.getValue());
                }
                else {
                    ship.move(action.getKey(), action.getValue());
                }
            });

            System.out.println("ship: eastWest " + ship.eastWest + " / northSouth " + ship.northSouth);
            System.out.println("result: " + (Math.abs(ship.eastWest) + Math.abs(ship.northSouth)));


        }
        catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}
