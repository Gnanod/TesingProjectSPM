package main.service.impl;

import main.dbconnection.DBConnection;
import main.model.PrefGroup;
import main.service.PrefGroupService;

import java.sql.*;

public class PrefGroupServiceImpl implements PrefGroupService {

    private Connection connection;

    public PrefGroupServiceImpl() {
        connection = DBConnection.getInstance().getConnection();
    }

    @Override
    public boolean savePrefGroupRoom(PrefGroup prefGroup) throws SQLException {
        String SQL = "Insert into PrefRoomGroup Values(?,?,?,?)";
        PreparedStatement stm = connection.prepareStatement(SQL);
        if(prefGroup.getGroupId() ==0){
            stm.setObject(2, null);
        }
        else{
            stm.setObject(2, prefGroup.getGroupId());
        }
        if(prefGroup.getSubGroupId() ==0){
            stm.setObject(3, null);
        }
        else{
            stm.setObject(3, prefGroup.getSubGroupId());
        }

        stm.setObject(1, 0);
        stm.setObject(4, prefGroup.getRoomId());

        System.out.println("savePrefGroupRoom:"+prefGroup.getGroupId());
        System.out.println("savePrefGroupRoom:"+prefGroup.getSubGroupId());
        System.out.println("savePrefGroupRoom:"+prefGroup.getRoomId());
        int res = stm.executeUpdate();
        return res > 0;
    }

    @Override
    public int getGroupMainId(String group) throws SQLException {
        String SQL ="Select id from maingroup where groupid LIKE '%"+group+"%'";
        System.out.println("getGroupMainId:"+SQL);
        Statement stm = connection.createStatement();
        ResultSet rst = stm.executeQuery(SQL);

        int result=0;
        if(rst.next()){
            result = rst.getInt("id");
        }

        return result;
    }

    @Override
    public int getGroupSubId(String group) throws SQLException {
        String SQL ="Select id from subgroup where subgroupid LIKE '%" + group + "%'";
        System.out.println("getGroupSubId:"+SQL);
        Statement stm = connection.createStatement();
        ResultSet rst = stm.executeQuery(SQL);

        int result=0;
        if(rst.next()){
            result = rst.getInt("id");
        }

        return result;
    }
}
