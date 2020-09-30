package main.service.impl;

import main.dbconnection.DBConnection;
import main.model.*;
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
        String SQL = "select duration from session s where s.sessionId In " +
                "(Select consectiveId from consectivesession cs where cs.sessionId='" + sessionId + "') ";
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
        String SQL = "select consectiveId from consectivesession cs where cs.sessionId='" + sessionId + "'";
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
        int buidlingId = 0;
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
        String SQL = "select su.subId,su.subName,t.tagName " +
                "from Subject su ,session s,tag t " +
                "where s.sessionId='" + Integer.parseInt(s.trim()) + "' and s.tagId = t.tagid and s.subjectId = su.subId ";
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
        String SQL = "select l.employeeName " +
                "from sessionlecture sl ,session s ,lecturer l " +
                "where s.sessionId = sl.sessionId and s.sessionId='" + Integer.parseInt(s.trim()) + "' and l.employeeId = sl.lecturerId";
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
        String SQL = "select r.room " +
                "from room r " +
                "where rid='" + Integer.parseInt(s.trim()) + "' ";
        Statement stmtnt = connection.createStatement();
        ResultSet rst = stmtnt.executeQuery(SQL);
        String result = "";
        while (rst.next()) {
            result = rst.getString("room");
        }
        return result;

    }

    @Override
    public String getSubgroupIdAccordingToSession(String s) throws SQLException {
        String SQL = "select sg.subgroupid " +
                "from session s, subgroup sg " +
                "where s.subGroupId = sg.id and s.sessionId='" + Integer.parseInt(s.trim()) + "'";
        Statement stmtnt = connection.createStatement();
        ResultSet rst = stmtnt.executeQuery(SQL);
        String result = "";
        while (rst.next()) {
            result = rst.getString("subgroupid");
        }
        return result;
    }

    @Override
    public boolean SaveTimeTable(String newday, String toTime, String fromTime, String s, String s1, String time) throws SQLException {
        String SQL = "Insert into timetable values(?,?,?,?,?,?,?)";
        PreparedStatement stm = connection.prepareStatement(SQL);
        stm.setObject(1, 0);
        stm.setObject(2, Integer.parseInt(s.trim()));
        stm.setObject(3, newday);
        stm.setObject(4, Integer.parseInt(s1.trim()));
        stm.setObject(5, toTime);
        stm.setObject(6, fromTime);
        stm.setObject(7, time);
        int res = stm.executeUpdate();
        return res > 0;
    }

    @Override
    public boolean getRoomIsAvailable(String toTime, String fromTime, String day, int roomId) throws SQLException {
        String SQL = "select id " +
                "from timetable t " +
                "where toTime='" + toTime + "' and fromTime='" + fromTime + "' and roomId='" + roomId + "' and day='" + day + "' ";
        Statement stm = connection.createStatement();
        ResultSet rst = stm.executeQuery(SQL);
        boolean result = false;
        if (rst.next()) {
            if (rst.getString("id") != null) {
                result = true;
            } else {
                result = false;
            }
        }
        return result;
//        String SQLTo = "select id from timetable " +
//                "where  day='" + day + "' and '" + LocalTime.parse(toTime) + "' > fromTime " +
//                "and '" + LocalTime.parse(toTime) + "' < toTime and roomId='"+roomId+"'";
//        String SQLFrom = "select id from timetable " +
//                "where   day='" + day + "' and '" + LocalTime.parse(fromTime) + "' > fromTime " +
//                "and '" + LocalTime.parse(fromTime) + "' < toTime and roomId='"+roomId+"'";
//        System.out.println(SQLTo);
//        System.out.println(SQLFrom);
//        Statement stm = connection.createStatement();
//        ResultSet rst = stm.executeQuery(SQLTo);
//        boolean result = false;
//        if (rst.next()) {
//            if (rst.getString("id") != null) {
//                result = true;
//            } else {
//                result = false;
//            }
//        }
//        rst = stm.executeQuery(SQLFrom);
//        boolean result1 = false;
//        if (rst.next()) {
//            if (rst.getString("id") != null) {
//                result1 = true;
//            } else {
//                result1 = false;
//            }
//        }
//        if (result || result1) {
//            return true;
//        } else {
//            return false;
//        }
    }

    @Override
    public ArrayList<LecturerTimeTable> getLectureTimeTableDetails(String lecName) throws SQLException {
        String SQL = "select  t.day,r.room,t.timeString,sb.subName,ta.tagName,sb.subId,s.groupId,s.subGroupId " +
                "from session s,timetable t , sessionlecture sl, room r,lecturer l ,subject sb ,tag ta " +
                "where s.sessionId = t.sessionId and s.sessionId=sl.sessionId and sl.lecturerId = l.employeeId " +
                "and sb.subId = s.subjectId and r.rid = t.roomId and ta.tagid=s.tagId " +
                " and l.employeeName ='" + lecName.trim() + "'";
        Statement stm = connection.createStatement();
        ResultSet rst = stm.executeQuery(SQL);
        ArrayList<LecturerTimeTable> lecturerTimeTables = new ArrayList<>();
        while (rst.next()) {
            LecturerTimeTable t1 = new LecturerTimeTable();
            t1.setDay(rst.getString("day"));
            t1.setRomm(rst.getString("room"));
            t1.setTagName(rst.getString("tagName"));
            t1.setSubName(rst.getString("subName"));
            t1.setTimeString(rst.getString("timeString"));
            t1.setMainGroupId(rst.getString("groupId"));
            t1.setSubGroupId(rst.getString("subGroupId"));
            t1.setSubCode(rst.getString("subId"));
            lecturerTimeTables.add(t1);
        }
        return lecturerTimeTables;
    }

    @Override
    public String getSubGroupId(int parseInt) throws SQLException {
        String SQL = "select subgroupid " +
                "from subgroup sg " +
                "where sg.id='" + parseInt+ "' ";
        Statement stmtnt = connection.createStatement();
        ResultSet rst = stmtnt.executeQuery(SQL);
        String result = "";
        while (rst.next()) {
            result = rst.getString("subgroupid");
        }
        return result;
    }

    @Override
    public String getMainGroupId(int parseInt) throws SQLException {
        String SQL = "select groupid " +
                "from maingroup mg " +
                "where mg.id='" + parseInt+ "' ";
        Statement stmtnt = connection.createStatement();
        ResultSet rst = stmtnt.executeQuery(SQL);
        String result = "";
        while (rst.next()) {
            result = rst.getString("groupid");
        }
        return result;
    }

    @Override
    public ArrayList<RoomTimeTable> getTimeTableForRoom(String center, String building, String room) throws SQLException {
        String SQL = "select t.day,t.timeString,sb.subName,ta.tagName,sb.subId,t.sessionId,s.groupId,s.subGroupId " +
                     "from session s,timetable t ,subject sb ,tag ta " +
                     "where s.sessionId = t.sessionId and sb.subId = s.subjectId and ta.tagid=s.tagId " +
                     "and t.roomId In (select r.rid " +
                     "from room r ,building b  " +
                     "where r.buildingid = b.bid and  b.center='"+center+"' " +
                     "and b.building='"+building+"' and r.room='"+room+"') ";
        Statement stmtnt = connection.createStatement();
        ResultSet rst = stmtnt.executeQuery(SQL);
        ArrayList<RoomTimeTable> result = new ArrayList<>();
        while (rst.next()) {
            RoomTimeTable roomTimeTable = new RoomTimeTable();
            roomTimeTable.setDay(rst.getString("day"));
            roomTimeTable.setTagName(rst.getString("tagName"));
            roomTimeTable.setSubName(rst.getString("subName"));
            roomTimeTable.setTimeString(rst.getString("timeString"));
            roomTimeTable.setMainGroupId(rst.getString("groupId"));
            roomTimeTable.setSubGroupId(rst.getString("subGroupId"));
            roomTimeTable.setSubCode(rst.getString("subId"));
            roomTimeTable.setSessionId(rst.getString("sessionId"));
            result.add(roomTimeTable);
        }
        return result;
    }

    @Override
    public ArrayList<ParallelSession> getParalleSessions(String id) throws SQLException {
        String SQL = "select sb.subName,ta.tagName,sb.subId,s.sessionId,sb.subName,mg.groupId,s.subgroupid,s.category " +
                     "from session s,subject sb ,tag ta,maingroup mg " +
                     "where  sb.subId = s.subjectId and ta.tagid=s.tagId and s.isParallel='Yes' " +
                     "and s.groupId=mg.id ";
        Statement stmtnt = connection.createStatement();
        ResultSet rst = stmtnt.executeQuery(SQL);
        ArrayList<ParallelSession> result = new ArrayList<>();
        while (rst.next()) {
            ParallelSession roomTimeTable = new ParallelSession();
            roomTimeTable.setSessionId(Integer.parseInt(rst.getString("sessionId")));
            roomTimeTable.setCategory(rst.getString("category"));
            roomTimeTable.setGroupId(rst.getString("groupId"));
            roomTimeTable.setSubgroupid(rst.getString("subgroupid"));
            roomTimeTable.setTagName(rst.getString("tagName"));
            roomTimeTable.setSubjectName(rst.getString("subName"));
            result.add(roomTimeTable);
        }
        return result;
    }

    @Override
    public String getResult() throws SQLException {
        String SQL = "select orderId from parrellSessions order by 1 desc limit 1";
        Statement stmtnt = connection.createStatement();
        ResultSet rst = stmtnt.executeQuery(SQL);
        String result = "";
        while (rst.next()) {
            result = rst.getString("orderId");
        }
        return result;
    }

    @Override
    public boolean addParallelSessions(ParallelSession p, String orderID) throws SQLException {
        String SQL = "Insert into parrellSessions values(?,?,?)";
        PreparedStatement stm = connection.prepareStatement(SQL);
        stm.setObject(1, 0);
        stm.setObject(2, p.getSessionId());
        stm.setObject(3, orderID);
        int res = stm.executeUpdate();
        return res > 0;
    }

    @Override
    public String getParallelSesionOrderNumberAccordingToId(int sessionId) throws SQLException {
        String SQL = "select orderId from parrellSessions where sessionId='"+sessionId+"'";
        Statement stmtnt = connection.createStatement();
        ResultSet rst = stmtnt.executeQuery(SQL);
        String result = "";
        while (rst.next()) {
            result = rst.getString("orderId");
        }
        return result;
    }


}
