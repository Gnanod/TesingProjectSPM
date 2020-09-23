package main.controller.TimeTableGenerate;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import main.model.Session;
import main.service.SessionService;
import main.service.WorkingDaysService;
import main.service.impl.SessionServiceImpl;
import main.service.impl.WorkingDaysServiceImpl;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class TimeTableGenerateController implements Initializable {

    private WorkingDaysService workingDaysService;
    private SessionService sessionService;
    private static String timeSlot="";
    private static double workingHours=0;
    private static int workingDaysCount=0;
    private static double hourSize=0;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        workingDaysService = new WorkingDaysServiceImpl();
        sessionService = new SessionServiceImpl();
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
        int [][]session = new int[workingDaysCount][(int)hourSize];
        ArrayList<Session> sessionList = new ArrayList<>();
        ArrayList<Session> mainGroupSessionList = sessionService.getSessionsAccordingToMainGroupId();

    }

    public String [][] getStringArray(){
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
                if (timeSlot.equals("One Hour")){
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
