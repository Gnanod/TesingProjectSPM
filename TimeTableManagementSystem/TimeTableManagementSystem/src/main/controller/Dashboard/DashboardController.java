package main.controller.Dashboard;

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
    private BarChart<String, Integer> buildingChart;

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

        try {
            ArrayList<Dashboard> list = this.dashboardService.getBuildingCount();
            XYChart.Series<String,Integer> series = new XYChart.Series<>();
            while(list.size()>0){
                series.getData().add(new XYChart.Data<>());
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
