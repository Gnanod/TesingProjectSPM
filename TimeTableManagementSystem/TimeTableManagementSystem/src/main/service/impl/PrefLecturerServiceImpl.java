package main.service.impl;

import main.dbconnection.DBConnection;
import main.model.Room;
import main.model.prefLecturer;
import main.service.PrefLecturerService;

import java.sql.*;
import java.util.ArrayList;

public class PrefLecturerServiceImpl implements PrefLecturerService {

    private Connection connection;
    public PrefLecturerServiceImpl() {
        connection = DBConnection.getInstance().getConnection();
    }

    @Override
    public int getBuildingIdFromLecturer(String lecturer) throws SQLException {

        String SQL ="Select buildingId from Lecturer where employeeName LIKE '%" +lecturer+ "%'";
        System.out.println("getBuildingIdFromLecturer"+SQL);
        Statement stm = connection.createStatement();
        ResultSet rst = stm.executeQuery(SQL);

        int result=0;
        if(rst.next()){
            result = rst.getInt("buildingId");
        }

        return result;

    }

    @Override
    public ArrayList<Room> getRoomNamesFromRooms(int buildingId) throws SQLException {

        String SQL ="Select room from room where buildingid ='" +buildingId+ "'";
        System.out.println("getRoomIdFromRooms:"+SQL);
        Statement stm = connection.createStatement();
        ResultSet rst = stm.executeQuery(SQL);
        ArrayList<Room> roomidList = new ArrayList<>();
        while(rst.next()){
            Room roomRows = new Room(rst.getString("room"));
            roomidList.add(roomRows);
        }
        System.out.println("hhh:"+roomidList);
        return roomidList;
    }

    @Override
    public int getRoomId(String room) throws SQLException {
        String SQL ="Select rid from room where room LIKE '%"+room+"%'";
        System.out.println("getRoomId:"+SQL);
        Statement stm = connection.createStatement();
        ResultSet rst = stm.executeQuery(SQL);

        int result=0;
        if(rst.next()){
            result = rst.getInt("rid");
        }

        return result;
    }

    @Override
    public int getLecturerIdFromLecturers(String lecturer) throws SQLException {
        String SQL ="Select employeeId from Lecturer where employeeName LIKE '%"+lecturer+"%'";
        System.out.println("getTagIdFromTags:"+SQL);
        Statement stm = connection.createStatement();
        ResultSet rst = stm.executeQuery(SQL);

        int result=0;
        if(rst.next()){
            result = rst.getInt("employeeId");
        }

        return result;
    }

    @Override
    public boolean savePrefLecturerRoom(prefLecturer prefLecturer) throws SQLException {
        String SQL = "Insert into PrefRoomLecturer Values(?,?,?)";
        PreparedStatement stm = connection.prepareStatement(SQL);
        stm.setObject(1, 0);
        stm.setObject(2, prefLecturer.getEmployeeId());
        stm.setObject(3, prefLecturer.getRoomId());
        int res = stm.executeUpdate();
        return res > 0;
    }


}
