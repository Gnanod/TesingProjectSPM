package main.controller.Location;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class AddBuildingController {

    @FXML
    private TableView<?> tblBuilding;

    @FXML
    private TableColumn<?, ?> editBuilding;

    @FXML
    private TableColumn<?, ?> removeBuilding;

    @FXML
    private TextField txtCenterAdd;

    @FXML
    private TextField txtBuildingAdd;

    @FXML
    private Button btnBuildingAdd;

    @FXML
    private Button btnBuildingSave;

    @FXML
    void handleEvents(ActionEvent event) {

    }

}


