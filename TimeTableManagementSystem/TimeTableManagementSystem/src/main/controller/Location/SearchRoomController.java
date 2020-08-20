package main.controller.Location;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import main.model.Room;
import main.service.RoomService;
import main.service.impl.RoomServiceImpl;
import org.controlsfx.control.textfield.TextFields;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class SearchRoomController implements Initializable {

    @FXML
    private TableView<Room> tblRoomSeach;

    @FXML
    private TextField txtBuildingSearch1;

    @FXML
    private TextField txtRoomSearch1;

    private RoomService roomService;
    private ArrayList<String> buildingList = new ArrayList<>();
    private ArrayList<String> roomList = new ArrayList<>();
    private ArrayList<Room> roomIdList = new ArrayList<>();

    public SearchRoomController(){
        this.roomService = new RoomServiceImpl();

    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.setTableProperties();
        getAllRoomDetails();
        getAllDetailsForSearch(null,null);
    }

    public void getAllRoomDetails() {
        try {
            ArrayList<Room> bList = this.roomService.getAllRoomDetails();
            for (Room p1 : bList
            ) {
                roomList.add(p1.getRoom());
                buildingList.add(p1.getBuilding());
                roomIdList.add(p1);
            }
            TextFields.bindAutoCompletion(txtBuildingSearch1, buildingList);
            TextFields.bindAutoCompletion(txtRoomSearch1, roomList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void searchDetails(){
        String rbuilding = null;
        String rroom = null;

        String building1 = txtBuildingSearch1.getText();
        String room = txtRoomSearch1.getText();
        for (Room r : this.roomIdList
        ) {
            if (building1.equals(r.getBuilding())) {
                rbuilding = r.getBuilding();

            }
            if (room.equals(r.getRoom())) {
                rroom = r.getRoom();

            }
        }

        getAllDetailsForSearch(rbuilding,rroom);
    }

    private void getAllDetailsForSearch(String rbuilding, String rroom) {

        try {
            ArrayList<Room> list = this.roomService.getAllDetailsForSearch(rbuilding,rroom);
            tblRoomSeach.setItems(FXCollections.observableArrayList(list));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setTableProperties() {
        tblRoomSeach.getSelectionModel().getTableView().getItems().clear();
        tblRoomSeach.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("building"));
        tblRoomSeach.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("room"));
        tblRoomSeach.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("capacity"));
    }
}

