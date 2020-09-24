package main.service;

import java.sql.SQLException;
import java.util.ArrayList;

public interface TimeTableGenerateService {

    ArrayList<Integer> getSubjectPreferedRoom(String subjectId,int tagId) throws SQLException;

    ArrayList<Integer> getLecturersAccordingToSessionId(int sessionId)throws SQLException;

    ArrayList<Integer> getLecturerPrefferedList(int i)throws SQLException;

    ArrayList<Integer> getPreferredRoomListForGroup(int groupId) throws SQLException;

    ArrayList<Integer> getPreferredRoomListForSession(int sessionId) throws SQLException;

    boolean getNotAvailableGroupStaus(String toTime, String fromTime, Integer spr, String day) throws SQLException;
}
