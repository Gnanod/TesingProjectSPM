package main.controller.Subject;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;
import main.model.Building;
import main.model.Subject;
import main.model.YearAndSemester;
import main.service.BuildingService;
import main.service.SubjectService;
import main.service.YearandSemesterService;
import main.service.impl.BuildingServiceImpl;
import main.service.impl.SubjectServiceImpl;
import main.service.impl.YearAndServiceImpl;
import org.controlsfx.control.textfield.TextFields;

import javax.swing.*;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class AddSubjectController implements Initializable {
    @FXML
    private Button btnSave;

    @FXML
    private TextField txtSubName;

    @FXML
    private TextField txtSubID;

    @FXML
    private TextField txtLecHours;

    @FXML
    private TextField txtEvalHours;

    @FXML
    private TextField txtYear;

    @FXML
    private TextField txtTutHours;
    private ArrayList<YearAndSemester> yearAndSemesters = new ArrayList<>();
    private ArrayList<String> yearSemName = new ArrayList<>();
    static int yId;
    @FXML
    void saveDetails(ActionEvent event) {

        String subId = txtSubID.getText();
        String subName = txtSubName.getText();

        String offeredYearSem =txtYear.getText();

        int yearCount=0;
        for (YearAndSemester y : this.yearAndSemesters) {
            if (offeredYearSem.equals(y.getFullName())) {
                yId = y.getId();
                yearCount++;
            }
        }
        System.out.print("hi"+yId);
        try{
            int noLecHrs = Integer.parseInt(txtLecHours.getText());
            try{
                int noTutHrs = Integer.parseInt(txtTutHours.getText());
                try{
                    int noEvalHrs = Integer.parseInt(txtEvalHours.getText());

                    if(!subId.equalsIgnoreCase("") ){
                        if(!subName.equalsIgnoreCase("")){
                            if(!offeredYearSem.equalsIgnoreCase("")){
                                if(noLecHrs!=0){
                                    try {
                                        Subject subject = new Subject(subId, subName, yId, noLecHrs, noTutHrs, noEvalHrs);
                                        SubjectService subjectService = new SubjectServiceImpl();
                                        boolean res=subjectService.saveSubject(subject);
                                        if(res==true){
                                            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                            alert.setTitle(null);
                                            alert.setHeaderText(null);
                                            alert.setContentText("Success Adding Subject!");

                                            alert.showAndWait();
                                            txtSubID.setText(" ");
                                            txtSubName.setText(" ");
                                            txtYear.setText(" ");
                                            txtLecHours.setText(" ");
                                            txtTutHours.setText(" ");
                                            txtEvalHours.setText(" ");
                                        }else{
                                            Alert al = new Alert(Alert.AlertType.ERROR);
                                            al.setTitle(null);
                                            al.setContentText("Error Adding Subject!");
                                            al.setHeaderText(null);
                                            al.showAndWait();
                                        }
                                    }catch (SQLException ex){
                                        ex.printStackTrace();
                                    }
                                }else{
                                    Alert al = new Alert(Alert.AlertType.ERROR);
                                    al.setTitle(null);
                                    al.setContentText("Number of Lecture Hours Empty!");
                                    al.setHeaderText(null);
                                    al.showAndWait();
                                }

                            }else{
                                Alert al = new Alert(Alert.AlertType.ERROR);
                                al.setTitle(null);
                                al.setContentText("Offered Year and Semester Empty!");
                                al.setHeaderText(null);
                                al.showAndWait();
                            }

                        }else{
                            Alert al = new Alert(Alert.AlertType.ERROR);
                            al.setTitle(null);
                            al.setContentText("Subject Name is Empty!");
                            al.setHeaderText(null);
                            al.showAndWait();
                        }

                    }else{
                        Alert al = new Alert(Alert.AlertType.ERROR);
                        al.setTitle(null);
                        al.setContentText("Subject Code is Empty!");
                        al.setHeaderText(null);
                        al.showAndWait();
                    }


                }catch(NumberFormatException ex){
                    Alert al = new Alert(Alert.AlertType.ERROR);
                    al.setTitle(null);
                    al.setContentText("Enter in Correct Format for Evaluation of Lecturer hours!");
                    al.setHeaderText(null);
                    al.showAndWait();
                }
            }catch(NumberFormatException ex){
                Alert al = new Alert(Alert.AlertType.ERROR);
                al.setTitle(null);
                al.setContentText("Enter in Correct Format for Tute of Lecturer hours!");
                al.setHeaderText(null);
                al.showAndWait();
            }
        }catch(NumberFormatException ex){
            Alert al = new Alert(Alert.AlertType.ERROR);
            al.setTitle(null);
            al.setContentText("Enter in Correct Format for Number of Lecturer hours!");
            al.setHeaderText(null);
            al.showAndWait();
        }



    }


    public  void getYearSem() {
        try{
            YearandSemesterService yearandSemesterService=new YearAndServiceImpl();
            ArrayList<YearAndSemester> list =yearandSemesterService.getAllDetails();
            for (YearAndSemester yearAndSemester : list
            ) {
                yearAndSemesters.add(yearAndSemester);
                yearSemName.add(yearAndSemester.getFullName());
                System.out.println(yearAndSemester.getFullName());
            }
            TextFields.bindAutoCompletion(txtYear, yearSemName);

        }catch (SQLException ex){
            ex.printStackTrace();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        getYearSem();
    }
}
