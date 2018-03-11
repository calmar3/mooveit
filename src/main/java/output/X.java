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

    public static void update(String commission, Integer target, Integer delay,Integer lastDelivery){
/*        if (commission.equals("5a199c0b649dcd3edb29ed7a")){
            System.out.println("comm:" +commission);
            System.out.println("target:" +target);
            System.out.println("delay:" +delay);
        }*/

        Integer delivery = (lastDelivery + delay) - target;
        if (delivery < -15){
            x.put(commission,target - AppConfig.EARLY_DELIVERY_TIME);
        }
        else{
            x.put(commission,lastDelivery + delay);
        }
    }
}
