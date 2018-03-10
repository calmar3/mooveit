package model;

import java.util.HashMap;

public class Adjacency {

    public static HashMap<String,String> adj = new HashMap<String, String>();

    public static HashMap<String, String> getAdj() {
        return adj;
    }

    public static void setAdj(HashMap<String, String> adj) {
        Adjacency.adj = adj;
    }
}
