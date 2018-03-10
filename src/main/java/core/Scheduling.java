package core;

import config.AppConfig;
import model.*;
import output.Goal;
import output.X;

import java.util.*;

public class Scheduling {

    private static HashMap<String,HashMap<String,Integer>> allocation;
    private static HashMap<String,Integer> assigned;
    private static Integer deleted = 0;

    public static void run() {
        initScheduling();
        assignMovers();
        while (TargetSet.getInstance().getCommissionTreeSet().size()>0){
            assignCommission();
        }
    }

    public static void assignCommission(){

        TargetSet targetSet = TargetSet.getInstance();
        HashMap<String,TreeSet<Distance>> distanceMap = DistanceMap.getInstance().getDistanceMap();
        List<Commission> temp = new ArrayList<Commission>(targetSet.getCommissionTreeSet());
        int j = 0;
        for (int i = 0; i < AppConfig.MOVER_NUMBER && temp.size()>0; i++) {
            Commission commission = temp.get(0);
            boolean found = false;
            Integer threshold;
            int count = 1;
            while (!found){
                Iterator<Distance> itr = distanceMap.get(commission.getId()).iterator();
                threshold = 15*count;
                while (itr.hasNext()) {
                    Distance distance = itr.next();
                    if (!distance.getId().contains("M")){
                        if (Adjacency.getAdj().get(distance.getId())==null){
                            if (assigned.get(distance.getId())!=null){
                                Integer delivery = assigned.get(distance.getId()) + distance.getDistance();
                                if (delivery-commission.getTarget() <=threshold){
                                    HashMap<String,Integer> value = new HashMap<String, Integer>();
                                    value.put(distance.getId(),distance.getDistance());
                                    allocation.put(commission.getId(),value);
                                    Adjacency.getAdj().put(distance.getId(),commission.getId());
                                    //Goal.updateGoal(distance.getDistance()-commission.getTarget());
                                    assigned.remove(distance.getId());
                                    X.update(commission.getId(),commission.getTarget(),distance.getDistance());
                                    assigned.put(commission.getId(),X.getX().get(commission.getId()));
                                    found = true;
                                    break;
                                }
                            }
                        }
                    }
                }
                count++;
                if (count > 4 && !found){
                    deleted++;
                    break;
                }
            }
            temp.remove(0);
        }
                   for (Map.Entry<String, Integer> entry : assigned.entrySet()) {
                System.out.println("Commission: " + entry.getKey() + " - Raw:" + entry.getValue().toString()); }
        // System.out.println(allocation.toString());
        //System.out.println(Adjacency.getAdj().toString());
        targetSet.setCommissionTreeSet(new TreeSet<Commission>(temp));
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
                        Goal.updateGoal(distance.getDistance()-commission.getTarget());
                        X.update(commission.getId(),commission.getTarget(),distance.getDistance());
                        assigned.put(commission.getId(),X.getX().get(commission.getId()));
                        break;
                    }
                }
            }
            temp.remove(0);
        }
/*        for (Map.Entry<String, Integer> entry : assigned.entrySet()) {
            System.out.println("Commission: " + entry.getKey() + " - Raw:" + entry.getValue().toString()); }
        System.out.println();
        System.out.println();*/
       // System.out.println(allocation.toString());
        //System.out.println(assigned.toString());
        //System.out.println(Adjacency.getAdj().toString());
        targetSet.setCommissionTreeSet(new TreeSet<Commission>(temp));
    }

    public static void initScheduling(){
        allocation = new HashMap<String, HashMap<String, Integer>>();
        assigned = new HashMap<String, Integer>();
    }
}
