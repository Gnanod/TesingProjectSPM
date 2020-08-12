package main.controller.Location;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class SubjectController {

    @FXML
    private TableView<?> tblSubOpt;

    @FXML
    private TableColumn<?, ?> editTag;

    @FXML
    private TableColumn<?, ?> removeTag;

    @FXML
    private TableColumn<?, ?> editSubOpt;

    @FXML
    private TableColumn<?, ?> removeSubOpt;

    @FXML
    private TextField txtSubOpt;

    @FXML
    private TextField txtSubTagOpt;

    @FXML
    private Button btnTagAdd;

    @FXML
    private FontAwesomeIconView btnAddSubOpt;

    @FXML
    private Button btnSubjectOptions;

    @FXML
    private TextField txtSubBuildingOpt;

    @FXML
    private TextField txtSubRoomOpt;

    @FXML
    void handleEvents(ActionEvent event) {

    }

}


