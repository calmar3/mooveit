package output;

import java.util.HashMap;

public class Goal {

    public static HashMap<String,Integer> goal = new HashMap<String, Integer>();

    public static HashMap<String, Integer> getGoal() {
        return goal;
    }

    public static void setGoal(HashMap<String, Integer> goal) {
        Goal.goal = goal;
    }

    public static void initGoal() {
        goal.put("value",0);
        goal.put("z",0);
        goal.put("z1",0);
        goal.put("z2",0);
        goal.put("w",0);
    }

    public static void updateGoal(Integer count){
        if (count == 2){
            goal.put("z",goal.get("z")+1);
            goal.put("value",goal.get("value")+1);
        }
        if (count == 3){
            goal.put("z1",goal.get("z1")+1);
            goal.put("value",goal.get("value")+2);
        }
        if (count == 4){
            goal.put("z2",goal.get("z2")+1);
            goal.put("value",goal.get("value")+3);
        }
        if (count > 4){
            goal.put("w",goal.get("w")+1);
            goal.put("value",goal.get("value")+10);
        }
    }
}
