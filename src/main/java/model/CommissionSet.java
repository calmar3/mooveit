package model;

import java.util.TreeSet;

public class CommissionSet {

    private TreeSet<Commission> commissionTreeSet;

    private static CommissionSet instance;

    private CommissionSet() {
        this.commissionTreeSet = new TreeSet<Commission>();
    }

    public static CommissionSet  getInstance(){
        if (instance == null)
            instance = new CommissionSet();
        return instance;
    }

    public void addCommission(Commission commission){
        this.commissionTreeSet.add(commission);
    }

    @Override
    public String toString() {
        return "CommissionSet{" +
                "commissionTreeSet=" + commissionTreeSet.toString() +
                '}';
    }
}
