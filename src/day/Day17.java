package day;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Day17 {

    public static void solve() {
        try {
            File myObj = new File("/home/pierre/work/perso/advent.input");
            Scanner myReader = new Scanner(myObj);


            List<List<String>> input = new ArrayList<>();
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                List<String> line = Arrays.asList(data.split(""));
                input.add(line);
            }
            System.out.println(input);
            myReader.close();

            char[][][][] grid = new char[input.size() + 20][input.size() + 20][input.size() + 20][input.size() + 20];

            //Init de la grille
            for (int w = 0; w < grid.length; w++) {
                for (int z = 0; z < grid.length; z++) {
                    for (int y = 0; y < grid.length; y++) {
                        for (int x = 0; x < grid.length; x++) {
                            grid[w][z][y][x] = '.';
                        }
                    }
                }
            }

            //Ajout de la valeur des input
            for (int y = 0; y < input.size(); y++) {
                for (int x = 0; x < input.get(0).size(); x++) {
                    char c = input.get(y).get(x).charAt(0);
                    // grid[middle z][middle y - half of input length][middle x - half input length]
                    grid[grid.length / 2 - input.size() / 2][grid.length / 2 - input.size() / 2][grid.length / 2 - input.size() / 2 + y][grid.length / 2
                            + x] = c;
                }
            }

            int cycle = 1;
            while (cycle <= 6) {
                char[][][][] copy = new char[grid.length][grid.length][grid.length][grid.length];
                for (int w = 0; w < grid.length; w++) {
                    for (int z = 0; z < grid.length; z++) {
                        for (int y = 0; y < grid.length; y++) {
                            for (int x = 0; x < grid.length; x++) {
                                char c = grid[w][z][y][x];
                                int actives = countActiveNeighboors(grid, w, z, y, x);
                                if (c == '#') {
                                    copy[w][z][y][x] = (actives == 2 || actives == 3) ? '#' : '.';
                                }
                                else {
                                    copy[w][z][y][x] = (actives == 3) ? '#' : '.';
                                }
                            }
                        }
                    }
                }
                grid = copy;

                System.out.println("cycle: " + cycle);
                //show(grid);
                cycle++;

            }

            int actives = 0;
            for (int w = 0; w < grid.length; w++) {
                for (int z = 0; z < grid.length; z++) {
                    for (int y = 0; y < grid.length; y++) {
                        for (int x = 0; x < grid.length; x++) {
                            if (grid[w][z][y][x] == '#') {
                                actives++;
                            }
                        }
                    }
                }
            }

            System.out.println("actives: " + actives);
        }
        catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    private static int countActiveNeighboors(char[][][][] grid, int w, int z, int y, int x) {
        int count = 0;
        for (int wi = -1; wi <= 1; wi++) {
            for (int zi = -1; zi <= 1; zi++) {
                for (int yi = -1; yi <= 1; yi++) {
                    for (int xi = -1; xi <= 1; xi++) {
                        int wj = wi + w;
                        int zj = zi + z;
                        int yj = yi + y;
                        int xj = xi + x;

                        if (zj >= 0 && yj >= 0 && xj >= 0 && wj > 0) {
                            if (zj < grid.length && yj < grid.length && xj < grid.length && wj < grid.length) {
                                if (!(zi == 0 && yi == 0 && xi == 0 && wi == 0)) {
                                    if (grid[wj][zj][yj][xj] == '#') {
                                        count++;
                                    }
                                }
                            }
                        }

                    }
                }
            }
        }

        return count;
    }

    private static void show(char[][][] grid) {
        for (int z = 0; z < grid.length; z++) {
            System.out.println("");
            System.out.println("z=" + z);
            for (int y = 0; y < grid.length; y++) {
                for (int x = 0; x < grid.length; x++) {
                    System.out.print(grid[z][y][x]);
                }
                System.out.println();
            }
        }
    }
}
