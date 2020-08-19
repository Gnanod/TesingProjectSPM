package main.controller.Subject;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;
import javafx.scene.control.cell.PropertyValueFactory;
import main.model.Lecturer;
import main.model.Subject;
import main.service.LecturerService;
import main.service.SubjectService;
import main.service.YearandSemesterService;
import main.service.impl.LectureServiceImpl;
import main.service.impl.SubjectServiceImpl;
import main.service.impl.YearAndServiceImpl;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class SearchSubjectController implements Initializable {
    @FXML
    private TableView<Subject> tblSubjectCount;

    @FXML
    private TextField txtSubject;

    @FXML
    private Button btnSearch;

    @FXML
    void searchDetails(ActionEvent event) {
        try {
            SubjectService subjectService=new SubjectServiceImpl();
            ArrayList<Subject> list = subjectService.searchSubjectDetails(txtSubject.getText());
            for (Subject str : list)
            {
                System.out.println(str.getSubId());
                System.out.println(str.getSubName());
                YearandSemesterService yearandSemesterService=new YearAndServiceImpl();
                str.setYearSem(yearandSemesterService.searchYearAndSemesterName(str.getOfferedYearSem()));
            }
            tblSubjectCount.setItems(FXCollections.observableArrayList(list));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void setTableProperties() {
        tblSubjectCount.getSelectionModel().getTableView().getItems().clear();
        tblSubjectCount.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("subId"));
        tblSubjectCount.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("subName"));
        tblSubjectCount.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("yearSem"));
        tblSubjectCount.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("noLecHrs"));
        tblSubjectCount.getColumns().get(4).setCellValueFactory(new PropertyValueFactory<>("noTutHrs"));
        tblSubjectCount.getColumns().get(5).setCellValueFactory(new PropertyValueFactory<>("noEvalHrs"));
    }
    public void getAllSubjects(){

        try {
            SubjectService subjectService=new SubjectServiceImpl();
            ArrayList<Subject> list = subjectService.getAllSubjectDetails();
            for (Subject str : list)
            {
                System.out.println(str.getSubId());
                System.out.println(str.getSubName());
                YearandSemesterService yearandSemesterService=new YearAndServiceImpl();
                str.setYearSem(yearandSemesterService.searchYearAndSemesterName(str.getOfferedYearSem()));
            }
            tblSubjectCount.setItems(FXCollections.observableArrayList(list));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.setTableProperties();
        this.getAllSubjects();
    }
}
