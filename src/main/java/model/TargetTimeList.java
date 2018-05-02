package model;

import java.util.ArrayList;
import java.util.List;

public class TargetTimeList {

    public static List<Double> getList() {
        return list;
    }

    public static void setList(List<Double> list) {
        TargetTimeList.list = list;
    }

    public static List<Double> list = new ArrayList<>();
}
