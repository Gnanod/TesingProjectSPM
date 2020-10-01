package main.service.impl;

import main.dbconnection.DBConnection;
import main.model.PrefSubject;
import main.service.PrefSubjectService;

import java.sql.*;

public class PrefSubjectServiceImpl implements PrefSubjectService {

    private Connection connection;
    public PrefSubjectServiceImpl() {
        connection = DBConnection.getInstance().getConnection();
    }


    @Override
    public String getSubIdFromSubjects(String subject) throws SQLException {
        String SQL ="Select subId from Subject where subName LIKE '%" +subject+ "%'";
        System.out.println("getTagIdFromTags:"+SQL);
        Statement stm = connection.createStatement();
        ResultSet rst = stm.executeQuery(SQL);

        String result="";
        if(rst.next()){
            result = rst.getString("subId");
        }

        return result;
    }

    @Override
    public boolean savePrefSubjectRoom(PrefSubject prefSub) throws SQLException {
        String SQL = "Insert into PrefRoomSubject Values(?,?,?,?)";
        PreparedStatement stm = connection.prepareStatement(SQL);
        stm.setObject(1, 0);
        stm.setObject(2, prefSub.getTagId());
        stm.setObject(3, prefSub.getSubjectId());
        stm.setObject(4, prefSub.getRoomId());
        System.out.println(prefSub.getTagId());
        System.out.println(prefSub.getSubjectId());
        System.out.println(prefSub.getRoomId());

        int res = stm.executeUpdate();
        return res > 0;
    }
}
