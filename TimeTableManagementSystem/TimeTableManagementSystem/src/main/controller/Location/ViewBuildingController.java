package main.controller.Location;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class ViewBuildingController {

    @FXML
    private TableView<?> tblBuildingView;

    @FXML
    private TableColumn<?, ?> editBuildingView;

    @FXML
    private TableColumn<?, ?> removeBuildingView;

    @FXML
    private TextField txtCenterEdit;

    @FXML
    private TextField txtBuildingEdit;

    @FXML
    private Button btnBuildingSave;

    @FXML
    void handleEvents(ActionEvent event) {

    }

}



