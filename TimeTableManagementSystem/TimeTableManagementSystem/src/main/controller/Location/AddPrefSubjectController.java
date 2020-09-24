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

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class AddPrefSubjectController implements Initializable {

    @FXML
    private TextField txtSubTagOpt;

    @FXML
    private Button btnSubjectOptions;

    @FXML
    private TextField txtBuildingOpt;

    @FXML
    private TextField txtRoomOpt1;

    @FXML
    private ComboBox<String> cmbCenter;

    @FXML
    private TableView<?> tblBuilding;

    @FXML
    private TableColumn<?, ?> removeBuilding;

    @FXML
    private TableColumn<?, ?> removeBuilding1;

    @FXML
    private TableColumn<?, ?> removeBuilding2;

    @FXML
    private Button btnBuildingAdd;

    @FXML
    private TextField txtTagOpt1;


    private ArrayList<Building> buildingsId = new ArrayList<>();
    private ArrayList<String> buildingName = new ArrayList<>();
    private ArrayList<Room> roomsId = new ArrayList<>();
    private ArrayList<String> roomName = new ArrayList<>();
    private ArrayList<Tag> tagId = new ArrayList<>();
    private ArrayList<String> tagName = new ArrayList<>();
    private ArrayList<Subject> subjectId = new ArrayList<>();
    private ArrayList<String> subjectName = new ArrayList<>();
    private AutoCompletionBinding<String> autoCompletionBinding;
    private AutoCompletionBinding<String> autoCompletionBinding2;
    private AutoCompletionBinding<String> autoCompletionBinding3;
    private AutoCompletionBinding<String> autoCompletionBinding4;


    private PrefSubjectService prefSubjectService;
    private PrefTagService prefTagService;

    public AddPrefSubjectController() {
        this.prefSubjectService = new PrefSubjectServiceImpl();
        this.prefTagService = new PrefTagServiceImpl();
    }

    @FXML
    void getBuilding(ActionEvent event) {
        String center = cmbCenter.getValue();
        System.out.println("ccccccccccccc:"+center);
        try {
            BuildingService buildingService = new BuildingServiceImpl();


            ArrayList<Building> list = buildingService.searchBuildingDetailsByUsingCenter(center);
            buildingsId = new ArrayList<>();
            buildingName = new ArrayList<>();

            for (Building building : list
            ) {
                buildingsId.add(building);
                buildingName.add(building.getBuilding());
            }

            if(autoCompletionBinding!=null){
                autoCompletionBinding.dispose();
            }
            autoCompletionBinding  = TextFields.bindAutoCompletion(txtBuildingOpt, buildingName);

        } catch (SQLException ex) {
            ex.printStackTrace();
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

            if(autoCompletionBinding3!=null){
                autoCompletionBinding3.dispose();
            }
            autoCompletionBinding3  =TextFields.bindAutoCompletion(txtTagOpt1, tagName);

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    void getRoom(ActionEvent event) {
        String building = txtRoomOpt1.getText();
        System.out.println("555555:"+building);
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
            autoCompletionBinding2  = TextFields.bindAutoCompletion(txtRoomOpt1, roomName);

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    void getSubject() {
        try {
            SubjectService tagService = new SubjectServiceImpl();

            ArrayList<Subject> list = tagService.getAllSubjectDetails();
            subjectId = new ArrayList<>();
            subjectName = new ArrayList<>();
            for (Subject subject : list
            ) {
                subjectId.add(subject);
                subjectName.add(subject.getSubName());
            }
            for (String subject : subjectName
            ) {
                System.out.println(subject);
            }
            if(autoCompletionBinding4!=null){
                autoCompletionBinding4.dispose();
            }
            autoCompletionBinding4  =TextFields.bindAutoCompletion(txtSubTagOpt, subjectName);

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    void AddBuildingsToTable(ActionEvent event) {

    }

    @FXML
    void saveTagRoom(ActionEvent event) throws SQLException {
        System.out.println("wwwww");
        String center = (String) cmbCenter.getValue();
        String building = txtBuildingOpt.getText();
        String room = txtRoomOpt1.getText();
        String tag = txtTagOpt1.getText();
        String subject = txtSubTagOpt.getText();
        int roomId=0;
        int tagId=0;
        String subId= "";

            if (building != null) {
                System.out.println("HHH");
                if (center != null) {
                    System.out.println("HHH1111");
                    if (room != null) {
                        System.out.println("HHH2222");
                        roomId = prefTagService.getRoomId(center,building,room);
                        System.out.println("KKKKKKKKKKKKKKKK"+prefTagService.getRoomId(center,building,room));

                    } else {
                        Alert al = new Alert(Alert.AlertType.ERROR);
                        al.setTitle(null);
                        al.setContentText("Please Select room!");
                        al.setHeaderText(null);
                        al.showAndWait();
                    }
                } else {
                    Alert al = new Alert(Alert.AlertType.ERROR);
                    al.setTitle(null);
                    al.setContentText("Please Select center!");
                    al.setHeaderText(null);
                    al.showAndWait();
                }

            } else {
                Alert al = new Alert(Alert.AlertType.ERROR);
                al.setTitle(null);
                al.setContentText("Please Select building!");
                al.setHeaderText(null);
                al.showAndWait();
            }





        if(tag != null) {
                tagId = prefTagService.getTagIdFromTags(tag);
                System.out.println("wwwwwTAGID:" + tagId);
            }else {
                Alert al = new Alert(Alert.AlertType.ERROR);
                al.setTitle(null);
                al.setContentText("Please Select Tag!");
                al.setHeaderText(null);
                al.showAndWait();
            }

        if(subject != null) {
            subId = prefSubjectService.getSubIdFromSubjects(subject);

        }else {
            Alert al = new Alert(Alert.AlertType.ERROR);
            al.setTitle(null);
            al.setContentText("Please Select Subject!");
            al.setHeaderText(null);
            al.showAndWait();
        }


        prefSubject prefSub = new prefSubject();
        prefSub.setTagId(tagId);
        prefSub.setRoomId(roomId);
        prefSub.setSubjectId(subId);

        boolean isAdded = false;

        if(tag != null){
            if(center != null){
                if(building != null){
                    if(room != null){
                        System.out.println("YYYY"+prefSub.getTagId());
                        System.out.println("YYYY"+prefSub.getSubjectId());
                        System.out.println("YYYY"+prefSub.getRoomId());
                        isAdded = this.prefSubjectService.savePrefSubjectRoom(prefSub);
                        if (isAdded) {
                            Alert al = new Alert(Alert.AlertType.INFORMATION);
                            al.setTitle(null);
                            al.setContentText("Added Successfully!!");
                            al.setHeaderText(null);
                            al.showAndWait();
//                            this.getAllDetails();
                        } else {
                            Alert al = new Alert(Alert.AlertType.ERROR);
                            al.setTitle(null);
                            al.setContentText("Added Failed!!");
                            al.setHeaderText(null);
                            al.showAndWait();
                        }
                    }else {
                        Alert al = new Alert(Alert.AlertType.ERROR);
                        al.setTitle(null);
                        al.setContentText("Please Select Room!");
                        al.setHeaderText(null);
                        al.showAndWait();
                    }
                }else {
                    Alert al = new Alert(Alert.AlertType.ERROR);
                    al.setTitle(null);
                    al.setContentText("Please Select Building!");
                    al.setHeaderText(null);
                    al.showAndWait();
                }
            }else {
                Alert al = new Alert(Alert.AlertType.ERROR);
                al.setTitle(null);
                al.setContentText("Please Select Center!");
                al.setHeaderText(null);
                al.showAndWait();
            }
        }else {
            Alert al = new Alert(Alert.AlertType.ERROR);
            al.setTitle(null);
            al.setContentText("Please Select tag!");
            al.setHeaderText(null);
            al.showAndWait();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.getSubject();
        this.getTag();
    }
}
