package main.controller.Dashboard;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.*;
import main.model.Dashboard;
import main.model.Dashboard2;
import main.service.DashboardService;
import main.service.impl.DashboardServiceImpl;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class DashboardController implements Initializable {

    @FXML
    private BarChart<String, Integer> buildingChart;

    @FXML
    private CategoryAxis x;

    @FXML
    private NumberAxis y;

    @FXML
    private BarChart<String, Integer> EmployeeChart;

    @FXML
    private CategoryAxis yEmp;

    @FXML
    private NumberAxis xEmp;

    @FXML
    private PieChart buildingPiechart;

    @FXML
    private PieChart desPiechart;




    private DashboardService dashboardService;

    public DashboardController(){
        this.dashboardService = new DashboardServiceImpl();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        this.loadEmployeeCounts();
        this.piechartForBuildings();
        this.piechartForDesignations();
//                this.loadSubjects();
    }

    private void loadSubjects(){

        try {

            ArrayList<Dashboard2> list1 = this.dashboardService.getSubjects();
            ObservableList<XYChart.Series<String,Integer>> data1 = FXCollections.observableArrayList();
            XYChart.Series<String,Integer> series = new XYChart.Series<>();
            for (Dashboard2 d1:list1
            ) {
                series.getData().add(new XYChart.Data(d1.getYearSem(),d1.getNoOfSubjects()));
            }
            data1.add(series);
            buildingChart.setData(data1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void loadEmployeeCounts(){

        try {

            ArrayList<Dashboard> list = this.dashboardService.getEmployeeCount();
            ObservableList<XYChart.Series<String,Integer>> data = FXCollections.observableArrayList();
            XYChart.Series<String,Integer> series = new XYChart.Series<>();
            for (Dashboard d1:list
            ) {
                series.getData().add(new XYChart.Data(d1.getFaculty(),d1.getNoOfEmployees()));
            }
            data.add(series);
            EmployeeChart.setData(data);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void piechartForBuildings(){

        try {
            ArrayList<Dashboard> list = this.dashboardService.getBuildingCount();
            ObservableList<PieChart.Data> piechartData = FXCollections.observableArrayList();
//            PieChart.Data pie = new XYChart.Series<>();
            for (Dashboard d1:list
            ) {
                piechartData.add(new PieChart.Data(d1.getCenter(),d1.getNoOfBuildings()));
            }

            buildingPiechart.setData(piechartData);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void piechartForDesignations(){

        try {
            ArrayList<Dashboard2> list = this.dashboardService.getDesignationCount();
            ObservableList<PieChart.Data> piechartData = FXCollections.observableArrayList();
//            PieChart.Data pie = new XYChart.Series<>();
            for (Dashboard2 d1:list
            ) {
                piechartData.add(new PieChart.Data(d1.getDesignation(),d1.getNoOfDesig()));
            }

            desPiechart.setData(piechartData);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
