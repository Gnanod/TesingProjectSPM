package main.controller.Subject;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;
public class ViewSubjectController {
    @FXML
    private Button btnUpdate;

    @FXML
    private TableView<?> tblMainGroup;

    @FXML
    private TableColumn<?, ?> colEdit;

    @FXML
    private TableColumn<?, ?> colDelete;

    @FXML
    private TextField txtDepartment;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtCenter;

    @FXML
    private TextField txtLevel;

    @FXML
    private TextField txtFaculty;

    @FXML
    private TextField txtBuilding;

    @FXML
    void updateLecturer(ActionEvent event) {

    }
}
