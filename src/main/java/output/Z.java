package output;

import java.util.HashMap;

public class Z {

    public static HashMap<String,Integer> z = new HashMap<String, Integer>();

    public static HashMap<String, Integer> getZ() {
        return z;
    }

    public static void setZ(HashMap<String, Integer> z) {
        Z.z = z;
    }

    public static void update() {
    }
}
