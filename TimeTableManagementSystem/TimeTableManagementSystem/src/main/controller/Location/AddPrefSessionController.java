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
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class AddPrefSessionController implements Initializable {

    @FXML
    private TextField txtLecturer;

    @FXML
    private TextField txtSubject;

    @FXML
    private TextField txtTag;

    @FXML
    private TextField txtGroup;

    @FXML
    private Button btnSearch;

    @FXML
    private RadioButton btnRadioMain;

    @FXML
    private RadioButton btnRadioSub;

    @FXML
    private Label lblSessionId;

    @FXML
    private TextField txtBuildingOpt;

    @FXML
    private TextField txtRoomOpt1;

    @FXML
    private ComboBox<String> cmbCenter;

    @FXML
    private Button btnSave;

    private MainGroupService mainGroupservice;
    private List<String> groupNameList;
    private List<Object> groupList;
    private AutoCompletionBinding<String> autoCompletionBinding;
    private AutoCompletionBinding<String> autoCompletionBinding2;
    private SubGroupService subGroupService;
    private LecturerService lecturerService;
    private TagService tagService;
    private SubjectService subjectService;
    private List<Lecturer> lectureList;
    private List<String> lectureNameList;
    private List<Tag> tagList;
    private List<String> tagNameList;
    private List<Subject> subList;
    private List<String> subNameList;
    private SessionService sessionService;
    private ArrayList<Building> buildingsId = new ArrayList<>();
    private ArrayList<String> buildingName = new ArrayList<>();
    private ArrayList<Room> roomsId = new ArrayList<>();
    private ArrayList<String> roomName = new ArrayList<>();
    private PrefLecturerService prefLecturerService;

    private PrefSessionService prefSessionService;

    public AddPrefSessionController() {
        this.prefSessionService = new PrefSessionServiceImpl();
        this.prefLecturerService = new PrefLecturerServiceImpl();
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
            autoCompletionBinding  = TextFields.bindAutoCompletion(txtBuildingOpt, buildingName);

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    void getRoom(ActionEvent event) {
        String building = txtBuildingOpt.getText();
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
            autoCompletionBinding2  = TextFields.bindAutoCompletion(txtRoomOpt1, roomName);

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
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
            autoCompletionBinding = TextFields.bindAutoCompletion(txtGroup, groupNameList);
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
            autoCompletionBinding = TextFields.bindAutoCompletion(txtGroup, groupNameList);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void loadTagDetails() {
        try {
            ArrayList<Tag> tag = tagService.getAllDetails();
            for (Tag t : tag
            ) {
                tagList.add(t);
                tagNameList.add(t.getTagName());
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        TextFields.bindAutoCompletion(txtTag, tagNameList);
    }

    public void loadSubjectDetails() {
        try {
            ArrayList<Subject> subjects = subjectService.getAllSubjectDetails();
            for (Subject s : subjects
            ) {
                subList.add(s);
                subNameList.add(s.getSubName());
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        TextFields.bindAutoCompletion(txtSubject, subNameList);
    }

    public void loadLectureDetails() {
        try {
            ArrayList<Lecturer> lec = lecturerService.getAllLecturerDetails();
            for (Lecturer l : lec
            ) {
                lectureNameList.add(l.getEmpName());
                lectureList.add(l);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        TextFields.bindAutoCompletion(txtLecturer, lectureNameList);
    }

    @FXML
    void saveSessionRoom(ActionEvent event) throws SQLException {
        System.out.println("wwwww");
        int sessionId = Integer.parseInt(lblSessionId.getText());
        String room = txtRoomOpt1.getText();
        int roomId=0;


        if(sessionId != 0 ){
            if(room != null) {

                    roomId = prefLecturerService.getRoomId(room);
                    System.out.println("wwwwwROOMID:" + roomId);
                }else {
                    Alert al = new Alert(Alert.AlertType.ERROR);
                    al.setTitle(null);
                    al.setContentText("Please Select room!!");
                    al.setHeaderText(null);
                    al.showAndWait();
                }

        }else {
            Alert al = new Alert(Alert.AlertType.ERROR);
            al.setTitle(null);
            al.setContentText("Please Search Session ID");
            al.setHeaderText(null);
            al.showAndWait();
        }


        PrefSession prefSession = new PrefSession();
        prefSession.setRoomId(roomId);
        prefSession.setSessionId(sessionId);

        boolean isAdded = false;

        if(sessionId != 0){
            if(room != null){

                        isAdded = this.prefSessionService.savePrefSessionRoom(prefSession);
                        if (isAdded) {
                            Alert al = new Alert(Alert.AlertType.INFORMATION);
                            al.setTitle(null);
                            al.setContentText("Added Successfully !!");
                            al.setHeaderText(null);
                            al.showAndWait();
//                            this.getAllDetails();
                        } else {
                            Alert al = new Alert(Alert.AlertType.ERROR);
                            al.setTitle(null);
                            al.setContentText("Added Failed !!");
                            al.setHeaderText(null);
                            al.showAndWait();
                        }
                    }else {
                        Alert al = new Alert(Alert.AlertType.ERROR);
                        al.setTitle(null);
                        al.setContentText("Please Select Room !");
                        al.setHeaderText(null);
                        al.showAndWait();
                    }
                }else {
                    Alert al = new Alert(Alert.AlertType.ERROR);
                    al.setTitle(null);
                    al.setContentText("Please Search Session ID!");
                    al.setHeaderText(null);
                    al.showAndWait();
                }



    }

    @FXML
    void searchSessionDetails(ActionEvent event) {
        String lectureName = txtLecturer.getText();
        int lecId = 0;
        int lecCount = 0;
        String subjectName = txtSubject.getText();
        String subId = "";
        int subCount = 0;
        String tagName = txtTag.getText();
        int tagId = 0;
        int tagCount = 0;
        String groupType = "";
        int groupCount = 0;
        int subGroupId = 0;
        int mainGroupId = 0;
        if (btnRadioMain.isSelected()) {
            groupType = "MainGroup";
        } else if (btnRadioSub.isSelected()) {
            groupType = "SubGroup";
        }
        String groupId = txtGroup.getText();

        for (Lecturer l1 : lectureList) {
            if (l1.getEmpName().equals(lectureName.trim())) {
                lecId = l1.getEmpId();
                lecCount++;
            }
        }
        for (Subject s1 : subList) {
            if (s1.getSubName().equals(subjectName.trim())) {
                subId = s1.getSubId();
                subCount++;
            }
        }
        for (Tag t1 : tagList) {
            if (t1.getTagName().equals(tagName.trim())) {
                tagId = t1.getTagId();
                tagCount++;
            }
        }
        for (Object m : this.groupList
        ) {
            if (m instanceof MainGroup) {
                if (groupId.equals(((MainGroup) m).getGroupid())) {
                    mainGroupId = ((MainGroup) m).getId();
                    groupCount++;

                }
            }
            if (m instanceof SubGroup) {
                if (groupId.equals(((SubGroup) m).getSubgroupid())) {
                    subGroupId = ((SubGroup) m).getId();
                    groupCount++;
                }
            }
        }

        if (!lectureName.isEmpty()) {
            if (!subjectName.isEmpty()) {
                if (!tagName.isEmpty()) {
                    if (!groupId.isEmpty()) {
                        if (lecCount != 0) {
                            if (subCount != 0) {
                                if (tagCount != 0) {
                                    if (groupCount != 0) {
                                        try {
                                            int sessionId = sessionService.searchSession(lecId, subId, tagId, subGroupId, mainGroupId);
                                            if (sessionId != 0) {
                                                lblSessionId.setText(Integer.toString(sessionId));
                                            } else {
                                                Alert al = new Alert(Alert.AlertType.ERROR);
                                                al.setTitle(null);
                                                al.setContentText("This session Is not Available!");
                                                al.setHeaderText(null);
                                                al.showAndWait();
                                            }
                                        } catch (SQLException e) {
                                            e.printStackTrace();
                                        }

                                    } else {
                                        Alert al = new Alert(Alert.AlertType.ERROR);
                                        al.setTitle(null);
                                        al.setContentText("Group Is not Exists In this System!");
                                        al.setHeaderText(null);
                                        al.showAndWait();
                                    }
                                } else {
                                    Alert al = new Alert(Alert.AlertType.ERROR);
                                    al.setTitle(null);
                                    al.setContentText("Tag Is not Exists In this System!");
                                    al.setHeaderText(null);
                                    al.showAndWait();
                                }
                            } else {
                                Alert al = new Alert(Alert.AlertType.ERROR);
                                al.setTitle(null);
                                al.setContentText("Subject Is not Exists In this System!");
                                al.setHeaderText(null);
                                al.showAndWait();
                            }
                        } else {
                            Alert al = new Alert(Alert.AlertType.ERROR);
                            al.setTitle(null);
                            al.setContentText("Lecture Is not Exists In this System!");
                            al.setHeaderText(null);
                            al.showAndWait();
                        }
                    } else {
                        Alert al = new Alert(Alert.AlertType.ERROR);
                        al.setTitle(null);
                        al.setContentText("GroupId Field Is Empty!");
                        al.setHeaderText(null);
                        al.showAndWait();
                    }
                } else {
                    Alert al = new Alert(Alert.AlertType.ERROR);
                    al.setTitle(null);
                    al.setContentText("Tag Field Is Empty!");
                    al.setHeaderText(null);
                    al.showAndWait();
                }
            } else {
                Alert al = new Alert(Alert.AlertType.ERROR);
                al.setTitle(null);
                al.setContentText("Subject Field Is Empty!");
                al.setHeaderText(null);
                al.showAndWait();
            }
        } else {
            Alert al = new Alert(Alert.AlertType.ERROR);
            al.setTitle(null);
            al.setContentText("Lecture Field Is Empty!");
            al.setHeaderText(null);
            al.showAndWait();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        mainGroupservice = new MainGroupServiceImpl();
        groupNameList = new ArrayList<>();
        groupList = new ArrayList<>();
        btnRadioMain.setSelected(true);
        lectureList = new ArrayList<>();
        lectureNameList = new ArrayList<>();
        tagList = new ArrayList<>();
        tagNameList = new ArrayList<>();
        subList = new ArrayList<>();
        subNameList = new ArrayList<>();
        subGroupService = new SubGroupServiceImpl();
        lecturerService = new LectureServiceImpl();
        tagService = new TagServiceImpl();
        subjectService = new SubjectServiceImpl();
        sessionService = new SessionServiceImpl();
        this.loadTagDetails();
        this.loadSubjectDetails();
        this.loadLectureDetails();
        this.loadGroupDetails();
    }
}
