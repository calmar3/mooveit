package output;

import java.util.HashMap;

public class W {

    public static HashMap<String,Integer> w = new HashMap<String, Integer>();

    public static HashMap<String, Integer> getW() {
        return w;
    }

    public static void setW(HashMap<String, Integer> w) {
        W.w = w;
    }
}
