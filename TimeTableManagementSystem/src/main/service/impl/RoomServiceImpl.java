package main.service.impl;

import main.dbconnection.DBConnection;
import main.model.Building;
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
        stm.setObject(2, room.getRoom());
        stm.setObject(3, room.getCapacity());

        int res = stm.executeUpdate();
        return res > 0;
    }

//    @Override
//    public ArrayList<Room> getAllDetails() throws SQLException {
//        String SQL ="Select b.center, r.building, r.room, r.capacity  from building b , room r where b.bid = r.rid";
//        Statement stm = connection.createStatement();
//        ResultSet rst = stm.executeQuery(SQL);
//        ArrayList<Room> roomList = new ArrayList<>();
//        while(rst.next()){
//            Room roomRows = new Room(Integer.parseInt(rst.getString("r.rid")),
//                    rst.getString("b.center"),
//                    rst.getString("r.building"),
//                    rst.getString("r.room"),
//                    Integer.parseInt(rst.getString("r.capacity")));
//            roomList.add(roomRows);
//        }
//        return roomList;
//    }

    @Override
    public boolean deleteRoom(int key) throws SQLException {
        return false;
    }

    @Override
    public boolean updateRoomDetails(Room room12) throws SQLException {
        return false;
    }


}
