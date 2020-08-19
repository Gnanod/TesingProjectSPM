package main.controller.WorkSchedule;

import java.io.PrintStream;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

public class WorkingHoursController implements Initializable {

    final int initialValue = 0;
    SpinnerValueFactory svfH;
    SpinnerValueFactory svfM;
    @FXML
    private RadioButton checkbxOneHour;
    @FXML
    private RadioButton checkbxThirtyMin;
    @FXML
    private Button btnAdd;
    @FXML
    private TableView tblWorkingDays;
    @FXML
    private TableColumn colEdit;
    @FXML
    private TableColumn colDelete;
    @FXML
    private Spinner spinnerHour;
    @FXML
    private Spinner spinnerMinute;

    public WorkingHoursController() {
        svfH = new javafx.scene.control.SpinnerValueFactory.IntegerSpinnerValueFactory(0, 24, 0);
        svfM = new javafx.scene.control.SpinnerValueFactory.IntegerSpinnerValueFactory(0, 60, 0);
    }

    @FXML
    void addWorkingHours(ActionEvent event) {
        if (checkbxThirtyMin.isSelected()) {
            if (((Integer) spinnerHour.getValue()).intValue() <= 0 && ((Integer) spinnerMinute.getValue()).intValue() < 31) {
                Alert al = new Alert(javafx.scene.control.Alert.AlertType.ERROR);
                al.setTitle(null);
                al.setContentText("Atleast 30min time is needed for Thirty minute time slot ");
                al.setHeaderText(null);
                al.showAndWait();
            }
        } else if (checkbxOneHour.isSelected() && ((Integer) spinnerHour.getValue()).intValue() < 1) {
            Alert al = new Alert(javafx.scene.control.Alert.AlertType.ERROR);
            al.setTitle(null);
            al.setContentText("Atleast 1 hour time is needed for One hour time slot");
            al.setHeaderText(null);
            al.showAndWait();
        }
        System.out.println("Validation complete");
    }

    public void initialize(URL location, ResourceBundle resources) {
        spinnerHour.setValueFactory(svfH);
        spinnerMinute.setValueFactory(svfM);
        checkbxOneHour.setSelected(true);
    }

}