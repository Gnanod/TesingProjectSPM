package main.controller.Subject;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import main.controller.MainController;

import java.io.IOException;

public class PnlSubjectController {


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
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
