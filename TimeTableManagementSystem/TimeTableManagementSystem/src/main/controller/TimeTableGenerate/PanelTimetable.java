package main.controller.TimeTableGenerate;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;


import java.io.IOException;

public class PanelTimetable {

    @FXML
    private Button btnTimeTable;

    @FXML
    private BorderPane pnlShedule;



    public void handleEvents(ActionEvent event) {
        try {
            pnlShedule.getChildren().removeAll(new Node[0]);
            if (event.getSource() == btnTimeTable) {
                Parent root = (Parent) FXMLLoader.load(getClass().getResource("../../views/TimeTableGenerate/TimeTableTypesPnl.fxml"));
                pnlShedule.setCenter(root);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
