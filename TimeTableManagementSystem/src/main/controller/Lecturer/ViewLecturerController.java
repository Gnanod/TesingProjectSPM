package main.controller.Lecturer;


import com.gluonhq.charm.glisten.control.AutoCompleteTextField;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.event.ActionEvent;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import main.model.Lecturer;
import main.model.MainGroup;
import main.service.LecturerService;
import main.service.impl.LectureServiceImpl;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class ViewLecturerController implements Initializable {

    @FXML
    private Button btnUpdate;

    @FXML
    private TableView<Lecturer> tblMainGroup;

    @FXML
    private TableColumn<Lecturer, Boolean> colEdit;

    @FXML
    private TableColumn<Lecturer, Boolean> colDelete;

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
    static int empId;

    @FXML
    void updateLecturer(ActionEvent event) {

            String empName=txtName.getText();
            String faculty=txtFaculty.getText();
            String department=txtDepartment.getText();
            String center=txtCenter.getText();
            String building=txtBuilding.getText();
            int level=Integer.parseInt(txtLevel.getText());
            Lecturer lecturer=new Lecturer(empId,empName,faculty,department,center,building,level);
            try{
                LecturerService lecturerService=new LectureServiceImpl();
                lecturerService.updateLecturer(lecturer);
                this.setTableProperties();
                getAllLecturers();
            }catch (SQLException ex){
                ex.printStackTrace();
            }

    }
    public void setTableProperties() {
        tblMainGroup.getSelectionModel().getTableView().getItems().clear();
        tblMainGroup.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("empId"));
        tblMainGroup.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("empName"));
        tblMainGroup.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("Faculty"));
        colEdit.setCellFactory(cellFactoryBtnEdit);
        colDelete.setCellFactory(cellFactoryBtnDelete);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.setTableProperties();
        getAllLecturers();
    }
    public void getAllLecturers(){

        try {
            LecturerService lecturerService=new LectureServiceImpl();
            ArrayList<Lecturer> list = lecturerService.getAllLecturerDetails();
            for (Lecturer str : list)
            {
                System.out.println(str.getEmpId());
                System.out.println(str.getEmpName());
            }
            tblMainGroup.setItems(FXCollections.observableArrayList(list));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    Callback<TableColumn<Lecturer, Boolean>, TableCell<Lecturer, Boolean>> cellFactoryBtnEdit =
            new Callback<TableColumn<Lecturer, Boolean>, TableCell<Lecturer, Boolean>>() {
                @Override
                public TableCell<Lecturer, Boolean> call(TableColumn<Lecturer, Boolean> param) {
                    final TableCell<Lecturer, Boolean> cell = new TableCell<Lecturer, Boolean>() {
                        FontAwesomeIconView iconView = new FontAwesomeIconView(FontAwesomeIcon.PENCIL_SQUARE);
                        final Button btnEdit = new Button();

                        @Override
                        public void updateItem(Boolean check, boolean empty) {
                            super.updateItem(check, empty);
                            if (empty) {
                                setGraphic(null);
                                setText(null);
                            } else {
                                btnEdit.setOnAction(e -> {
                                    Lecturer lecturer = getTableView().getItems().get(getIndex());
                                    setMainGroupDetailsToTheField(lecturer);
                                });
                                btnEdit.setStyle("-fx-background-color: transparent;");
                                btnEdit.setGraphic(iconView);
                                setGraphic(btnEdit);
                                setAlignment(Pos.CENTER);
                                setText(null);
                            }
                        }

                        private void setMainGroupDetailsToTheField(Lecturer m) {
                            empId=m.getEmpId();
                            txtName.setText(m.getEmpName());
                            txtFaculty.setText(m.getFaculty());
                            txtDepartment.setText(m.getEmpName());
                            txtCenter.setText(m.getCenter());
                            txtLevel.setText(Integer.toString(m.getLevel()));
                            txtBuilding.setText(m.getBuilding());
                        }
                    };
                    return cell;
                }
    };

    Callback<TableColumn<Lecturer, Boolean>, TableCell<Lecturer, Boolean>> cellFactoryBtnDelete =
            new Callback<TableColumn<Lecturer, Boolean>, TableCell<Lecturer, Boolean>>() {
                @Override
                public TableCell<Lecturer, Boolean> call(TableColumn<Lecturer, Boolean> param) {
                    final TableCell<Lecturer, Boolean> cell = new TableCell<Lecturer, Boolean>() {
                        FontAwesomeIconView iconViewDelete = new FontAwesomeIconView(FontAwesomeIcon.TRASH);
                        final Button btnDelete = new Button();

                        @Override
                        public void updateItem(Boolean check, boolean empty) {
                            super.updateItem(check, empty);
                            if (empty) {
                                setGraphic(null);
                                setText(null);
                            } else {
                                btnDelete.setOnAction(e -> {
                                    Lecturer lecturer = getTableView().getItems().get(getIndex());
                                    Alert a2 = new Alert(Alert.AlertType.CONFIRMATION);
                                    a2.setTitle(null);
                                    a2.setHeaderText("Are You Okay To Delete This Row !!!");
                                    a2.setContentText(null);
                                    Optional<ButtonType> result = a2.showAndWait();
                                    if (result.get() == ButtonType.OK) {
                                        deleteLecturer(lecturer.getEmpId());
                                    } else {

                                    }
                                });
                                btnDelete.setStyle("-fx-background-color: transparent;");
                                btnDelete.setGraphic(iconViewDelete);
                                setGraphic(btnDelete);
                                setAlignment(Pos.CENTER);
                                setText(null);

                            }
                        }
                    };
                    return cell;
                }
            };

    public void deleteLecturer(int id) {
        System.out.println(id);
        try{
            LecturerService lecturerService=new LectureServiceImpl();
            lecturerService.deleteLecturerDetails(id);
            this.setTableProperties();
            getAllLecturers();
        }catch (SQLException ex){
            ex.printStackTrace();
        }
    }
}
