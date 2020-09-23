package main.controller.Session;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.event.ActionEvent;

import java.io.IOException;

public class SessionMainController {

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
                Parent root = FXMLLoader.load(getClass().getResource("../../views/Lecturer/MainSessions.fxml"));
                pnlMain.setCenter(root);

            } else if (event.getSource() == btnSearch) {
                pnlMain.getChildren().removeAll();
                Parent root = FXMLLoader.load(getClass().getResource("../../views/Lecturer/ViewSearchSessions.fxml"));
                pnlMain.setCenter(root);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
