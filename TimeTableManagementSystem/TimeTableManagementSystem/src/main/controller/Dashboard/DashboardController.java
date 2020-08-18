package main.controller.Dashboard;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import main.model.Dashboard;
import main.service.DashboardService;
import main.service.impl.DashboardServiceImpl;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class DashboardController implements Initializable {

    @FXML
    private BarChart<String, Double> buildingChart;

    @FXML
    private CategoryAxis x;

    @FXML
    private NumberAxis y;

    private DashboardService dashboardService;

    public DashboardController(){
        this.dashboardService = new DashboardServiceImpl();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        this.loadBuidlingCounts();
    }

    private void loadBuidlingCounts(){

        try {
            ArrayList<Dashboard> list = this.dashboardService.getBuildingCount();
            ObservableList<XYChart.Series<String,Double>> data = FXCollections.observableArrayList();
            XYChart.Series<String,Double> series = new XYChart.Series<>();
            for (Dashboard d1:list
            ) {
                series.getData().add(new XYChart.Data(d1.getCenter(),d1.getNoOfBuildings()));
            }
            data.add(series);
            buildingChart.setData(data);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
