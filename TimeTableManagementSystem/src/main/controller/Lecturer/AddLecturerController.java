package main.controller.Lecturer;

import com.gluonhq.charm.glisten.control.AutoCompleteTextField;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.fxml.Initializable;


import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.scene.input.InputMethodEvent;
import main.model.Lecturer;
import main.service.LecturerService;
import main.service.impl.LectureServiceImpl;

public class AddLecturerController {

    @FXML
    private Button btnSave;

    @FXML
    private TextField txtDepartment;

    @FXML
    private TextField txtEmpName;

    @FXML
    private TextField txtEmpID;

    @FXML
    private TextField txtCenter;

    @FXML
    private AutoCompleteTextField<?> txtFaculty;

    @FXML
    private AutoCompleteTextField<?> txtBuilding;

    @FXML
    private TextField txtlevel;

    @FXML
    private TextField txtrank;


    @FXML
    void saveDetails(ActionEvent event) {
            int empId=Integer.parseInt(txtEmpID.getText());
            String Name=txtEmpName.getText();
            String Faculty=txtFaculty.getText();
            String department=txtDepartment.getText();
            String center=txtCenter.getText();
            String building=txtBuilding.getText();
            String level=txtlevel.getText();
            txtrank.setText(level+"."+empId);
            String rank=level+"."+empId;
        Lecturer lecturer=new Lecturer(empId, Name, Faculty,department,center, building, level, rank);
        LecturerService lecturerService=new LectureServiceImpl();
        try{
            boolean res=lecturerService.saveLecturer(lecturer);
        }catch (SQLException exception){
            System.out.println(exception);
        }

    }

    @FXML
    void setRank(ActionEvent event) {
        String empId=txtEmpID.getText();
        String level=txtlevel.getText();
        System.out.println(level+"."+empId);
        txtrank.setText(level+"."+empId);
    }

}
