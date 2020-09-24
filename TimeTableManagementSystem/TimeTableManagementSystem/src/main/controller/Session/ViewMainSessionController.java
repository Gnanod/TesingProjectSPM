package main.controller.Session;

import javafx.collections.FXCollections;
import javafx.fxml.Initializable;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;
import javafx.scene.control.cell.PropertyValueFactory;
import main.model.Lecturer;
import main.model.Session;
import main.model.SessionDTO;
import main.service.*;
import main.service.impl.*;

public class ViewMainSessionController implements Initializable {

    @FXML
    private TableView<SessionDTO> tblGroupCount;

    @FXML
    private TextField txtLecturer;

    @FXML
    private TextField txtLecturer1;

    @FXML
    private TextField txtLecturer2;
    private ArrayList<SessionDTO> sessionDTOS=new ArrayList<>();


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        this.setTableProperties();
        this.getAllSessions();
    }
    public void setTableProperties() {
        tblGroupCount.getSelectionModel().getTableView().getItems().clear();
        tblGroupCount.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("sessionId"));
        tblGroupCount.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("subjectName"));
        tblGroupCount.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("tagName"));
        tblGroupCount.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("groupName"));
        tblGroupCount.getColumns().get(4).setCellValueFactory(new PropertyValueFactory<>("studentCount"));
        tblGroupCount.getColumns().get(5).setCellValueFactory(new PropertyValueFactory<>("duration"));
        tblGroupCount.getColumns().get(6).setCellValueFactory(new PropertyValueFactory<>("lecturer"));

    }
    public  void getAllSessions(){

        try {
           SessionService sessionService=new SessionServiceImpl();
           ArrayList<SessionDTO> arrayList=sessionService.getAllSessions();
            tblGroupCount.setItems(FXCollections.observableArrayList(arrayList));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void searchSession(ActionEvent event) {
        String lecturer=txtLecturer.getText();
        String subject=txtLecturer1.getText();
        String groupId=txtLecturer2.getText();
        try{
            SessionService sessionService=new SessionServiceImpl();
            ArrayList<SessionDTO> arrayList;
            if(!lecturer.equalsIgnoreCase("")){
                arrayList=sessionService.searchSessions(lecturer);
            }else if(!subject.equalsIgnoreCase("")){
                arrayList=sessionService.searchSessions(subject);
            }else{
                arrayList=sessionService.searchSessions(groupId);
            }
            this.setTableProperties();
            tblGroupCount.setItems(FXCollections.observableArrayList(arrayList));
        }catch (SQLException ex){

        }

    }

}