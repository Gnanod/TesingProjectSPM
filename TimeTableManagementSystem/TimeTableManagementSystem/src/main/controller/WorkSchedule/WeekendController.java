package main.controller.WorkSchedule;

import java.io.PrintStream;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;

public class WeekendController {

    public WeekendController() {
    }

    @FXML
    public  CheckBox checkSAT;
    @FXML
    public  CheckBox checkSUN;
    public static int count;
    public static boolean saturday = false;
    public static boolean sunday = false;

    @FXML
    void selectedDays(ActionEvent event) {
        count = 0;
        if (checkSAT.isSelected()) {
            System.out.println("monday is selected");
            count++;
            saturday=true;
        }
        if (checkSUN.isSelected()) {
            System.out.println("tuesday is selected");
            count++;
            sunday=true;
        }
    }



}