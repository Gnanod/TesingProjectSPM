package main.controller.Location;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import main.model.Building;
import main.model.Room;
import main.service.BuildingService;
import main.service.PrefGroupService;
import main.service.RoomService;
import main.service.impl.BuildingServiceImpl;
import main.service.impl.PrefGroupServiceImpl;
import main.service.impl.RoomServiceImpl;
import org.controlsfx.control.textfield.AutoCompletionBinding;
import org.controlsfx.control.textfield.TextFields;

import java.sql.SQLException;
import java.util.ArrayList;

public class AddPrefGroupController {

    @FXML
    private TextField textGroup;

    @FXML
    private TextField txtBuilding;

    @FXML
    private ComboBox<String> cmbCenter;


    @FXML
    private TextField txtRoom;

    @FXML
    private Button btnGroupOptions;

    private ArrayList<Building> buildingsId = new ArrayList<>();
    private ArrayList<String> buildingName = new ArrayList<>();
    private ArrayList<Room> roomsId = new ArrayList<>();
    private ArrayList<String> roomName = new ArrayList<>();
    private AutoCompletionBinding<String> autoCompletionBinding;
    private AutoCompletionBinding<String> autoCompletionBinding2;

    private PrefGroupService prefGroupService;

    public AddPrefGroupController() {
        this.prefGroupService = new PrefGroupServiceImpl();
    }

    @FXML
    void getBuilding(ActionEvent event) {
        String center = cmbCenter.getValue();
        System.out.println("zzzzzzzzz:"+center);
        BuildingService buildingService = new BuildingServiceImpl();

        ArrayList<Building> list = null;
        try {
            list = buildingService.searchBuildingDetailsByUsingCenter(center);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        buildingsId = new ArrayList<>();
        buildingName = new ArrayList<>();

        for (Building building : list
        ) {
            buildingsId.add(building);
            buildingName.add(building.getBuilding());
        }
        for (String building : buildingName
        ) {
            System.out.println(building);
        }
        if(autoCompletionBinding!=null){
            autoCompletionBinding.dispose();
        }
        autoCompletionBinding  = TextFields.bindAutoCompletion(txtBuilding, buildingName);

    }

    @FXML
    void getRoom(ActionEvent event) {
        String building = txtBuilding.getText();
        System.out.println("ddddddddddd:"+building);
        try {
            RoomService roomService = new RoomServiceImpl();

            ArrayList<Room> list = roomService.searchRoomDetailsByUsingbuilding(building);
            roomsId = new ArrayList<>();
            roomName = new ArrayList<>();

            for (Room room : list
            ) {
                roomsId.add(room);
                roomName.add(room.getRoom());
            }

            if(autoCompletionBinding2!=null){
                autoCompletionBinding2.dispose();
            }
            autoCompletionBinding2  = TextFields.bindAutoCompletion(txtRoom, roomName);

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    void saveGroupRoom(ActionEvent event) {

    }

}
