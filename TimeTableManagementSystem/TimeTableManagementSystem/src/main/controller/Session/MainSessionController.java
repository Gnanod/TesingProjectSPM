package main.controller.Session;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import com.jfoenix.controls.JFXTimePicker;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import main.model.*;
import main.service.*;
import main.service.impl.*;
import org.controlsfx.control.textfield.AutoCompletionBinding;
import org.controlsfx.control.textfield.TextFields;


public class MainSessionController implements Initializable{

    @FXML
    private Button btnSave;

    @FXML
    private TextField txtLecturer;

    @FXML
    private TextField txtSubject;

    @FXML
    private TextField txtTag;

    @FXML
    private TextField txtGroup;

    @FXML
    private RadioButton btnRadioMain;

    @FXML
    private RadioButton btnRadioSub;

    @FXML
    private TextField txtCount;

    @FXML
    private TableView<Lecturer> lectTbl;

    @FXML
    private TextField txtDuration;
    @FXML
    private TableColumn<Lecturer, Boolean> colDelete;

    private AutoCompletionBinding<String> autoCompletionBindings;
    private LecturerService lecturerServices;
    private SubjectService subjectServices;
    private TagService tagServices;
    private MainGroupService mainGroupServices;
    private SubGroupService subGroupServices;
    private List<Lecturer> lectureLists;
    private List<Subject> subjectLists;
    private List<Tag> tagLists;
    private List<Object> mainGroupLists;
    private List<SubGroup> subGroupLists;
    private List<String> lectureNameLists;
    private List<String> subNameLists;
    private List<String> tagNameLists;
    private List<String> maingroupNameLists;
    private List<String> subGroupNameLists;
    private ArrayList<Lecturer> list1=new ArrayList<>();
    private String subId1;
    private int tagId;
    private int gId;
    private int subGroupId;
    private  int empId;

    @FXML
    void loadGroupDetails(ActionEvent event) {

    }

