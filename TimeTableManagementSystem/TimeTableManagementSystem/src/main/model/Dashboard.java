package main.model;

public class Dashboard {

    int noOfBuildings;
    String center;

    public Dashboard(){}

    public Dashboard(int noOfBuildings, String center) {
        this.noOfBuildings = noOfBuildings;
        this.center = center;
    }

    public int getNoOfBuildings() {
        return noOfBuildings;
    }

    public void setNoOfBuildings(int noOfBuildings) {
        this.noOfBuildings = noOfBuildings;
    }

    public String getCenter() {
        return center;
    }

    public void setCenter(String center) {
        this.center = center;
    }
}
