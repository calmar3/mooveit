package format;


import config.AppConfig;
import core.Mooveit;
import model.*;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import output.*;

import java.io.*;
import java.util.*;


public class MooveitReader {

    public static void initVectors(String id,Double target){
        X.getX().put(id,0.0);
        W.getW().put(id,0);
        Z.getZ().put(id,0);
        Z1.getZ1().put(id,0);
        Z2.getZ2().put(id,0);
        CommissionList.getList().add(id);
        TargetTimeList.getList().add(target);
    }

    public static void parseCommissionTarget(String input){
        try {
            Reader in = new java.io.FileReader(input);
            Iterable<CSVRecord> records = CSVFormat.EXCEL.withFirstRecordAsHeader().parse(in);
            TargetSet commissionSet = TargetSet.getInstance();
            for (CSVRecord record : records) {
                Commission temp = new Commission();
                temp.setId(record.get("order"));
                temp.setTarget(Double.parseDouble(record.get("t")));
                commissionSet.addCommission(temp);
                initVectors(temp.getId(),temp.getTarget());
            }
           /*for (Commission comm : commissionSet.getCommissionTreeSet()) {
                System.out.println("Commission: " + comm.getId() + " - Time:" + comm.getTarget());
            }*/

        }
        catch (UnknownFormatConversionException e) {
            e.printStackTrace();
        }catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public static void initAdjacency(String id){
        Adjacency.getAdj().put(id,null);
    }

    public static void parseMatrix(String input){
        try {
            Reader in = new java.io.FileReader(input);
            Iterable<CSVRecord> records = CSVFormat.EXCEL.withFirstRecordAsHeader().parse(in);
            Map<String,String> map;
            DistanceMap distanceMap = DistanceMap.getInstance();
            int count = 0;
            HashMap<String,Integer> bannedMovers = new HashMap<>();
            for (CSVRecord record : records) {
                initAdjacency(record.get(0));
                if (record.get(0).contains("M")){
                    if (count < AppConfig.MOVER_NUMBER){
                        Movers.getMovers().add(record.get(0));
                        count++;
                    }
                    else
                        bannedMovers.put(record.get(0),0);
                }
                map = record.toMap();
                if (bannedMovers.get(record.get(0))== null){
                    for (Map.Entry<String, String> entry : map.entrySet()) {
                        if (!entry.getKey().equals("D_bar")){
                            if (entry.getKey().contains("M"))
                                System.out.println(entry.getKey());
                            if (distanceMap.getDistanceMap().get(entry.getKey())==null){
                                if (Double.parseDouble(entry.getValue()) >0){
                                    distanceMap.addDistance(entry.getKey(),
                                            new Distance(record.get(0),Double.parseDouble(entry.getValue())));
                                }

                            }
                            else{
                                if (!entry.getKey().equals(record.get(0))){
                                    distanceMap.addDistance(entry.getKey(),
                                            new Distance(record.get(0),Double.parseDouble(entry.getValue())));
                                }
                            }
                        }
                    }
                }
            }
/*           for (Map.Entry<String, TreeSet<Distance>> entry : distanceMap.getDistanceMap().entrySet()) {
                System.out.println("Commission: " + entry.getKey() + " - Raw:" + entry.getValue().size());
            }*/
        }
        catch (UnknownFormatConversionException e) {
            e.printStackTrace();
        }catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        }
    }


    public static void parseMoverNumber(String path) {
        try {
            try (BufferedReader br = new BufferedReader(new java.io.FileReader(path))) {
                String line;
                while ((line = br.readLine()) != null) {
                    List<Integer> number = new ArrayList<>();
                    if (line.contains("ist"))
                        continue;
                    String[] num = line.split(",");
                    for (int i = 1; i < num.length ; i++) {
                        number.add(Integer.parseInt(num[i]));
                    }
                    Mooveit.dataset.put(num[0],number);
                }
            }
        }
        catch (UnknownFormatConversionException e) {
            e.printStackTrace();
        }catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}