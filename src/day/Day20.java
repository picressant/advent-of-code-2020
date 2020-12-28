package day;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Day20 {

    private static int maxrotate = 7;

    static class Tile {
        long id;
        List<List<String>> rows;
        List<List<String>> unmodified;

        public Tile(long id) {
            this.id = id;
            this.rows = new ArrayList<>();
        }

        public void addRow(List<String> row) {
            this.rows.add(row);
        }

        public void saveDefault() {
            this.unmodified = new ArrayList<>();
            this.rows.forEach(r -> this.unmodified.add(new ArrayList<>(r)));
        }

        public void restore() {
            this.rows = new ArrayList<>();
            this.unmodified.forEach(r -> this.rows.add(new ArrayList<>(r)));
        }

        public void rotate() {
            List<List<String>> rotated = new ArrayList<>();
            for (int i = 0; i < this.rows.size(); i++) {
                String col = this.getCol(i);
                rotated.add(i, Arrays.asList(col.split("")));
                Collections.reverse(rotated.get(i));
            }

            this.rows = rotated;
        }

        public void flip() {
            Collections.reverse(this.rows);
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("Tile: ").append(this.id);
            sb.append(System.getProperty("line.separator"));
            this.rows.forEach(row -> {
                sb.append(String.join("", row));
                sb.append(System.getProperty("line.separator"));
            });
            return sb.toString();
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            Tile tile = (Tile) o;
            return id == tile.id;
        }

        @Override
        public int hashCode() {
            return Objects.hash(id);
        }

        public String getRow(int index) {
            return String.join("", this.rows.get(index));
        }

        public String getCol(int index) {
            String col = "";
            for (List<String> row : this.rows) {
                col += row.get(index);
            }
            return col;
        }

        public boolean matchUp(Tile other) {
            return other.getRow(other.rows.size() - 1).equals(getRow(0));
        }

        public boolean matchDown(Tile other) {
            return other.getRow(0).equals(getRow(rows.size() - 1));
        }

        public boolean matchLeft(Tile other) {
            return other.getCol(other.rows.get(0).size() - 1).equals(getCol(0));
        }

        public boolean matchRight(Tile other) {
            return other.getCol(0).equals(getCol(rows.get(0).size() - 1));
        }
    }

    public static void solve() {
        try {
            File myObj = new File("/home/pierre/work/perso/advent.input");
            Scanner myReader = new Scanner(myObj);

            List<Tile> tiles = new ArrayList<>();
            Tile currentTile = null;
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine().trim();

                if (data.startsWith("Tile")) {
                    currentTile = new Tile(Long.parseLong(data.substring("Tile ".length(), data.length() - 1)));
                }
                else if (!data.equals("")) {
                    currentTile.addRow(Arrays.asList(data.split("")));
                }
                else {
                    tiles.add(currentTile);
                }
            }

            tiles.forEach(Tile::saveDefault);
            List<Tile> tilesLeft = new ArrayList<>(tiles);
            List<Tile> testedAsFirst = new ArrayList<>();

            //tiles.forEach(tile -> System.out.println(tile.toString()));
            Tile[][] orderedTiles = new Tile[(int) Math.sqrt(tiles.size())][(int) Math.sqrt(tiles.size())];
            int firstTileTry = 0;


            for (int i = 0; i < orderedTiles.length; i++) {
                for (int j = 0; j < orderedTiles.length; j++) {
                    if (i == 0 && j == 0) {
                        tilesLeft = new ArrayList<>(tiles);
                        orderedTiles[0][0] = tilesLeft.stream().filter(title -> !testedAsFirst.contains(title)).findFirst().get();
                        tilesLeft.remove(orderedTiles[0][0]);
                        testedAsFirst.add(orderedTiles[0][0]);
                        //System.out.println("testing as first : " + orderedTiles[0][0].id);
                    }
                    else {
                        Tile matchingTile = null;
                        for (Tile runningTile : tilesLeft) {
                            //System.out.println("Testing " + runningTile.id);
                            matchingTile = null;
                            if (i > 0) {
                                if (!matchDown(orderedTiles[i - 1][j], runningTile)) {
                                    continue;
                                }
                            }
                            if (j > 0) {
                                if (!matchLeft(orderedTiles[i][j - 1], runningTile)) {
                                    continue;
                                }
                            }

                            matchingTile = runningTile;
                            //System.out.println("matching " + runningTile.id);
                            break;
                        }

                        if (matchingTile == null) {
                            //On a pas trouvé de next, on doit changer la première
                            //Si on pas testé toutes les combinaisons de la première, on doit continuer
                            //System.out.println("matching none - switching first - " + firstTileTry);
                            if (firstTileTry > maxrotate) {
                                //Reset en changeant la première
                                firstTileTry = 0;
                                i = 0;
                                j = -1;
                            }
                            else {
                                changeTile(orderedTiles[0][0], firstTileTry);

                                i = 0;
                                j = 0;

                                firstTileTry++;
                            }
                        }
                        else {
                            orderedTiles[i][j] = matchingTile;
                            tilesLeft.remove(matchingTile);
                        }
                    }
                }
            }

            displayArray(orderedTiles);

            long res = orderedTiles[0][0].id * orderedTiles[0][orderedTiles.length - 1].id * orderedTiles[orderedTiles.length - 1][0].id * orderedTiles[orderedTiles.length - 1][orderedTiles.length - 1].id;
            System.out.println("res: " + res);


        }
        catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    private static void changeTile(Tile tile, int switchIndex) {
        tile.restore();
        if (switchIndex == 1) {
            tile.rotate();
        }
        else if (switchIndex == 2) {
            tile.rotate();
            tile.rotate();
        }
        else if (switchIndex == 3) {
            tile.rotate();
            tile.rotate();
            tile.rotate();
        }
        else if (switchIndex == 4) {
            tile.flip();
        }
        else if (switchIndex == 5) {
            tile.flip();
            tile.rotate();
        }
        else if (switchIndex == 6) {
            tile.flip();
            tile.rotate();
            tile.rotate();
        }
        else if (switchIndex == 7) {
            tile.flip();
            tile.rotate();
            tile.rotate();
            tile.rotate();
        }
        else {
            tile.restore();
        }
    }

    private static void displayArray(Tile[][] tiles) {
        System.out.println();
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles.length; j++) {
                System.out.print(tiles[i][j].id + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    private static boolean matchLeft(Tile tile, Tile other) {
        int switchIndex = 0;
        boolean res = false;

        while (switchIndex <= maxrotate && !res) {
            changeTile(other, switchIndex);
            res = tile.matchLeft(other);
            switchIndex++;
        }

        return res;
    }

    private static boolean matchDown(Tile tile, Tile other) {
        int switchIndex = 0;
        boolean res = false;

        while (switchIndex <= maxrotate && !res) {
            changeTile(other, switchIndex);
            res = tile.matchDown(other);
            switchIndex++;
        }

        return res;
    }
}
