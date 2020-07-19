package main.controller.SampleController;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    private Button btnStudent;

    @FXML
    private Button btnTeacher;

    @FXML
    private Button btnFees;

    @FXML
    private Button btnUsers;

    @FXML
    private Button btnSettings;

    @FXML
    private Pane pnlStatus;

    @FXML
    private Label lblStatus;

    @FXML
    private Label lblStatusMin;

    @FXML
    private BorderPane pnlMain;

    @FXML
    private FontAwesomeIconView btnClose;

    @FXML
    private void handleEvents(ActionEvent event) {

        if (event.getSource() == btnStudent) {
            lblStatus.setText("/home/students");
            lblStatus.setText("Students");
            pnlStatus.setBackground(new Background(new BackgroundFill(Color.rgb(113, 86, 221), CornerRadii.EMPTY, Insets.EMPTY)));
            try {
                Parent root = FXMLLoader.load(getClass().getResource("../../views/SamplePnlLoad/UiOne.fxml"));
                pnlMain.setCenter(root);

            } catch (IOException e) {
                e.printStackTrace();
            }
            //            pnlMain.set
        } else if (event.getSource() == btnTeacher) {
            lblStatus.setText("/home/teacher");
            lblStatus.setText("Teacher");
            pnlStatus.setBackground(new Background(new BackgroundFill(Color.rgb(43, 63, 99), CornerRadii.EMPTY, Insets.EMPTY)));
        } else if (event.getSource() == btnFees) {
            lblStatus.setText("/home/fees");
            lblStatus.setText("Fees");
            pnlStatus.setBackground(new Background(new BackgroundFill(Color.rgb(43, 99, 63), CornerRadii.EMPTY, Insets.EMPTY)));
        } else if (event.getSource() == btnUsers) {
            lblStatus.setText("/home/users");
            lblStatus.setText("Users");
            pnlStatus.setBackground(new Background(new BackgroundFill(Color.rgb(99, 43, 63), CornerRadii.EMPTY, Insets.EMPTY)));
        } else if (event.getSource() == btnSettings) {
            lblStatus.setText("/home/settings");
            lblStatus.setText("Settings");
            pnlStatus.setBackground(new Background(new BackgroundFill(Color.rgb(42, 28, 66), CornerRadii.EMPTY, Insets.EMPTY)));
        }
    }

    @FXML
    public void btnHandleClose(MouseEvent mouseEvent) {
        if (mouseEvent.getSource() == btnClose) {
            System.exit(0);
        }
    }
}
