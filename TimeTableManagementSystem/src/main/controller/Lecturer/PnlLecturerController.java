package main.controller.Lecturer;

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

public class PnlLecturerController implements Initializable {
    @FXML
    private Button btnLecturer;

    @FXML
    private Button btnDepartment;


    @FXML
    private Button btnNotAvailable;

    @FXML
    private BorderPane pnlStudent;

    @FXML
    void handleEvents(ActionEvent event) {
        try {
            if (event.getSource() == btnNotAvailable) {
                pnlStudent.getChildren().removeAll();
                Parent root = FXMLLoader.load(getClass().getResource("../../views/Lecturer/NotAvailableLecturer.fxml"));
                pnlStudent.setCenter(root);
            }
            if (event.getSource() == btnLecturer) {
                pnlStudent.getChildren().removeAll();
                Parent root = FXMLLoader.load(getClass().getResource("../../views/Lecturer/MainLecturer.fxml"));
                pnlStudent.setCenter(root);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            Parent   root = FXMLLoader.load(getClass().getResource("../../views/Lecturer/MainLecturer.fxml"));
            pnlStudent.setCenter(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
