package format;


import model.Commission;
import model.CommissionSet;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.*;

/**
 * Created by marco on 24/05/17.
 */
public class CSVReader {

    public static void parseCommissionTarget(String input){
        try {

            Reader in = new FileReader(input);
            Iterable<CSVRecord> records = CSVFormat.EXCEL.withFirstRecordAsHeader().parse(in);
            CommissionSet commissionSet = CommissionSet.getInstance();
            for (CSVRecord record : records) {
                Commission temp = new Commission();
                temp.setId(record.get("order"));
                temp.setTarget(Integer.parseInt(record.get("t")));
                commissionSet.addCommission(temp);
            }
           // System.out.println(commissionSet.toString());
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
            System.out.println();
            System.out.println("pippooooo");
            System.out.println();
            HashMap<String ,List<Integer>> hm = new HashMap<String, List<Integer>>();
            Map<String,String> map = new HashMap<String, String>();
            int i =0;
            for (CSVRecord record : records) {

                    map = record.toMap();
                    for (Map.Entry<String, String> entry : map.entrySet()) {
                        if (!entry.getKey().equals("D_bar")){
                            if (hm.get(entry.getKey())==null){
                                List<Integer> list = new ArrayList<Integer>();
                                list.add(Integer.parseInt(entry.getValue()));
                                hm.put(entry.getKey(),list);
                            }
                            else{
                                List<Integer> list = hm.get(entry.getKey());
                                list.add(Integer.parseInt(entry.getValue()));
                                hm.put(entry.getKey(),list);
                            }
                        }
                    }

                i++;
            }
            for (Map.Entry<String, List<Integer>> entry : hm.entrySet()) {
                System.out.println(entry.getKey());
                System.out.println(entry.getValue());

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