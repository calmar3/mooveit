package core;

import config.AppConfig;
import format.MooveitReader;
import format.MooveitWriter;
import model.*;
import output.Goal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Mooveit {

    private static String commissionTarget = new String();
    private static String deliveryMatrix = new String();
    public static List<List<Integer>> moverNumber = new ArrayList();

    public static void main(String[] args) {
        if (args.length == 3 && args[0].equals("-test")){
            Integer numberInput = Integer.parseInt(args[2]);
            String inputPath = args[1];
            if (inputPath.substring(inputPath.length()-1).equals("/")) {
                inputPath = inputPath.substring(0, inputPath.length() - 1);
            }
            MooveitReader.parseMoverNumber(inputPath+ "/mover_number.txt");
            MooveitWriter.createPaths(numberInput);
            for (int i = 0; i < numberInput ; i++){
                for (int j = 0; j < moverNumber.get(i).size(); j++) {
                    AppConfig.MOVER_NUMBER = moverNumber.get(i).get(j);
                    commissionTarget = inputPath+"/deliveryTime_ist"+(i+1)+".csv";
                    deliveryMatrix = inputPath+"/distanceMatrix_ist"+(i+1)+".csv";
                    long programStart = System.currentTimeMillis();
                    loadInput();
                    long algoStart = System.currentTimeMillis();
                    exe();
                    long algoElapsed = System.currentTimeMillis();

                    long programElapsed = System.currentTimeMillis();
                    MooveitWriter.printResults("output/ist"+(i+1)+"/"+AppConfig.MOVER_NUMBER+"mover/");
                    MooveitWriter.printOutput("output/ist"+(i+1)+"/execution_time_ist"+(i+1)+".txt",(algoElapsed-algoStart),
                            (programElapsed-programStart),Goal.getGoal().get("value"),(j==0));
                    clean();
                }
            }
        }
        else{
            if (args.length < 3 ){
                System.out.println("Bad usage, expected <deliveryTime_path.csv> <distanceMatrix_path.csv> <mover_number[0-38]>");
                System.exit(1);
            }
            else {
                AppConfig.MOVER_NUMBER = Integer.parseInt(args[2]);
                System.out.println("Mover Number: " + AppConfig.MOVER_NUMBER);
                commissionTarget = args[0];
                deliveryMatrix = args[1];
                long programStart = System.currentTimeMillis();
                loadInput();
                long algoStart = System.currentTimeMillis();
                exe();
                long algoElapsed = System.currentTimeMillis();
                System.out.println("Algorithm execution time: " + (algoElapsed-algoStart) + " ms");
                System.out.println("Goal value: " + Goal.getGoal().get("value"));
                long programElapsed = System.currentTimeMillis();
                MooveitWriter.printResults("");
                System.out.println("Program execution time: " + (programElapsed-programStart) + " ms");
            }
        }
    }

    private static void clean() {
        Adjacency.setAdj(new HashMap<>());
        CommissionList.setList(new ArrayList<>());
        DistanceMap.clear();
        Movers.setMovers(new ArrayList<>());
        TargetSet.clear();
        TargetTimeList.setList(new ArrayList<>());
    }

    public static void loadInput(){
        MooveitReader.parseCommissionTarget(commissionTarget);
        MooveitReader.parseMatrix(deliveryMatrix);
    }

    public static void exe(){
        Goal.initGoal();
        Scheduling.run();
    }

}
