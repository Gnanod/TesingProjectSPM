package main.controller.Student;

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

public class PanelStudentsController implements Initializable {

    public PanelStudentsController() {

    }

    @FXML
    private Button btnSubGroup;

    @FXML
    private Button btnNotAvailable;

    @FXML
    private Button btnAcademicYear;

    @FXML
    private Button btnMainGroup;

    @FXML
    private BorderPane pnlStudent;

    @FXML
    private Button btnProgramme;

    @FXML
    void handleEvents(ActionEvent event) {
        try {
            if (event.getSource() == btnAcademicYear) {
                pnlStudent.getChildren().removeAll();
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/main/views/Students/AcademicYear.fxml"));
                Parent root = loader.load();
                pnlStudent.setCenter(root);
                String currentUrl = MainController.urlName;
                MainController.urlName = currentUrl + "/" + "academicyear&semester";
//                new MainController().changeUrl();
            } else if (event.getSource() == btnMainGroup) {
                pnlStudent.getChildren().removeAll();
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/main/views/Students/NewMainGroup.fxml"));
                Parent root = loader.load();
                pnlStudent.setCenter(root);

            } else if (event.getSource() == btnSubGroup) {
                pnlStudent.getChildren().removeAll();
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/main/views/Students/NewSubGroup.fxml"));
                Parent root = loader.load();
                pnlStudent.setCenter(root);
//                pnlStudent.set
            } else if (event.getSource() == btnNotAvailable) {
                pnlStudent.getChildren().removeAll();
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/main/views/Students/NotAvailableGroup.fxml"));
                Parent root = loader.load();
                pnlStudent.setCenter(root);

            } else if (event.getSource() == btnProgramme) {
                pnlStudent.getChildren().removeAll();
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/main/views/Students/Programme.fxml"));
                Parent root = loader.load();
                pnlStudent.setCenter(root);             
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            pnlStudent.getChildren().removeAll();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/main/views/Students/AcademicYear.fxml"));
            Parent root = loader.load();
            pnlStudent.setCenter(root);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
