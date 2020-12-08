package day;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day8 {

    static class Instruction {
        public String action;
        public int value;
        public boolean runned;

        public Instruction(String action, int value) {
            this.action = action;
            this.value = value;
            this.runned = false;
        }
    }

    static class LoopedException extends Exception {}

    public static void solve() {
        try {
            File myObj = new File("C:\\Workspaces\\advent.input");
            Scanner myReader = new Scanner(myObj);

            List<Instruction> instructions = new ArrayList<>();

            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                String pattern = "^(.+)\\s(.+)$";
                Pattern r = Pattern.compile(pattern);

                Matcher matcher = r.matcher(data);
                if (matcher.find()) {
                    String action = matcher.group(1);
                    int value = Integer.parseInt(matcher.group(2));

                    instructions.add(new Instruction(action, value));
                }

            }
            myReader.close();

            int total = 0;
            int changedIndex = 0;

            while (total == 0) {
                try {
                    System.out.println("run changing " + changedIndex);
                    total = run(0, 0, instructions, changedIndex);
                } catch (LoopedException le) {
                    resetRunned(instructions);
                    changedIndex++;
                }
            }
            System.out.println("total: " + total);


        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    private static void resetRunned(List<Instruction> instructions) {
        instructions.forEach(i -> i.runned = false);
    }

    private static int run(int acc, int index, List<Instruction> instructions, int switchIndex) throws LoopedException {
        if (index >= instructions.size()) {
            return acc;
        } else {
            Instruction ins = instructions.get(index);
            int addToIndex = 1;
            if (ins.runned)
                throw new LoopedException();
            else if (ins.action.equals("acc")) {
                acc += ins.value;
            } else if ((ins.action.equals("jmp") && !(switchIndex == index)) || (ins.action.equals("nop") && (switchIndex == index))){
                addToIndex = ins.value;
            }

            index += addToIndex;
            ins.runned = true;
            return run(acc, index, instructions, switchIndex);
        }

    }
}
