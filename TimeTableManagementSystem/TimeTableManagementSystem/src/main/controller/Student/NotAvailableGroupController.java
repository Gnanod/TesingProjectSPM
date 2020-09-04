package main.controller.Student;

import javafx.fxml.Initializable;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXTimePicker;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import main.model.MainGroup;
import main.model.SubGroup;
import main.service.MainGroupService;
import main.service.SubGroupService;
import main.service.impl.MainGroupServiceImpl;
import main.service.impl.SubGroupServiceImpl;
import org.controlsfx.control.textfield.TextFields;

public class NotAvailableGroupController implements Initializable {

    @FXML
    private Button btnSave;

    @FXML
    private ComboBox<Object> cmbDate;

    @FXML
    private TableView<?> tblSemester;

    @FXML
    private TextField groupId;

    @FXML
    private RadioButton btnRadioMain;

    @FXML
    private RadioButton btnRadioSub;

    @FXML
    private JFXTimePicker toTime;

    @FXML
    private JFXTimePicker fromTime;

    private MainGroupService mainGroupservice;
    private SubGroupService subGroupService;
    private List<String> groupNameList;
    private List<Object> groupList;

    @FXML
    void loadGroupDetails() {
        if(btnRadioMain.isSelected()){
            loadMainGroupDetails();
        }else if(btnRadioSub.isSelected()){
            loadSubGroupDetails();
        }
    }

    private void loadSubGroupDetails() {
        try {
            ArrayList<SubGroup> subList = this.subGroupService.getAllSubGroupDetails(0);
            groupNameList.clear();
            groupList.clear();
            System.out.println("Size Sub"+groupNameList.size());
            for (SubGroup s : subList
            ) {
                groupNameList.add(s.getSubgroupid());
                groupList.add(s);
            }
            TextFields.bindAutoCompletion(groupId, groupNameList);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void loadMainGroupDetails() {
        try {
            ArrayList<MainGroup> mainList = this.mainGroupservice.getAllMainGroupDetails();
            groupNameList.clear();
            groupList.clear();
            System.out.println("Size main"+groupNameList.size());
            for (MainGroup m : mainList
            ) {
                groupNameList.add(m.getGroupid());
                groupList.add(m);
            }
            TextFields.bindAutoCompletion(groupId, groupNameList);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void initialize(URL location, ResourceBundle resources){
        mainGroupservice = new MainGroupServiceImpl();
        groupNameList = new ArrayList<>();
        groupList = new ArrayList<>();
        subGroupService = new SubGroupServiceImpl();
        btnRadioMain.setSelected(true);
        loadGroupDetails();
    }
}
