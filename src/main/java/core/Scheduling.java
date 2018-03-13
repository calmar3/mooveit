package core;

import config.AppConfig;
import model.*;
import output.*;

import java.util.*;

public class Scheduling {

    private static HashMap<String,HashMap<String,Integer>> allocation;
    private static HashMap<String,Integer> assigned;
    private static HashMap<String,Integer> assigned2;
    private static TreeSet<Commission> proposed;

    public static void run() throws InterruptedException {
        initScheduling();
        assignMovers();
        proposed = new TreeSet<Commission>();
        while (TargetSet.getInstance().getCommissionTreeSet().size()>0){
            assignCommission();
            assigned = new HashMap<String, Integer>(assigned2);
            assigned2 = new HashMap<String, Integer>();



        }
    }

    public static void assignCommission() throws InterruptedException {

        TargetSet targetSet = TargetSet.getInstance();
        HashMap<String,TreeSet<Distance>> distanceMap = DistanceMap.getInstance().getDistanceMap();
        List<Commission> temp = new ArrayList<Commission>(targetSet.getCommissionTreeSet());
        HashMap<String, Integer> fromToDistance = new HashMap<String, Integer>();
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
                                Integer delivery = X.getX().get(distance.getId()) + distance.getDistance();
                                if (delivery-commission.getTarget() <= threshold){
                                    Commission tmp = new Commission();
                                    tmp.setId(distance.getId());
                                    tmp.setTarget((delivery-commission.getTarget()));
                                    fromToDistance.put(distance.getId(),distance.getDistance());
                                    proposed.add(tmp);
                                }
                            }
                        }
                    }
                }
                if(proposed.size()!=0){
                    Commission candidate = proposed.first();
                    HashMap<String,Integer> value = new HashMap<String, Integer>();
                    value.put(candidate.getId(),fromToDistance.get(candidate.getId()));
                    allocation.put(commission.getId(),value);
                    Adjacency.getAdj().put(candidate.getId(),commission.getId());
                    assigned.remove(candidate.getId());
                    X.update(commission.getId(),commission.getTarget(),fromToDistance.get(candidate.getId()),X.getX().get(candidate.getId()));
                    Goal.updateGoal(count);
                    assigned2.put(commission.getId(),X.getX().get(commission.getId()));
                    found = true;
                    updateVectors(count,commission.getId());
                    proposed.clear();
                    break;

                }
                count++;
                if (count > 4 && !found){
                   updateVectors(count,commission.getId());
                   Goal.updateGoal(count);
                   break;
                }
            }
            temp.remove(0);
        }
        targetSet.setCommissionTreeSet(new TreeSet<Commission>(temp));
    }

    private static void updateVectors(int count,String commission) {
        if (count == 2){
            Z.getZ().put(commission,1);
        }
        if (count == 3){
            Z1.getZ1().put(commission,1);
        }
        if (count == 4){
            Z2.getZ2().put(commission,1);
        }
        if (count > 4){
            W.getW().put(commission,1);
        }
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
                        X.update(commission.getId(),commission.getTarget(),distance.getDistance(),0);
                        assigned.put(commission.getId(),X.getX().get(commission.getId()));
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
        assigned = new HashMap<String, Integer>();
        assigned2 = new HashMap<String,Integer>();
    }
}
