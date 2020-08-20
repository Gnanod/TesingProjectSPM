package main.controller.Location;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import main.model.Building;
import main.model.Room;
import main.service.BuildingService;
import main.service.impl.BuildingServiceImpl;
import org.controlsfx.control.textfield.TextFields;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class SearchBuildingController implements Initializable {

    @FXML
    private TableView<Building> tblBuildingSeach;

    @FXML
    private TextField txtCenterSearch;

    @FXML
    private TextField txtBuildingSearch;

    private BuildingService buildingService;
    private ArrayList<String> buildingList = new ArrayList<>();
    private ArrayList<String> centerList = new ArrayList<>();
    private ArrayList<Building> bIdList = new ArrayList<>();

    public SearchBuildingController(){
        this.buildingService = new BuildingServiceImpl();

    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.setTableProperties();
        getAllAllBuildingsDetails();
        getAllDetailsForSearch(null,null);
    }


    public void getAllAllBuildingsDetails() {
        try {
            ArrayList<Building> bList = this.buildingService.getAllAllRoomDetails();
            for (Building p1 : bList
            ) {
                centerList.add(p1.getCenter());
                buildingList.add(p1.getBuilding());
                bIdList.add(p1);
            }
            TextFields.bindAutoCompletion(txtBuildingSearch, buildingList);
            TextFields.bindAutoCompletion(txtCenterSearch, centerList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void searchDetails(ActionEvent event) {
        String bbuilding = null;
        String bcenter = null;

        System.out.println("sssssssssssssss");
        String building = txtCenterSearch.getText();
        String center = txtBuildingSearch.getText();
        for (Building r : this.bIdList
        ) {
            if (building.equals(r.getBuilding())) {
                bbuilding = r.getBuilding();

            }
            if (center.equals(r.getCenter())) {
                bcenter = r.getCenter();

            }
        }

        getAllDetailsForSearch(bcenter,bbuilding);
    }

    private void getAllDetailsForSearch(String bcenter,String bbuilding) {

        try {
            ArrayList<Building> list = this.buildingService.getAllDetailsForSearch(bcenter,bbuilding);
            tblBuildingSeach.setItems(FXCollections.observableArrayList(list));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setTableProperties() {
        tblBuildingSeach.getSelectionModel().getTableView().getItems().clear();
        tblBuildingSeach.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("center"));
        tblBuildingSeach.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("building"));
    }

}

