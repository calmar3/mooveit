package model;

import java.util.ArrayList;
import java.util.List;

public class CommissionList {

    public static List<String> getList() {
        return list;
    }

    public static void setList(List<String> list) {
        CommissionList.list = list;
    }

    public static List<String> list = new ArrayList<>();

}
