package main.controller.Location;


import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import main.model.Room;
import main.service.RoomService;
import main.service.impl.RoomServiceImpl;

import java.sql.SQLException;
import java.util.ArrayList;

public class ViewRoomController {

    @FXML
    private TableView<Room> tblRoomView;

    @FXML
    private TableColumn<Room, Boolean> editBuildingView;

    @FXML
    private TableColumn<Room, Boolean> removeBuildingView;

    @FXML
    private TextField txtBuildingEdit1;

    @FXML
    private TextField txtRoomEdit1;

    @FXML
    private Button btnRoomUpdate;

    @FXML
    private TextField txtCapacitiesEdit1;

    @FXML
    private ComboBox<?> cmbCenterEdit;


    private RoomService roomService;
    private boolean updateStatus;
    private int roomId;

    public ViewRoomController() {
        this.roomService =new RoomServiceImpl();
    }

    public void getAllDetails() {
//        try {
//            ArrayList<Room> listB = this.roomService.getAllDetails();
//            tblRoomView.setItems(FXCollections.observableArrayList(listB));
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
    }

    @FXML
    void updateRoomDetails(ActionEvent event) {

    }

}
