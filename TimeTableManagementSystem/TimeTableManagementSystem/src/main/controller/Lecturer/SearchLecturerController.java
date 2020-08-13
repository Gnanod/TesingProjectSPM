package main.controller.Lecturer;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class SearchLecturerController implements Initializable {
    @FXML
    private TableView<?> tblGroupCount;

    @FXML
    private TextField txtyear;

    @FXML
    private Button btnSave;



    @FXML
    void searchDetails(ActionEvent event) {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
