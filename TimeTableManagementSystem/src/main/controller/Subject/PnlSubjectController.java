package main.controller.Subject;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import main.controller.MainController;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class PnlSubjectController implements Initializable {

    @FXML
    private Button btnSubject;

    @FXML
    private Button btnTag;

    @FXML
    private BorderPane pnlSubject;

    @FXML
    void handleEvents(ActionEvent event) {
        try {
            if (event.getSource() == btnTag) {
                pnlSubject.getChildren().removeAll();
                Parent root = FXMLLoader.load(getClass().getResource("../../views/Subject/Tag.fxml"));
                pnlSubject.setCenter(root);
            }
            if (event.getSource() == btnSubject) {
                pnlSubject.getChildren().removeAll();
                Parent root = FXMLLoader.load(getClass().getResource("../../views/Subject/MainSubject.fxml"));
                pnlSubject.setCenter(root);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            pnlSubject.getChildren().removeAll();
            Parent root = FXMLLoader.load(getClass().getResource("../../views/Subject/MainSubject.fxml"));
            pnlSubject.setCenter(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
