package main.service.impl;

import main.dbconnection.DBConnection;
import main.model.Building;
import main.model.MainGroupCount;
import main.model.Room;
import main.service.RoomService;

import java.sql.*;
import java.util.ArrayList;

public class RoomServiceImpl implements RoomService {

    private Connection connection;

    public RoomServiceImpl() {
        connection = DBConnection.getInstance().getConnection();
    }

    @Override
    public boolean saveRooms(Room room) throws SQLException {
        String SQL = "Insert into room Values(?,?,?,?)";
        PreparedStatement stm = connection.prepareStatement(SQL);
        stm.setObject(1, 0);
        stm.setObject(2, room.getBuildingid());
        stm.setObject(3, room.getRoom());
        stm.setObject(4, room.getCapacity());
        int res = stm.executeUpdate();
        return res > 0;
    }

    @Override
    public ArrayList<Room> getAllDetails() throws SQLException {
        String SQL =" Select r.rid, b.bid, r.room, r.capacity, b.center, b.building from building b ,room r where b.bid = r.buildingid";
        Statement stm = connection.createStatement();
        ResultSet rst = stm.executeQuery(SQL);
        ArrayList<Room> roomList = new ArrayList<>();
        while(rst.next()){
            Room roomRows = new Room(Integer.parseInt(rst.getString("r.rid")),
                    Integer.parseInt(rst.getString("b.bid")),
                    rst.getString("r.room"),
                    Integer.parseInt(rst.getString("r.capacity")),
                    rst.getString("b.center"),
                            rst.getString("b.building"));
            roomList.add(roomRows);
        }
        return roomList;
    }

    @Override
    public boolean deleteRoom(int key) throws SQLException {
        String SQL = "Delete From room where rid = '"+key+"'";
        Statement stm = connection.createStatement();
        return stm.executeUpdate(SQL)>0;
    }

    @Override
    public boolean updateRoomDetails(Room room12) throws SQLException {
        String SQL="Update room set "+
                "buildingid='"+room12.getBuildingid()+"', " +
                "room='"+room12.getRoom()+"',  " +
                "capacity='"+room12.getCapacity()+"'  " +
                "where rid='"+room12.getRid()+"'";

        Statement stm=connection.createStatement();
        return stm.executeUpdate(SQL)>0;
    }

    @Override
    public boolean searchRoom(String building, String room) throws SQLException {
        String SQL = "select rid from room where building = '" + building + "' " +
                "&& room='" + room + "'";
        Statement stm = connection.createStatement();
        ResultSet rst = stm.executeQuery(SQL);
        boolean result = false;
        if (rst.next()) {
            if (rst.getString("rid") != null) {
                result = true;
            } else {
                result = false;
            }
        }
        return result;
    }

    @Override
    public ArrayList<Room> getAllRoomDetails() throws SQLException {
        String SQL ="Select * from room r, building b";
        Statement stm = connection.createStatement();
        ResultSet rst = stm.executeQuery(SQL);
        ArrayList<Room> rooms = new ArrayList<>();
        while(rst.next()){
            Room r1 = new Room(Integer.parseInt(rst.getString("r.rid")),
                    Integer.parseInt(rst.getString("b.bid")),
                    rst.getString("r.room"),
                    Integer.parseInt(rst.getString("r.capacity")),
                            rst.getString("b.center"),
                            rst.getString("b.building"));
            rooms.add(r1);
        }
        return rooms;
    }

    @Override
    public ArrayList<Room> getAllDetailsForSearch(String rbuilding, String rroom) throws SQLException {
        String buildingsql = "";
        String roomSql = "";
        if (rbuilding != null) {
            buildingsql = " and b.building LIKE '%" + rbuilding + "%'";
        }
        if (rroom != null) {
            roomSql = " and r.room LIKE '%" + rroom + "%'";
        }
        String SQL = "select b.building, r.room, r.capacity " +
                "from building b, room r " +
                "where b.bid = r.buildingid "+buildingsql+" "+roomSql +" ";
        Statement stm = null;
        try {
            stm = connection.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        ResultSet rst = null;
        try {
            rst = stm.executeQuery(SQL);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        ArrayList<Room> roomA = new ArrayList<>();
        while(rst.next()){
            Room room = new Room(rst.getString("b.building"),
                    rst.getString("r.room"),
                    Integer.parseInt(rst.getString("r.capacity")));
            roomA.add(room);
        }
        return roomA;
    }


}
