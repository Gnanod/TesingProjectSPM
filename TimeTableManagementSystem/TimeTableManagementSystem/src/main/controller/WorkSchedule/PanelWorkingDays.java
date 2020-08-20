package main.controller.WorkSchedule;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;

public class PanelWorkingDays implements Initializable {
    @FXML
    private Button btnWorkingDays;
    @FXML
    private Button btnWorkingHours;
    @FXML
    private BorderPane pnlShedule;

    public PanelWorkingDays() {
    }

    @FXML
    void handleEvents(ActionEvent event) {
        try {
            pnlShedule.getChildren().removeAll(new Node[0]);
            if (event.getSource() == btnWorkingDays) {
                Parent root = (Parent) FXMLLoader.load(getClass().getResource("../../views/WorkSchedule/pnlDays.fxml"));
                pnlShedule.setCenter(root);
            } else if (event.getSource() == btnWorkingHours) {
                Parent root = (Parent) FXMLLoader.load(getClass().getResource("../../views/WorkSchedule/WorkingHours.fxml"));
                pnlShedule.setCenter(root);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void initialize(URL location, ResourceBundle resources) {
        pnlShedule.getChildren().removeAll(new Node[0]);
        Parent root = null;
        try {
            root = (Parent) FXMLLoader.load(getClass().getResource("../../views/WorkSchedule/pnlDays.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        pnlShedule.setCenter(root);
    }


}