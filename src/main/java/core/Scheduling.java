package core;

import config.AppConfig;
import model.*;
import output.Goal;
import output.X;

import java.util.*;

public class Scheduling {

    private static HashMap<String,HashMap<String,Integer>> allocation;
    private static HashMap<String,Integer> assigned;
    private static HashMap<String,Integer> assigned2;
    private static Integer deleted = 0;

    public static void run() {
        initScheduling();
        int i = 1;
        System.out.println("--------------------------------------------STEP " + i + "----------------------------------------------------");
        assignMovers();
        while (TargetSet.getInstance().getCommissionTreeSet().size()>0){
/*            System.out.println("Pre assigned" + assigned.toString());
            System.out.println("Pre assigned2" + assigned2.toString());*/
            i++;
            System.out.println("--------------------------------------------STEP " + i + "----------------------------------------------------");
            assignCommission();
/*            System.out.println("Post assigned" + assigned.toString());
            System.out.println("Post assigned2" + assigned2.toString());*/
            /**
             * servono due mappe: i primi 38 ordini vanno in assigned, i secondi 38 in assigned due e man mano che vengono assegnati e sono
             * associati ad ordini di assigned, questi ultimi in assigned vengono rimossi. Servono per gestire Round senza sovrapposizioni
             */
            assigned = new HashMap<String, Integer>(assigned2);
            assigned2 = new HashMap<String, Integer>();
        }
        System.out.println("Deleted: " + deleted);
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
                                Integer delivery = X.getX().get(distance.getId()) + distance.getDistance();
                                if (delivery-commission.getTarget() <=threshold){
                                    /**
                                     * Stampa per verificare il target attuale, il precedente e il tempo di delivery tra un ordine ed un altro
                                     */
                                    System.out.println("commission id: " + commission.getId() + "\t target: " + commission.getTarget() + "\t prev target: "
                                            + X.getX().get(distance.getId()) + " \t prev distance" + distance.getDistance() + "\t\t prev id: " + distance.getId());
                                    HashMap<String,Integer> value = new HashMap<String, Integer>();
                                    value.put(distance.getId(),distance.getDistance());
                                    allocation.put(commission.getId(),value);
                                    Adjacency.getAdj().put(distance.getId(),commission.getId());
                                    //Goal.updateGoal(distance.getDistance()-commission.getTarget());
                                    assigned.remove(distance.getId());
                                    X.update(commission.getId(),commission.getTarget(),distance.getDistance(),X.getX().get(distance.getId()));
                                    /**
                                     * prima era assigned
                                     */
                                    assigned2.put(commission.getId(),X.getX().get(commission.getId()));
                                    found = true;
                                    break;
                                }
                            }
                        }
                    }
                }
                count++;
                if (count > 4 && !found){
                    System.out.println("qui dovrebbe cancellare");
                    deleted++;
                    break;
                }
            }
            temp.remove(0);
        }
/*                   for (Map.Entry<String, Integer> entry : assigned.entrySet()) {
                System.out.println("Commission: " + entry.getKey() + " - Raw:" + entry.getValue().toString()); }*/
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
                        System.out.println("commission id: " + commission.getId() + "\t target: " + commission.getTarget() + "\t\t prev target: "
                                + 0 + " \t prev distance" + distance.getDistance() + "\t\t prev id: " + distance.getId());
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
        assigned2 = new HashMap<String,Integer>();
    }
}
