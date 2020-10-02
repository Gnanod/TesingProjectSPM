package main.controller.TimeTableGenerate;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class PanelTimetable implements Initializable {

    @FXML
    private Button btnTimeTable;

    @FXML
    private BorderPane pnlShedule;

    public void handleEvents(ActionEvent event) {
        try {
            pnlShedule.getChildren().removeAll(new Node[0]);
            if (event.getSource() == btnTimeTable) {
                pnlShedule.getChildren().removeAll();
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/main/views/TimeTableGenerate/TimeTableTypesPnl.fxml"));
                Parent root = loader.load();
                pnlShedule.setCenter(root);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {


        try {
            pnlShedule.getChildren().removeAll();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/main/views/TimeTableGenerate/TimeTableTypesPnl.fxml"));
            Parent root = loader.load();
            pnlShedule.setCenter(root);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
