package main.model;

public class Room {

    int rid;
    int buildingid;
    String room;
    int capacity;
    String center;

    public Room(){}

    public Room(int rid, int buildingid, String room, int capacity) {
        this.rid = rid;
        this.buildingid = buildingid;
        this.room = room;
        this.capacity = capacity;
    }

    public Room(int rid, int buildingid, String room, int capacity, String center) {
        this.rid = rid;
        this.buildingid = buildingid;
        this.room = room;
        this.capacity = capacity;
        this.center = center;
    }

    public String getCenter() {
        return center;
    }

    public void setCenter(String center) {
        this.center = center;
    }

    public int getRid() {
        return rid;
    }

    public void setRid(int rid) {
        this.rid = rid;
    }

    public int getBuildingid() {
        return buildingid;
    }

    public void setBuildingid(int buildingid) {
        this.buildingid = buildingid;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }
}
