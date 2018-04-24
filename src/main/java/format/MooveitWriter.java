package format;

import config.AppConfig;
import core.Mooveit;
import model.Adjacency;
import model.CommissionList;
import model.Movers;
import model.TargetTimeList;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import output.*;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;


public class MooveitWriter {

    public static void printResults(String path){
        printVectors(path);
        printMatrix(path);
        printGoal(path);
    }

    private static void printGoal(String path) {
        try {
            BufferedWriter goal = Files.newBufferedWriter(Paths.get(path+"goal_"+ AppConfig.MOVER_NUMBER+"movers.csv"));
            CSVPrinter goalPrinter = new CSVPrinter(goal, CSVFormat.EXCEL);
            for (Map.Entry<String, Integer> entry : Goal.getGoal().entrySet()) {
                goalPrinter.printRecord(entry.getKey(),entry.getValue());
            }
            goalPrinter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void printMatrix(String path) {
        try {
            BufferedWriter y = Files.newBufferedWriter(Paths.get(path+"y_"+ AppConfig.MOVER_NUMBER+"movers.csv"));

            CSVPrinter yPrinter = new CSVPrinter(y, CSVFormat.EXCEL.withFirstRecordAsHeader());
            CommissionList.getList().add(0,"D_bar");
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

    private static void printVectors(String path) {
        try {
            BufferedWriter x = Files.newBufferedWriter(Paths.get( path+"x_"+ AppConfig.MOVER_NUMBER+"movers.csv"));
            BufferedWriter z = Files.newBufferedWriter(Paths.get(path+"z_"+ AppConfig.MOVER_NUMBER+"movers.csv"));
            BufferedWriter z1 = Files.newBufferedWriter(Paths.get(path+"z1_"+ AppConfig.MOVER_NUMBER+"movers.csv"));
            BufferedWriter z2 = Files.newBufferedWriter(Paths.get(path+"z2_"+ AppConfig.MOVER_NUMBER+"movers.csv"));
            BufferedWriter w = Files.newBufferedWriter(Paths.get(path+"w_"+ AppConfig.MOVER_NUMBER+"movers.csv"));
            CSVPrinter xPrinter = new CSVPrinter(x, CSVFormat.EXCEL.withHeader("commission","value"));
            CSVPrinter zPrinter = new CSVPrinter(z, CSVFormat.EXCEL.withHeader("commission","value"));
            CSVPrinter z1Printer = new CSVPrinter(z1, CSVFormat.EXCEL.withHeader("commission","value"));
            CSVPrinter z2Printer = new CSVPrinter(z2, CSVFormat.EXCEL.withHeader("commission","value"));
            CSVPrinter wPrinter = new CSVPrinter(w, CSVFormat.EXCEL.withHeader("commission","value"));
            for (int i = 0; i < CommissionList.getList().size();i++){
                String comm = CommissionList.getList().get(i);
                if (X.getX().get(comm)>0)
                    xPrinter.printRecord(comm,X.getX().get(comm));
                else
                    xPrinter.printRecord(comm,TargetTimeList.getList().get(i));
                zPrinter.printRecord(comm,Z.getZ().get(comm));
                z1Printer.printRecord(comm,Z1.getZ1().get(comm));
                z2Printer.printRecord(comm,Z2.getZ2().get(comm));
                wPrinter.printRecord(comm,W.getW().get(comm));
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

    public static void createPaths(Integer numberInput){
        for (int i = 0; i < numberInput; i++) {
            for (int j = 0; j < Mooveit.moverNumber.get(i).size(); j++) {
                String pathString = "output/ist"+(i+1)+"/"+Mooveit.moverNumber.get(i).get(j)+"mover/";
                Path path = Paths.get(pathString);
                try {
                    Files.createDirectories(path);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
    }

    public static void printOutput(String filename, long algo, long program, Integer goal, boolean first) {

            if (first){
                try (Writer writer = new BufferedWriter(new OutputStreamWriter(
                        new FileOutputStream(filename), "utf-8"))) {
                    writer.write("Algorithm execution time: tempo di esecuzione dell’euristica ASO\n" +
                            "Program execution time: tempo di esecuzione dell’intero programma, comprese le operazioni di input ed output da file CSV.\n\n"
                            + "————————————————————————————————————————————————\n\n");
                    writer.write("Mover number: " + AppConfig.MOVER_NUMBER +"\n"+"Algorithm execution time: " +algo +"\n"
                            +"Goal value: " + goal +"\n"+"Program execution time: " + program +"\n" +"\n————————————————————————————————————————————————\n\n" );
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            else{
                try (Writer writer = new BufferedWriter(new OutputStreamWriter(
                        new FileOutputStream(filename,true), "utf-8"))) {
                    writer.write("Mover number: " + AppConfig.MOVER_NUMBER +"\n"+"Algorithm execution time: " +algo +"\n"
                            +"Goal value: " + goal +"\n"+"Program execution time: " + program +"\n" +"\n————————————————————————————————————————————————\n\n" );
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }

        }

}
