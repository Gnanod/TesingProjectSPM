package main.controller.Location;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class AddRoomController {

    @FXML
    private TableView<?> tblRoomAdd;

    @FXML
    private TableColumn<?, ?> editRoomAdd;

    @FXML
    private TableColumn<?, ?> removeRoomAdd;

    @FXML
    private TextField txtSelectBuilding;

    @FXML
    private TextField txtRoomAdd;

    @FXML
    private Button btnRoomAdd;

    @FXML
    private Button btnRoomSave;

    @FXML
    private TextField txtCapacities;

    @FXML
    void handleEvents(ActionEvent event) {

    }

}


