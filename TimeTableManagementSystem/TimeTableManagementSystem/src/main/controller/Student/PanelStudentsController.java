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
                Parent root = FXMLLoader.load(getClass().getResource("../../views/Students/AcademicYear.fxml"));
                pnlStudent.setCenter(root);
                String currentUrl = MainController.urlName;
                System.out.println("UrlCurrent"+MainController.urlName);
                MainController.urlName = currentUrl+"/"+"academicyear&semester";
//                new MainController().changeUrl();
            } else if (event.getSource() == btnMainGroup) {
                pnlStudent.getChildren().removeAll();
                Parent root = FXMLLoader.load(getClass().getResource("../../views/Students/NewMainGroup.fxml"));
//                root.setScaleX(pnlStudent.getScaleX());
//                root.setScaleY(pnlStudent.getScaleY());
//                root.setScaleZ(pnlStudent.getScaleZ());
//                root.setLayoutX(pnlStudent.getLayoutX());
//                root.setLayoutY(pnlStudent.getLayoutY());
                root.setTranslateX(pnlStudent.getTranslateX());
                root.setTranslateY(pnlStudent.getTranslateY());
                root.setTranslateZ(pnlStudent.getTranslateZ());

                pnlStudent.setCenter(root);
            } else if (event.getSource() == btnSubGroup) {
                pnlStudent.getChildren().removeAll();
                Parent root = FXMLLoader.load(getClass().getResource("../../views/Students/NewSubGroup.fxml"));
//                pnlStudent.set
                pnlStudent.setCenter(root);
            } else if (event.getSource() == btnNotAvailable) {
                pnlStudent.getChildren().removeAll();
                Parent root = FXMLLoader.load(getClass().getResource("../../views/Students/NotAvailableGroup.fxml"));

                pnlStudent.setCenter(root);
            } else if(event.getSource()==btnProgramme){
                pnlStudent.getChildren().removeAll();
                Parent root = FXMLLoader.load(getClass().getResource("../../views/Students/Programme.fxml"));
                pnlStudent.setCenter(root);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            Parent   root = FXMLLoader.load(getClass().getResource("../../views/Students/AcademicYear.fxml"));
            pnlStudent.setCenter(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
