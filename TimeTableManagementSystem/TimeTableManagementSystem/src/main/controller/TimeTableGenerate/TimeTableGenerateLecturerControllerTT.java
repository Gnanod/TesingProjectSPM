package main.controller.TimeTableGenerate;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import main.controller.Pdf.PrintLecturerTimeTable;
import main.model.Lecturer;
import main.model.LecturerTimeTable;
import main.service.LecturerService;
import main.service.TimeTableGenerateService;
import main.service.WorkingDaysService;
import main.service.impl.LectureServiceImpl;
import main.service.impl.TimeTableGenerateServiceImpl;
import main.service.impl.WorkingDaysServiceImpl;
import org.controlsfx.control.textfield.AutoCompletionBinding;
import org.controlsfx.control.textfield.TextFields;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class TimeTableGenerateLecturerControllerTT implements Initializable {

    @FXML
    private Button btnGenerate;

    @FXML
    private ComboBox<?> LecType;

    @FXML
    private TextField txtSubID;

    @FXML
    private TableView<?> tblTimeTable;

    private LecturerService lectureService;
    private AutoCompletionBinding<String> autoCompletionBinding;
    private List<String> lecNameList;
    private List<main.model.Lecturer> lecObj;
    private String curTime;
    private TimeTableGenerateService timeTableGenerateService;
    private WorkingDaysService workingDaysService;
    private static String timeSlot = "";
    private static double workingHours = 0;
    private static int workingDaysCount = 0;
    private static double hourSize = 0;
    private String[][] lectSession;


    @FXML
    void generateTimeTable(ActionEvent event) {
        String lecName = txtSubID.getText();

        if (!lecName.isEmpty()) {
            String[][] timeString = this.getStringArray();
            timeSlot = this.getWorkingTimeType();
            workingHours = this.getWorkingTime();
            workingDaysCount = this.getCountOfWorkingDays();
            try {
                ArrayList<LecturerTimeTable> lecturerTimeTables = timeTableGenerateService.getLectureTimeTableDetails(lecName);
                if (lecturerTimeTables.size() != 0) {
                    lectSession = new String[workingDaysCount][(int) hourSize];
                    for (LecturerTimeTable lec : lecturerTimeTables
                    ) {
                        int dayNumber = getDayNumber(lec.getDay());
                        String groupId="";
                        if(lec.getSubGroupId()!=null){
                            groupId = this.timeTableGenerateService.getSubGroupId(Integer.parseInt(lec.getSubGroupId()));
                        }else{
                            groupId = this.timeTableGenerateService.getMainGroupId(Integer.parseInt(lec.getMainGroupId()));
                        }
                        if (lec.getDay().equalsIgnoreCase("Monday")) {
                            for (int i = 0; i < hourSize; i++) {
                                if (lec.getTimeString().equalsIgnoreCase(timeString[dayNumber][i])) {
                                    String session = lec.getSubCode() + "\n" + lec.getSubName() + "\n(" +
                                            lec.getTagName() + ")\n" + lec.getRomm()+ "\n" +groupId;
                                    lectSession[dayNumber][i] = session;
                                }
                            }
                        } else if (lec.getDay().equalsIgnoreCase("Tuesday")) {
                            for (int i = 0; i < hourSize; i++) {
                                if (lec.getTimeString().equalsIgnoreCase(timeString[dayNumber][i])) {
                                    String session = lec.getSubCode() + "\n" + lec.getSubName() + "\n(" +
                                            lec.getTagName() + ")\n" + lec.getRomm()+"\n"+groupId;
                                    lectSession[dayNumber][i] = session;
                                }
                            }
                        } else if (lec.getDay().equalsIgnoreCase("Wednesday")) {
                            for (int i = 0; i < hourSize; i++) {
                                if (lec.getTimeString().equalsIgnoreCase(timeString[dayNumber][i])) {
                                    String session = lec.getSubCode() + "\n" + lec.getSubName() + "\n(" +
                                            lec.getTagName() + ")\n" + lec.getRomm()+"\n"+groupId;
                                    lectSession[dayNumber][i] = session;
                                }
                            }
                        } else if (lec.getDay().equalsIgnoreCase("Thursday")) {
                            for (int i = 0; i < hourSize; i++) {
                                if (lec.getTimeString().equalsIgnoreCase(timeString[dayNumber][i])) {
                                    String session = lec.getSubCode() + "\n" + lec.getSubName() +
                                            "\n(" + lec.getTagName() + ")\n" + lec.getRomm()+"\n"+groupId;
                                    lectSession[dayNumber][i] = session;
                                }
                            }
                        } else if (lec.getDay().equalsIgnoreCase("Friday")) {
                            for (int i = 0; i < hourSize; i++) {
                                if (lec.getTimeString().equalsIgnoreCase(timeString[dayNumber][i])) {
                                    String session = lec.getSubCode() + "\n" + lec.getSubName() + "\n(" +
                                            lec.getTagName() + ")\n" + lec.getRomm()+"\n"+groupId;
                                    lectSession[dayNumber][i] = session;
                                }
                            }
                        } else if (lec.getDay().equalsIgnoreCase("Saturday")) {
                            for (int i = 0; i < hourSize; i++) {
                                if (lec.getTimeString().equalsIgnoreCase(timeString[dayNumber][i])) {
                                    String session = lec.getSubCode() + "\n" + lec.getSubName() + "\n(" +
                                            lec.getTagName() + ")\n" + lec.getRomm()+"\n"+groupId;
                                    lectSession[dayNumber][i] = session;
                                }
                            }
                        } else if (lec.getDay().equalsIgnoreCase("Sunday")) {
                            for (int i = 0; i < hourSize; i++) {
                                if (lec.getTimeString().equalsIgnoreCase(timeString[dayNumber][i])) {
                                    String session = lec.getSubCode() + "\n" + lec.getSubName() + "\n(" +
                                            lec.getTagName() + ")\n" + lec.getRomm()+"\n"+groupId;
                                    lectSession[dayNumber][i] = session;
                                }
                            }
                        }
                    }
                    String[][] swapArray = new String[(int) hourSize][workingDaysCount];
                    String[][] swapArrayTime = new String[(int) hourSize][workingDaysCount];
                    for (int i = 0; i < (int) hourSize; i++) {
                        for (int j = 0; j < workingDaysCount; j++) {
                            swapArray[i][j] = lectSession[j][i];
                            swapArrayTime[i][j] = timeString[j][i];
                        }
                    }
                    PrintLecturerTimeTable printTimeTable = new PrintLecturerTimeTable();
                    printTimeTable.generateCustomerReportPdf(swapArray, swapArrayTime, workingDaysCount, (int) hourSize, lecName);
                    Alert al = new Alert(Alert.AlertType.INFORMATION);
                    al.setTitle(null);
                    al.setContentText("Time Table Is Generated");
                    al.setHeaderText(null);
                    al.showAndWait();
                }else{
                    Alert al = new Alert(Alert.AlertType.ERROR);
                    al.setTitle(null);
                    al.setContentText("Any Time Table Not Found For This Lecturer");
                    al.setHeaderText(null);
                    al.showAndWait();
                }


            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            Alert al = new Alert(Alert.AlertType.ERROR);
            al.setTitle(null);
            al.setContentText("Lecturer Field Is Empty");
            al.setHeaderText(null);
            al.showAndWait();
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

    public int getDayNumber(String day) {
        int number = 0;
        if (day.equalsIgnoreCase("Monday")) {
            number = 0;
        } else if (day.equalsIgnoreCase("Tuesday")) {
            number = 1;
        } else if (day.equalsIgnoreCase("Wednesday")) {
            number = 2;
        } else if (day.equalsIgnoreCase("Thursday")) {
            number = 3;
        } else if (day.equalsIgnoreCase("Friday")) {
            number = 4;
        } else if (day.equalsIgnoreCase("Saturday")) {
            number = 5;
        } else if (day.equalsIgnoreCase("Sunday")) {
            number = 6;
        }
        return number;
    }


    @FXML
    void setText(ActionEvent event) {

    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        lectureService = new LectureServiceImpl();
        lecNameList = new ArrayList<>();
        this.getAllLecturerDetails();
        timeTableGenerateService = new TimeTableGenerateServiceImpl();
        workingDaysService = new WorkingDaysServiceImpl();
    }

    public void getAllLecturerDetails() {
        try {
            ArrayList<Lecturer> lec = lectureService.getAllLecturerDetails();
            lecNameList.clear();
            for (Lecturer l : lec) {
                lecNameList.add(l.getEmpName());
            }
            autoCompletionBinding = TextFields.bindAutoCompletion(txtSubID, lecNameList);
            TextFields.bindAutoCompletion(txtSubID, lecNameList);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

}
