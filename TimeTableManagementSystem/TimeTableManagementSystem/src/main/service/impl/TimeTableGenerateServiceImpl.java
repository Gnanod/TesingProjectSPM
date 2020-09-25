package main.service.impl;

import main.dbconnection.DBConnection;
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
//        String SQL = "select id from notAvailableGroup where toTime >= '" + LocalTime.parse(toTime)+ "' and  fromTime <= '"+LocalTime.parse(fromTime)+"' and day='"+day+"'" +
////                "and mainGroupId='" + spr + "'";
////        System.out.println(SQL);
////        Statement stm = connection.createStatement();
////        ResultSet rst = stm.executeQuery(SQL);
////        boolean result = false;
////        if (rst.next()) {
////            if (rst.getString("id") != null) {
////                result = true;
////            } else {
////                result = false;
////            }
////        }
////        return result;

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
//        String SQL = "select id from notavailablesession where " +
//                "'" + LocalTime.parse(toTime) + "' BETWEEN  fromTime and toTime and '" + LocalTime.parse(fromTime) + "' BETWEEN fromTime and toTime and day='" + day + "'" +
//                "and sessionId='" + sessionId + "'";
//        Statement stm = connection.createStatement();
//        ResultSet rst = stm.executeQuery(SQL);
//        boolean result = false;
//        if (rst.next()) {
//            if (rst.getString("id") != null) {
//                result = true;
//            } else {
//                result = false;
//            }
//        }
//        return result;
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
//        String SQL = "select id from notavailablelecture where toTime >= '" + LocalTime.parse(toTime) + "' and  fromTime <= '" + LocalTime.parse(fromTime) + "' and day='" + day + "'" +
//                "and lectureId='" + lec + "'";
//        System.out.println(SQL);
//        Statement stm = connection.createStatement();
//        ResultSet rst = stm.executeQuery(SQL);
//        boolean result = false;
//        if (rst.next()) {
//            if (rst.getString("id") != null) {
//                result = true;
//            } else {
//                result = false;
//            }
//        }
//        return result;

        String SQLTo = "select id from notavailablelecture " +
                "where  lectureId='" + lec + "' and day='" + day + "' and '" + LocalTime.parse(toTime) + "' > fromTime " +
                "and '" + LocalTime.parse(toTime) + "' < toTime";
        String SQLFrom = "select id from notavailablelecture " +
                "where  lectureId='" + lec + "' and day='" + day + "' and '" + LocalTime.parse(fromTime) + "' > fromTime " +
                "and '" + LocalTime.parse(fromTime) + "' < toTime";
        System.out.println(SQLTo);
        System.out.println(SQLFrom);
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
        System.out.println("Result1 " + result);
        System.out.println("Result1 " + result1);
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
    public int getConsectiveSessionIdAccordingToSession(int sessionId) throws SQLException {
        String SQL = "select consectiveId from consectivesession where sessionId='"+sessionId+"'" ;
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


}
