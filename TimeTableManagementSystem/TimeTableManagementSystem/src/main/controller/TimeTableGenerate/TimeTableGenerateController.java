package main.controller.TimeTableGenerate;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import main.service.WorkingDaysService;
import main.service.impl.WorkingDaysServiceImpl;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class TimeTableGenerateController implements Initializable {

    private WorkingDaysService workingDaysService;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        workingDaysService = new WorkingDaysServiceImpl();
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
//        String groupId = ""
    }

    public String [][] getStringArray(){
        String timeSlot = this.getWorkingTimeType();
        double workingHours = this.getWorkingTime();
        int workingDaysCount = this.getCountOfWorkingDays();
        double hourSize = 0;
        if (timeSlot.equals("One Hour")) {
            hourSize = workingHours;
        } else {
            hourSize = workingHours * 2;
        }
        String[][] timeString = new String[workingDaysCount][(int) hourSize];
        int hoursCount = 8;
        int minutCount = 30;
        for (int i = 0; i < workingDaysCount; i++) {
            for (int j = 0; j < workingHours; j++) {
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

        return timeString;

    }

}
