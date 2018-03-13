package format;


import model.*;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import output.*;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Map;
import java.util.UnknownFormatConversionException;


public class CSVReader {

    public static void initVectors(String id){
        X.getX().put(id,0);
        W.getW().put(id,0);
        Z.getZ().put(id,0);
        Z1.getZ1().put(id,0);
        Z2.getZ2().put(id,0);
        CommissionList.getList().add(id);
    }

    public static void parseCommissionTarget(String input){
        try {
            Reader in = new FileReader(input);
            Iterable<CSVRecord> records = CSVFormat.EXCEL.withFirstRecordAsHeader().parse(in);
            TargetSet commissionSet = TargetSet.getInstance();
            for (CSVRecord record : records) {
                Commission temp = new Commission();
                temp.setId(record.get("order"));
                temp.setTarget(Integer.parseInt(record.get("t")));
                commissionSet.addCommission(temp);
                initVectors(temp.getId());
            }
/*           for (Map.Entry<String, Integer> entry : Z2.getZ2().entrySet()) {
                System.out.println("Commission: " + entry.getKey() + " - Time:" + entry.getValue());
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
            Reader in = new FileReader(input);
            Iterable<CSVRecord> records = CSVFormat.EXCEL.withFirstRecordAsHeader().parse(in);
            Map<String,String> map;
            DistanceMap distanceMap = DistanceMap.getInstance();
            for (CSVRecord record : records) {
                initAdjacency(record.get(0));
                if (record.get(0).contains("M"))
                    Movers.getMovers().add(record.get(0));
                map = record.toMap();
                for (Map.Entry<String, String> entry : map.entrySet()) {
                    if (!entry.getKey().equals("D_bar")){
                        if (distanceMap.getDistanceMap().get(entry.getKey())==null){
                            if (Integer.parseInt(entry.getValue()) >0){
                                distanceMap.addDistance(entry.getKey(),
                                        new Distance(record.get(0),Integer.parseInt(entry.getValue())));
                            }
                        }
                        else{
                            if (Integer.parseInt(entry.getValue()) >0){
                                distanceMap.addDistance(entry.getKey(),
                                        new Distance(record.get(0),Integer.parseInt(entry.getValue())));
                            }
                        }
                    }
                }
            }
/*           for (Map.Entry<String, TreeSet<Distance>> entry : distanceMap.getDistanceMap().entrySet()) {
                System.out.println("Commission: " + entry.getKey() + " - Raw:" + entry.getValue().toString());
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


}