package main.controller.Location;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import main.model.*;
import main.service.*;
import main.service.impl.*;
import org.controlsfx.control.textfield.AutoCompletionBinding;
import org.controlsfx.control.textfield.TextFields;

import java.net.SocketTimeoutException;
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
    private PrefTagService prefTagService;
    Boolean status;

    public AddPrefGroupController() {
        this.prefGroupService = new PrefGroupServiceImpl();
        this.prefTagService = new PrefTagServiceImpl();
        this.mainGroupservice = new MainGroupServiceImpl();
        this.subGroupService = new SubGroupServiceImpl();
        this.groupNameList = new ArrayList<>();
        this.groupList = new ArrayList<>();
    }

    @FXML
    void loadGroupDetails() {
        if (btnRadioMain.isSelected()) {
            status = true;
            System.out.println("btnRadioMain");
            loadMainGroupDetails();
        } else if (btnRadioSub.isSelected()) {
            status = false;
            System.out.println("btnRadioSub");
            loadSubGroupDetails();
        }
    }

    private void loadMainGroupDetails() {
        try {
            ArrayList<MainGroup> mainList = this.mainGroupservice.getAllMainGroupDetails();
            groupNameList.clear();
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
    void saveGroupRoom(ActionEvent event) throws SQLException {
        System.out.println("wwwww");
        String center = (String) cmbCenter.getValue();
        String building = txtBuilding.getText();
        String room = txtRoom.getText();
        String group = textGroup.getText();
        int roomId=0;
        int groupId=0;

        if(building != null ){
            if(center != null) {
                if(room != null) {
                    roomId = prefTagService.getRoomId(center, building, room);
                    System.out.println("wwwwwROOMID:" + roomId);
                }else {
                    Alert al = new Alert(Alert.AlertType.ERROR);
                    al.setTitle(null);
                    al.setContentText("Please Select room");
                    al.setHeaderText(null);
                    al.showAndWait();
                }
            }else {
                Alert al = new Alert(Alert.AlertType.ERROR);
                al.setTitle(null);
                al.setContentText("Please Select center");
                al.setHeaderText(null);
                al.showAndWait();
            }

        }else {
            Alert al = new Alert(Alert.AlertType.ERROR);
            al.setTitle(null);
            al.setContentText("Please Select building");
            al.setHeaderText(null);
            al.showAndWait();
        }


        PrefGroup prefGroup = new PrefGroup();
        prefGroup.setRoomId(roomId);

        if(group != null) {
            if(status) {
                groupId = prefGroupService.getGroupMainId(group);
                System.out.println("wwwwwTAGID:" + groupId);
                prefGroup.setGroupId(groupId);
                prefGroup.setSubGroupId(0);
            }
            else{
                groupId = prefGroupService.getGroupSubId(group);
                System.out.println("ID:" + groupId);
                prefGroup.setSubGroupId(groupId);
                prefGroup.setGroupId(0);
            }
        }else {
            Alert al = new Alert(Alert.AlertType.ERROR);
            al.setTitle(null);
            al.setContentText("Please Select Values !");
            al.setHeaderText(null);
            al.showAndWait();
        }


        boolean isAdded = false;

        if(group != null){
            if(center != null){
                if(building != null){
                    if(room != null){
                        isAdded = this.prefGroupService.savePrefGroupRoom(prefGroup);
                        if (isAdded) {
                            Alert al = new Alert(Alert.AlertType.INFORMATION);
                            al.setTitle(null);
                            al.setContentText("Added Successfully!");
                            al.setHeaderText(null);
                            al.showAndWait();
//                            this.getAllDetails();
                        } else {
                            Alert al = new Alert(Alert.AlertType.ERROR);
                            al.setTitle(null);
                            al.setContentText("Added Failed!");
                            al.setHeaderText(null);
                            al.showAndWait();
                        }
                    }else {
                        Alert al = new Alert(Alert.AlertType.ERROR);
                        al.setTitle(null);
                        al.setContentText("Please Select Room");
                        al.setHeaderText(null);
                        al.showAndWait();
                    }
                }else {
                    Alert al = new Alert(Alert.AlertType.ERROR);
                    al.setTitle(null);
                    al.setContentText("Please Select Building");
                    al.setHeaderText(null);
                    al.showAndWait();
                }
            }else {
                Alert al = new Alert(Alert.AlertType.ERROR);
                al.setTitle(null);
                al.setContentText("Please Select Center");
                al.setHeaderText(null);
                al.showAndWait();
            }
        }else {
            Alert al = new Alert(Alert.AlertType.ERROR);
            al.setTitle(null);
            al.setContentText("Please Select Group");
            al.setHeaderText(null);
            al.showAndWait();
        }

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.loadGroupDetails();
    }
}
