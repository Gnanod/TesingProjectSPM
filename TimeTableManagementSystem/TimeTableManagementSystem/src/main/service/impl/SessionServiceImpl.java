package main.service.impl;

import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import main.dbconnection.DBConnection;
import main.model.ConsectiveSession;
import main.model.NotAvailableSession;
import main.service.SessionService;

import java.sql.*;
import java.util.ArrayList;

public class SessionServiceImpl implements SessionService {

    private Connection connection;

    public SessionServiceImpl() {
        connection = DBConnection.getInstance().getConnection();
    }

    @Override
    public int searchSession(int lecId, String subId, int tagId, int subGroupId, int mainGroupId) throws SQLException {
        String SQL = "";
        if (subGroupId != 0) {
            SQL = "select s.sessionId from Session s ,SessionLecture sl where s.sessionId= sl.sessionId and " +
                    "sl.lecturerId = '" + lecId + "' and subjectId ='" + subId + "' " +
                    "and tagId='" + tagId + "' and (subGroupId ='" + subGroupId + "' or groupId =NULL)";
        } else if (mainGroupId != 0) {
            SQL = "select s.sessionId from Session s ,SessionLecture sl  where s.sessionId= sl.sessionId and " +
                    "sl.lecturerId  = '" + lecId + "' " +
                    "and subjectId ='" + subId + "' " +
                    "and tagId='" + tagId + "' and (subGroupId =NULL or groupId ='" + mainGroupId + "')";
        }
        Statement stm = connection.createStatement();
        ResultSet rst = stm.executeQuery(SQL);
        int result = 0;
        if (rst.next()) {
            if (!rst.getString("sessionId").isEmpty()) {
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

    @Override
    public ArrayList<ConsectiveSession> getAllConsectiveSessions(String lecturer ,String subject) throws SQLException {
        String SQL = "";
        if (!lecturer.isEmpty() && !subject.isEmpty()) {
            SQL = "select s.sessionId,mg.groupid,t.tagName,su.subName " +
                    "from Session s ,Subject su,tag t,maingroup mg,SessionLecture sl ,Lecturer l " +
                    "where s.isConsecutive = 'Yes' and mg.id=s.groupId  and sl.sessionId= s.sessionId " +
                    "and l.employeeId= sl.lecturerId and " +
                    "su.subId = s.subjectId and t.tagid = s.tagId and s.consectiveAdded ='No' " +
                    "and l.employeeName='"+lecturer+"' and su.subId='"+subject+"' order by su.subName";
        }else if(lecturer.isEmpty() && subject.isEmpty()){
            SQL = "select s.sessionId,mg.groupid,t.tagName,su.subName " +
                    "from Session s ,Subject su,tag t,maingroup mg " +
                    "where s.isConsecutive = 'Yes' and mg.id=s.groupId  and " +
                    "su.subId = s.subjectId and t.tagid = s.tagId and s.consectiveAdded ='No' order by su.subName";
        }else if(lecturer.isEmpty() && !subject.isEmpty()){
            SQL = "select s.sessionId,mg.groupid,t.tagName,su.subName " +
                    "from Session s ,Subject su,tag t,maingroup mg " +
                    "where s.isConsecutive = 'Yes' and mg.id=s.groupId and " +
                    "su.subId = s.subjectId and t.tagid = s.tagId and s.consectiveAdded ='No' " +
                    "and su.subId='"+subject+"' order by su.subName";
        }else if(!lecturer.isEmpty() && subject.isEmpty()){
            SQL = "select s.sessionId,mg.groupid,t.tagName,su.subName " +
                    "from Session s ,Subject su,tag t,maingroup mg,SessionLecture sl ,Lecturer l " +
                    "where s.isConsecutive = 'Yes' and mg.id=s.groupId  and sl.sessionId= s.sessionId " +
                    "and l.employeeId= sl.lecturerId and " +
                    "su.subId = s.subjectId and t.tagid = s.tagId and s.consectiveAdded ='No' " +
                    "and l.employeeName='"+lecturer+"' order by su.subName";
        }
        Statement stm = connection.createStatement();
        ResultSet rst = stm.executeQuery(SQL);
        ArrayList<ConsectiveSession> csList = new ArrayList<>();
        while (rst.next()) {
            ConsectiveSession cs = new ConsectiveSession();
            cs.setId(Integer.parseInt(rst.getString("sessionId")));
            cs.setGroupId(rst.getString("groupid"));
            cs.setSubject(rst.getString("subName"));
            cs.setTag(rst.getString("tagName"));
            csList.add(cs);
        }
        return csList;
    }

    @Override
    public int getSessionIdForConsectiveSession(String groupId, String subject, String tagName) throws SQLException {
        String SQL = "select sessionId " +
                "from Session s ,maingroup mg ,Subject su,tag t " +
                "where s.isConsecutive = 'Yes'  and  mg.id=s.groupId and " +
                "su.subId = s.subjectId and t.tagid = s.tagId  and " +
                "mg.groupid ='" + groupId + "' and su.subName='" + subject + "' and t.tagName='" + tagName + "' ";
        Statement stm = connection.createStatement();
        ResultSet rst = stm.executeQuery(SQL);
        int result = 0;
        if (rst.next()) {
            if (!rst.getString("sessionId").isEmpty()) {
                result = Integer.parseInt(rst.getString("sessionId"));
            } else {
                result = 0;
            }
        }
        return result;
    }

    @Override
    public boolean updateRowForConsectiveSession(int id) throws SQLException {
        String SQL = "Update Session set consectiveAdded='Yes' where sessionId='" + id + "'";
        Statement stm = connection.createStatement();
        return stm.executeUpdate(SQL) > 0;
    }

    @Override
    public boolean saveCosectiveSession(int id, int id1) throws SQLException {
        String SQL = "Insert into ConsectiveSession Values(?,?,?)";
        PreparedStatement stm = connection.prepareStatement(SQL);
        stm.setObject(1, 0);
        stm.setObject(2, id);
        stm.setObject(3, id1);
        int res = stm.executeUpdate();
        return res > 0;
    }
}





