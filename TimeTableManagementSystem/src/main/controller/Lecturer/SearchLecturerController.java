package main.controller.Lecturer;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;
import javafx.scene.control.cell.PropertyValueFactory;
import main.model.Lecturer;
import main.model.MainGroupCount;
import main.service.LecturerService;
import main.service.impl.LectureServiceImpl;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class SearchLecturerController implements Initializable {
    @FXML
    private TableView<Lecturer> tblGroupCount;

    @FXML
    private TextField txtLecturer;

    @FXML
    private Button btnSave;



    @FXML
    void searchDetails(ActionEvent event) {
        String lecturerName=txtLecturer.getText();
        try{
            LecturerService lecturerService=new LectureServiceImpl();
            ArrayList<Lecturer> list = lecturerService.searchLecturerDetails(lecturerName);
            this.setTableProperties();
            tblGroupCount.setItems(FXCollections.observableArrayList(list));
        }catch(Exception exception){
            exception.printStackTrace();

        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.setTableProperties();
        getAllLecturers();
    }
    public void setTableProperties() {
        tblGroupCount.getSelectionModel().getTableView().getItems().clear();
        tblGroupCount.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("empId"));
        tblGroupCount.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("empName"));
        tblGroupCount.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("Faculty"));
        tblGroupCount.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("Department"));
        tblGroupCount.getColumns().get(4).setCellValueFactory(new PropertyValueFactory<>("Center"));
        tblGroupCount.getColumns().get(5).setCellValueFactory(new PropertyValueFactory<>("Building"));
        tblGroupCount.getColumns().get(6).setCellValueFactory(new PropertyValueFactory<>("Level"));
        tblGroupCount.getColumns().get(7).setCellValueFactory(new PropertyValueFactory<>("rank"));
    }
    public void getAllLecturers(){

        try {
            LecturerService lecturerService=new LectureServiceImpl();
            ArrayList<Lecturer> list = lecturerService.getAllLecturerDetails();
            for (Lecturer str : list)
            {
                System.out.println(str.getEmpId());
                System.out.println(str.getEmpName());
            }
            tblGroupCount.setItems(FXCollections.observableArrayList(list));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
