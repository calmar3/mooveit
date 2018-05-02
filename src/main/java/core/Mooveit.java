package core;

import config.AppConfig;
import format.MooveitReader;
import format.MooveitWriter;
import model.*;
import output.Goal;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Mooveit {

    private static String commissionTarget = new String();
    private static String deliveryMatrix = new String();
    public static HashMap<String,List<Integer>> dataset = new HashMap<>();

    public static void main(String[] args) {
        if (args.length == 2 && args[0].equals("-test")){
            String inputPath = args[1];
            final File folder = new File(inputPath);
            for (final File fileEntry : folder.listFiles()) {
                if (!fileEntry.isDirectory() && !fileEntry.getName().contains("mover_number")) {
                    String[] temp1 = fileEntry.getName().split("_ist");
                    String[] temp2 = temp1[1].split(".csv");
                    if (dataset.get(temp2[0])==null)
                        dataset.put(temp2[0],new ArrayList<>());
                }
            }
            if (inputPath.substring(inputPath.length()-1).equals("/")) {
                inputPath = inputPath.substring(0, inputPath.length() - 1);
            }
            MooveitReader.parseMoverNumber(inputPath+ "/mover_number.txt");
            MooveitWriter.createPaths();
            boolean first = true;
            for (Map.Entry<String, List<Integer>> entry : dataset.entrySet()) {
                for (int j = 0; j < entry.getValue().size(); j++) {
                    AppConfig.MOVER_NUMBER = entry.getValue().get(j);
                    commissionTarget = inputPath+"/deliveryTime_ist"+entry.getKey()+".csv";
                    deliveryMatrix = inputPath+"/distanceMatrix_ist"+entry.getKey()+".csv";
                    long programStart = System.currentTimeMillis();
                    loadInput();
                    long algoStart = System.currentTimeMillis();
                    exe();
                    long algoElapsed = System.currentTimeMillis();

                    long programElapsed = System.currentTimeMillis();
                    MooveitWriter.printResults("output/ist"+entry.getKey()+"/"+AppConfig.MOVER_NUMBER+"mover/");
                    MooveitWriter.printOutput("output/ist"+entry.getKey()+"/execution_time_ist"+entry.getKey()+".txt",(algoElapsed-algoStart),
                            (programElapsed-programStart),Goal.getGoal().get("value"),(j==0));
                    MooveitWriter.printTable("output/summary.csv",entry.getKey(),first);
                    first = false;
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
                commissionTarget = args[0];
                deliveryMatrix = args[1];
                long programStart = System.currentTimeMillis();
                loadInput();
                long algoStart = System.currentTimeMillis();
                exe();
                long algoElapsed = System.currentTimeMillis();
                long programElapsed = System.currentTimeMillis();
                MooveitWriter.printResults("");
                String[] temp1 = commissionTarget.split("_ist");
                String[] temp2 = temp1[1].split(".csv");
                MooveitWriter.printOutput("execution_time_ist"+temp2[0]+".txt",(algoElapsed-algoStart),
                        (programElapsed-programStart),Goal.getGoal().get("value"),true);
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
