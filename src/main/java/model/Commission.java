package model;

public class Commission implements Comparable<Commission>{

    private String id;
    private Double target;

    public Commission() {
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Double getTarget() {
        return target;
    }

    public void setTarget(Double target) {
        this.target = target;
    }

    @Override
    public String toString() {
        return "Commission{" +
                "id='" + id + '\'' +
                ", target=" + target +
                '}';
    }

    public int compareTo(Commission o) {
        // necessary to ensure the proper functioning of the treeset data structure
        if (this.target < o.target)
            return -1;
        else if ((this.target == o.target) && (this.id.equals(o.id)))
            return 0;
        else
            return 1;
    }
}
