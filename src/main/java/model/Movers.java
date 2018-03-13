package model;

import java.util.ArrayList;
import java.util.List;

public class Movers {

    public static List<String> movers = new ArrayList<>();

    public static List<String> getMovers() {
        return movers;
    }

    public static void setMovers(List<String> movers) {
        Movers.movers = movers;
    }
}
