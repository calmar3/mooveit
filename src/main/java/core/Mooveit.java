package core;

import config.AppConfig;
import format.CSVReader;
import format.CSVWriter;
import output.Goal;

public class Mooveit {

    public static void main(String[] args) {
        if (args.length < 3){
            System.out.println("Bad usage, expected <deliveryTime_path.csv> <distanceMatrix_path.csv> <mover_number[0-38]>");
            System.exit(1);
        }
        else {
            AppConfig.MOVER_NUMBER = Integer.parseInt(args[2]);
            System.out.println("Mover Number: " + AppConfig.MOVER_NUMBER);
            long programStart = System.currentTimeMillis();
            CSVReader.parseCommissionTarget(args[0]);
            CSVReader.parseMatrix(args[1]);
            long algoStart = System.currentTimeMillis();
            Goal.initGoal();
            Scheduling.run();
            long algoElapsed = System.currentTimeMillis();
            System.out.println("Algorithm execution time: " + (algoElapsed-algoStart) + " ms");
            System.out.println("Goal value: " + Goal.getGoal().get("value"));
            long programElapsed = System.currentTimeMillis();
            CSVWriter.printResults();
            System.out.println("Program execution time: " + (programElapsed-programStart) + " ms");
        }
    }

}
