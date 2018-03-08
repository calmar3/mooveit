package model;

public class Distance implements Comparable<Distance> {

    private String id;
    private Integer distance;

    public Distance(String id, Integer distance) {
        this.id = id;
        this.distance = distance;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getDistance() {
        return distance;
    }

    public void setDistance(Integer distance) {
        this.distance = distance;
    }

    @Override
    public String toString() {
        return "Distance{" +
                "id='" + id + '\'' +
                ", distance=" + distance +
                '}';
    }

    public int compareTo(Distance o) {
        // necessary to ensure the proper functioning of the treeset data structure
        if (this.distance < o.distance)
            return -1;
        else if ((this.distance == o.distance) && (this.id.equals(o.id)))
            return 0;
        else
            return 1;
    }
}
