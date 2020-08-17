package main.controller.Subject;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;
import main.model.Subject;
import main.service.SubjectService;
import main.service.impl.SubjectServiceImpl;

import java.sql.SQLException;

public class AddSubjectController {
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


    @FXML
    void saveDetails(ActionEvent event) {
       try {
           String subId = txtSubID.getText();
           String subName = txtSubName.getText();
           int offeredYearSem = Integer.parseInt(txtYear.getText());
           int noLecHrs = Integer.parseInt(txtLecHours.getText());
           int noTutHrs = Integer.parseInt(txtTutHours.getText());
           int noEvalHrs = Integer.parseInt(txtEvalHours.getText());
           Subject subject = new Subject(subId, subName, offeredYearSem, noLecHrs, noTutHrs, noEvalHrs);
           SubjectService subjectService = new SubjectServiceImpl();
           subjectService.saveSubject(subject);
       }catch (SQLException ex){
           ex.printStackTrace();
       }

    }

}