    @FXML
    void saveDetails(ActionEvent event) {
        String subject=txtSubject.getText();
        String tag=txtTag.getText();
        String groupType;
        if((tag.equalsIgnoreCase("Lecture"))||(tag.equalsIgnoreCase("Tute"))){
            groupType="Main";
        }else{
            groupType="Sub";
        }
        String groupId=txtGroup.getText();
        try{
            int stdCount=Integer.parseInt(txtCount.getText());
            float duration=Float.parseFloat(txtDuration.getText());



        int subCount=0;
        for (Subject s1 : subjectLists) {
            if (s1.getSubName().equals(subject.trim())) {
                subId1 = s1.getSubId();
                subCount++;
            }
        }
        subCount=0;
        for (Tag t1 : tagLists) {
            if (t1.getTagName().equals(tag.trim())) {
                tagId = t1.getTagId();
                subCount++;
            }
        }

       subCount=0;
        for (Object m : this.mainGroupLists
        ) {
            if (m instanceof MainGroup) {
                if (groupId.equals(((MainGroup) m).getGroupid())) {
                    gId = ((MainGroup) m).getId();
                    subCount++;

                }
            }
            if (m instanceof SubGroup) {
                if (groupId.equals(((SubGroup) m).getSubgroupid())) {
                    subGroupId = ((SubGroup) m).getId();
                    subCount++;
                }
            }
        }

        String isConsecutive;
        if(subId1!=null){
            if(tagId!=0){
                if(stdCount!=0){
                    if(duration!=0){
                        if(groupType.equalsIgnoreCase("Main")){
                            System.out.println("Group"+gId);
                            isConsecutive="Yes";
                            Session session=new Session(subId1,tagId,Integer.toString(gId),null,stdCount,duration,isConsecutive);
                            SessionService sessionService=new SessionServiceImpl();

                            try {
                                boolean res=sessionService.addSession(session);
                               int sessionId=sessionService.searchSessionByDetails(subId1,tagId,subGroupId,gId);
                               // System.out.println(sessionId);
                                Iterator<Lecturer> itr = list1.iterator();

                                while (itr.hasNext()) {
                                    Lecturer lecture1 = itr.next();
                                    empId=lecture1.getEmpId();
                                    sessionService.addLectureSession(empId,sessionId);
                                }


                                if(res==true){
                                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                    alert.setTitle(null);
                                    alert.setHeaderText(null);
                                    alert.setContentText("Success Adding Session!");

                                    alert.showAndWait();
                                    txtSubject.setText("");
                                    txtTag.setText("");
                                    txtGroup.setText("");
                                    txtCount.setText(" ");
                                    txtDuration.setText(" ");
                                    txtLecturer.setText(" ");
                                    this.setTableProperties();
                                }else{
                                    Alert al = new Alert(Alert.AlertType.ERROR);
                                    al.setTitle(null);
                                    al.setContentText("Error Adding Employee!");
                                    al.setHeaderText(null);
                                    al.showAndWait();
                                }
                                System.out.print(res);
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                        }else{
                            isConsecutive="No";
                            Session session=new Session(subId1,tagId,null,Integer.toString(subGroupId),stdCount,duration,isConsecutive);
                            SessionService sessionService=new SessionServiceImpl();

                            try {
                                boolean res=sessionService.addSession(session);
                                System.out.print(res);
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                        }
                    }else{
                        Alert ald = new Alert(Alert.AlertType.ERROR);
                        ald.setTitle(null);
                        ald.setContentText("Invalid Time Duration");
                        ald.setHeaderText(null);
                        ald.showAndWait();
                    }

                }else{
                    Alert alst = new Alert(Alert.AlertType.ERROR);
                    alst.setTitle(null);
                    alst.setContentText("Invalid Student Count");
                    alst.setHeaderText(null);
                    alst.showAndWait();
                }

            }else{
                Alert alt = new Alert(Alert.AlertType.ERROR);
                alt.setTitle(null);
                alt.setContentText("Tag Is not Exists In this System");
                alt.setHeaderText(null);
                alt.showAndWait();
            }

        }else{
            Alert als = new Alert(Alert.AlertType.ERROR);
            als.setTitle(null);
            als.setContentText("Subject Is not Exists In this System");
            als.setHeaderText(null);
            als.showAndWait();
        }
        }catch (NumberFormatException ex){
            Alert als = new Alert(Alert.AlertType.ERROR);
            als.setTitle(null);
            als.setContentText("Enter Time & Duration in Correct Format");
            als.setHeaderText(null);
            als.showAndWait();
        }

    }


    @FXML
    void AddLecturer(ActionEvent event) {
        String lectureName1 = txtLecturer.getText();
        int lecId1 = 0;
        int lecCount = 0;
        for (Lecturer l1 : lectureLists) {
            if (l1.getEmpName().equals(lectureName1.trim())) {
                lecId1 = l1.getEmpId();
                lecCount++;
            }
        }
        Lecturer l1=new Lecturer(lecId1,lectureName1);
        list1.add(l1);
        lectTbl.setItems(FXCollections.observableArrayList(list1));
        txtLecturer.setText("");
    }

    @FXML
    void SelectTag(ActionEvent event) {
        String tag=txtTag.getText();
        if((tag.equalsIgnoreCase("Lecture")||(tag.equalsIgnoreCase("Tute")))){
            //System.out.print("Lect");
            btnRadioMain.setSelected(true);
            btnRadioSub.setSelected(false);
            this.loadAllMainGroupDetails();
        }else{
            btnRadioSub.setSelected(true);
            btnRadioMain.setSelected(false);
            this.loadAllSubGroupDetails();


        }

    }
    private void loadAllMainGroupDetails() {
        try {
            ArrayList<MainGroup> mainLists = this.mainGroupServices.getAllMainGroupDetails();
            maingroupNameLists.clear();
            mainGroupLists.clear();
            if (autoCompletionBindings != null) {
                autoCompletionBindings.dispose();
            }
            for (MainGroup m1 : mainLists
            ) {
                maingroupNameLists.add(m1.getGroupid());
                mainGroupLists.add(m1);
            }
            autoCompletionBindings = TextFields.bindAutoCompletion(txtGroup, maingroupNameLists);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private void loadAllSubGroupDetails() {
        try {
            ArrayList<SubGroup> subLists = this.subGroupServices.getAllSubGroupDetails(0);

            maingroupNameLists.clear();
            mainGroupLists.clear();
            if (autoCompletionBindings != null) {
                autoCompletionBindings.dispose();
            }
            for (SubGroup s1 : subLists
            ) {
                maingroupNameLists.add(s1.getSubgroupid());
                mainGroupLists.add(s1);
            }
            autoCompletionBindings = TextFields.bindAutoCompletion(txtGroup, maingroupNameLists);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void loadAllLectureDetails() {
        try {
            ArrayList<Lecturer> lec = lecturerServices.getAllLecturerDetails();
            for (Lecturer l : lec
            ) {
                lectureNameLists.add(l.getEmpName());
                lectureLists.add(l);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        TextFields.bindAutoCompletion(txtLecturer, lectureNameLists);
    }
    public void loadAllSubjectDetails() {
        try {
            ArrayList<Subject> subjects = subjectServices.getAllSubjectDetails();
            for (Subject s : subjects
            ) {
                subjectLists.add(s);
                subNameLists.add(s.getSubName());
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        TextFields.bindAutoCompletion(txtSubject, subNameLists);
    }
    public void loadAllTagDetails() {
        try {
            ArrayList<Tag> tag = tagServices.getAllDetails();
            for (Tag t : tag
            ) {
                tagLists.add(t);
                tagNameLists.add(t.getTagName());
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        TextFields.bindAutoCompletion(txtTag, tagNameLists);
    }

    public void setTableProperties() {
        lectTbl.getSelectionModel().getTableView().getItems().clear();
        lectTbl.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("empId"));
        lectTbl.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("empName"));
        colDelete.setCellFactory(cellFactoryBtnDelete);
    }
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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        lecturerServices=new LectureServiceImpl();
        lectureNameLists = new ArrayList<>();
        lectureLists = new ArrayList<>();
        subjectServices=new SubjectServiceImpl();
        subNameLists=new ArrayList<>();
        subjectLists=new ArrayList<>();
        tagServices=new TagServiceImpl();
        tagLists=new ArrayList<>();
        tagNameLists=new ArrayList<>();
        mainGroupServices=new MainGroupServiceImpl();
        mainGroupLists=new ArrayList<>();
        maingroupNameLists=new ArrayList<>();
        subGroupServices=new SubGroupServiceImpl();
        this.loadAllLectureDetails();
        this.loadAllSubjectDetails();
        this.loadAllTagDetails();
        this.setTableProperties();
    }

    public void deleteLecturer(int EmpId){

        Iterator<Lecturer> itr = list1.iterator();
        while (itr.hasNext()) {
            Lecturer lecture1 = itr.next();
            if (lecture1.getEmpId()==EmpId) {
                //System.out.println("Delete Lecturer");
                itr.remove();
            }
        }

        setTableProperties();
        lectTbl.setItems(FXCollections.observableArrayList(list1));
    }
}