package main.service.impl;

import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import main.dbconnection.DBConnection;
import main.model.*;
import main.service.SessionService;

import java.sql.*;
import java.util.ArrayList;

public class SessionServiceImpl implements SessionService {

    private Connection connection;
    private String mains;
    private  SessionDTO cs;
    private  ArrayList<SessionDTO> csList = new ArrayList<>();

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
    public int searchSessionByDetails(String subId, int tagId, int subGroupId, int mainGroupId) throws SQLException {
        String SQL1 = "";
        if (subGroupId != 0) {
            System.out.println("Hey"+subGroupId);
            SQL1 = "select sessionId from Session where" +
                  " subjectId ='" + subId + "' " +
                    "and tagId='" + tagId + "' and (subGroupId ='" + subGroupId + "' or groupId =NULL)";
        } else if (mainGroupId != 0) {
            System.out.println("Bluu"+mainGroupId);
            SQL1 = "select sessionId from Session where" +
                    " subjectId ='" + subId + "' " +
                    "and tagId='" + tagId + "' and (groupId ='" + mainGroupId + "' or  subGroupId =NULL)";
        }
        Statement stm1 = connection.createStatement();
        ResultSet rst1 = stm1.executeQuery(SQL1);
        int result1 = 0;
        if (rst1.next()) {
            if (!rst1.getString("sessionId").isEmpty()) {
                result1 = Integer.parseInt(rst1.getString("sessionId"));
            } else {
                result1 = 0;
            }
        }
        return result1;

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

    @Override
    public boolean addSession(Session s1) throws SQLException {
        if(s1.getSubGroupId()!=null){

            String SQL = "Insert into Session(subjectId,tagId,groupId,subGroupId,studentCount,duration,isConsecutive,consectiveAdded,isParallel,category)  Values(?,?,?,?,?,?,?,?,?,?)";
            PreparedStatement stm1 = connection.prepareStatement(SQL);
            stm1.setObject(1, s1.getSubjectId());
            stm1.setObject(2, s1.getTagId());
            stm1.setObject(3, Integer.parseInt(s1.getGroupId()));
            stm1.setObject(4, Integer.parseInt(s1.getSubGroupId()));
            stm1.setObject(5, s1.getStudentCount());
            stm1.setObject(6, s1.getDuration());
            stm1.setObject(7, s1.getIsConsecutive());
            stm1.setObject(8, "No");
            stm1.setObject(9, s1.getIsParallel());
            stm1.setObject(10, s1.getCategory());
            int res = stm1.executeUpdate();
            return res > 0;
        }else{

            String SQL = "Insert into Session(subjectId,tagId,groupId,studentCount,duration,isConsecutive,consectiveAdded,isParallel,category)  Values(?,?,?,?,?,?,?,?,?)";
            PreparedStatement stm2 = connection.prepareStatement(SQL);
            stm2.setObject(1, s1.getSubjectId());
            stm2.setObject(2, s1.getTagId());
            stm2.setObject(3, Integer.parseInt(s1.getGroupId()));
            stm2.setObject(4, s1.getStudentCount());
            stm2.setObject(5, s1.getDuration());
            stm2.setObject(6, s1.getIsConsecutive());
            stm2.setObject(7, "No");
            stm2.setObject(8, s1.getIsParallel());
            stm2.setObject(9, s1.getCategory());
            int res = stm2.executeUpdate();
            return res > 0;
        }

    }

    @Override
    public boolean addLectureSession(int lecturerId, int sessionId) throws SQLException {

        String SQL = "Insert into SessionLecture(lecturerId,sessionId) Values(?,?)";
        PreparedStatement stmt = connection.prepareStatement(SQL);
        stmt.setObject(1, lecturerId);
        stmt.setObject(2, sessionId);
        int res = stmt.executeUpdate();
        System.out.println(res);
        return res > 0;
    }

    @Override
    public ArrayList<SessionDTO> getAllSessions() throws SQLException {
        String SQL= "Select s.sessionId,sub.subName,t.tagName,s.studentCount,s.duration,l.employeeName,m.mgroupName from Session s ,SessionLecture sl,Subject sub,Lecturer l,tag t,maingroup m where s. sessionId=sl.sessionId and s.tagId=t.tagid and s.subjectId=sub.subId and sl.lecturerId=l.employeeId and s.groupId=m.id";
        Statement stmtnt = connection.createStatement();
        ResultSet rst = stmtnt.executeQuery(SQL);


        while (rst.next()) {
           // System.out.println(rst.getString("employeeName"));
            SessionDTO cs=new SessionDTO(Integer.parseInt(rst.getString("sessionId")),rst.getString("subName"), rst.getString("tagName"),Integer.parseInt( rst.getString("studentCount")),Float.parseFloat(rst.getString("duration")), rst.getString("mgroupName"),rst.getString("employeeName"));
            csList.add(cs);
        }
        return csList;

    }

    @Override
    public ArrayList<SessionDTO> searchSessions(String id) throws SQLException {

       // String SQL= "Select s.sessionId,sub.subName,t.tagName,s.studentCount,s.duration,l.employeeName,m.mgroupName from Session s ,SessionLecture sl,Subject sub,Lecturer l,tag t,maingroup m where s. sessionId=sl.sessionId and s.tagId=t.tagid and s.subjectId=sub.subId and sl.lecturerId=l.employeeId and s.groupId=m.id and s.subjectId='" + id + "' OR l.employeeName='" + id + "' OR m.mgroupName='" + id+ "' ";
        String sql="Select s.sessionId,sub.subName,t.tagName,s.studentCount,s.duration,l.employeeName,m.mgroupName from Session s ,SessionLecture sl,Subject sub,Lecturer l,tag t,maingroup m where s. sessionId=sl.sessionId and s.tagId=t.tagid and s.subjectId=sub.subId and sl.lecturerId=l.employeeId and s.groupId=m.id and sub.subName='" + id+ "' OR l.employeeName='" + id+ "' OR m.mgroupName='" + id+ "'";
        Statement stmtnty = connection.createStatement();
        ResultSet rst = stmtnty.executeQuery(sql);
        while (rst.next()) {
            // System.out.println(rst.getString("employeeName"));
            SessionDTO cs=new SessionDTO(Integer.parseInt(rst.getString("sessionId")),rst.getString("subName"), rst.getString("tagName"),Integer.parseInt( rst.getString("studentCount")),Float.parseFloat(rst.getString("duration")), rst.getString("mgroupName"),rst.getString("employeeName"));
            csList.add(cs);
        }
        return csList;
    }

    @Override
    public ArrayList<SessionTagGroup> getSessionsAccordingToMainGroupId(String groupId) throws SQLException {
        String SQL= "select s.*,t.tagname " +
                    "from Session s, tag t ,maingroup m " +
                    "where s.tagId = t.tagid and s.groupId = m.id and m.groupid='"+groupId+"' and isParallel='No' " +
                    "order by s.subjectId";
        Statement stmtnt = connection.createStatement();
        ResultSet rst = stmtnt.executeQuery(SQL);
        ArrayList<SessionTagGroup> csList = new ArrayList<>();

        while (rst.next()) {
            SessionTagGroup stg = new SessionTagGroup(
                    Integer.parseInt(rst.getString("sessionId")),
                    rst.getString("subjectId"),
                    Integer.parseInt(rst.getString("tagId")),
                    rst.getString("groupId"),
                    rst.getString("subGroupId"),
                    Integer.parseInt(rst.getString("studentCount")),
                    Float.parseFloat(rst.getString("duration")),
                    rst.getString("isConsecutive"),
                    rst.getString("consectiveAdded"),
                    rst.getString("tagname"));
            csList.add(stg);
        }
        return csList;
    }


}





