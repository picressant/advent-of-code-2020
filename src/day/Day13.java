package day;

import javafx.util.Pair;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import static java.util.Arrays.stream;

public class Day13 {

    public static void solve() {
        try {
            File myObj = new File("/home/pierre/work/perso/advent.input");
            Scanner myReader = new Scanner(myObj);

            List<String> buses = new ArrayList<>();
            int timestamp = 0;

            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                if (timestamp == 0) {
                    timestamp = Integer.parseInt(data);
                }
                else {
                    buses = Arrays.asList(data.split(","));
                }
            }

            List<Pair<Long, Long>> busesAndModulo = new ArrayList<>();
            for (int i = 0; i < buses.size(); i++) {
                if (buses.get(i).equals("x")) {
                    continue;
                }

                long bus = Long.parseLong(buses.get(i));
                long mod = (i == 0) ? 0 : bus - i;
                while (mod < 0)
                    mod += bus;
                Pair<Long, Long> p = new Pair<>(bus, mod);

                busesAndModulo.add(p);
            }

            System.out.println(busesAndModulo);

            Long[] modulos = busesAndModulo.stream().map(Pair::getValue).toArray(Long[]::new);
            Long[] div = busesAndModulo.stream().map(Pair::getKey).toArray(Long[]::new);

            System.out.println("res: " + chineseRemainder(div, modulos));

            myReader.close();

        }
        catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public static long chineseRemainder(Long[] n, Long[] a) {

        long prod = stream(n).reduce(1L, (i, j) -> i * j);

        long p, sm = 0;
        for (int i = 0; i < n.length; i++) {
            p = prod / n[i];
            sm += a[i] * mulInv(p, n[i]) * p;
        }
        return sm % prod;
    }

    private static long mulInv(long a, long b) {
        long b0 = b;
        long x0 = 0;
        long x1 = 1;

        if (b == 1) {
            return 1;
        }

        while (a > 1) {
            long q = a / b;
            long amb = a % b;
            a = b;
            b = amb;
            long xqx = x1 - q * x0;
            x1 = x0;
            x0 = xqx;
        }

        if (x1 < 0) {
            x1 += b0;
        }

        return x1;
    }
}
