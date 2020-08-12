package main.controller.Location;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class TagController implements Initializable {

    @FXML
    private TableView<?> tblTagOpt;

    @FXML
    private TableColumn<?, ?> editTag;

    @FXML
    private TableColumn<?, ?> removeTag;

    @FXML
    private TextField txtTagOpt;

    @FXML
    private TextField txtRoomOpt;

    @FXML
    private Button btnTagAdd;

    @FXML
    private Button btnTagOptions;

    @FXML
    void handleEvents(ActionEvent event) {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}

