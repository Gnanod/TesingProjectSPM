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
import main.service.BuildingService;
import main.service.DepartmentService;
import main.service.LecturerService;
import main.service.impl.BuildingServiceImpl;
import main.service.impl.DepartmentServiceImpl;
import main.service.impl.LectureServiceImpl;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class SearchLecturerController implements Initializable {
    @FXML
    private TableView<Lecturer> tblGroupCount;

    @FXML
    private TextField txtLecturer;

    @FXML
    private Button btnSave;
    static ArrayList<Lecturer> list1=new ArrayList<>();
    static ArrayList<Lecturer> list2=new ArrayList<>();


    @FXML
    void searchDetails(ActionEvent event) {
        list2.clear();
        String lecturerName=txtLecturer.getText();
        try{
            LecturerService lecturerService=new LectureServiceImpl();
            ArrayList<Lecturer> list = lecturerService.searchLecturerDetails(lecturerName);
            try{
                for (Lecturer str : list)
                {
                    System.out.println(str.getEmpId());
                    System.out.println(str.getEmpName());


                    DepartmentService departmentService=new DepartmentServiceImpl();
                    str.setDepartmentName(departmentService.searchDepartmentName(str.getDepartment()));

                    BuildingService buildingService=new BuildingServiceImpl();
                    str.setBuildingName(buildingService.searchBuildingName(str.getBuilding()));
                    System.out.println(str.getDepartmentName());
                    list2.add(str);


                }
            }catch (SQLException ex){
                ex.printStackTrace();
            }
            this.setTableProperties();
            tblGroupCount.setItems(FXCollections.observableArrayList(list2));
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
        tblGroupCount.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("DepartmentName"));
        tblGroupCount.getColumns().get(4).setCellValueFactory(new PropertyValueFactory<>("Center"));
        tblGroupCount.getColumns().get(5).setCellValueFactory(new PropertyValueFactory<>("BuildingName"));
        tblGroupCount.getColumns().get(6).setCellValueFactory(new PropertyValueFactory<>("designation"));
        tblGroupCount.getColumns().get(7).setCellValueFactory(new PropertyValueFactory<>("Level"));
        tblGroupCount.getColumns().get(8).setCellValueFactory(new PropertyValueFactory<>("rank"));
    }
    public  void getAllLecturers(){

        try {
            LecturerService lecturerService=new LectureServiceImpl();
            ArrayList<Lecturer> list = lecturerService.getAllLecturerDetails();
            try{
            for (Lecturer str : list)
            {
//                System.out.println(str.getEmpId());
//                System.out.println(str.getEmpName());


                    DepartmentService departmentService=new DepartmentServiceImpl();
                    str.setDepartmentName(departmentService.searchDepartmentName(str.getDepartment()));

                BuildingService buildingService=new BuildingServiceImpl();
                str.setBuildingName(buildingService.searchBuildingName(str.getBuilding()));
                    System.out.println(str.getDepartmentName());
                list1.add(str);


            }
            }catch (SQLException ex){
                ex.printStackTrace();
            }
            tblGroupCount.setItems(FXCollections.observableArrayList(list));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
