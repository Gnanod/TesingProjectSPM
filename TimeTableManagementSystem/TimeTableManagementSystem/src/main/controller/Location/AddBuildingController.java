package main.controller.Location;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import main.model.Building;
import main.service.impl.BuildingServiceImpl;
import main.service.BuildingService;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class AddBuildingController implements Initializable {

    @FXML
    private TableView<?> tblBuilding;

    @FXML
    private TableColumn<?, ?> editBuilding;

    @FXML
    private TableColumn<?, ?> removeBuilding;

    @FXML
    private ComboBox<String> cmbCenterAdd;

    @FXML
    private TextField txtBuildingAdd;

    @FXML
    private Button btnBuildingAdd;

    @FXML
    private Button btnBuildingSave;

    private BuildingService buildingService;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public AddBuildingController() {
        this.buildingService = new BuildingServiceImpl();

    }


    @FXML
    void AddBuildingsToTable(ActionEvent event) {

    }



    @FXML
    void saveBuildingDetails(ActionEvent event) {
        String building = txtBuildingAdd.getText();
        String center = (String) cmbCenterAdd.getValue();


        if(center != null){
            if(building != null){
                boolean isAdded = false;
                Building buildingObj = new Building();
                buildingObj.setBuilding(building);
                buildingObj.setCenter(center);

                try {
                    isAdded = this.buildingService.saveBuildings(buildingObj);
                } catch (SQLException e) {
                    e.printStackTrace();
                }

                if (isAdded) {
                    Alert al = new Alert(Alert.AlertType.INFORMATION);
                    al.setTitle(null);
                    al.setContentText("Added Successfully!");
                    al.setHeaderText(null);
                    al.showAndWait();
                    txtBuildingAdd.setText(null);
                    cmbCenterAdd.setValue(null);

                } else {
                    Alert al = new Alert(Alert.AlertType.ERROR);
                    al.setTitle(null);
                    al.setContentText("Added Failed!");
                    al.setHeaderText(null);
                    al.showAndWait();
                }


            }else{
                Alert al = new Alert(Alert.AlertType.ERROR);
                al.setTitle(null);
                al.setContentText("Building field is empty!");
                al.setHeaderText(null);
                al.showAndWait();
            }
        }else{
            Alert al = new Alert(Alert.AlertType.ERROR);
            al.setTitle(null);
            al.setContentText("Please select Center!");
            al.setHeaderText(null);
            al.showAndWait();
        }
    }


}


