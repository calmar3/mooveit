package format;

import config.AppConfig;
import model.Adjacency;
import model.CommissionList;
import model.Movers;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import output.*;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;


public class CSVWriter {

    public static void printResults(){
        printVectors();
        printMatrix();
        printGoal();
    }

    private static void printGoal() {
        try {
            BufferedWriter goal = Files.newBufferedWriter(Paths.get("goal_"+ AppConfig.MOVER_NUMBER+"movers.csv"));
            CSVPrinter goalPrinter = new CSVPrinter(goal, CSVFormat.EXCEL);
            for (Map.Entry<String, Integer> entry : Goal.getGoal().entrySet()) {
                goalPrinter.printRecord(entry.getKey(),entry.getValue());
            }
            goalPrinter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void printMatrix() {
        try {
            BufferedWriter y = Files.newBufferedWriter(Paths.get("y_"+ AppConfig.MOVER_NUMBER+"movers.csv"));

            CSVPrinter yPrinter = new CSVPrinter(y, CSVFormat.EXCEL.withFirstRecordAsHeader());
            CommissionList.getList().add(0,"Matrix");
            String[] tempList = new String[CommissionList.getList().size()];
            tempList = CommissionList.getList().toArray(tempList);
            yPrinter.printRecord(tempList);
            CommissionList.getList().remove(0);
            for (int i = 0; i < CommissionList.getList().size(); i++) {
                String[] commissionList = new String[CommissionList.getList().size()+1];
                commissionList[0] = CommissionList.getList().get(i);
                for (int j = 1; j < commissionList.length; j++) {
                    commissionList[j] = "0";
                }
                String nextCommission = Adjacency.getAdj().get(CommissionList.getList().get(i));
                if (nextCommission!=null){
                    Integer position = CommissionList.getList().indexOf(nextCommission);
                    commissionList[position+1] = "1";
                }
                yPrinter.printRecord(commissionList);
            }
            for (int i = 0; i < Movers.getMovers().size(); i++) {
                String[] commissionList = new String[CommissionList.getList().size()+1];
                commissionList[0] = Movers.getMovers().get(i);
                for (int j = 1; j < commissionList.length; j++) {
                    commissionList[j] = "0";
                }
                String nextCommission = Adjacency.getAdj().get(Movers.getMovers().get(i));
                if (nextCommission!=null){
                    Integer position = CommissionList.getList().indexOf(nextCommission);
                    commissionList[position+1] = "1";
                }
                yPrinter.printRecord(commissionList);
            }
            yPrinter.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void printVectors() {
        try {
            BufferedWriter x = Files.newBufferedWriter(Paths.get( "x_"+ AppConfig.MOVER_NUMBER+"movers.csv"));
            BufferedWriter z = Files.newBufferedWriter(Paths.get("z_"+ AppConfig.MOVER_NUMBER+"movers.csv"));
            BufferedWriter z1 = Files.newBufferedWriter(Paths.get("z1_"+ AppConfig.MOVER_NUMBER+"movers.csv"));
            BufferedWriter z2 = Files.newBufferedWriter(Paths.get("z2_"+ AppConfig.MOVER_NUMBER+"movers.csv"));
            BufferedWriter w = Files.newBufferedWriter(Paths.get("w_"+ AppConfig.MOVER_NUMBER+"movers.csv"));
            CSVPrinter xPrinter = new CSVPrinter(x, CSVFormat.EXCEL.withHeader("commission","value"));
            CSVPrinter zPrinter = new CSVPrinter(z, CSVFormat.EXCEL.withHeader("commission","value"));
            CSVPrinter z1Printer = new CSVPrinter(z1, CSVFormat.EXCEL.withHeader("commission","value"));
            CSVPrinter z2Printer = new CSVPrinter(z2, CSVFormat.EXCEL.withHeader("commission","value"));
            CSVPrinter wPrinter = new CSVPrinter(w, CSVFormat.EXCEL.withHeader("commission","value"));
            for (Map.Entry<String, Integer> entry : X.getX().entrySet()) {
                xPrinter.printRecord(entry.getKey(),entry.getValue());
                zPrinter.printRecord(entry.getKey(), Z.getZ().get(entry.getKey()));
                z1Printer.printRecord(entry.getKey(), Z1.getZ1().get(entry.getKey()));
                z2Printer.printRecord(entry.getKey(), Z2.getZ2().get(entry.getKey()));
                wPrinter.printRecord(entry.getKey(), W.getW().get(entry.getKey()));
            }
            for (String mover: Movers.getMovers())
                xPrinter.printRecord(mover,0);
            xPrinter.flush();
            zPrinter.flush();
            z1Printer.flush();
            z2Printer.flush();
            wPrinter.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
