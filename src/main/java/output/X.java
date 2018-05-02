package output;

import config.AppConfig;

import java.util.HashMap;

public class X {

    public static HashMap<String,Double> x = new HashMap<String, Double>();

    public static HashMap<String, Double> getX() {
        return x;
    }


    public static void update(String commission, Double target, Double delay,Double lastDelivery){
        Double delivery = (lastDelivery + delay) - target;
        if (delivery < AppConfig.MIN_DELIVERY_TIME){
            x.put(commission,target - AppConfig.EARLY_DELIVERY_TIME);
        }
        else{
            x.put(commission,lastDelivery + delay);
        }
    }
}
