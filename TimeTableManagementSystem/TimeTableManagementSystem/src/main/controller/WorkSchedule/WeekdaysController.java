package main.controller.WorkSchedule;

import java.io.PrintStream;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;

public class WeekdaysController {

    public WeekdaysController() {
    }

    @FXML
    public  CheckBox checkMON;
    @FXML
    public  CheckBox checkTUE;
    @FXML
    public  CheckBox checkWED;
    @FXML
    public  CheckBox checkTHU;
    @FXML
    public  CheckBox checkFRI;


    public static boolean monday = false;
    public static boolean tuesday = false;
    public static boolean wednesday = false;
    public static boolean thursday = false;
    public static boolean friday = false;
    public static int count;

    @FXML
    public void selectedDays(ActionEvent event) {
        count = 0;
        if (checkMON.isSelected()) {
            monday = true;
            count++;
        }else{
            monday=false;
        }
        if (checkTUE.isSelected()) {
            System.out.println("tuesday is selected");
            tuesday = true;
            count++;
        }else{
            tuesday=false;
        }
        if (checkWED.isSelected()) {
            System.out.println("wednesday is selected");
            wednesday = true;
            count++;
        }else{
            wednesday=false;
        }
        if (checkTHU.isSelected()) {
            System.out.println("thursday is selected");
            thursday = true;
            count++;
        }else{
            thursday=false;
        }
        if (checkFRI.isSelected()) {
            System.out.println("friday is selected");
            friday = true;
            count++;
        }else{
            friday=false;
        }
        System.out.println((new StringBuilder()).append("count = ").append(count).toString());
        System.out.println("Monday"+monday);
        System.out.println("TuesDay"+tuesday);
        System.out.println("WednesDay"+wednesday);
        System.out.println("Thursday"+thursday);
        System.out.println("Friday"+friday);
    }

//    public static void updateCheckStatus() {
//        if (monday) {
////            checkMON=new CheckBox();
//            checkMON.setSelected(true);
//        }
//        if (tuesday) {
////            checkTUE=new CheckBox();
//            checkTUE.setSelected(true);
//        }
//        if (wednesday) {
//            checkWED = new CheckBox();
//            checkWED.setSelected(true);
//        }
//        if (thursday) {
//            checkTHU = new CheckBox();
//            checkTHU.setSelected(true);
//        }
//        if (friday) {
//            checkFRI = new CheckBox();
//            checkFRI.setSelected(true);
//        }
//    }

}


