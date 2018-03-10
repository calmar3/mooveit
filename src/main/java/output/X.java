package output;

import java.util.HashMap;

public class X {

    public static HashMap<String,Integer> x = new HashMap<String, Integer>();

    public static HashMap<String, Integer> getX() {
        return x;
    }

    public static void setX(HashMap<String, Integer> x) {
        X.x = x;
    }
}
