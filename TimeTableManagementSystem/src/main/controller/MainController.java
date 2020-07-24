package main.controller;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {


    private double x = 0, y = 0;
    private Stage stage;
    public static String urlName;
    @FXML
    private Button btnDashboard;

    @FXML
    private Button btnWorkingDays;

    @FXML
    private Button btnSubject;

    @FXML
    private Button btnLecturer;

    @FXML
    private Button btnLocation;

    @FXML
    private Button btnStudent;

    @FXML
    private Button btnSession;

    @FXML
    private Label lblStatus;

    @FXML
    private Label lblUrl;

    @FXML
    private Button btnlogout;

    @FXML
    private FontAwesomeIconView btnClose;

    @FXML
    private BorderPane pnlMain;

    @FXML
    private AnchorPane mainPnl;

    public MainController() {

    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        lblUrl.setText(urlName);
        this.dragabled();
    }

    private void dragabled() {
        mainPnl.setOnMousePressed((event -> {
            x = event.getSceneX();
            y = event.getSceneY();
        }));
        mainPnl.setOnMouseDragged((event -> {
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setX(event.getScreenX() - x);
            stage.setY(event.getScreenY() - y);
        }));
    }

    @FXML
    void btnHandleClose(MouseEvent event) {
        if (event.getSource() == btnClose) {
            System.exit(0);
        }
    }

    @FXML
    void handleEvents(ActionEvent event) {
        try {
            if (event.getSource() == btnDashboard) {
                chageLableStatus("/home/dashboard", "Dashboard");

            } else if (event.getSource() == btnWorkingDays) {
                chageLableStatus("/home/Students&hours", "Working Days & Hours");
            } else if (event.getSource() == btnSubject) {
                chageLableStatus("/home/subject", "Subject");
                Parent root = FXMLLoader.load(getClass().getResource("../views/Subject/pnlsubject.fxml"));
                pnlMain.setCenter(root);
            } else if (event.getSource() == btnLecturer) {
                chageLableStatus("/home/lecturer", "Lecturer");
                Parent root = FXMLLoader.load(getClass().getResource("../views/Lecturer/pnlLecturer.fxml"));
                pnlMain.setCenter(root);
            } else if (event.getSource() == btnLocation) {
                chageLableStatus("/home/location", "Location");
            } else if (event.getSource() == btnStudent) {
                chageLableStatus("/home/student", "Student");
                Parent root = FXMLLoader.load(getClass().getResource("../views/Students/pnlstudents.fxml"));
                pnlMain.setCenter(root);
            } else if (event.getSource() == btnSession) {
                chageLableStatus("/home/session", "Session");
            } else if (event.getSource() == btnlogout) {
                System.exit(0);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void chageLableStatus(String url, String mainStatus) {
        urlName = url;
        lblUrl.setText(urlName);
        lblStatus.setText(mainStatus);
    }


}