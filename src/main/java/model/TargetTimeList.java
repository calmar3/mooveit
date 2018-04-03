package model;

import java.util.ArrayList;
import java.util.List;

public class TargetTimeList {

    public static List<Integer> getList() {
        return list;
    }

    public static void setList(List<Integer> list) {
        TargetTimeList.list = list;
    }

    public static List<Integer> list = new ArrayList<>();
}
