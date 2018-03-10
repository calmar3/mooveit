package core;

import config.AppConfig;
import model.*;

import java.util.*;

public class Scheduling {

    private static HashMap<String,HashMap<String,Integer>> allocation;

    public static void run() {
        initScheduling();
        assignMovers();
        assignCommission();
    }

    public static void assignCommission(){

    }

    public static void assignMovers(){
        TargetSet targetSet = TargetSet.getInstance();
        HashMap<String,TreeSet<Distance>> distanceMap = DistanceMap.getInstance().getDistanceMap();
        List<Commission> temp = new ArrayList<Commission>(targetSet.getCommissionTreeSet());
        for (int i = 0; i < AppConfig.MOVER_NUMBER; i++) {
            Commission commission = temp.get(0);
            Iterator<Distance> itr = distanceMap.get(commission.getId()).iterator();
            while (itr.hasNext()) {
                Distance distance = itr.next();
                if (distance.getId().contains("M")){
                    if (Adjacency.getAdj().get(distance.getId())==null){
                        HashMap<String,Integer> value = new HashMap<String, Integer>();
                        value.put(distance.getId(),distance.getDistance());
                        allocation.put(commission.getId(),value);
                        Adjacency.getAdj().put(distance.getId(),commission.getId());
                        break;
                    }
                }
            }
            temp.remove(0);
        }
        targetSet.setCommissionTreeSet(new TreeSet<Commission>(temp));
    }

    public static void initScheduling(){
        allocation = new HashMap<String, HashMap<String, Integer>>();
    }
}
