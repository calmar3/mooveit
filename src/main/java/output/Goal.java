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

    public static void updateGoal(Integer delay){
        if (delay > 15 && delay <= 30){
            goal.put("z",goal.get("z")+1);
            goal.put("value",goal.get("value")+1);
        }
        if (delay > 30 && delay <= 45){
            goal.put("z1",goal.get("z1")+1);
            goal.put("value",goal.get("value")+2);
        }
        if (delay > 45 && delay <= 60){
            goal.put("z2",goal.get("z2")+1);
            goal.put("value",goal.get("value")+3);
        }
        if (delay > 60){
            goal.put("w",goal.get("w")+1);
            goal.put("value",goal.get("value")+10);
        }
    }
}
