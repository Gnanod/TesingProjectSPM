package main.controller.Location;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class ViewRoomController {

    @FXML
    private TableView<?> tblRoomView;

    @FXML
    private TableColumn<?, ?> editBuildingView;

    @FXML
    private TableColumn<?, ?> removeBuildingView;

    @FXML
    private TextField txtBuildingEdit1;

    @FXML
    private TextField txtRoomEdit1;

    @FXML
    private Button btnRoomUpdate;

    @FXML
    private TextField txtCapacitiesEdit;

    @FXML
    void handleEvents(ActionEvent event) {

    }

}


