package main.controller.Location;


import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class LecturerController {

    @FXML
    private TableView<?> tblLecOpt;

    @FXML
    private TableColumn<?, ?> editTag;

    @FXML
    private TableColumn<?, ?> editLecOpt;

    @FXML
    private TableColumn<?, ?> removeLecOpt;

    @FXML
    private TextField txtLecOpt;

    @FXML
    private TextField txtLecTagOpt;

    @FXML
    private Button btnTagAdd;

    @FXML
    private FontAwesomeIconView btnAddLecOpt;

    @FXML
    private Button btnSaveLecOptions;

    @FXML
    private TextField txtLecBuildingOpt;

    @FXML
    void handleEvents(ActionEvent event) {

    }

}

