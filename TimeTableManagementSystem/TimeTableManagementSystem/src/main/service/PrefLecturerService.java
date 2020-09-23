package main.service;

import main.model.Room;
import main.model.prefLecturer;

import java.sql.SQLException;
import java.util.ArrayList;

public interface PrefLecturerService {

    int getBuildingIdFromLecturer(String lecturer) throws SQLException;


    ArrayList<Room> getRoomNamesFromRooms(int buildingId) throws SQLException;

    int getRoomId(String room) throws SQLException;

    int getLecturerIdFromLecturers(String lecturer) throws SQLException;

    boolean savePrefLecturerRoom(prefLecturer prefLecturer) throws SQLException;
}
