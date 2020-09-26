package main.service.impl;

import main.dbconnection.DBConnection;
import main.model.Session;
import main.model.SessionArray;
import main.model.SessionDTO;
import main.service.TimeTableGenerateService;

import java.sql.*;
import java.time.LocalTime;
import java.util.ArrayList;

public class TimeTableGenerateServiceImpl implements TimeTableGenerateService {
    private Connection connection;

    public TimeTableGenerateServiceImpl() {
        connection = DBConnection.getInstance().getConnection();
    }


    @Override
    public ArrayList<Integer> getSubjectPreferedRoom(String subjectId, int tagId) throws SQLException {
        String SQL = "select roomId from PrefRoomSubject where subjectId ='" + subjectId + "' and tagId='" + tagId + "'";
        Statement stmtnt = connection.createStatement();
        ResultSet rst = stmtnt.executeQuery(SQL);
        ArrayList<Integer> roomList = new ArrayList<>();
        while (rst.next()) {
            roomList.add(Integer.parseInt(rst.getString("roomId")));
        }
        return roomList;
    }

    @Override
    public ArrayList<Integer> getLecturersAccordingToSessionId(int sessionId) throws SQLException {
        String SQL = "select lecturerId from SessionLecture where sessionId ='" + sessionId + "'";
        Statement stmtnt = connection.createStatement();
        ResultSet rst = stmtnt.executeQuery(SQL);
        ArrayList<Integer> roomList = new ArrayList<>();
        while (rst.next()) {
            roomList.add(Integer.parseInt(rst.getString("lecturerId")));
        }
        return roomList;
    }

    @Override
    public ArrayList<Integer> getLecturerPrefferedList(int i) throws SQLException {
        String SQL = "select roomId from PrefRoomLecturer where employeeId ='" + i + "'";
        Statement stmtnt = connection.createStatement();
        ResultSet rst = stmtnt.executeQuery(SQL);
        ArrayList<Integer> roomList = new ArrayList<>();
        while (rst.next()) {
            roomList.add(Integer.parseInt(rst.getString("roomId")));
        }
        return roomList;
    }

    @Override
    public ArrayList<Integer> getPreferredRoomListForGroup(int groupId) throws SQLException {
        String SQL = "select roomId from PrefRoomGroup where groupId ='" + groupId + "'";
        Statement stmtnt = connection.createStatement();
        ResultSet rst = stmtnt.executeQuery(SQL);
        ArrayList<Integer> roomList = new ArrayList<>();
        while (rst.next()) {
            roomList.add(Integer.parseInt(rst.getString("roomId")));
        }
        return roomList;
    }

    @Override
    public ArrayList<Integer> getPreferredRoomListForSession(int sessionId) throws SQLException {
        String SQL = "select roomId from PrefRoomSession where sessionId ='" + sessionId + "'";
        Statement stmtnt = connection.createStatement();
        ResultSet rst = stmtnt.executeQuery(SQL);
        ArrayList<Integer> roomList = new ArrayList<>();
        while (rst.next()) {
            roomList.add(Integer.parseInt(rst.getString("roomId")));
        }
        return roomList;
    }

