package main.controller.Location;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class SubjectController {

    @FXML
    private TextField txtSubTagOpt;

    @FXML
    private Button btnSubjectOptions;

    @FXML
    private TextField txtBuildingOpt;

    @FXML
    private TextField txtBuildingOpt1;

    @FXML
    private ComboBox<?> cmbCenter;

    @FXML
    private TableView<?> tblBuilding;

    @FXML
    private TableColumn<?, ?> removeBuilding;

    @FXML
    private TableColumn<?, ?> removeBuilding1;

    @FXML
    private TableColumn<?, ?> removeBuilding2;

    @FXML
    private Button btnBuildingAdd;

    @FXML
    private TextField txtTagOpt1;

    @FXML
    void AddBuildingsToTable(ActionEvent event) {

    }

    @FXML
    void saveTagRoom(ActionEvent event) {

    }

}
