package main.controller.TimeTableGenerate;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;

import java.io.IOException;

public class TimeTableTypesPnl {

    @FXML
    private Button btnStudent;

    @FXML
    private Button btnLecturer;

    @FXML
    private Button btnRoom;

    @FXML
    private BorderPane pnlMain;

    @FXML
    void handleEvents(ActionEvent event) {
        try {
            if (event.getSource() == btnLecturer) {
                pnlMain.getChildren().removeAll();
                Parent root = FXMLLoader.load(getClass().getResource("../../views/TimeTableGenerate/Lecturer.fxml"));
                pnlMain.setCenter(root);
            } else if (event.getSource() == btnStudent) {
                pnlMain.getChildren().removeAll();
                Parent root = FXMLLoader.load(getClass().getResource("../../views/TimeTableGenerate/Student.fxml"));
                pnlMain.setCenter(root);
            }else if (event.getSource() == btnRoom) {
                pnlMain.getChildren().removeAll();
                Parent root = FXMLLoader.load(getClass().getResource("../../views/TimeTableGenerate/Rooms.fxml"));
                pnlMain.setCenter(root);
            }
        } catch ( IOException e) {
            e.printStackTrace();
        }

    }


}
