package main.controller.TimeTableGenerate;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
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
            String groupId = "Y1.S1.Software Engineering.01";
            String[][] timeString = this.getStringArray();
            int[][] session = new int[workingDaysCount][(int) hourSize];
            ArrayList<SessionTagGroup> sessionList = sessionService.getSessionsAccordingToMainGroupId(groupId.trim());
            if (sessionList.size() != 0) {
                for (SessionTagGroup stg : sessionList
                ) {
                    double sessionHour = stg.getDuration();
                    int arraySpaces = 0;
                    if (timeSlot.equals("One Hour")) {
                        arraySpaces = (int) sessionHour;
                    } else {
                        arraySpaces = (int) sessionHour * 2;
                    }
                    ArrayList<Integer> subjectPreferedRoom = timeTableGenerateService.getSubjectPreferedRoom(stg.getSubjectId().trim(),stg.getTagId());
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
                    firstLoop:
                    for (int i = 0; i < workingDaysCount; i++) {
                        day = getDay(i);
                        for (int j = 0; j < hourSize; j++) {
                            String time = timeString[i][j];
                            String[] arrTime = time.split("-");
                            String toTime = arrTime[0];
                            String fromTime = arrTime[1];
                            if (subjectPreferedRoom.size() != 0) {
                                for (Integer spr : subjectPreferedRoom
                                     ) {
                                    if(stg.getTagName().equals("Lecture")||stg.getTagName().equals("Tute")){
                                        boolean notAvailableGroupStatus = timeTableGenerateService.getNotAvailableGroupStaus(toTime,fromTime,spr,day);
//                                        boolean notAvailableSessionStatus = timeTableGenerateService.getNotAvailableSessionStatus(toTime,fromTime,stg.getSessionId());
//                                        boolean notAvailableLectureStatus = timeTableGenerateService.getNotAvailableLectureStatus(toTime,fromTime,stg.get);
                                    }
                                }
                            }
                        }
                    }
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
        String day="";
        if(i==0){
            day= "Monday";
        }else if(i==1){
            day = "Tuesday";
        }else if(i==2){
            day = "Wednesday";
        }else if(i==3){
            day = "Thursday";
        }else if(i==4){
            day = "Friday";
        }else if(i==5){
            day = "Staurday";
        }else if(i==6){
            day = "Sunday";
        }
        return day;
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
                    tempTime = hoursCount + "." + minutCount;
                    hoursCount += 1;
                    timeString[i][j] = tempTime + "-" + hoursCount + "." + minutCount;
                } else {
                    if (minutCount != 30) {
                        tempTime = hoursCount + "." + minutCount + "0";
                    } else {
                        tempTime = hoursCount + "." + minutCount;
                    }
                    minutCount += 30;
                    if (minutCount >= 60) {
                        hoursCount++;
                        minutCount = 0;
                    }
                    if (minutCount != 30) {
                        timeString[i][j] = tempTime + "-" + hoursCount + "." + minutCount + "0";
                    } else {
                        timeString[i][j] = tempTime + "-" + hoursCount + "." + minutCount;
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
