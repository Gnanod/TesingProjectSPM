package main.model;

public class Room {

    int rid;
    int buildingid;
    String room;
    int capacity;

    public Room(){}

    public Room(int rid, int buildingid, String room, int capacity) {
        this.rid = rid;
        this.buildingid = buildingid;
        this.room = room;
        this.capacity = capacity;
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
