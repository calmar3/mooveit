package format;


import model.Commission;
import model.Distance;
import model.DistanceMap;
import model.TargetSet;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.*;


public class CSVReader {


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

    public static void parseMatrix(String input){
        try {
            Reader in = new FileReader(input);
            Iterable<CSVRecord> records = CSVFormat.EXCEL.withFirstRecordAsHeader().parse(in);
            Map<String,String> map;
            DistanceMap distanceMap = DistanceMap.getInstance();
            for (CSVRecord record : records) {
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
                System.out.println("Commission: " + entry.getKey() + " - Column:" + entry.getValue().size());
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