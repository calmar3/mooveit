package core;

import config.AppConfig;
import model.Commission;
import model.Distance;
import model.DistanceMap;
import model.TargetSet;

import java.util.HashMap;
import java.util.Iterator;
import java.util.TreeSet;

public class Scheduling {

    public static void run() {

        TargetSet targetSet = TargetSet.getInstance();
        HashMap<String,TreeSet<Distance>> distanceMap = DistanceMap.getInstance().getDistanceMap();
        HashMap<String,HashMap<String,Integer>> allocation = new HashMap<String, HashMap<String, Integer>>();
        Iterator<Commission> iterator = targetSet.getCommissionTreeSet().iterator();
        for (int i = 0; i < AppConfig.MOVER_NUMBER; i++) {
            Commission commission = iterator.next();
            Iterator<Distance> itr = distanceMap.get(commission.getId()).iterator();
            while (itr.hasNext()) {
                Distance distance = itr.next();
                if (distance.getId().contains("M")){
                    HashMap<String,Integer> value = new HashMap<String, Integer>();
                    value.put(distance.getId(),distance.getDistance());
                    allocation.put(commission.getId(),value);
                    break;
                }
            }
        }
    }
}
