package model;

import java.util.TreeSet;

public class TargetSet {

    private TreeSet<Commission> commissionTreeSet;

    private static TargetSet instance;

    private TargetSet() {
        this.commissionTreeSet = new TreeSet<Commission>();
    }

    public static void clear() {
        instance = null;
    }

    public TreeSet<Commission> getCommissionTreeSet() {
        return commissionTreeSet;
    }

    public static TargetSet getInstance(){
        if (instance == null)
            instance = new TargetSet();
        return instance;
    }

    public void addCommission(Commission commission){
        this.commissionTreeSet.add(commission);
    }

    public void setCommissionTreeSet(TreeSet<Commission> commissionTreeSet) {
        this.commissionTreeSet = commissionTreeSet;
    }

    @Override
    public String toString() {
        return "TargetSet{" +
                "commissionTreeSet=" + commissionTreeSet.toString() +
                '}';
    }
}
