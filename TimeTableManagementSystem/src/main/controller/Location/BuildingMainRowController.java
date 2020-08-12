package main.controller.Location;

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

public class BuildingMainRowController implements Initializable {

    @FXML
    private Button btnViewBuilding;

    @FXML
    private Button btnSearchBuilding;

    @FXML
    private Button btnAddBuilding;

    @FXML
    private BorderPane pnlMainBuilding;

    @FXML
    void handleEvents(ActionEvent event) {
        try {
            if (event.getSource() == btnAddBuilding) {
                pnlMainBuilding.getChildren().removeAll();
                Parent root = FXMLLoader.load(getClass().getResource("../../views/Location/AddBuilding.fxml"));
                pnlMainBuilding.setCenter(root);
            } else if (event.getSource() == btnSearchBuilding) {
                pnlMainBuilding.getChildren().removeAll();
                Parent root = FXMLLoader.load(getClass().getResource("../../views/Location/SearchBuilding.fxml"));
                pnlMainBuilding.setCenter(root);
            } else if (event.getSource() == btnViewBuilding) {
                pnlMainBuilding.getChildren().removeAll();
                Parent root = FXMLLoader.load(getClass().getResource("../../views/Location/ViewBuilding.fxml"));
                pnlMainBuilding.setCenter(root);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
