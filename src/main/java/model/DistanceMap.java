package model;

import java.util.HashMap;
import java.util.TreeSet;

public class DistanceMap {

    private HashMap<String,TreeSet<Distance>> distanceMap;

    private static DistanceMap instance;

    private DistanceMap() {
        this.distanceMap = new HashMap<String, TreeSet<Distance>>();
    }

    public static DistanceMap getInstance(){
        if (instance == null)
            instance = new DistanceMap();
        return instance;
    }

    public static void clear() {
        instance = null;
    }

    public void addDistance(String key, Distance distance){
        TreeSet<Distance> set = this.distanceMap.get(key);
        if (set == null)
            set = new TreeSet<Distance>();
        set.add(distance);
        this.distanceMap.put(key,set);
    }

    public HashMap<String, TreeSet<Distance>> getDistanceMap() {
        return distanceMap;
    }

    @Override
    public String toString() {
        return "TargetSet{" +
                "commissionTreeSet=" + distanceMap.toString() +
                '}';
    }
}
