package main.service.impl;

import main.dbconnection.DBConnection;
import main.model.SessionDTO;
import main.service.TimeTableGenerateService;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class TimeTableGenerateServiceImpl implements TimeTableGenerateService {
    private Connection connection;

    public TimeTableGenerateServiceImpl() {
        connection = DBConnection.getInstance().getConnection();
    }


    @Override
    public ArrayList<Integer> getSubjectPreferedRoom(String subjectId,int tagId) throws SQLException {
        String SQL= "select roomId from PrefRoomSubject where subjectId ='"+subjectId+"' and tagId='"+tagId+"'";
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
        String SQL= "select lecturerId from SessionLecture where sessionId ='"+sessionId+"'";
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
        String SQL= "select roomId from PrefRoomLecturer where employeeId ='"+i+"'";
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
        String SQL= "select roomId from PrefRoomGroup where groupId ='"+groupId+"'";
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
        String SQL= "select roomId from PrefRoomSession where sessionId ='"+sessionId+"'";
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
//        String SQL = "select rid from room where toTime >= '" + toTime + "' and  fromTime <= '"+fromTime+"' and mainGroupId='"++"'" +
//                "&& room='" + room + "'";
//        Statement stm = connection.createStatement();
//        ResultSet rst = stm.executeQuery(SQL);
        boolean result = false;
//        if (rst.next()) {
//            if (rst.getString("rid") != null) {
//                result = true;
//            } else {
//                result = false;
//            }
//        }
        return result;
    }


}
