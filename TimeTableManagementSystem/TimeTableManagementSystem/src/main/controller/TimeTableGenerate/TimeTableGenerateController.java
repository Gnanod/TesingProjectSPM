package main.controller.TimeTableGenerate;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import main.model.Session;
import main.model.SessionTagGroup;
import main.service.SessionService;
import main.service.TimeTableGenerateService;
import main.service.WorkingDaysService;
import main.service.impl.SessionServiceImpl;
import main.service.impl.TimeTableGenerateServiceImpl;
import main.service.impl.WorkingDaysServiceImpl;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

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
        try {
            String groupId = "Y1.S1.SE.01";
            String[][] timeString = this.getStringArray();
            String[][] session = new String[workingDaysCount][(int) hourSize];
            ArrayList<SessionTagGroup> sessionList = sessionService.getSessionsAccordingToMainGroupId(groupId.trim());
            if (sessionList.size() != 0) {
                int consectiveSesionRoom = 0;
                int consectiveSessionId =0;
                int consectiveSessionCount=0;
                double lectureTagCount=0;
                double tuteTagCount=0;
                for (SessionTagGroup stg : sessionList
                ) {
                    double sessionHour = stg.getDuration();
                    int arraySpaces = 0;
                    if (timeSlot.equals("One Hour")){
                        arraySpaces = (int) sessionHour;
                        if(stg.getTagName().equalsIgnoreCase("Lecture")){
                            lectureTagCount= sessionHour;
                        }else if(stg.getTagName().equalsIgnoreCase("tute")){
                            tuteTagCount=sessionHour;
                        }
                    }else{
                        arraySpaces = (int) sessionHour * 2;
                        if(stg.getTagName().equalsIgnoreCase("Lecture")){
                            lectureTagCount= sessionHour*2;
                        }else if(stg.getTagName().equalsIgnoreCase("tute")){
                            tuteTagCount=sessionHour*2;
                        }
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
                        groupList:
                        for (Integer spr : subjectPreferedRoom
                        ) {
                            int roomSize = getRoomSize(spr);
                            if (stg.getStudentCount() <= roomSize) {
                                firstLoop:
                                for (int i = 0; i < workingDaysCount; i++) {
                                    day = getDay(i);
                                    //  int
                                    for (int j = 0; j < hourSize; j++) {
                                        if (session[i][j] != null) {
                                            String time = timeString[i][j];
                                            String[] arrTime = time.split("-");
                                            String toTime = arrTime[1];
                                            String fromTime = arrTime[0];
                                            boolean notAvailableSessionStatus = timeTableGenerateService.getNotAvailableSessionStatus(stg.getSessionId(), day, toTime, fromTime);
                                            boolean notAvailableLectureStatus = false;
                                            for (Integer lec : lecturesList
                                            ) {
                                                boolean status = timeTableGenerateService.getNotAvailableLectureStatus(toTime, fromTime, day, lec);
                                                System.out.println("Stats" + status);
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
                                            if (!notAvailableGroupStatus && !notAvailableSessionStatus && !notAvailableLectureStatus) {
                                                if (stg.getConsectiveAdded().equalsIgnoreCase("Yes")) {
                                                    consectiveSessionId = this.timeTableGenerateService.getConsectiveSessionIdAccordingToSession(stg.getSessionId());
                                                    if(consectiveSesionRoom!=0){

                                                    }
                                                    consectiveSesionRoom = spr;
                                                    if(consectiveSessionCount == (lectureTagCount+tuteTagCount)){
                                                        consectiveSesionRoom=0;
                                                        consectiveSessionId=0;
                                                    }
                                                    consectiveSessionCount++;
                                                }
                                                session[i][j] = Integer.toString(stg.getSessionId());
                                                if (arraySpaces == j)
                                                    break groupList;

                                            }else{
                                                System.out.println("Time Table Not Print");
                                            }

                                        }


                                    }
                                }
                            }
                        }
                    } else {

                    }


//                    for (int i = 0; i < workingDaysCount; i++) {
//                        day = getDay(i);
//                        for (int j = 0; j < hourSize; j++) {
//                            String time = timeString[i][j];
//                            String[] arrTime = time.split("-");
//                            String toTime = arrTime[1];
//                            String fromTime = arrTime[0];
//                            if (subjectPreferedRoom.size() != 0) {
//                                for (Integer spr : subjectPreferedRoom
//                                ) {
//                                    int roomSize = getRoomSize(spr);
//                                    if (stg.getStudentCount() <= roomSize) {
//
//                                    }
//
//                                }
//                            }
//                        }
//                    }
                }
            } else {
                Alert al = new Alert(Alert.AlertType.ERROR);
                al.setTitle(null);
                al.setContentText("Any Of the Sessions didn't Added For this Group");
                al.setHeaderText(null);
                al.showAndWait();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }


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

        for (int i = 0; i < workingDaysCount; i++) {
            System.out.print("[");
            for (int j = 0; j < hourSize; j++) {
                System.out.print(timeString[i][j] + " ");
            }
            System.out.print("]");
            System.out.println();
        }
        return timeString;

    }

}
