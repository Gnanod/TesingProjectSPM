package main.service.impl;

import main.dbconnection.DBConnection;
import main.model.PrefTag;
import main.service.PrefTagService;

import java.sql.*;

public class PrefTagServiceImpl implements PrefTagService {

    private Connection connection;
    public PrefTagServiceImpl() {
        connection = DBConnection.getInstance().getConnection();
    }


    @Override
    public int getRoomId(String center, String building, String room) throws SQLException {
        String SQL ="Select r.rid from building b ,room r where b.bid = r.buildingid and b.center LIKE '%" + center + "%' and b.building LIKE '%" + building + "%' and r.room LIKE '%" + room + "%'";
        System.out.println("getroomid:"+SQL);
        Statement stm = connection.createStatement();
        ResultSet rst = stm.executeQuery(SQL);

        int result=0;
        if(rst.next()){
            result = rst.getInt("r.rid");
        }

//        System.out.println(rst.getInt("r.rid"));
        return result;
    }

    @Override
    public int getTagIdFromTags(String tag) throws SQLException {
        String SQL ="Select tagid from tag where tagName LIKE '%" + tag + "'";
        System.out.println("getTagIdFromTags:"+SQL);
        Statement stm = connection.createStatement();
        ResultSet rst = stm.executeQuery(SQL);

        int result=0;
        if(rst.next()){
            result = rst.getInt("tagid");
        }

        return result;
    }

    @Override
    public boolean savePrefTagRoom(PrefTag prefTag) throws SQLException {
        String SQL = "Insert into PrefRoomTag Values(?,?,?)";
        PreparedStatement stm = connection.prepareStatement(SQL);
        stm.setObject(1, 0);
        stm.setObject(2, prefTag.getTagId());
        stm.setObject(3, prefTag.getRoomId());

        int res = stm.executeUpdate();
        return res > 0;

    }

}
