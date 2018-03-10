package output;

import config.AppConfig;

import java.util.HashMap;

public class X {

    public static HashMap<String,Integer> x = new HashMap<String, Integer>();

    public static HashMap<String, Integer> getX() {
        return x;
    }

    public static void setX(HashMap<String, Integer> x) {
        X.x = x;
    }

    public static void update(String commission, Integer target, Integer delay){
        Integer delivery = delay-target;
        if (delivery < -15){
            x.put(commission,target- AppConfig.EARLY_DELIVERY_TIME);
        }
        else{
            //todo
            x.put(commission,delivery);
        }
    }
}
