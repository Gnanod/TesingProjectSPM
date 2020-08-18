package main.controller.Student;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SubGroupController implements Initializable {

    @FXML
    private Button btnView;

    @FXML
    private Button btnSearch;

    @FXML
    private Button btnAdd;

    @FXML
    private BorderPane pnlMain;

    @FXML
    void handleEvents(ActionEvent event) {

        try {
            if (event.getSource() == btnAdd) {
                pnlMain.getChildren().removeAll();
                Parent root = FXMLLoader.load(getClass().getResource("../../views/Students/AddSubGroup.fxml"));
                pnlMain.setCenter(root);
            } else if (event.getSource() == btnSearch) {
                pnlMain.getChildren().removeAll();
                Parent root = FXMLLoader.load(getClass().getResource("../../views/Students/SearchSubGroup.fxml"));
                pnlMain.setCenter(root);
            } else if (event.getSource() == btnView) {
                pnlMain.getChildren().removeAll();
                Parent root = FXMLLoader.load(getClass().getResource("../../views/Students/ViewSubGroup.fxml"));
                pnlMain.setCenter(root);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            pnlMain.getChildren().removeAll();
            Parent root = FXMLLoader.load(getClass().getResource("../../views/Students/AddSubGroup.fxml"));
            pnlMain.setCenter(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
