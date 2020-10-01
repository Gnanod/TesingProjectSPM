package main.controller.TimeTableGenerate;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import main.controller.Pdf.PrintTimeTable;
import main.model.SessionArray;
import main.model.SessionTagGroup;
import main.service.SessionService;
import main.service.TimeTableGenerateService;
import main.service.WorkingDaysService;
import main.service.impl.SessionServiceImpl;
import main.service.impl.TimeTableGenerateServiceImpl;
import main.service.impl.WorkingDaysServiceImpl;

import java.net.URL;
import java.sql.SQLException;
import java.util.*;

public class TimeTableGenerateController implements Initializable {

    @FXML
    private Button btnGenerate;

    @FXML
    private ComboBox<?> StudentType;

    @FXML
    private TextField txtSubID;

    @FXML
    private TableView<?> tblTimeTable;


    @FXML
    void setText(ActionEvent event) {

    }


    private WorkingDaysService workingDaysService;
    private SessionService sessionService;
    private TimeTableGenerateService timeTableGenerateService;
    private static String timeSlot = "";
    private static double workingHours = 0;
    private static int workingDaysCount = 0;
    private static double hourSize = 0;
    String groupId = "Y1.S1.SE.01";

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        workingDaysService = new WorkingDaysServiceImpl();
        sessionService = new SessionServiceImpl();
        timeTableGenerateService = new TimeTableGenerateServiceImpl();
    }


    public int getCountOfWorkingDays() {
        int countWorkingDays = 0;
        try {
            countWorkingDays = workingDaysService.getCountOfWorkingDays();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return countWorkingDays;
    }

    public double getWorkingTime() {
        double workingTime = 0;
        try {
            workingTime = workingDaysService.getWorkingTime();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return workingTime;
    }

    public String getWorkingTimeType() {
        String type = "";
        try {
            type = workingDaysService.getWorkingTimeType();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return type;
    }

    @FXML
    private Button BtnGenerate;

    @FXML
    void generateTimeTable(ActionEvent event) {


        String[][] timeString = this.getStringArray();
        String[][] session = new String[workingDaysCount][(int) hourSize];
        String[][] roomSession = new String[workingDaysCount][(int) hourSize];
        try {
            ArrayList<SessionTagGroup> sessionList = sessionService.getSessionsAccordingToMainGroupId(groupId.trim());
            ArrayList<SessionTagGroup> parrellSessionListCategoryOne = sessionService.getParallelSessionsAccordingToMainGroupId(groupId.trim());
            int parallelSessionCount = parrellSessionListCategoryOne.size();
            if (parrellSessionListCategoryOne.size() != 0) {
                for (SessionTagGroup stg : parrellSessionListCategoryOne) {
                    if (stg.getConsectiveAdded().equalsIgnoreCase("Yes")) {
                        if (stg.getTagName().equalsIgnoreCase("Tute") || stg.getTagName().equalsIgnoreCase("Lecture")) {
                            double consectiveLecHour = this.timeTableGenerateService.getConsectiveSessionHourAccordingToSession(stg.getSessionId());
                            int consectiveLecId = this.timeTableGenerateService.getConsectiveSessionIdAccordingToSession(stg.getSessionId());
                            int tuteLecHourCount = 0;
                            int arraySpaces = 0;
                            double sessionHour = stg.getDuration();
                            if (timeSlot.equals("One Hour")) {
                                arraySpaces = (int) sessionHour;
                                tuteLecHourCount = (int) (stg.getDuration() + consectiveLecHour);
                            } else {
                                arraySpaces = (int) sessionHour * 2;
                                tuteLecHourCount = (int) (stg.getDuration() + consectiveLecHour);
                            }
                            ArrayList<Integer> subjectPreferedRoom = timeTableGenerateService.getSubjectPreferedRoom(stg.getSubjectId().trim(), stg.getTagId());
                            ArrayList<Integer> lecturesList = timeTableGenerateService.getLecturersAccordingToSessionId(stg.getSessionId());
                            ArrayList<Integer> lecturerPreferedRoomList = new ArrayList<>();
                            for (int i : lecturesList
                            ) {
                                ArrayList<Integer> lectureRoomId = timeTableGenerateService.getLecturerPrefferedList(i);
                                for (Integer lri : lectureRoomId
                                ) {
                                    lecturerPreferedRoomList.add(lri);
                                }
                            }
                            ArrayList<Integer> groupPreferedRooList = timeTableGenerateService.getPreferredRoomListForGroup(
                                    Integer.parseInt(stg.getGroupId()));
                            ArrayList<Integer> sessionPreferredRoomList = timeTableGenerateService.getPreferredRoomListForSession(stg.getSessionId());
                            String day = "";
                            if (subjectPreferedRoom.size() != 0) {
                                subjectRoomList:
                                for (Integer spr : subjectPreferedRoom) {
                                    int roomSize = getRoomSize(spr);
                                    if (stg.getStudentCount() <= roomSize) {
                                        firstLoop:
                                        for (int i = 0; i < workingDaysCount; i++) {
                                            day = getDay(i);
                                            int count = 0;
                                            for (int j = 0; j < hourSize; j++) {
                                                if (session[i][j] == null) {
                                                    String time = timeString[i][j];
                                                    String[] arrTime = time.split("-");
                                                    String toTime = arrTime[1];
                                                    String fromTime = arrTime[0];
                                                    boolean sessionIsAvailable = sessionIsAvailable(stg.getSessionId(), day, toTime, fromTime);
                                                    boolean notAvailableLectureStatus = false;
                                                    for (Integer lec : lecturesList
                                                    ) {
                                                        boolean status = timeTableGenerateService.getNotAvailableLectureStatus(toTime, fromTime, day, lec);
                                                        if (status) {
                                                            notAvailableLectureStatus = true;
                                                        }
                                                    }
                                                    boolean notAvailableGroupStatus = false;
                                                    if (stg.getTagName().equals("Lecture") || stg.getTagName().equals("Tute")) {
                                                        notAvailableGroupStatus = timeTableGenerateService.getNotAvailableGroupStaus(toTime, fromTime, Integer.parseInt(stg.getGroupId()), day);
                                                    } else {
                                                        notAvailableGroupStatus = timeTableGenerateService.getNotAvailableSubGroupStaus(toTime, fromTime, Integer.parseInt(stg.getSubGroupId()), day);
                                                    }
                                                    if (!sessionIsAvailable && !notAvailableLectureStatus && !notAvailableGroupStatus) {

                                                    }

                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            if (sessionList.size() != 0) {
                int tempConsectiveId = 0;
                int count = 0;
                int tempJ = 0;
                int tempSum = 0;
                int tempSessionId = 0;
                for (SessionTagGroup stg : sessionList) {
                    if (stg.getConsectiveAdded().equalsIgnoreCase("Yes")) {
                        if (stg.getTagName().equalsIgnoreCase("Tute") || stg.getTagName().equalsIgnoreCase("Lecture")) {
                            if (tempConsectiveId != stg.getSessionId()) {
                                double consectiveLecHour = this.timeTableGenerateService.getConsectiveSessionHourAccordingToSession(stg.getSessionId());
                                int consectiveLecId = this.timeTableGenerateService.getConsectiveSessionIdAccordingToSession(stg.getSessionId());
                                int tuteLecHourCount = 0;
                                int arraySpaces = 0;
                                double sessionHour = stg.getDuration();
                                if (timeSlot.equals("One Hour")) {
                                    arraySpaces = (int) sessionHour;
                                    tuteLecHourCount = (int) (stg.getDuration() + consectiveLecHour);
                                } else {
                                    arraySpaces = (int) sessionHour * 2;
                                    tuteLecHourCount = (int) (stg.getDuration() + consectiveLecHour);
                                }
                                ArrayList<Integer> subjectPreferedRoom = timeTableGenerateService.getSubjectPreferedRoom(stg.getSubjectId().trim(), stg.getTagId());
                                ArrayList<Integer> lecturesList = timeTableGenerateService.getLecturersAccordingToSessionId(stg.getSessionId());
                                ArrayList<Integer> lecturerPreferedRoomList = new ArrayList<>();
                                for (int i : lecturesList
                                ) {
                                    ArrayList<Integer> lectureRoomId = timeTableGenerateService.getLecturerPrefferedList(i);
                                    for (Integer lri : lectureRoomId
                                    ) {
                                        lecturerPreferedRoomList.add(lri);
                                    }
                                }
                                ArrayList<Integer> groupPreferedRooList = timeTableGenerateService.getPreferredRoomListForGroup(
                                        Integer.parseInt(stg.getGroupId()));
                                ArrayList<Integer> sessionPreferredRoomList = timeTableGenerateService.getPreferredRoomListForSession(stg.getSessionId());
                                String day = "";

                                if (subjectPreferedRoom.size() != 0) {
                                    System.out.println("Subject PREFFERD" + stg.getSessionId());
                                    subjectRoomList:
                                    for (Integer spr : subjectPreferedRoom) {
                                        int roomSize = getRoomSize(spr);
                                        if (stg.getStudentCount() <= roomSize) {
                                            firstLoop:
                                            for (int i = 0; i < workingDaysCount; i++) {
                                                day = getDay(i);
                                                secondforLoop:
                                                for (int j = 0; j < hourSize; j++) {
                                                    if (session[i][j] == null) {
                                                        String time = timeString[i][j];
                                                        String[] arrTime = time.split("-");
                                                        String toTime = arrTime[1];
                                                        String fromTime = arrTime[0];

                                                        boolean sessionIsAvailable = sessionIsAvailable(stg.getSessionId(), day, toTime, fromTime);
                                                        boolean notAvailableLectureStatus = false;
                                                        for (Integer lec : lecturesList
                                                        ) {
                                                            boolean status = timeTableGenerateService.getNotAvailableLectureStatus(toTime, fromTime, day, lec);
                                                            if (status) {
                                                                notAvailableLectureStatus = true;
                                                            }
                                                        }
                                                        boolean notAvailableGroupStatus = false;
                                                        if (stg.getTagName().equals("Lecture") || stg.getTagName().equals("Tute")) {
                                                            notAvailableGroupStatus = timeTableGenerateService.getNotAvailableGroupStaus(toTime, fromTime, Integer.parseInt(stg.getGroupId()), day);
//
                                                        } else {
                                                            notAvailableGroupStatus = timeTableGenerateService.getNotAvailableSubGroupStaus(toTime, fromTime, Integer.parseInt(stg.getSubGroupId()), day);
                                                        }
                                                        boolean roomIsAvailable = timeTableGenerateService.getRoomIsAvailable(toTime, fromTime, day, spr);

                                                        if (!sessionIsAvailable && !notAvailableLectureStatus && !notAvailableGroupStatus && !roomIsAvailable) {
                                                            boolean completeIndexses = false;
                                                            if (count == 0) {
                                                                System.out.println("ppppppppppppppppppppp");
                                                                int k = j;
                                                                int sum = k + (tuteLecHourCount - 1);
                                                                if (sum < hourSize) {
                                                                    tempJ = j;
                                                                    tempSum = sum;
                                                                    tempSessionId = stg.getSessionId();
                                                                    System.out.println("K" + k);
                                                                    System.out.println("tuteLec Hours" + tuteLecHourCount);
                                                                    System.out.println("temp Sum" + tempSum);
                                                                    System.out.println("temp J" + tempJ);
                                                                    try {
                                                                        for (int l = k; l <= sum; l++) {
                                                                            if (session[i][j] == null) {
                                                                                completeIndexses = true;
                                                                            } else {
                                                                                completeIndexses = false;
                                                                            }
                                                                        }
                                                                    } catch (ArrayIndexOutOfBoundsException e) {
                                                                        completeIndexses = false;
                                                                        break secondforLoop;
                                                                    }
                                                                    if (!completeIndexses) {
                                                                        break secondforLoop;
                                                                    }
                                                                    count++;
                                                                }
                                                            }
                                                            count++;
                                                            if (count == tuteLecHourCount) {
                                                                for (int m = tempJ; m < tempSum; m++) {
                                                                    session[i][m] = Integer.toString(tempSessionId);
                                                                    roomSession[i][m] = Integer.toString(spr);
                                                                }
                                                                session[i][tempSum] = Integer.toString(stg.getSessionId());
                                                                roomSession[i][tempSum] = Integer.toString(spr);
                                                                tempConsectiveId = consectiveLecId;
//                                                                count = 0;
//                                                                tempJ = 0;
//                                                                tempSum = 0;
//                                                                tempSessionId = 0;
                                                                break subjectRoomList;
                                                            }
//                                                            completeLoop:
//                                                            for (int k = 0; k < arraySpaces; k++) {
//                                                                try {
//                                                                    if (session[i][j + k] == null) {
//                                                                        completeStatus = true;
//                                                                        ;
//                                                                    }
//                                                                } catch (ArrayIndexOutOfBoundsException e) {
//                                                                    completeStatus = false;
//                                                                    break completeLoop;
//                                                                }
//                                                            }
//                                                            if (completeStatus) {
//                                                                if (count < arraySpaces) {
//                                                                    session[i][j] = Integer.toString(stg.getSessionId());
//                                                                    roomSession[i][j] = Integer.toString(spr);;
//                                                                }
//                                                                if (count >= arraySpaces && count < tuteLecHourCount) {
//                                                                    session[i][j] = Integer.toString(consectiveLecId);;;;
//                                                                    roomSession[i][j] = Integer.toString(spr);;
//                                                                }
//                                                                count++;
//                                                                if (tuteLecHourCount == count) {
//                                                                    tempConsectiveId = consectiveLecId;
//                                                                    break subjectRoomList;
//                                                                }
//                                                            }
                                                        } else {
                                                            count = 0;
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                } else if (lecturerPreferedRoomList.size() != 0) {
                                    System.out.println("Lecturer Preffered " + stg.getSessionId());

                                    lecturerRoomList:
                                    for (Integer lpr : lecturerPreferedRoomList) {
                                        int roomSize = getRoomSize(lpr);
                                        if (stg.getStudentCount() <= roomSize) {
                                            firstLoop:
                                            for (int i = 0; i < workingDaysCount; i++) {
                                                day = getDay(i);

                                                for (int j = 0; j < hourSize; j++) {
                                                    if (session[i][j] == null) {
                                                        String time = timeString[i][j];
                                                        String[] arrTime = time.split("-");
                                                        String toTime = arrTime[1];
                                                        String fromTime = arrTime[0];
                                                        boolean sessionIsAvailable = sessionIsAvailable(stg.getSessionId(), day, toTime, fromTime);
                                                        boolean notAvailableLectureStatus = false;
                                                        for (Integer lec : lecturesList
                                                        ) {
                                                            boolean status = timeTableGenerateService.getNotAvailableLectureStatus(toTime, fromTime, day, lec);
                                                            if (status) {
                                                                notAvailableLectureStatus = true;
                                                            }
                                                        }
                                                        boolean notAvailableGroupStatus = false;
                                                        if (stg.getTagName().equals("Lecture") || stg.getTagName().equals("Tute")) {
                                                            notAvailableGroupStatus = timeTableGenerateService.getNotAvailableGroupStaus(toTime, fromTime, Integer.parseInt(stg.getGroupId()), day);
//
                                                        } else {
                                                            notAvailableGroupStatus = timeTableGenerateService.getNotAvailableSubGroupStaus(toTime, fromTime, Integer.parseInt(stg.getSubGroupId()), day);
                                                        }
                                                        boolean roomIsAvailable = timeTableGenerateService.getRoomIsAvailable(toTime, fromTime, day, lpr);

                                                        if (!sessionIsAvailable && !notAvailableLectureStatus && !notAvailableGroupStatus && !roomIsAvailable) {
                                                            boolean completeStatus = false;
                                                            completeLoop:
                                                            for (int k = 0; k < arraySpaces; k++) {
                                                                try {
                                                                    if (session[i][j + k] == null) {
                                                                        completeStatus = true;
                                                                        ;
                                                                    }
                                                                } catch (ArrayIndexOutOfBoundsException e) {
                                                                    completeStatus = false;
                                                                    ;
                                                                    break completeLoop;
                                                                }
                                                            }
                                                            if (completeStatus) {
                                                                if (count < arraySpaces) {
                                                                    session[i][j] = Integer.toString(stg.getSessionId());
                                                                    ;
                                                                    roomSession[i][j] = Integer.toString(lpr);
                                                                }
                                                                if (count >= arraySpaces && count < tuteLecHourCount) {
                                                                    session[i][j] = Integer.toString(consectiveLecId);
                                                                    roomSession[i][j] = Integer.toString(lpr);
                                                                }
                                                                count++;
                                                                if (tuteLecHourCount == count) {
                                                                    tempConsectiveId = consectiveLecId;
                                                                    break lecturerRoomList;
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                } else if (groupPreferedRooList.size() != 0) {
                                    System.out.println("Group Preffered " + stg.getSessionId());

                                    groupRoomList:
                                    for (Integer gpr : groupPreferedRooList) {
                                        int roomSize = getRoomSize(gpr);
                                        if (stg.getStudentCount() <= roomSize) {
                                            firstLoop:
                                            for (int i = 0; i < workingDaysCount; i++) {
                                                day = getDay(i);

                                                for (int j = 0; j < hourSize; j++) {
                                                    if (session[i][j] == null) {
                                                        String time = timeString[i][j];
                                                        String[] arrTime = time.split("-");
                                                        String toTime = arrTime[1];
                                                        String fromTime = arrTime[0];
                                                        boolean sessionIsAvailable = sessionIsAvailable(stg.getSessionId(), day, toTime, fromTime);
                                                        boolean notAvailableLectureStatus = false;
                                                        for (Integer lec : lecturesList
                                                        ) {
                                                            boolean status = timeTableGenerateService.getNotAvailableLectureStatus(toTime, fromTime, day, lec);
                                                            if (status) {
                                                                notAvailableLectureStatus = true;
                                                            }
                                                        }
                                                        boolean notAvailableGroupStatus = false;
                                                        if (stg.getTagName().equals("Lecture") || stg.getTagName().equals("Tute")) {
                                                            notAvailableGroupStatus = timeTableGenerateService.getNotAvailableGroupStaus(toTime, fromTime, Integer.parseInt(stg.getGroupId()), day);
//
                                                        } else {
                                                            notAvailableGroupStatus = timeTableGenerateService.getNotAvailableSubGroupStaus(toTime, fromTime, Integer.parseInt(stg.getSubGroupId()), day);
                                                        }
                                                        boolean roomIsAvailable = timeTableGenerateService.getRoomIsAvailable(toTime, fromTime, day, gpr);

                                                        if (!sessionIsAvailable && !notAvailableLectureStatus && !notAvailableGroupStatus && !roomIsAvailable) {
                                                            boolean completeStatus = false;
                                                            completeLoop:
                                                            for (int k = 0; k < arraySpaces; k++) {
                                                                try {
                                                                    if (session[i][j + k] == null) {
                                                                        completeStatus = true;
                                                                        ;
                                                                        ;
                                                                    }
                                                                } catch (ArrayIndexOutOfBoundsException e) {
                                                                    completeStatus = false;
                                                                    ;
                                                                    break completeLoop;
                                                                }
                                                            }
                                                            if (completeStatus) {
                                                                if (count < arraySpaces) {
                                                                    session[i][j] = Integer.toString(stg.getSessionId());
                                                                    ;
                                                                    roomSession[i][j] = Integer.toString(gpr);
                                                                    ;
                                                                }
                                                                if (count >= arraySpaces && count < tuteLecHourCount) {
                                                                    session[i][j] = Integer.toString(consectiveLecId);
                                                                    roomSession[i][j] = Integer.toString(gpr);
                                                                }
                                                                count++;
                                                                if (tuteLecHourCount == count) {
                                                                    tempConsectiveId = consectiveLecId;
                                                                    break groupRoomList;
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                } else if (sessionPreferredRoomList.size() != 0) {
                                    System.out.println("Session Preffered " + stg.getSessionId());

                                    sessionRoomList:
                                    for (Integer spr : sessionPreferredRoomList) {
                                        int roomSize = getRoomSize(spr);
                                        ;
                                        if (stg.getStudentCount() <= roomSize) {
                                            firstLoop:
                                            for (int i = 0; i < workingDaysCount; i++) {
                                                day = getDay(i);

                                                for (int j = 0; j < hourSize; j++) {
                                                    if (session[i][j] == null) {
                                                        String time = timeString[i][j];
                                                        String[] arrTime = time.split("-");
                                                        String toTime = arrTime[1];
                                                        String fromTime = arrTime[0];
                                                        boolean sessionIsAvailable = sessionIsAvailable(stg.getSessionId(), day, toTime, fromTime);
                                                        boolean notAvailableLectureStatus = false;
                                                        for (Integer lec : lecturesList
                                                        ) {
                                                            boolean status = timeTableGenerateService.getNotAvailableLectureStatus(toTime, fromTime, day, lec);
                                                            if (status) {
                                                                notAvailableLectureStatus = true;
                                                            }
                                                        }
                                                        boolean notAvailableGroupStatus = false;
                                                        if (stg.getTagName().equals("Lecture") || stg.getTagName().equals("Tute")) {
                                                            notAvailableGroupStatus = timeTableGenerateService.getNotAvailableGroupStaus(toTime, fromTime, Integer.parseInt(stg.getGroupId()), day);
//
                                                        } else {
                                                            notAvailableGroupStatus = timeTableGenerateService.getNotAvailableSubGroupStaus(toTime, fromTime, Integer.parseInt(stg.getSubGroupId()), day);
                                                        }
                                                        boolean roomIsAvailable = timeTableGenerateService.getRoomIsAvailable(toTime, fromTime, day, spr);
                                                        if (!sessionIsAvailable && !notAvailableLectureStatus && !notAvailableGroupStatus && !roomIsAvailable) {
                                                            boolean completeStatus = false;
                                                            completeLoop:
                                                            for (int k = 0; k < arraySpaces; k++) {
                                                                try {
                                                                    if (session[i][j + k] == null) {
                                                                        completeStatus = true;
                                                                        ;
                                                                        System.out.println("");
                                                                    }
                                                                } catch (ArrayIndexOutOfBoundsException e) {
                                                                    completeStatus = false;
                                                                    ;
                                                                    break completeLoop;
                                                                }
                                                            }
                                                            if (completeStatus) {
                                                                if (count < arraySpaces) {
                                                                    session[i][j] = Integer.toString(stg.getSessionId());
                                                                    ;
                                                                    roomSession[i][j] = Integer.toString(spr);
                                                                    ;
                                                                }
                                                                if (count >= arraySpaces && count < tuteLecHourCount) {
                                                                    session[i][j] = Integer.toString(consectiveLecId);
                                                                    roomSession[i][j] = Integer.toString(spr);
                                                                }
                                                                count++;
                                                                if (tuteLecHourCount == count) {
                                                                    tempConsectiveId = consectiveLecId;
                                                                    ;
                                                                    break sessionRoomList;
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                } else {
                                    System.out.println("Other Preffered " + stg.getSessionId());

                                    Set<Integer> buildingList = new HashSet<>();
                                    Set<Integer> roomList = new HashSet<>();
                                    for (Integer lec : lecturesList
                                    ) {
                                        Integer buidling = timeTableGenerateService.getBuilidingForLecturer(lec);
                                        buildingList.add(buidling);
                                    }
                                    for (Integer i : buildingList
                                    ) {
                                        ArrayList<Integer> integers = timeTableGenerateService.getRoomsAccordingToBuilding(i);
                                        for (Integer room : integers
                                        ) {
                                            roomList.add(room);
                                        }
                                    }
                                    otherRoomList:
                                    for (Integer spr : roomList) {
                                        int roomSize = getRoomSize(spr);
                                        if (stg.getStudentCount() <= roomSize) {
                                            firstLoop:
                                            for (int i = 0; i < workingDaysCount; i++) {
                                                day = getDay(i);
                                                secondforLoop:
                                                for (int j = 0; j < hourSize; j++) {
                                                    if (session[i][j] == null) {
                                                        String time = timeString[i][j];
                                                        String[] arrTime = time.split("-");
                                                        String toTime = arrTime[1];
                                                        String fromTime = arrTime[0];

                                                        boolean sessionIsAvailable = sessionIsAvailable(stg.getSessionId(), day, toTime, fromTime);
                                                        boolean notAvailableLectureStatus = false;
                                                        for (Integer lec : lecturesList
                                                        ) {
                                                            boolean status = timeTableGenerateService.getNotAvailableLectureStatus(toTime, fromTime, day, lec);
                                                            if (status) {
                                                                notAvailableLectureStatus = true;
                                                            }
                                                        }
                                                        boolean notAvailableGroupStatus = false;
                                                        if (stg.getTagName().equals("Lecture") || stg.getTagName().equals("Tute")) {
                                                            notAvailableGroupStatus = timeTableGenerateService.getNotAvailableGroupStaus(toTime, fromTime, Integer.parseInt(stg.getGroupId()), day);
//
                                                        } else {
                                                            notAvailableGroupStatus = timeTableGenerateService.getNotAvailableSubGroupStaus(toTime, fromTime, Integer.parseInt(stg.getSubGroupId()), day);
                                                        }
                                                        boolean roomIsAvailable = timeTableGenerateService.getRoomIsAvailable(toTime, fromTime, day, spr);

                                                        if (!sessionIsAvailable && !notAvailableLectureStatus && !notAvailableGroupStatus && !roomIsAvailable) {
                                                            System.out.println("GGGGGGG");
                                                            System.out.println("i :" + i + " j" + j);
                                                            boolean completeIndexses = false;
                                                            if (count == 0) {
                                                                int k = j;
                                                                int sum = k + (tuteLecHourCount - 1);
                                                                if (sum < hourSize) {
                                                                    tempJ = j;
                                                                    tempSum = sum;
                                                                    tempSessionId = stg.getSessionId();
                                                                    System.out.println("K" + k);
                                                                    System.out.println("tuteLec Hours" + tuteLecHourCount);
                                                                    System.out.println("temp Sum" + tempSum);
                                                                    System.out.println("temp J" + tempJ);
                                                                    try {
                                                                        for (int l = k; l <= sum; l++) {
                                                                            if (session[i][j] == null) {
                                                                                completeIndexses = true;
                                                                            } else {
                                                                                completeIndexses = false;
                                                                            }
                                                                        }
                                                                    } catch (ArrayIndexOutOfBoundsException e) {
                                                                        completeIndexses = false;
                                                                        break secondforLoop;
                                                                    }
                                                                    if (!completeIndexses) {
                                                                        break secondforLoop;
                                                                    }
                                                                    count++;
                                                                }
                                                            }
                                                            count++;
                                                            if (count == tuteLecHourCount) {
                                                                for (int m = tempJ; m < tempSum; m++) {
                                                                    session[i][m] = Integer.toString(tempSessionId);
                                                                    roomSession[i][m] = Integer.toString(spr);
                                                                }
                                                                session[i][tempSum] = Integer.toString(stg.getSessionId());
                                                                roomSession[i][tempSum] = Integer.toString(spr);
                                                                tempConsectiveId = consectiveLecId;
//                                                                count = 0;
//                                                                tempJ = 0;
//                                                                tempSum = 0;
//                                                                tempSessionId = 0;
                                                                break otherRoomList;
                                                            }
                                                            //                                                            boolean completeStatus = false;
//                                                            completeLoop:
//                                                            for (int k = 0; k < arraySpaces; k++) {
//                                                                try {
//                                                                    if (session[i][j + k] == null) {
//                                                                        completeStatus = true;
//                                                                        ;
//                                                                    }
//                                                                } catch (ArrayIndexOutOfBoundsException e) {
//                                                                    completeStatus = false;
//                                                                    break completeLoop;
//                                                                }
//                                                            }
//                                                            if (completeStatus) {
//                                                                if (count < arraySpaces) {
//                                                                    session[i][j] = Integer.toString(stg.getSessionId());
//                                                                    roomSession[i][j] = Integer.toString(spr);
//                                                                }
//                                                                if (count >= arraySpaces && count < tuteLecHourCount) {
//                                                                    session[i][j] = Integer.toString(consectiveLecId);
//                                                                    roomSession[i][j] = Integer.toString(spr);
////                                                                    System.out.println("PPP");
//                                                                }
//                                                                count++;
//                                                                if (tuteLecHourCount == count) {
//                                                                    tempConsectiveId = consectiveLecId;
//                                                                    break otherRoomList;
//                                                                }
//                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }

                                }
                            }
                        }
                    } else if (stg.getTagName().equalsIgnoreCase("Lab") || (stg.getConsectiveAdded().equalsIgnoreCase("No") && stg.getIsConsecutive().equalsIgnoreCase("Yes"))) {
                        System.out.println("Lab");
                        int arraySpaces = 0;
                        double sessionHour = stg.getDuration();
                        if (timeSlot.equals("One Hour")) {
                            arraySpaces = (int) sessionHour;
                        } else {
                            arraySpaces = (int) sessionHour * 2;
                        }
                        ArrayList<Integer> subjectPreferedRoom = timeTableGenerateService.getSubjectPreferedRoom(stg.getSubjectId().trim(), stg.getTagId());
                        ArrayList<Integer> lecturesList = timeTableGenerateService.getLecturersAccordingToSessionId(stg.getSessionId());
                        ArrayList<Integer> lecturerPreferedRoomList = new ArrayList<>();
                        for (int i : lecturesList
                        ) {
                            ArrayList<Integer> lectureRoomId = timeTableGenerateService.getLecturerPrefferedList(i);
                            for (Integer lri : lectureRoomId
                            ) {
                                lecturerPreferedRoomList.add(lri);
                            }
                        }
                        ArrayList<Integer> groupPreferedRooList = new ArrayList<>();
                        if (stg.getTagName().equals("Lecture") || stg.getTagName().equals("Tute")) {
                            groupPreferedRooList = timeTableGenerateService.getPreferredRoomListForGroup(Integer.parseInt(stg.getGroupId()));
                        } else if (stg.getTagName().equals("Lab")) {
                            groupPreferedRooList = timeTableGenerateService.getPreferredRoomListForSubGroup(Integer.parseInt(stg.getSubGroupId()));
                        }
                        ArrayList<Integer> sessionPreferredRoomList = timeTableGenerateService.getPreferredRoomListForSession(stg.getSessionId());
                        String day = "";
                        if (subjectPreferedRoom.size() != 0) {
                            System.out.println("Lab Subject Pre" + stg.getSessionId());
                            groupList:
                            for (Integer spr : subjectPreferedRoom) {
                                int roomSize = getRoomSize(spr);
                                if (stg.getStudentCount() <= roomSize) {
                                    firstLoop:
                                    for (int i = 0; i < workingDaysCount; i++) {
                                        day = getDay(i);
                                        int labCount = 0;
                                        for (int j = 0; j < hourSize; j++) {
                                            if (session[i][j] == null) {
                                                String time = timeString[i][j];
                                                String[] arrTime = time.split("-");
                                                String toTime = arrTime[1];
                                                String fromTime = arrTime[0];

                                                boolean notAvailableSessionStatus = timeTableGenerateService.getNotAvailableSessionStatus(stg.getSessionId(), day, toTime, fromTime);
                                                boolean notAvailableLectureStatus = false;
                                                for (Integer lec : lecturesList
                                                ) {
                                                    boolean status = timeTableGenerateService.getNotAvailableLectureStatus(toTime, fromTime, day, lec);
                                                    if (status) {
                                                        notAvailableLectureStatus = true;
                                                    }
                                                }
                                                boolean notAvailableGroupStatus = false;
                                                if (stg.getTagName().equals("Lecture") || stg.getTagName().equals("Tute")) {
                                                    notAvailableGroupStatus = timeTableGenerateService.getNotAvailableGroupStaus(toTime, fromTime, Integer.parseInt(stg.getGroupId()), day);
//
                                                } else {
                                                    notAvailableGroupStatus = timeTableGenerateService.getNotAvailableGroupStaus(toTime, fromTime, Integer.parseInt(stg.getSubGroupId()), day);
                                                }
                                                boolean roomIsAvailable = timeTableGenerateService.getRoomIsAvailable(toTime, fromTime, day, spr);
//                                                System.out.println();
//                                                System.out.println("ToTime :"+toTime +"From Time :"+fromTime+"  SessionId :"+stg.getSessionId()+" SessionIs Available "+notAvailableGroupStatus);
//                                                System.out.println("ToTime :"+toTime +"From Time :"+fromTime+"SessionId :"+stg.getSessionId()+" Lecturer Available "+notAvailableLectureStatus);
//                                                System.out.println("ToTime :"+toTime +"From Time :"+fromTime+"SessionId :"+stg.getSessionId()+" Group IS Available "+notAvailableGroupStatus);
//                                                System.out.println("ToTime :"+toTime +"From Time :"+fromTime+"SessionId :"+stg.getSessionId()+" Room IS Available "+roomIsAvailable);
//                                                System.out.println();
                                                if (!notAvailableSessionStatus && !notAvailableLectureStatus && !notAvailableGroupStatus && !roomIsAvailable) {

                                                    boolean completeStatus = false;
                                                    completeLoop:
                                                    for (int k = 0; k < arraySpaces; k++) {
                                                        try {
                                                            if (session[i][j + k] == null) {
                                                                completeStatus = true;
                                                            }
                                                        } catch (ArrayIndexOutOfBoundsException e) {
                                                            completeStatus = false;
                                                            break completeLoop;
                                                        }

                                                    }
                                                    if (completeStatus) {
                                                        session[i][j] = Integer.toString(stg.getSessionId());
                                                        roomSession[i][j] = Integer.toString(spr);
                                                        labCount++;
                                                        if (labCount == arraySpaces) {
                                                            break groupList;
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        } else if (lecturerPreferedRoomList.size() != 0) {
                            System.out.println("Lab Lecture Pre" + stg.getSessionId());

                            lecturerRoomList:
                            for (Integer lpr : lecturerPreferedRoomList) {
                                int roomSize = getRoomSize(lpr);
                                if (stg.getStudentCount() <= roomSize) {
                                    firstLoop:
                                    for (int i = 0; i < workingDaysCount; i++) {
                                        day = getDay(i);
                                        int labCount = 0;
                                        for (int j = 0; j < hourSize; j++) {
                                            if (session[i][j] == null) {
                                                String time = timeString[i][j];
                                                String[] arrTime = time.split("-");
                                                String toTime = arrTime[1];
                                                String fromTime = arrTime[0];
                                                boolean sessionIsAvailable = sessionIsAvailable(stg.getSessionId(), day, toTime, fromTime);
                                                boolean notAvailableLectureStatus = false;
                                                for (Integer lec : lecturesList
                                                ) {
                                                    boolean status = timeTableGenerateService.getNotAvailableLectureStatus(toTime, fromTime, day, lec);
                                                    if (status) {
                                                        notAvailableLectureStatus = true;
                                                    }
                                                }
                                                boolean notAvailableGroupStatus = false;
                                                if (stg.getTagName().equals("Lecture") || stg.getTagName().equals("Tute")) {
                                                    notAvailableGroupStatus = timeTableGenerateService.getNotAvailableGroupStaus(toTime, fromTime, Integer.parseInt(stg.getGroupId()), day);
//
                                                } else {
                                                    notAvailableGroupStatus = timeTableGenerateService.getNotAvailableSubGroupStaus(toTime, fromTime, Integer.parseInt(stg.getSubGroupId()), day);
                                                }
                                                boolean roomIsAvailable = timeTableGenerateService.getRoomIsAvailable(toTime, fromTime, day, lpr);

                                                if (!sessionIsAvailable && !notAvailableLectureStatus && !notAvailableGroupStatus && !roomIsAvailable) {
                                                    boolean completeStatus = false;
                                                    completeLoop:
                                                    for (int k = 0; k < arraySpaces; k++) {
                                                        try {
                                                            if (session[i][j + k] == null) {
                                                                completeStatus = true;
                                                            }
                                                        } catch (ArrayIndexOutOfBoundsException e) {
                                                            completeStatus = false;
                                                            break completeLoop;
                                                        }

                                                    }
                                                    if (completeStatus) {
                                                        session[i][j] = Integer.toString(stg.getSessionId());
                                                        roomSession[i][j] = Integer.toString(lpr);
                                                        labCount++;
                                                        if (labCount == arraySpaces) {
                                                            break lecturerRoomList;
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        } else if (groupPreferedRooList.size() != 0) {
                            System.out.println("Lab Group Pre" + stg.getSessionId());

                            groupRoomList:
                            for (Integer gpr : groupPreferedRooList) {
                                int roomSize = getRoomSize(gpr);
                                if (stg.getStudentCount() <= roomSize) {
                                    firstLoop:
                                    for (int i = 0; i < workingDaysCount; i++) {
                                        day = getDay(i);
                                        int labCount = 0;
                                        ;
                                        for (int j = 0; j < hourSize; j++) {
                                            if (session[i][j] == null) {
                                                String time = timeString[i][j];
                                                String[] arrTime = time.split("-");
                                                String toTime = arrTime[1];
                                                String fromTime = arrTime[0];
                                                boolean sessionIsAvailable = sessionIsAvailable(stg.getSessionId(), day, toTime, fromTime);
                                                boolean notAvailableLectureStatus = false;
                                                for (Integer lec : lecturesList
                                                ) {
                                                    boolean status = timeTableGenerateService.getNotAvailableLectureStatus(toTime, fromTime, day, lec);
                                                    if (status) {
                                                        notAvailableLectureStatus = true;
                                                    }
                                                }
                                                boolean notAvailableGroupStatus = false;
                                                if (stg.getTagName().equals("Lecture") || stg.getTagName().equals("Tute")) {
                                                    notAvailableGroupStatus = timeTableGenerateService.getNotAvailableGroupStaus(toTime, fromTime, Integer.parseInt(stg.getGroupId()), day);
//
                                                } else {
                                                    notAvailableGroupStatus = timeTableGenerateService.getNotAvailableSubGroupStaus(toTime, fromTime, Integer.parseInt(stg.getSubGroupId()), day);
                                                }
                                                boolean roomIsAvailable = timeTableGenerateService.getRoomIsAvailable(toTime, fromTime, day, gpr);
                                                if (!sessionIsAvailable && !notAvailableLectureStatus && !notAvailableGroupStatus && !roomIsAvailable) {
                                                    boolean completeStatus = false;
                                                    completeLoop:
                                                    for (int k = 0; k < arraySpaces; k++) {
                                                        try {
                                                            if (session[i][j + k] == null) {
                                                                completeStatus = true;
                                                            }
                                                        } catch (ArrayIndexOutOfBoundsException e) {
                                                            completeStatus = false;
                                                            break completeLoop;
                                                        }

                                                    }
                                                    if (completeStatus) {
                                                        session[i][j] = Integer.toString(stg.getSessionId());
                                                        roomSession[i][j] = Integer.toString(gpr);
                                                        labCount++;
                                                        if (labCount == arraySpaces) {
                                                            break groupRoomList;
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        } else if (sessionPreferredRoomList.size() != 0) {
                            System.out.println("Lab Session Pre" + stg.getSessionId());

                            sessionRoomList:
                            for (Integer spr : sessionPreferredRoomList) {
                                int roomSize = getRoomSize(spr);
                                ;
                                if (stg.getStudentCount() <= roomSize) {
                                    firstLoop:
                                    for (int i = 0; i < workingDaysCount; i++) {
                                        day = getDay(i);
                                        int labCount = 0;
                                        ;
                                        for (int j = 0; j < hourSize; j++) {
                                            if (session[i][j] == null) {
                                                String time = timeString[i][j];
                                                String[] arrTime = time.split("-");
                                                String toTime = arrTime[1];
                                                String fromTime = arrTime[0];
                                                boolean sessionIsAvailable = sessionIsAvailable(stg.getSessionId(), day, toTime, fromTime);
                                                boolean notAvailableLectureStatus = false;
                                                for (Integer lec : lecturesList
                                                ) {
                                                    boolean status = timeTableGenerateService.getNotAvailableLectureStatus(toTime, fromTime, day, lec);
                                                    if (status) {
                                                        notAvailableLectureStatus = true;
                                                    }
                                                }
                                                boolean notAvailableGroupStatus = false;
                                                if (stg.getTagName().equals("Lecture") || stg.getTagName().equals("Tute")) {
                                                    notAvailableGroupStatus = timeTableGenerateService.getNotAvailableGroupStaus(toTime, fromTime, Integer.parseInt(stg.getGroupId()), day);
//
                                                } else {
                                                    notAvailableGroupStatus = timeTableGenerateService.getNotAvailableSubGroupStaus(toTime, fromTime, Integer.parseInt(stg.getSubGroupId()), day);
                                                }
                                                boolean roomIsAvailable = timeTableGenerateService.getRoomIsAvailable(toTime, fromTime, day, spr);
                                                if (!sessionIsAvailable && !notAvailableLectureStatus && !notAvailableGroupStatus && !roomIsAvailable) {
                                                    boolean completeStatus = false;
                                                    completeLoop:
                                                    for (int k = 0; k < arraySpaces; k++) {
                                                        try {
                                                            if (session[i][j + k] == null) {
                                                                completeStatus = true;
                                                            }
                                                        } catch (ArrayIndexOutOfBoundsException e) {
                                                            completeStatus = false;
                                                            break completeLoop;
                                                        }

                                                    }
                                                    if (completeStatus) {
                                                        session[i][j] = Integer.toString(stg.getSessionId());
                                                        roomSession[i][j] = Integer.toString(spr);
                                                        labCount++;
                                                        if (labCount == arraySpaces) {
                                                            break sessionRoomList;
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        } else {
                            System.out.println("Lab Other " + stg.getSessionId());

                            Set<Integer> buildingList = new HashSet<>();
                            Set<Integer> roomList = new HashSet<>();
                            for (Integer lec : lecturesList
                            ) {
                                Integer buidling = timeTableGenerateService.getBuilidingForLecturer(lec);
                                buildingList.add(buidling);
                            }
                            for (Integer i : buildingList
                            ) {
                                ArrayList<Integer> integers = timeTableGenerateService.getRoomsAccordingToBuilding(i);
                                for (Integer room : integers
                                ) {
                                    roomList.add(room);
                                }
                            }
                            otherRoomList:
                            for (Integer spr : roomList) {
                                int roomSize = getRoomSize(spr);
                                if (stg.getStudentCount() <= roomSize) {
                                    firstLoop:
                                    for (int i = 0; i < workingDaysCount; i++) {
                                        day = getDay(i);
                                        int labCount = 0;
                                        ;
                                        for (int j = 0; j < hourSize; j++) {
                                            if (session[i][j] == null) {
                                                String time = timeString[i][j];
                                                String[] arrTime = time.split("-");
                                                String toTime = arrTime[1];
                                                String fromTime = arrTime[0];

                                                boolean sessionIsAvailable = sessionIsAvailable(stg.getSessionId(), day, toTime, fromTime);
                                                boolean notAvailableLectureStatus = false;
                                                for (Integer lec : lecturesList
                                                ) {
                                                    boolean status = timeTableGenerateService.getNotAvailableLectureStatus(toTime, fromTime, day, lec);
                                                    if (status) {
                                                        notAvailableLectureStatus = true;
                                                    }
                                                }
                                                boolean notAvailableGroupStatus = false;
                                                if (stg.getTagName().equals("Lecture") || stg.getTagName().equals("Tute")) {
                                                    notAvailableGroupStatus = timeTableGenerateService.getNotAvailableGroupStaus(toTime, fromTime, Integer.parseInt(stg.getGroupId()), day);
//
                                                } else {
                                                    notAvailableGroupStatus = timeTableGenerateService.getNotAvailableSubGroupStaus(toTime, fromTime, Integer.parseInt(stg.getSubGroupId()), day);
                                                }
                                                boolean roomIsAvailable = timeTableGenerateService.getRoomIsAvailable(toTime, fromTime, day, spr);

                                                if (!sessionIsAvailable && !notAvailableLectureStatus && !notAvailableGroupStatus && !roomIsAvailable) {
                                                    boolean completeStatus = false;
                                                    completeLoop:
                                                    for (int k = 0; k < arraySpaces; k++) {
                                                        try {
                                                            if (session[i][j + k] == null) {
                                                                completeStatus = true;
                                                            }
                                                        } catch (ArrayIndexOutOfBoundsException e) {
                                                            completeStatus = false;
                                                            break completeLoop;
                                                        }

                                                    }
                                                    if (completeStatus) {
                                                        session[i][j] = Integer.toString(stg.getSessionId());
                                                        roomSession[i][j] = Integer.toString(spr);
                                                        labCount++;
                                                        if (labCount == arraySpaces) {
                                                            break otherRoomList;
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }

                        }
                    }
                }
            }
            String arrNew[][] = new String[workingDaysCount][(int) hourSize];
            for (int i = 0; i < workingDaysCount; i++) {
                for (int j = 0; j < hourSize; j++) {
                    if (session[i][j] != null) {
                        SessionArray sessionDetails = this.timeTableGenerateService.getSessionDetailsAccordingToSessionId(session[i][j]);
                        String subGroupId = this.timeTableGenerateService.getSubgroupIdAccordingToSession(session[i][j]);
                        String sessionString = sessionDetails.getSubjectCode() + "\n" + sessionDetails.getSubjectName() + "\n(" + sessionDetails.getTagName() + ")";
                        ArrayList<String> lecturer = this.timeTableGenerateService.getLecturerNamesAccordingTo(session[i][j]);
                        String lectString = "";
                        StringBuilder sb = new StringBuilder();
                        for (String lec : lecturer
                        ) {
                            sb.append(lec).append(",");
                        }
                        lectString = sb.deleteCharAt(sb.length() - 1).toString();
                        sessionString = sessionString + "\n" + lectString;
                        String roomNo = this.timeTableGenerateService.getRoomNumberAccordingToRoomId(roomSession[i][j]);
                        sessionString = sessionString + "\n" + roomNo + "\n" + subGroupId + "\n" + session[i][j];
                        arrNew[i][j] = sessionString;
                        sessionString = "";
                    } else {
                        arrNew[i][j] = "-";
                    }

                }
            }

            String[][] swapArray = new String[(int) hourSize][workingDaysCount];
            String[][] swapArrayTime = new String[(int) hourSize][workingDaysCount];
            for (int i = 0; i < (int) hourSize; i++) {
                for (int j = 0; j < workingDaysCount; j++) {
                    swapArray[i][j] = arrNew[j][i];
                    swapArrayTime[i][j] = timeString[j][i];
                }
            }
            System.out.println("////////////////////////////////////");

            for (int i = 0; i < (int) workingDaysCount; i++) {
                System.out.print("[ ");
                for (int j = 0; j < hourSize; j++) {
                    System.out.print(session[i][j] + " ");
                }
                System.out.print(" ]");
                System.out.println();
            }

            System.out.println("////////////////////////////");
            PrintTimeTable printTimeTable = new PrintTimeTable();
            printTimeTable.generateCustomerReportPdf(swapArray, swapArrayTime, workingDaysCount, (int) hourSize, groupId);
            for (int i = 0; i < workingDaysCount; i++) {
                String newday = getDay(i);
                for (int j = 0; j < (int) hourSize; j++) {
                    if (session[i][j] != null) {
                        String time = timeString[i][j];
                        String[] arrTime = time.split("-");
                        String toTime = arrTime[1];
                        String fromTime = arrTime[0];
                        boolean isAdded = this.timeTableGenerateService.SaveTimeTable(newday, toTime, fromTime, session[i][j], roomSession[i][j], timeString[i][j]);
//                        }
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }


    public boolean sessionIsAvailable(int sessionId, String day, String toTime, String fromTime) throws SQLException {
        return timeTableGenerateService.getNotAvailableSessionStatus(sessionId, day, toTime, fromTime);

    }

    private String getDay(int i) {
        String day = "";
        if (i == 0) {
            day = "Monday";
        } else if (i == 1) {
            day = "Tuesday";
        } else if (i == 2) {
            day = "Wednesday";
        } else if (i == 3) {
            day = "Thursday";
        } else if (i == 4) {
            day = "Friday";
        } else if (i == 5) {
            day = "Staurday";
        } else if (i == 6) {
            day = "Sunday";
        }
        return day;
    }

    public int getRoomSize(int roomId) {
        int result = 0;
        try {
            result = this.timeTableGenerateService.getRoomSize(roomId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public String[][] getStringArray() {
        timeSlot = this.getWorkingTimeType();
        workingHours = this.getWorkingTime();
        workingDaysCount = this.getCountOfWorkingDays();
        hourSize = 0;
        if (timeSlot.equals("One Hour")) {
            hourSize = workingHours;
        } else {
            hourSize = workingHours * 2;
        }
        String[][] timeString = new String[workingDaysCount][(int) hourSize];
        int hoursCount = 8;
        int minutCount = 30;
        for (int i = 0; i < workingDaysCount; i++) {
            for (int j = 0; j < hourSize; j++) {
                String tempTime = "";
                if (timeSlot.equals("One Hour")) {
                    if (hoursCount < 10) {
                        tempTime = "0" + hoursCount + ":" + minutCount;
                    } else {
                        tempTime = hoursCount + ":" + minutCount;
                    }
                    hoursCount += 1;
                    if (hoursCount < 10) {
                        timeString[i][j] = tempTime + "-0" + hoursCount + ":" + minutCount;
                    } else {
                        timeString[i][j] = tempTime + "-" + hoursCount + ":" + minutCount;
                    }
                } else {
                    if (minutCount != 30) {
                        if (hoursCount < 10) {
                            tempTime = "0" + hoursCount + ":" + minutCount + "0";
                        } else {
                            tempTime = hoursCount + ":" + minutCount + "0";
                        }

                    } else {
                        if (hoursCount < 10) {
                            tempTime = "0" + hoursCount + ":" + minutCount;
                        } else {
                            tempTime = hoursCount + ":" + minutCount;
                        }

                    }
                    minutCount += 30;
                    if (minutCount >= 60) {
                        hoursCount++;
                        minutCount = 0;
                    }
                    if (minutCount != 30) {
                        if (hoursCount < 10) {
                            timeString[i][j] = tempTime + "-0" + hoursCount + ":" + minutCount + "0";
                        } else {
                            timeString[i][j] = tempTime + "-" + hoursCount + ":" + minutCount + "0";
                        }

                    } else {
                        if (hoursCount < 10) {
                            timeString[i][j] = tempTime + "-0" + hoursCount + ":" + minutCount;
                        } else {
                            timeString[i][j] = tempTime + "-" + hoursCount + ":" + minutCount;

                        }
                    }
                }
            }
            hoursCount = 8;
            minutCount = 30;
        }
//        for (int i = 0; i < workingDaysCount; i++) {
//            System.out.print("[");
//            for (int j = 0; j < hourSize; j++) {
//                System.out.print(timeString[i][j] + " ");
//            }
//            System.out.print("]");
//            System.out.println();
//        }
        return timeString;
    }
}
