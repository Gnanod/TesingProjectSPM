package main.controller.WorkSchedule;

import com.gluonhq.charm.glisten.control.ToggleButtonGroup;

import java.io.IOException;
import java.io.PrintStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.util.Callback;
import main.dbconnection.DBConnection;
import main.model.WorkingDaysMain;
import main.model.WorkingDaysSub;
import main.model.YearAndSemester;
import main.service.WorkingDaysService;
import main.service.impl.WorkingDaysServiceImpl;

// Referenced classes of package main.controller.WorkSchedule:
//            WeekdaysController, WeekendController

public class WorkingDaysController implements Initializable {


    ObservableList WeekdayNumber;
    ObservableList WeekendNumber;
    @FXML
    private Button btnAdd;
    @FXML
    private ComboBox cmbNoDays;
    @FXML
    private TableView<WorkingDaysMain> tblWorkingDays;
    @FXML
    private TableColumn<WorkingDaysMain, Boolean> colEdit;
    @FXML
    private TableColumn<WorkingDaysMain, Boolean> colDelete;
    @FXML
    private BorderPane pnlWorkingDays;
    private Label pnlW;
    @FXML
    private RadioButton btnRadioWeekEnd;
    @FXML
    private RadioButton btnRadioWeekday;
    @FXML
    private ToggleButtonGroup togglebtnDays;
    private WorkingDaysService workingDaysService;
    private boolean updateStatus = false;
    private int updateId=0;

    public WorkingDaysController() {
        this.workingDaysService = new WorkingDaysServiceImpl();
    }

    @FXML
    void addDetails(ActionEvent event) {
        String noDays = (String) cmbNoDays.getValue();
        int countcheckboxWeekdays = Integer.valueOf(WeekdaysController.count).intValue();
        int countcheckboxWeekends = Integer.valueOf(WeekendController.count).intValue();
        if (!btnRadioWeekEnd.isSelected() && !btnRadioWeekday.isSelected()) {
            Alert al = new Alert(javafx.scene.control.Alert.AlertType.ERROR);
            al.setTitle(null);
            al.setContentText("Select Weekend or Weekday ");
            al.setHeaderText(null);
            al.showAndWait();
        } else if (noDays == null) {
            Alert al = new Alert(javafx.scene.control.Alert.AlertType.ERROR);
            al.setTitle(null);
            al.setContentText("Select number of working days ");
            al.setHeaderText(null);
            al.showAndWait();
        } else if (!((String) cmbNoDays.getValue()).matches(String.valueOf(countcheckboxWeekdays)) && !((String) cmbNoDays.getValue()).matches(String.valueOf(countcheckboxWeekends))) {
            Alert al = new Alert(javafx.scene.control.Alert.AlertType.ERROR);
            al.setTitle(null);
            al.setContentText("Number of working days doesn't match the days selected !");
            al.setHeaderText(null);
            al.showAndWait();
        } else {

            if(!updateStatus){
                saveDetails();
            }else{
                updateDetails();
            }

        }
    }

