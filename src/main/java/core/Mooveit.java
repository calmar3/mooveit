package core;

import config.AppConfig;
import format.CSVReader;
import format.CSVWriter;
import output.Goal;
import output.Z;
import output.Z1;
import output.Z2;

public class Mooveit {

    public static void main(String[] args) throws InterruptedException {
        if (args.length != 4){
            System.out.println("Bad usage, expected <deliveryTime_path.csv> <distanceMatrix_path.csv> <output_directory_path> <mover_number[0-38]>");
            System.exit(1);
        }
        else {
            AppConfig.MOVER_NUMBER = Integer.parseInt(args[3]);
            long programStart = System.currentTimeMillis();
            CSVReader.parseCommissionTarget(args[0]);
            CSVReader.parseMatrix(args[1]);
            long algoStart = System.currentTimeMillis();
            Goal.initGoal();
            Scheduling.run();
            long algoElapsed = System.currentTimeMillis();
            System.out.println("Algorithm execution time: " + (algoElapsed-algoStart));
            System.out.println("Goal value: " + Goal.getGoal().get("value"));
            System.out.println("Function : " + Goal.getGoal().get("z") + "," + Goal.getGoal().get("z1") + " , " + Goal.getGoal().get("z2"));
            long programElapsed = System.currentTimeMillis();
            /**
             * Print method to be done
             */
            CSVWriter.printResults(args[2]);
            System.out.println("Program execution time: " + (programElapsed-programStart));
        }
    }

}
