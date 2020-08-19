package main.service;

import main.model.Building;
import main.model.Room;

import java.sql.SQLException;
import java.util.ArrayList;

public interface RoomService {

    public boolean saveRooms(Room room) throws SQLException;

//    public ArrayList<Building> getAllRoomDetails(String center) throws SQLException;

    ArrayList<Room> getAllDetails() throws SQLException;

    public boolean deleteRoom(int key) throws SQLException;

    boolean updateRoomDetails(Room room12) throws SQLException;

    public boolean searchRoom(String building, String room) throws SQLException;


}