package main.controller.TimeTableGenerate;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import main.controller.Pdf.PrintTimeTable;
import main.model.MainGroup;
import main.model.SessionArray;
import main.model.SessionTagGroup;
import main.service.MainGroupService;
import main.service.SessionService;
import main.service.TimeTableGenerateService;
import main.service.WorkingDaysService;
import main.service.impl.MainGroupServiceImpl;
import main.service.impl.SessionServiceImpl;
import main.service.impl.TimeTableGenerateServiceImpl;
import main.service.impl.WorkingDaysServiceImpl;
import org.controlsfx.control.textfield.AutoCompletionBinding;
import org.controlsfx.control.textfield.TextFields;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;

public class TimeTableGenerateGroupController implements Initializable {
    @FXML
    private Button btnGenerate;

    @FXML
    private TextField txtGroupId;

    private ArrayList<String> groupName;
    private MainGroupService mainGroupService;
    private AutoCompletionBinding<String> autoCompletionBinding;
    private WorkingDaysService workingDaysService;
    private SessionService sessionService;
    private TimeTableGenerateService timeTableGenerateService;
    private static String timeSlot = "";
    private static double workingHours = 0;
    private static int workingDaysCount = 0;
    private static double hourSize = 0;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        groupName = new ArrayList<>();
        mainGroupService = new MainGroupServiceImpl();
        this.getAllGroupDetails();
        workingDaysService = new WorkingDaysServiceImpl();
        sessionService = new SessionServiceImpl();
        timeTableGenerateService = new TimeTableGenerateServiceImpl();

    }

    public void getAllGroupDetails() {
        try {
            ArrayList<MainGroup> mainGroup = mainGroupService.getAllMainGroupDetails();
            groupName.clear();
            for (MainGroup m : mainGroup) {
                groupName.add(m.getGroupid());
            }
            autoCompletionBinding = TextFields.bindAutoCompletion(txtGroupId, groupName);
            TextFields.bindAutoCompletion(txtGroupId, groupName);
        } catch (SQLException e) {
            e.printStackTrace();
        }
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
        return timeString;
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

    public boolean sessionIsAvailable(int sessionId, String day, String toTime, String fromTime) throws SQLException {
        return timeTableGenerateService.getNotAvailableSessionStatus(sessionId, day, toTime, fromTime);

    }

    @FXML
    void generateTimeTable(ActionEvent event) {
        String groupId = txtGroupId.getText();
        String[][] timeString = this.getStringArray();
        String[][] session = new String[workingDaysCount][(int) hourSize];
        String[][] roomSession = new String[workingDaysCount][(int) hourSize];

        if (!groupId.isEmpty()) {
            try {
                ArrayList<SessionTagGroup> sessionList = sessionService.getSessionsAccordingToMainGroupId(groupId.trim());
                if (sessionList.size() != 0) {
                    int tempConsectiveId = 0;
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
                                        ;
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
                                                    int tempJ = 0;
                                                    int tempSum = 0;
                                                    int tempSessionId = 0;
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
                                                                    int k = j;
                                                                    int sum = k + (tuteLecHourCount - 1);
                                                                    if (sum <= hourSize) {
                                                                        tempJ = j;
                                                                        tempSum = sum;
                                                                        tempSessionId = stg.getSessionId();
                                                                        try {
                                                                            for (int l = k; l <= sum; l++) {
                                                                                if (session[i][l] == null) {
                                                                                    completeIndexses = true;
                                                                                } else {
                                                                                    completeIndexses = false;
                                                                                    break secondforLoop;
                                                                                }
                                                                            }
                                                                        } catch (ArrayIndexOutOfBoundsException e) {
                                                                            completeIndexses = false;
                                                                            break secondforLoop;
                                                                        }
                                                                        if (!completeIndexses) {
                                                                            break secondforLoop;
                                                                        }

                                                                    }
                                                                }
                                                                count++;
                                                                if (count == tuteLecHourCount) {
                                                                    for (int m = tempJ; m < tempSum; m++) {
                                                                        session[i][m] = Integer.toString(tempSessionId);
                                                                        roomSession[i][m] = Integer.toString(spr);
                                                                    }
                                                                    session[i][tempSum] = Integer.toString(consectiveLecId);
                                                                    roomSession[i][tempSum] = Integer.toString(spr);
                                                                    tempConsectiveId = consectiveLecId;
                                                                    break subjectRoomList;
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    } else if (lecturerPreferedRoomList.size() != 0) {
                                        lecturerRoomList:
                                        for (Integer lpr : lecturerPreferedRoomList) {
                                            int roomSize = getRoomSize(lpr);
                                            if (stg.getStudentCount() <= roomSize) {
                                                firstLoop:
                                                for (int i = 0; i < workingDaysCount; i++) {
                                                    day = getDay(i);
                                                    day = getDay(i);
                                                    int count = 0;
                                                    int tempJ = 0;
                                                    int tempSum = 0;
                                                    int tempSessionId = 0;
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
                                                            boolean roomIsAvailable = timeTableGenerateService.getRoomIsAvailable(toTime, fromTime, day, lpr);
                                                            if (!sessionIsAvailable && !notAvailableLectureStatus && !notAvailableGroupStatus && !roomIsAvailable) {
                                                                boolean completeIndexses = false;
                                                                if (count == 0) {
                                                                    int k = j;
                                                                    int sum = k + (tuteLecHourCount - 1);
                                                                    if (sum <= hourSize) {
                                                                        tempJ = j;
                                                                        tempSum = sum;
                                                                        tempSessionId = stg.getSessionId();
                                                                        try {
                                                                            for (int l = k; l <= sum; l++) {
                                                                                if (session[i][l] == null) {
                                                                                    completeIndexses = true;
                                                                                } else {
                                                                                    completeIndexses = false;
                                                                                    break secondforLoop;
                                                                                }
                                                                            }
                                                                        } catch (ArrayIndexOutOfBoundsException e) {
                                                                            completeIndexses = false;
                                                                            break secondforLoop;
                                                                        }
                                                                        if (!completeIndexses) {
                                                                            break secondforLoop;
                                                                        }

                                                                    }
                                                                }
                                                                count++;
                                                                if (count == tuteLecHourCount) {
                                                                    for (int m = tempJ; m < tempSum; m++) {
                                                                        session[i][m] = Integer.toString(tempSessionId);
                                                                        roomSession[i][m] = Integer.toString(lpr);
                                                                    }
                                                                    session[i][tempSum] = Integer.toString(consectiveLecId);
                                                                    roomSession[i][tempSum] = Integer.toString(lpr);
                                                                    tempConsectiveId = consectiveLecId;
                                                                    break lecturerRoomList;
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    } else if (groupPreferedRooList.size() != 0) {
                                        groupRoomList:
                                        for (Integer gpr : groupPreferedRooList) {
                                            int roomSize = getRoomSize(gpr);
                                            if (stg.getStudentCount() <= roomSize) {
                                                firstLoop:
                                                for (int i = 0; i < workingDaysCount; i++) {
                                                    day = getDay(i);
                                                    day = getDay(i);
                                                    int count = 0;
                                                    int tempJ = 0;
                                                    int tempSum = 0;
                                                    int tempSessionId = 0;
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
                                                            boolean roomIsAvailable = timeTableGenerateService.getRoomIsAvailable(toTime, fromTime, day, gpr);
                                                            if (!sessionIsAvailable && !notAvailableLectureStatus && !notAvailableGroupStatus && !roomIsAvailable) {
                                                                boolean completeIndexses = false;
                                                                if (count == 0) {
                                                                    int k = j;
                                                                    int sum = k + (tuteLecHourCount - 1);
                                                                    ;
                                                                    if (sum <= hourSize) {
                                                                        tempJ = j;
                                                                        tempSum = sum;
                                                                        tempSessionId = stg.getSessionId();
                                                                        try {
                                                                            for (int l = k; l <= sum; l++) {
                                                                                if (session[i][l] == null) {
                                                                                    completeIndexses = true;
                                                                                } else {
                                                                                    completeIndexses = false;
                                                                                    break secondforLoop;
                                                                                }
                                                                            }
                                                                        } catch (ArrayIndexOutOfBoundsException e) {
                                                                            completeIndexses = false;
                                                                            break secondforLoop;
                                                                        }
                                                                        if (!completeIndexses) {
                                                                            break secondforLoop;
                                                                        }

                                                                    }
                                                                }
                                                                count++;
                                                                if (count == tuteLecHourCount) {
                                                                    for (int m = tempJ; m < tempSum; m++) {
                                                                        session[i][m] = Integer.toString(tempSessionId);
                                                                        roomSession[i][m] = Integer.toString(gpr);
                                                                    }
                                                                    session[i][tempSum] = Integer.toString(consectiveLecId);
                                                                    roomSession[i][tempSum] = Integer.toString(gpr);
                                                                    tempConsectiveId = consectiveLecId;
                                                                    break groupRoomList;
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }

                                    } else if (sessionPreferredRoomList.size() != 0) {
                                        sessionRoomList:
                                        for (Integer spr : sessionPreferredRoomList) {
                                            int roomSize = getRoomSize(spr);
                                            if (stg.getStudentCount() <= roomSize) {
                                                firstLoop:
                                                for (int i = 0; i < workingDaysCount; i++) {
                                                    day = getDay(i);
                                                    day = getDay(i);
                                                    int count = 0;
                                                    int tempJ = 0;
                                                    int tempSum = 0;
                                                    int tempSessionId = 0;
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
                                                                    int k = j;
                                                                    int sum = k + (tuteLecHourCount - 1);
                                                                    if (sum <= hourSize) {
                                                                        tempJ = j;
                                                                        tempSum = sum;
                                                                        tempSessionId = stg.getSessionId();
                                                                        try {
                                                                            for (int l = k; l <= sum; l++) {
                                                                                if (session[i][l] == null) {
                                                                                    completeIndexses = true;
                                                                                } else {
                                                                                    completeIndexses = false;
                                                                                    ;
                                                                                    break secondforLoop;
                                                                                }
                                                                            }
                                                                        } catch (ArrayIndexOutOfBoundsException e) {
                                                                            completeIndexses = false;
                                                                            break secondforLoop;
                                                                        }
                                                                        if (!completeIndexses) {
                                                                            break secondforLoop;
                                                                        }

                                                                    }
                                                                }
                                                                count++;
                                                                if (count == tuteLecHourCount) {
                                                                    for (int m = tempJ; m < tempSum; m++) {
                                                                        session[i][m] = Integer.toString(tempSessionId);
                                                                        roomSession[i][m] = Integer.toString(spr);
                                                                        ;
                                                                    }
                                                                    session[i][tempSum] = Integer.toString(consectiveLecId);
                                                                    roomSession[i][tempSum] = Integer.toString(spr);
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
                                    } else {
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
                                            ;
                                            if (stg.getStudentCount() <= roomSize) {
                                                firstLoop:
                                                for (int i = 0; i < workingDaysCount; i++) {
                                                    day = getDay(i);
                                                    int count = 0;
                                                    int tempJ = 0;
                                                    int tempSum = 0;
                                                    int tempSessionId = 0;
                                                    ;
                                                    secondforLoop:
                                                    for (int j = 0; j < hourSize; j++) {
                                                        if (session[i][j] == null) {
                                                            String time = timeString[i][j];
                                                            String[] arrTime = time.split("-");
                                                            String toTime = arrTime[1];
                                                            String fromTime = arrTime[0];
                                                            boolean sessionIsAvailable = sessionIsAvailable(stg.getSessionId(), day, toTime, fromTime);
                                                            boolean notAvailableLectureStatus = false;;
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
                                                                    int k = j;
                                                                    int sum = k + (tuteLecHourCount - 1);
                                                                    if (sum <= hourSize) {
                                                                        tempJ = j;
                                                                        tempSum = sum;
                                                                        tempSessionId = stg.getSessionId();
                                                                        try {
                                                                            for (int l = k; l <= sum; l++) {
                                                                                if (session[i][l] == null) {
                                                                                    completeIndexses = true;
                                                                                    ;
                                                                                } else {
                                                                                    completeIndexses = false;
                                                                                    break secondforLoop;
                                                                                }
                                                                            }
                                                                        } catch (ArrayIndexOutOfBoundsException e) {
                                                                            completeIndexses = false;
                                                                            break secondforLoop;
                                                                        }
                                                                        if (!completeIndexses) {
                                                                            break secondforLoop;
                                                                        }

                                                                    }
                                                                }
                                                                count++;
                                                                if (count == tuteLecHourCount) {
                                                                    for (int m = tempJ; m < tempSum; m++) {
                                                                        session[i][m] = Integer.toString(tempSessionId);
                                                                        roomSession[i][m] = Integer.toString(spr);
                                                                    }
                                                                    session[i][tempSum] = Integer.toString(consectiveLecId);
                                                                    roomSession[i][tempSum] = Integer.toString(spr);
                                                                    tempConsectiveId = consectiveLecId;
                                                                    ;
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
                        } else if (stg.getTagName().equalsIgnoreCase("Lab") || (stg.getConsectiveAdded().equalsIgnoreCase("No") && stg.getIsConsecutive().equalsIgnoreCase("Yes"))) {
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
                                LabSubjectRoom:
                                for (Integer spr : subjectPreferedRoom) {
                                    int roomSize = getRoomSize(spr);
                                    if (stg.getStudentCount() <= roomSize) {
                                        firstLoop:
                                        for (int i = 0; i < workingDaysCount; i++) {
                                            day = getDay(i);
                                            int count = 0;
                                            int tempJ = 0;
                                            int tempSum = 0;
                                            int tempSessionId = 0;
                                            secondforLoop:
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
                                                    if (!notAvailableSessionStatus && !notAvailableLectureStatus && !notAvailableGroupStatus && !roomIsAvailable) {
                                                        boolean completeIndexses = false;
                                                        if (count == 0) {
                                                            int k = j;
                                                            int sum = k + (arraySpaces);
                                                            if (sum < hourSize) {
                                                                tempJ = j;
                                                                tempSum = sum;
                                                                tempSessionId = stg.getSessionId();
                                                                try {
                                                                    for (int l = k; l < sum; l++) {
                                                                        if (session[i][l] == null) {
                                                                            completeIndexses = true;
                                                                        } else {
                                                                            completeIndexses = false;
                                                                            break secondforLoop;
                                                                        }
                                                                    }
                                                                } catch (ArrayIndexOutOfBoundsException e) {
                                                                    completeIndexses = false;
                                                                    break secondforLoop;
                                                                }
                                                                if (!completeIndexses) {
                                                                    break secondforLoop;
                                                                }
                                                            }
                                                        }
                                                        count++;
                                                        if (count == arraySpaces) {
                                                            for (int m = tempJ; m < tempSum; m++) {
                                                                session[i][m] = Integer.toString(tempSessionId);
                                                                roomSession[i][m] = Integer.toString(spr);
                                                            }
                                                            break LabSubjectRoom;
                                                        }
                                                    }

                                                }
                                            }
                                        }
                                    }
                                }
                            }else if (lecturerPreferedRoomList.size() != 0) {
                                lecturerRoomList:
                                for (Integer lpr : lecturerPreferedRoomList) {
                                    int roomSize = getRoomSize(lpr);
                                    if (stg.getStudentCount() <= roomSize) {
                                        firstLoop:
                                        for (int i = 0; i < workingDaysCount; i++) {
                                            day = getDay(i);
                                            int count = 0;
                                            int tempJ = 0;
                                            int tempSum = 0;
                                            int tempSessionId = 0;
                                            secondforLoop:
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
                                                    boolean roomIsAvailable = timeTableGenerateService.getRoomIsAvailable(toTime, fromTime, day, lpr);
                                                    if (!notAvailableSessionStatus && !notAvailableLectureStatus && !notAvailableGroupStatus && !roomIsAvailable) {
                                                        boolean completeIndexses = false;
                                                        if (count == 0) {
                                                            int k = j;
                                                            int sum = k + (arraySpaces);
                                                            if (sum < hourSize) {
                                                                tempJ = j;
                                                                tempSum = sum;
                                                                tempSessionId = stg.getSessionId();
                                                                try {
                                                                    for (int l = k; l < sum; l++) {
                                                                        if (session[i][l] == null) {
                                                                            completeIndexses = true;
                                                                        } else {
                                                                            completeIndexses = false;
                                                                            break secondforLoop;
                                                                        }
                                                                    }
                                                                } catch (ArrayIndexOutOfBoundsException e) {
                                                                    completeIndexses = false;
                                                                    break secondforLoop;
                                                                }
                                                                if (!completeIndexses) {
                                                                    break secondforLoop;
                                                                }
                                                            }
                                                        }
                                                        count++;
                                                        if (count == arraySpaces) {
                                                            for (int m = tempJ; m < tempSum; m++) {
                                                                session[i][m] = Integer.toString(tempSessionId);
                                                                roomSession[i][m] = Integer.toString(lpr);
                                                            }
                                                            break lecturerRoomList;
                                                        }
                                                    }

                                                }
                                            }
                                        }

                                    }
                                }
                            }else if(groupPreferedRooList.size() != 0){
                                groupRoomList:
                                for (Integer gpr : groupPreferedRooList) {
                                    int roomSize = getRoomSize(gpr);
                                    if (stg.getStudentCount() <= roomSize) {
                                        firstLoop:
                                        for (int i = 0; i < workingDaysCount; i++) {
                                            day = getDay(i);
                                            int count = 0;
                                            int tempJ = 0;
                                            int tempSum = 0;
                                            int tempSessionId = 0;
                                            secondforLoop:
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
                                                    boolean roomIsAvailable = timeTableGenerateService.getRoomIsAvailable(toTime, fromTime, day, gpr);
                                                    if (!notAvailableSessionStatus && !notAvailableLectureStatus && !notAvailableGroupStatus && !roomIsAvailable) {
                                                        boolean completeIndexses = false;
                                                        if (count == 0) {
                                                            int k = j;
                                                            int sum = k + (arraySpaces);
                                                            if (sum < hourSize) {
                                                                tempJ = j;
                                                                tempSum = sum;
                                                                tempSessionId = stg.getSessionId();
                                                                try {
                                                                    for (int l = k; l < sum; l++) {
                                                                        if (session[i][l] == null) {
                                                                            completeIndexses = true;
                                                                        } else {
                                                                            completeIndexses = false;
                                                                            break secondforLoop;
                                                                        }
                                                                    }
                                                                } catch (ArrayIndexOutOfBoundsException e) {
                                                                    completeIndexses = false;
                                                                    break secondforLoop;
                                                                }
                                                                if (!completeIndexses) {
                                                                    break secondforLoop;
                                                                }
                                                            }
                                                        }
                                                        count++;
                                                        if (count == arraySpaces) {
                                                            for (int m = tempJ; m < tempSum; m++) {
                                                                session[i][m] = Integer.toString(tempSessionId);
                                                                roomSession[i][m] = Integer.toString(gpr);
                                                            }
                                                            break groupRoomList;
                                                        }
                                                    }

                                                }
                                            }
                                        }
                                    }
                                }
                            }else if(sessionPreferredRoomList.size() != 0){
                                sessionRoomList:
                                for (Integer spr : sessionPreferredRoomList) {
                                    int roomSize = getRoomSize(spr);
                                    ;
                                    if (stg.getStudentCount() <= roomSize) {
                                        firstLoop:
                                        for (int i = 0; i < workingDaysCount; i++) {
                                            day = getDay(i);
                                            int count = 0;
                                            int tempJ = 0;
                                            int tempSum = 0;
                                            int tempSessionId = 0;
                                            secondforLoop:
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
                                                    if (!notAvailableSessionStatus && !notAvailableLectureStatus && !notAvailableGroupStatus && !roomIsAvailable) {
                                                        boolean completeIndexses = false;
                                                        if (count == 0) {
                                                            int k = j;
                                                            int sum = k + (arraySpaces);
                                                            if (sum < hourSize) {
                                                                tempJ = j;
                                                                tempSum = sum;
                                                                tempSessionId = stg.getSessionId();
                                                                try {
                                                                    for (int l = k; l < sum; l++) {
                                                                        if (session[i][l] == null) {
                                                                            completeIndexses = true;
                                                                        } else {
                                                                            completeIndexses = false;
                                                                            break secondforLoop;
                                                                        }
                                                                    }
                                                                } catch (ArrayIndexOutOfBoundsException e) {
                                                                    completeIndexses = false;
                                                                    break secondforLoop;
                                                                }
                                                                if (!completeIndexses) {
                                                                    break secondforLoop;
                                                                }
                                                            }
                                                        }
                                                        count++;
                                                        if (count == arraySpaces) {
                                                            for (int m = tempJ; m < tempSum; m++) {
                                                                session[i][m] = Integer.toString(tempSessionId);
                                                                roomSession[i][m] = Integer.toString(spr);
                                                            }
                                                            break sessionRoomList;
                                                        }
                                                    }

                                                }
                                            }
                                        }
                                    }
                                }
                            }else{
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
                                    ;
                                    if (stg.getStudentCount() <= roomSize) {
                                        firstLoop:
                                        for (int i = 0; i < workingDaysCount; i++) {
                                            day = getDay(i);
                                            int count = 0;
                                            int tempJ = 0;
                                            int tempSum = 0;
                                            int tempSessionId = 0;
                                            ;
                                            secondforLoop:
                                            for (int j = 0; j < hourSize; j++) {
                                                if (session[i][j] == null) {
                                                    String time = timeString[i][j];
                                                    String[] arrTime = time.split("-");
                                                    String toTime = arrTime[1];
                                                    String fromTime = arrTime[0];
                                                    boolean sessionIsAvailable = sessionIsAvailable(stg.getSessionId(), day, toTime, fromTime);
                                                    boolean notAvailableLectureStatus = false;;
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
                                                            int k = j;
                                                            int sum = k + (arraySpaces);
                                                            if (sum < hourSize) {
                                                                tempJ = j;
                                                                tempSum = sum;
                                                                tempSessionId = stg.getSessionId();
                                                                try {
                                                                    for (int l = k; l < sum; l++) {
                                                                        if (session[i][l] == null) {
                                                                            completeIndexses = true;
                                                                        } else {
                                                                            completeIndexses = false;
                                                                            break secondforLoop;
                                                                        }
                                                                    }
                                                                } catch (ArrayIndexOutOfBoundsException e) {
                                                                    completeIndexses = false;
                                                                    break secondforLoop;
                                                                }
                                                                if (!completeIndexses) {
                                                                    break secondforLoop;
                                                                }
                                                            }
                                                        }
                                                        count++;
                                                        if (count == arraySpaces) {
                                                            for (int m = tempJ; m < tempSum; m++) {
                                                                session[i][m] = Integer.toString(tempSessionId);
                                                                roomSession[i][m] = Integer.toString(spr);
                                                            }
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
                    //////////////////////////////Create Session String ///////////////////
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

                    //////////////////////////////Swap Array/////////////////////
                    String[][] swapArray = new String[(int) hourSize][workingDaysCount];
                    String[][] swapArrayTime = new String[(int) hourSize][workingDaysCount];
                    for (int i = 0; i < (int) hourSize; i++) {
                        for (int j = 0; j < workingDaysCount; j++) {
                            swapArray[i][j] = arrNew[j][i];
                            swapArrayTime[i][j] = timeString[j][i];
                        }
                    }

                    ///////////////////////////////////////////


                    ///////////////////////////////
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
                            }
                        }
                    }

                    Alert al = new Alert(Alert.AlertType.INFORMATION);
                    al.setTitle(null);
                    al.setContentText("Time Table Generated Successfully");
                    al.setHeaderText(null);
                    al.showAndWait();

                } else {
                    Alert al = new Alert(Alert.AlertType.ERROR);
                    al.setTitle(null);
                    al.setContentText("Any Sessions Not Found");
                    al.setHeaderText(null);
                    al.showAndWait();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

        } else {
            Alert al = new Alert(Alert.AlertType.ERROR);
            al.setTitle(null);
            al.setContentText("Group Id Field Is Empty");
            al.setHeaderText(null);
            al.showAndWait();
        }

    }


}
