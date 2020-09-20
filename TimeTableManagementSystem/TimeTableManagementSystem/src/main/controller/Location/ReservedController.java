package main.controller.Location;

import com.jfoenix.controls.JFXTimePicker;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

public class ReservedController {

    @FXML
    private TextField txtBuildingOpt1;

    @FXML
    private TextField txtRoomOpt1;

    @FXML
    private ComboBox<?> cmbCenter;

    @FXML
    private ComboBox<?> cmbCenter1;

    @FXML
    private JFXTimePicker toTime;

    @FXML
    private JFXTimePicker fromTime;

    @FXML
    private Button btnReservedOptions;

    @FXML
    void saveReservedRoom(ActionEvent event) {

    }

}
