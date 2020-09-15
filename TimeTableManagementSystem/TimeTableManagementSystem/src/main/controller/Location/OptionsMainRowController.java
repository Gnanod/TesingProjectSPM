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

public class OptionsMainRowController implements Initializable {

    @FXML
    private Button btnTagOptions;

    @FXML
    private BorderPane pnlMainOptions;

    @FXML
    private Button btnSubjectOptions;

    @FXML
    private Button btnLecturerOptions;

    @FXML
    private Button btnGroupOptions;

    @FXML
    private Button btnReservedOptions;

    @FXML
    private Button btnSessionOptions;

    @FXML
    void handleEvents(ActionEvent event) {
        try {
            if (event.getSource() == btnTagOptions) {
                pnlMainOptions.getChildren().removeAll();
                Parent root = FXMLLoader.load(getClass().getResource("../../views/Location/Tag.fxml"));
                pnlMainOptions.setCenter(root);
            } else if (event.getSource() == btnSubjectOptions) {
                pnlMainOptions.getChildren().removeAll();
                Parent root = FXMLLoader.load(getClass().getResource("../../views/Location/Subject.fxml"));
                pnlMainOptions.setCenter(root);
            } else if (event.getSource() == btnLecturerOptions) {
                pnlMainOptions.getChildren().removeAll();
                Parent root = FXMLLoader.load(getClass().getResource("../../views/Location/Lecturer.fxml"));
                pnlMainOptions.setCenter(root);
            }else if (event.getSource() == btnGroupOptions) {
                pnlMainOptions.getChildren().removeAll();
                Parent root = FXMLLoader.load(getClass().getResource("../../views/Location/Group.fxml"));
                pnlMainOptions.setCenter(root);
            } else if (event.getSource() == btnSessionOptions) {
                pnlMainOptions.getChildren().removeAll();
                Parent root = FXMLLoader.load(getClass().getResource("../../views/Location/Session.fxml"));
                pnlMainOptions.setCenter(root);
            } else if (event.getSource() == btnReservedOptions) {
                pnlMainOptions.getChildren().removeAll();
                Parent root = FXMLLoader.load(getClass().getResource("../../views/Location/Reserved.fxml"));
                pnlMainOptions.setCenter(root);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        pnlMainOptions.getChildren().removeAll();
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("../../views/Location/Tag.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        pnlMainOptions.setCenter(root);

    }
}

