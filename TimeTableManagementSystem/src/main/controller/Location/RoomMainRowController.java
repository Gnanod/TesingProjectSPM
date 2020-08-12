package main.controller.Location;

import javafx.event.ActionEvent;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;

import java.io.IOException;

public class RoomMainRowController {

    @FXML
    private Button btnViewRoom;

    @FXML
    private Button btnSearchRoom;

    @FXML
    private Button btnAddRoom;

    @FXML
    private BorderPane pnlMainRoom;

    @FXML
    void handleEvents(ActionEvent event) {
        try {
            if (event.getSource() == btnAddRoom) {
                pnlMainRoom.getChildren().removeAll();
                Parent root = FXMLLoader.load(getClass().getResource("../../views/Location/AddRoom.fxml"));
                pnlMainRoom.setCenter(root);
            } else if (event.getSource() == btnSearchRoom) {
                pnlMainRoom.getChildren().removeAll();
                Parent root = FXMLLoader.load(getClass().getResource("../../views/Location/SearchRoom.fxml"));
                pnlMainRoom.setCenter(root);
            } else if (event.getSource() == btnViewRoom) {
                pnlMainRoom.getChildren().removeAll();
                Parent root = FXMLLoader.load(getClass().getResource("../../views/Location/ViewRoom.fxml"));
                pnlMainRoom.setCenter(root);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}



