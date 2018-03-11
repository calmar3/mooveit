package core;

import format.CSVReader;
import output.Goal;

public class Mooveit {

    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        CSVReader.parseCommissionTarget("src/main/resources/deliveryTime_ist3.csv");
        CSVReader.parseMatrix("src/main/resources/distanceMatrix_ist3.csv");
        Goal.initGoal();
        Scheduling.run();
        long elapsed = System.currentTimeMillis();
        System.out.println("Execution time: " + (elapsed-start));
    }

}
