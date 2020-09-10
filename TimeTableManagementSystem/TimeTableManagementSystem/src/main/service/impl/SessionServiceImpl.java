package main.service.impl;

import main.dbconnection.DBConnection;
import main.model.NotAvailableSession;
import main.service.SessionService;

import java.sql.*;

public class SessionServiceImpl implements SessionService {

    private Connection connection;

    public SessionServiceImpl() {
        connection = DBConnection.getInstance().getConnection();
    }

    @Override
    public int searchSession(int lecId, String subId, int tagId, int subGroupId, int mainGroupId) throws SQLException {
        String SQL ="";
        if(subGroupId!=0){
            SQL = "select sessionId from Session where lecturerId = '" + lecId + "' and subjectId ='"+subId+"' " +
                    "and tagId='" + tagId + "' and (subGroupId ='"+subGroupId+"' or groupId =NULL)";
        }else if(mainGroupId!=0){
            SQL = "select sessionId from Session where lecturerId = '" + lecId + "' and subjectId ='"+subId+"' " +
                    "and tagId='" + tagId + "' and (subGroupId =NULL or groupId ='"+mainGroupId+"')";
        }
        Statement stm = connection.createStatement();
        ResultSet rst = stm.executeQuery(SQL);
        System.out.println(SQL  );
        int result = 0;
        if (rst.next()) {
            System.out.println("GGG"+rst.getString("sessionId"));
            if (!rst.getString("sessionId").isEmpty()) {
                System.out.println("KKKK");
                result = Integer.parseInt(rst.getString("sessionId"));
            } else {
                result = 0;
            }
        }
        return result;
    }

    @Override
    public boolean saveDetails(NotAvailableSession nas) throws SQLException {
        String SQL = "Insert into NotAvailableSession Values(?,?,?,?,?)";
        PreparedStatement stm = connection.prepareStatement(SQL);
        stm.setObject(1, 0);
        stm.setObject(2, nas.getSessionId());
        stm.setObject(3, nas.getDay());
        stm.setObject(4, nas.getToTime());
        stm.setObject(5, nas.getFromTime());
        int res = stm.executeUpdate();
        return res > 0;
    }
}