    public void updateDetails(){
        try {
            boolean isDeleted = this.workingDaysService.deleteWorkingDaysfromSub(updateId);
            if(isDeleted){
                ArrayList<String> updateArrayListDay = new ArrayList<>();
                String selectedType = "";
                int noOfDays = Integer.parseInt(cmbNoDays.getValue().toString());
                if (btnRadioWeekday.isSelected()){
                     selectedType = "Weekdays";;
                    if (WeekdaysController.monday) {
                        updateArrayListDay.add("Monday");
                    }
                    if (WeekdaysController.tuesday) {
                        updateArrayListDay.add("Tuesday");
                    }
                    if (WeekdaysController.wednesday) {
                        updateArrayListDay.add("Wednesday");
                    }
                    if (WeekdaysController.thursday) {
                        updateArrayListDay.add("Thursday");
                    }
                    if (WeekdaysController.friday) {
                        updateArrayListDay.add("Friday");
                    }
                } else if (btnRadioWeekEnd.isSelected()) {
                    selectedType = "Weekends";;
                    if (WeekendController.saturday) {
                        updateArrayListDay.add("Saturday");
                    }
                    if (WeekendController.sunday) {
                        updateArrayListDay.add("Sunday");
                    }
                }
                WorkingDaysMain workingDaysMain = new WorkingDaysMain(updateId,selectedType, noOfDays);
                boolean isUpdated = this.workingDaysService.updateNoOfWorkingDays(workingDaysMain);
                int count = 0;

                if(isUpdated){
                    for (String day : updateArrayListDay
                    ) {
                        WorkingDaysSub workingDaysSub = new WorkingDaysSub(updateId, day);
                        this.workingDaysService.addWorkingDaysSub(workingDaysSub);
                        count++;
                    }

                    if (count == updateArrayListDay.size()) {
                        Alert al = new Alert(javafx.scene.control.Alert.AlertType.INFORMATION);
                        al.setTitle(null);
                        al.setContentText("Updated Successfully ");
                        WeekendController.count = 0;
                        WeekdaysController.count = 0;
                        al.setHeaderText(null);
                        al.showAndWait();
                        updateStatus=false;
                    } else {
                        Alert al = new Alert(Alert.AlertType.ERROR);
                        al.setTitle(null);
                        al.setContentText(" Updated Fail ");
                        al.setHeaderText(null);
                        al.showAndWait();
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void saveDetails() {
        try {
            ArrayList<String> arrayListDay = new ArrayList<>();
            String selectedType = "";
            int noOfDays = Integer.parseInt(cmbNoDays.getValue().toString());
            if (btnRadioWeekday.isSelected()) {
                selectedType = "Weekdays";
                if (WeekdaysController.monday) {
                    arrayListDay.add("Monday");
                }
                if (WeekdaysController.tuesday) {
                    arrayListDay.add("Tuesday");
                }
                if (WeekdaysController.wednesday) {
                    arrayListDay.add("Wednesday");
                }
                if (WeekdaysController.thursday) {
                    arrayListDay.add("Thursday");
                }
                if (WeekdaysController.friday) {
                    arrayListDay.add("Friday");
                }
            } else if (btnRadioWeekEnd.isSelected()) {
                selectedType = "Weekends";
                if (WeekendController.saturday) {
                    arrayListDay.add("Saturday");
                }
                if (WeekendController.sunday) {
                    arrayListDay.add("Sunday");
                }
            }

            WorkingDaysMain workingDaysMain = new WorkingDaysMain(selectedType, noOfDays);

            int lastId = this.workingDaysService.addWorkingDays(workingDaysMain);
            int count = 0;
            if (lastId != 0) {
                for (String day : arrayListDay
                ) {
                    WorkingDaysSub workingDaysSub = new WorkingDaysSub(lastId, day);
                    this.workingDaysService.addWorkingDaysSub(workingDaysSub);
                    count++;
                }
            }
            if (count == arrayListDay.size()) {
                Alert al = new Alert(javafx.scene.control.Alert.AlertType.INFORMATION);
                al.setTitle(null);
                al.setContentText("Added Successfully ");
                WeekendController.count = 0;
                WeekdaysController.count = 0;
                al.setHeaderText(null);
                al.showAndWait();
            } else {
                Alert al = new Alert(Alert.AlertType.ERROR);
                al.setTitle(null);
                al.setContentText(" Added Fail ");
                al.setHeaderText(null);
                al.showAndWait();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void changePanel(ActionEvent event) {
        pnlWorkingDays.getChildren().removeAll(new Node[0]);
        try {
            if (btnRadioWeekday.isSelected()) {
                Parent root = (Parent) FXMLLoader.load(getClass().getResource("../../views/WorkSchedule/Weekdays.fxml"));
                cmbNoDays.setItems(WeekdayNumber);
                pnlWorkingDays.setCenter(root);
            } else if (btnRadioWeekEnd.isSelected()) {
                Parent root = (Parent) FXMLLoader.load(getClass().getResource("../../views/WorkSchedule/Weekends.fxml"));
                cmbNoDays.setItems(WeekendNumber);
                pnlWorkingDays.setCenter(root);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void initialize(URL location, ResourceBundle resources) {
        WeekdayNumber = FXCollections.observableArrayList(new String[]{"1", "2", "3", "4", "5"});
        WeekendNumber = FXCollections.observableArrayList(new String[]{"1", "2"});
        btnRadioWeekday.setSelected(true);
        if (btnRadioWeekday.isSelected()) {
            Parent root = null;
            try {
                root = (Parent) FXMLLoader.load(getClass().getResource("../../views/WorkSchedule/Weekdays.fxml"));
            } catch (IOException e) {
                e.printStackTrace();
            }
            pnlWorkingDays.setCenter(root);
            cmbNoDays.setItems(WeekdayNumber);
        }
        this.setTableProperties();
        this.getAllDetails();
    }

    public void getAllDetails() {
        try {
            ArrayList<WorkingDaysMain> list = this.workingDaysService.getAllNoOfWorkingDays();
            tblWorkingDays.setItems(FXCollections.observableArrayList(list));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void setTableProperties() {
        tblWorkingDays.getSelectionModel().getTableView().getItems().clear();
        tblWorkingDays.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("workingId"));
        tblWorkingDays.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("type"));
        tblWorkingDays.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("noOfDays"));
        colEdit.setCellFactory(cellFactoryBtnEdit);
        colDelete.setCellFactory(cellFactoryBtnDelete);
    }



    Callback<TableColumn<WorkingDaysMain, Boolean>, TableCell<WorkingDaysMain, Boolean>> cellFactoryBtnEdit =
            new Callback<TableColumn<WorkingDaysMain, Boolean>, TableCell<WorkingDaysMain, Boolean>>() {
                @Override
                public TableCell<WorkingDaysMain, Boolean> call(TableColumn<WorkingDaysMain, Boolean> param) {
                    final TableCell<WorkingDaysMain, Boolean> cell = new TableCell<WorkingDaysMain, Boolean>() {
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
                                    WorkingDaysMain mainDays = getTableView().getItems().get(getIndex());
                                    setWorkingDaysMainDetailsToFiled(mainDays);
                                    updateStatus = true;
                                    updateId = mainDays.getWorkingId();
                                });
                                btnEdit.setStyle("-fx-background-color: transparent;");
                                btnEdit.setGraphic(iconView);
                                setGraphic(btnEdit);
                                setAlignment(Pos.CENTER);
                                setText(null);
                            }
                        }

                    };
                    return cell;
                }
            };

    Callback<TableColumn<WorkingDaysMain, Boolean>, TableCell<WorkingDaysMain, Boolean>> cellFactoryBtnDelete =
            new Callback<TableColumn<WorkingDaysMain, Boolean>, TableCell<WorkingDaysMain, Boolean>>() {
                @Override
                public TableCell<WorkingDaysMain, Boolean> call(TableColumn<WorkingDaysMain, Boolean> param) {
                    final TableCell<WorkingDaysMain, Boolean> cell = new TableCell<WorkingDaysMain, Boolean>() {
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
                                    WorkingDaysMain daysMain = getTableView().getItems().get(getIndex());
                                    Alert a2 = new Alert(Alert.AlertType.CONFIRMATION);
                                    a2.setTitle(null);
                                    a2.setHeaderText("Are You Okay To Delete This Row !!!");
                                    a2.setContentText(null);
                                    Optional<ButtonType> result = a2.showAndWait();
                                    if (result.get() == ButtonType.OK) {
                                        deleteWorkingDay(daysMain.getWorkingId());
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

    public void deleteWorkingDay(int workingId){
        try {
            boolean isDeleted = this.workingDaysService.deleteWorkingDay(workingId);

            if(isDeleted){
                Alert al = new Alert(Alert.AlertType.INFORMATION);
                al.setTitle(null);
                al.setContentText("Deleted SuccessFully ");
                al.setHeaderText(null);
                al.showAndWait();
            }else{
                Alert al = new Alert(Alert.AlertType.INFORMATION);
                al.setTitle(null);
                al.setContentText("Deleted Fail ");
                al.setHeaderText(null);
                al.showAndWait();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void setWorkingDaysMainDetailsToFiled(WorkingDaysMain mainDays) {
        try {
            ArrayList<String> days = this.workingDaysService.getWorkingDaysAccordingId(mainDays.getWorkingId());
            if (mainDays.getType().equals("Weekends")) {

                btnRadioWeekEnd.setSelected(true);
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../../views/WorkSchedule/Weekends.fxml"));
                Parent root = (Parent) fxmlLoader.load();
                pnlWorkingDays.setCenter(root);
                WeekendController weekendController = fxmlLoader.getController();
                weekendController.checkSUN.setSelected(false);
                weekendController.checkSAT.setSelected(false);
                cmbNoDays.setItems(WeekendNumber);
                for (String s : days) {
                    if (s.equals("Sunday")) {
                        weekendController.checkSUN.setSelected(true);
                        weekendController.selectedDays(new ActionEvent());
                    } else if (s.equals("Saturday")) {
                        weekendController.checkSAT.setSelected(true);
                        weekendController.selectedDays(new ActionEvent());
                    }
                }

            } else if (mainDays.getType().equals("Weekdays")) {
                btnRadioWeekday.setSelected(true);
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../../views/WorkSchedule/Weekdays.fxml"));
                Parent root = (Parent) fxmlLoader.load();
                pnlWorkingDays.setCenter(root);
                cmbNoDays.setItems(WeekdayNumber);
                WeekdaysController weekdaysController = fxmlLoader.getController();
                for (String s : days
                ) {
                    System.out.println(s);
                    if (s.equals("Monday")) {
                       weekdaysController.checkMON.setSelected(true);
                    } else if (s.equals("Tuesday")) {
                        weekdaysController.checkTUE.setSelected(true);
                    } else if (s.equals("Wednesday")) {
                        weekdaysController.checkWED.setSelected(true);
                    } else if (s.equals("Thursday")) {
                        weekdaysController.checkTHU.setSelected(true);
                    } else if (s.equals("Friday")) {
                        weekdaysController.checkFRI.setSelected(true);
                    }
                }
            }



        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}