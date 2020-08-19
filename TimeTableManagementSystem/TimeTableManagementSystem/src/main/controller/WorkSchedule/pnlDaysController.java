package main.controller.WorkSchedule;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;

import java.io.IOException;

public class pnlDaysController {

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnAdd;

    @FXML
    private BorderPane pnlMain;

    @FXML
    void handleEvents(ActionEvent event) {

        try {
            if (event.getSource() == btnAdd) {
                pnlMain.getChildren().removeAll();
                Parent root = FXMLLoader.load(getClass().getResource("../../views/WorkSchedule/WorkingDays.fxml"));
                pnlMain.setCenter(root);
            } else if (event.getSource() == btnDelete) {
                pnlMain.getChildren().removeAll();
                Parent root = FXMLLoader.load(getClass().getResource("../../views/WorkSchedule/WorkingDaysDelete.fxml"));
                pnlMain.setCenter(root);
            }
        } catch ( IOException e) {
            e.printStackTrace();
        }

    }


}
