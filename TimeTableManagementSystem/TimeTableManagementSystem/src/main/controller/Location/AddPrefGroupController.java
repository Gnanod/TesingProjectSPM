package main.controller.Location;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import main.model.Building;
import main.model.MainGroup;
import main.model.Room;
import main.model.SubGroup;
import main.service.*;
import main.service.impl.BuildingServiceImpl;
import main.service.impl.PrefGroupServiceImpl;
import main.service.impl.RoomServiceImpl;
import org.controlsfx.control.textfield.AutoCompletionBinding;
import org.controlsfx.control.textfield.TextFields;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class AddPrefGroupController implements Initializable {

    @FXML
    private TextField textGroup;

    @FXML
    private TextField txtBuilding;

    @FXML
    private ComboBox<String> cmbCenter;


    @FXML
    private TextField txtRoom;

    @FXML
    private RadioButton btnRadioMain;

    @FXML
    private RadioButton btnRadioSub;

    @FXML
    private Button btnGroupOptions;

    private ArrayList<Building> buildingsId = new ArrayList<>();
    private ArrayList<String> buildingName = new ArrayList<>();
    private ArrayList<Room> roomsId = new ArrayList<>();
    private ArrayList<String> roomName = new ArrayList<>();
    private AutoCompletionBinding<String> autoCompletionBinding;
    private AutoCompletionBinding<String> autoCompletionBinding2;
    private MainGroupService mainGroupservice;
    private List<String> groupNameList;
    private List<Object> groupList;
    private SubGroupService subGroupService;

    private PrefGroupService prefGroupService;

    public AddPrefGroupController() {
        this.prefGroupService = new PrefGroupServiceImpl();
    }

    @FXML
    void loadGroupDetails() {
        if (btnRadioMain.isSelected()) {
            loadMainGroupDetails();
        } else if (btnRadioSub.isSelected()) {
            loadSubGroupDetails();
        }
    }

    private void loadMainGroupDetails() {
        try {
            ArrayList<MainGroup> mainList = this.mainGroupservice.getAllMainGroupDetails();
            groupNameList.clear();
            ;
            groupList.clear();
            if (autoCompletionBinding != null) {
                autoCompletionBinding.dispose();
            }
            for (MainGroup m : mainList
            ) {
                groupNameList.add(m.getGroupid());
                groupList.add(m);
            }
            autoCompletionBinding = TextFields.bindAutoCompletion(textGroup, groupNameList);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void loadSubGroupDetails() {
        try {
            ArrayList<SubGroup> subList = this.subGroupService.getAllSubGroupDetails(0);
            ;
            groupNameList.clear();
            groupList.clear();
            if (autoCompletionBinding != null) {
                autoCompletionBinding.dispose();
            }
            for (SubGroup s : subList
            ) {
                groupNameList.add(s.getSubgroupid());
                groupList.add(s);
            }
            autoCompletionBinding = TextFields.bindAutoCompletion(textGroup, groupNameList);
        } catch (SQLException e) {
            e.printStackTrace();
        }
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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.loadGroupDetails();
    }
}