    @Override
    public boolean getNotAvailableGroupStaus(String toTime, String fromTime, Integer spr, String day) throws SQLException {
        String SQLTo = "select id from notAvailableGroup " +
                "where  mainGroupId='" + spr + "' and day='" + day + "' and '" + LocalTime.parse(toTime) + "' > fromTime " +
                "and '" + LocalTime.parse(toTime) + "' < toTime";
        String SQLFrom = "select id from notAvailableGroup " +
                "where  mainGroupId='" + spr + "' and day='" + day + "' and '" + LocalTime.parse(fromTime) + "' > fromTime " +
                "and '" + LocalTime.parse(fromTime) + "' < toTime";
//        System.out.println(SQLTo);
//        System.out.println(SQLFrom  );
        Statement stm = connection.createStatement();
        ResultSet rst = stm.executeQuery(SQLTo);
        boolean result = false;
        if (rst.next()) {
            if (rst.getString("id") != null) {
                result = true;
            } else {
                result = false;
            }
        }
        rst = stm.executeQuery(SQLFrom);
        boolean result1 = false;
        if (rst.next()) {
            if (rst.getString("id") != null) {
                result1 = true;
            } else {
                result1 = false;
            }
        }
        if (result || result1) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean getNotAvailableSessionStatus(int sessionId, String day, String toTime, String fromTime) throws SQLException {
        String SQLTo = "select id from notavailablesession " +
                "where  sessionId='" + sessionId + "' and day='" + day + "' and '" + LocalTime.parse(toTime) + "' > fromTime " +
                "and '" + LocalTime.parse(toTime) + "' < toTime";
        String SQLFrom = "select id from notavailablesession " +
                "where  sessionId='" + sessionId + "' and day='" + day + "' and '" + LocalTime.parse(fromTime) + "' > fromTime " +
                "and '" + LocalTime.parse(fromTime) + "' < toTime";
//        System.out.println(SQLTo);
//        System.out.println(SQLFrom  );
        Statement stm = connection.createStatement();
        ResultSet rst = stm.executeQuery(SQLTo);
        boolean result = false;
        if (rst.next()) {
            if (rst.getString("id") != null) {
                result = true;
            } else {
                result = false;
            }
        }
        rst = stm.executeQuery(SQLFrom);
        boolean result1 = false;
        if (rst.next()) {
            if (rst.getString("id") != null) {
                result1 = true;
            } else {
                result1 = false;
            }
        }
        if (result || result1) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean getNotAvailableLectureStatus(String toTime, String fromTime, String day, Integer lec) throws SQLException {
        String SQLTo = "select id from notavailablelecture " +
                "where  lectureId='" + lec + "' and day='" + day + "' and '" + LocalTime.parse(toTime) + "' > fromTime " +
                "and '" + LocalTime.parse(toTime) + "' < toTime";
        String SQLFrom = "select id from notavailablelecture " +
                "where  lectureId='" + lec + "' and day='" + day + "' and '" + LocalTime.parse(fromTime) + "' > fromTime " +
                "and '" + LocalTime.parse(fromTime) + "' < toTime";
//        System.out.println(SQLTo);
//        System.out.println(SQLFrom);
        Statement stm = connection.createStatement();
        ResultSet rst = stm.executeQuery(SQLTo);
        boolean result = false;
        if (rst.next()) {
            if (rst.getString("id") != null) {
                result = true;
            } else {
                result = false;
            }
        }
        rst = stm.executeQuery(SQLFrom);
        boolean result1 = false;
        if (rst.next()) {
            if (rst.getString("id") != null) {
                result1 = true;
            } else {
                result1 = false;
            }
        }
        if (result || result1) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public int getRoomSize(int roomId) throws SQLException {
        String SQL = "select capacity from room where rid='" + roomId + "'";
        Statement stm = connection.createStatement();
        ResultSet rst = stm.executeQuery(SQL);
        int result = 0;
        if (rst.next()) {
            if (rst.getString("capacity") != null) {
                result = Integer.parseInt(rst.getString("capacity"));
            }
        }
        return result;
    }

    @Override
    public boolean getNotAvailableSubGroupStaus(String toTime, String fromTime, int parseInt, String day) throws SQLException {
        String SQLTo = "select id from notAvailableGroup " +
                "where  subgroupId='" + parseInt + "' and day='" + day + "' and '" + LocalTime.parse(toTime) + "' > fromTime " +
                "and '" + LocalTime.parse(toTime) + "' < toTime";
        String SQLFrom = "select id from notAvailableGroup " +
                "where  subgroupId='" + parseInt + "' and day='" + day + "' and '" + LocalTime.parse(fromTime) + "' > fromTime " +
                "and '" + LocalTime.parse(fromTime) + "' < toTime";
//        System.out.println(SQLTo);
//        System.out.println(SQLFrom  );
        Statement stm = connection.createStatement();
        ResultSet rst = stm.executeQuery(SQLTo);
        boolean result = false;
        if (rst.next()) {
            if (rst.getString("id") != null) {
                result = true;
            } else {
                result = false;
            }
        }
        rst = stm.executeQuery(SQLFrom);
        boolean result1 = false;
        if (rst.next()) {
            if (rst.getString("id") != null) {
                result1 = true;
            } else {
                result1 = false;
            }
        }
        if (result || result1) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public double getConsectiveSessionHourAccordingToSession(int sessionId) throws SQLException {
//        String SQL = "select duration from session s ,consectivesession cs where " +
//                " s.sessionId = cs.sessionId and cs.sessionId='"+sessionId+"' " ;
        String SQL ="select duration from session s where s.sessionId In " +
                "(Select consectiveId from consectivesession cs where cs.sessionId='"+sessionId+"') ";
        Statement stm = connection.createStatement();
        ResultSet rst = stm.executeQuery(SQL);
        double result = 0;
        if (rst.next()) {
            if (rst.getString("duration") != null) {
                result = Double.parseDouble(rst.getString("duration"));
            } else {
                result = 0;
            }
        }
        return result;
    }

    @Override
    public int getConsectiveSessionIdAccordingToSession(int sessionId) throws SQLException {
        String SQL = "select consectiveId from consectivesession cs where cs.sessionId='"+sessionId+"'";
        Statement stm = connection.createStatement();
        ResultSet rst = stm.executeQuery(SQL);
        int result = 0;
        if (rst.next()) {
            if (rst.getString("consectiveId") != null) {
                result = Integer.parseInt(rst.getString("consectiveId"));
            } else {
                result = 0;
            }
        }
        return result;
    }

    @Override
    public ArrayList<Integer> getPreferredRoomListForSubGroup(int parseInt) throws SQLException {
        String SQL = "select roomId from PrefRoomGroup where subGroupId ='" + parseInt + "'";
        Statement stmtnt = connection.createStatement();
        ResultSet rst = stmtnt.executeQuery(SQL);
        ArrayList<Integer> roomList = new ArrayList<>();
        while (rst.next()) {
            roomList.add(Integer.parseInt(rst.getString("roomId")));
        }
        return roomList;
    }

    @Override
    public Integer getBuilidingForLecturer(Integer i) throws SQLException {
        String SQL = "select buildingId from lecturer where employeeId ='" + i + "'";
        Statement stmtnt = connection.createStatement();
        ResultSet rst = stmtnt.executeQuery(SQL);
        int buidlingId =0;
        while (rst.next()) {
            buidlingId = Integer.parseInt(rst.getString("buildingId"));
        }
        return buidlingId;
    }

    @Override
    public ArrayList<Integer> getRoomsAccordingToBuilding(Integer i) throws SQLException {
        String SQL = "select rid from room where buildingid ='" + i + "'";
        Statement stmtnt = connection.createStatement();
        ResultSet rst = stmtnt.executeQuery(SQL);
        ArrayList<Integer> roomList = new ArrayList<>();
        while (rst.next()) {
            roomList.add(Integer.parseInt(rst.getString("rid")));
        }
        return roomList;
    }

    @Override
    public SessionArray getSessionDetailsAccordingToSessionId(String s) throws SQLException {
        String SQL="select su.subId,su.subName,t.tagName " +
                "from Subject su ,session s,tag t " +
                "where s.sessionId='"+Integer.parseInt(s.trim())+"' and s.tagId = t.tagid and s.subjectId = su.subId ";
        Statement stmtnt = connection.createStatement();
        ResultSet rst = stmtnt.executeQuery(SQL);
        SessionArray session = new SessionArray();
        while (rst.next()) {
            session.setSubjectCode(rst.getString("subId"));
            session.setSubjectName(rst.getString("subName"));
            session.setTagName(rst.getString("tagName"));

        }
        return session;
    }

    @Override
    public ArrayList<String> getLecturerNamesAccordingTo(String s) throws SQLException {
        String SQL ="select l.employeeName " +
                "from sessionlecture sl ,session s ,lecturer l " +
                "where s.sessionId = sl.sessionId and s.sessionId='"+Integer.parseInt(s.trim())+"' and l.employeeId = sl.lecturerId";
        Statement stmtnt = connection.createStatement();
        ResultSet rst = stmtnt.executeQuery(SQL);
        ArrayList<String> list = new ArrayList<>();
        while (rst.next()) {
            list.add(rst.getString("employeeName"));

        }
        return list;
    }

    @Override
    public String getRoomNumberAccordingToRoomId(String s) throws SQLException {
        String SQL ="select r.room " +
                    "from room r " +
                    "where rid='"+Integer.parseInt(s.trim())+"' ";
        Statement stmtnt = connection.createStatement();
        ResultSet rst = stmtnt.executeQuery(SQL);
        String result ="";
        while (rst.next()) {
            result =rst.getString("room");
        }
        return result;

    }

    @Override
    public String getSubgroupIdAccordingToSession(String s) throws SQLException {
        String SQL="select sg.subgroupid " +
                "from session s, subgroup sg " +
                "where s.subGroupId = sg.id and s.sessionId='"+Integer.parseInt(s.trim())+"'";
        Statement stmtnt = connection.createStatement();
        ResultSet rst = stmtnt.executeQuery(SQL);
        String result ="";
        while (rst.next()) {
            result =rst.getString("subgroupid");
        }
        return result;
    }


}
