package main.controller.Subject;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.event.ActionEvent;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainSubjectController implements Initializable {
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
            Parent root = FXMLLoader.load(getClass().getResource("../../views/Subject/AddSubject.fxml"));
            pnlMain.setCenter(root);
        } else if (event.getSource() == btnSearch) {
            pnlMain.getChildren().removeAll();
            Parent root = FXMLLoader.load(getClass().getResource("../../views/Subject/SearchSubject.fxml"));
            pnlMain.setCenter(root);
        } else if (event.getSource() == btnView) {
            pnlMain.getChildren().removeAll();
            Parent root = FXMLLoader.load(getClass().getResource("../../views/Subject/ViewSubject.fxml"));
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
            Parent root = FXMLLoader.load(getClass().getResource("../../views/Subject/AddSubject.fxml"));
            pnlMain.setCenter(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
