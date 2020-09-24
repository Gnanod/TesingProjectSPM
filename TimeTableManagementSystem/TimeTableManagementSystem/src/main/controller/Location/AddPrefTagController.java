package main.controller.Location;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import main.model.Building;
import main.model.PrefTag;
import main.model.Room;
import main.model.Tag;
import main.service.BuildingService;
import main.service.PrefTagService;
import main.service.RoomService;
import main.service.TagService;
import main.service.impl.BuildingServiceImpl;
import main.service.impl.PrefTagServiceImpl;
import main.service.impl.RoomServiceImpl;
import main.service.impl.TagServiceImpl;
import org.controlsfx.control.textfield.AutoCompletionBinding;
import org.controlsfx.control.textfield.TextFields;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class AddPrefTagController implements Initializable {

    @FXML
    private Button btnTagOptions;

    @FXML
    private TextField txtBuildingOpt1;

    @FXML
    private TextField txtRoomOpt1;

    @FXML
    private ComboBox<String> cmbCenter;

    @FXML
    private TextField txtTagOpt11;


    private ArrayList<Building> buildingsId = new ArrayList<>();
    private ArrayList<String> buildingName = new ArrayList<>();
    private ArrayList<Room> roomsId = new ArrayList<>();
    private ArrayList<String> roomName = new ArrayList<>();
    private ArrayList<Tag> tagId = new ArrayList<>();
    private ArrayList<String> tagName = new ArrayList<>();
    private AutoCompletionBinding<String> autoCompletionBinding;
    private AutoCompletionBinding<String> autoCompletionBinding2;
    private AutoCompletionBinding<String> autoCompletionBinding3;
    private String curTime;

    private PrefTagService prefTagService;

    public AddPrefTagController() {
        this.prefTagService = new PrefTagServiceImpl();
    }

    @FXML
    void saveTagRoom(ActionEvent event) throws SQLException {
        System.out.println("wwwww");
        String center = (String) cmbCenter.getValue();
        String building = txtBuildingOpt1.getText();
        String room = txtRoomOpt1.getText();
        String tag = txtTagOpt11.getText();
        int roomId=0;
        int tagId=0;

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


        if(tag != null) {
            tagId = prefTagService.getTagIdFromTags(tag);
            System.out.println("wwwwwTAGID:" + tagId);
        }else {
            Alert al = new Alert(Alert.AlertType.ERROR);
            al.setTitle(null);
            al.setContentText("Please Select Values!");
            al.setHeaderText(null);
            al.showAndWait();
        }

        PrefTag prefTag = new PrefTag();
        prefTag.setRoomId(roomId);
        prefTag.setTagId(tagId);

        boolean isAdded = false;

        if(tag != null){
            if(center != null){
                if(building != null){
                    if(room != null){
                        isAdded = this.prefTagService.savePrefTagRoom(prefTag);
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
            al.setContentText("Please Select tag");
            al.setHeaderText(null);
            al.showAndWait();
        }

    }

    void getTag() {
        try {
            TagService tagService = new TagServiceImpl();

            ArrayList<Tag> list = tagService.getAllDetails();
            tagId = new ArrayList<>();
            tagName = new ArrayList<>();
            for (Tag tag : list
            ) {
                tagId.add(tag);
                tagName.add(tag.getTagName());
            }
            for (String tag : tagName
            ) {
                System.out.println(tag);
            }
            if(autoCompletionBinding3!=null){
                autoCompletionBinding3.dispose();
            }
            autoCompletionBinding3  =TextFields.bindAutoCompletion(txtTagOpt11, tagName);

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    void getBuilding(ActionEvent event) {
        String center = cmbCenter.getValue();
        System.out.println("ccccccccccccc:"+center);
        try {
            BuildingService buildingService = new BuildingServiceImpl();
            ;

            ArrayList<Building> list = buildingService.searchBuildingDetailsByUsingCenter(center);
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
            autoCompletionBinding  = TextFields.bindAutoCompletion(txtBuildingOpt1, buildingName);

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }



    @FXML
    void getRoom(ActionEvent event) {
        String building = txtBuildingOpt1.getText();
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
            for (String room : roomName
            ) {
                System.out.println(room);
            }
            if(autoCompletionBinding2!=null){
                autoCompletionBinding2.dispose();
            }
            autoCompletionBinding2  = TextFields.bindAutoCompletion(txtRoomOpt1, roomName);

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.getTag();
    }
}
