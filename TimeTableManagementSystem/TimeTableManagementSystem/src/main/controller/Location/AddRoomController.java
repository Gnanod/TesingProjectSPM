package main.controller.Location;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import main.model.Building;
import main.model.Room;
import main.service.BuildingService;
import main.service.RoomService;
import main.service.impl.BuildingServiceImpl;
import main.service.impl.RoomServiceImpl;
import org.controlsfx.control.textfield.TextFields;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class AddRoomController implements Initializable {

    @FXML
    private TableView<?> tblRoomAdd;

    @FXML
    private TableColumn<?, ?> editRoomAdd;

    @FXML
    private TableColumn<?, ?> removeRoomAdd;

    @FXML
    private TextField txtRoomAdd;

    @FXML
    private Button btnRoomAdd;

    @FXML
    private Button btnRoomSave;

    @FXML
    private TextField txtCapacities;

    @FXML
    private ComboBox<String> cmbCenter;

    @FXML
    private TextField txtBuilding;

    private ArrayList<Building> buildingsId = new ArrayList<>();
    private ArrayList<String> buildingName = new ArrayList<>();

    @FXML
    void getBuilding(ActionEvent event) {
        String center= cmbCenter.getValue();
        System.out.print(center);
        try{
            BuildingService buildingService=new BuildingServiceImpl();
            ArrayList<Building> list =buildingService.searchBuildingDetailsByUsingCenter(center);
            for (Building building : list
            ) {
                buildingsId.add(building);
                buildingName.add(building.getBuilding());
            }
            TextFields.bindAutoCompletion(txtBuilding, buildingName);

        }catch (SQLException ex){
            ex.printStackTrace();
        }
    }

    @FXML
    void handleEvents(ActionEvent event) {

    }


    private RoomService roomService;

    public AddRoomController(){
        this.roomService = new RoomServiceImpl();
    }

    @FXML
    void saveRoomDetails(ActionEvent event) {

        int capacity = Integer.parseInt(txtCapacities.getText());
        String room = txtRoomAdd.getText();
        String center = cmbCenter.getValue();
        String building = txtBuilding.getText();
        int buildid = 0;
        for (Building b : this.buildingsId
        ) {
            if (building.equals(b.getBuilding())) {
                buildid = b.getBid();
            }
        }


        if(center != null){
            if(building != null){
                if(room != null) {
                    if (capacity != 0) {
                        boolean isAdded = false;
                        Room rooomObj = new Room();
                        rooomObj.setCenter(center);
                        rooomObj.setRoom(room);
                        rooomObj.setCapacity(capacity);
                        rooomObj.setBuildingid(buildid);

                        try {
                            isAdded = this.roomService.saveRooms(rooomObj);
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }

                        if (isAdded) {
                            Alert al = new Alert(Alert.AlertType.INFORMATION);
                            al.setTitle(null);
                            al.setContentText("Added Successfully!!");
                            al.setHeaderText(null);
                            al.showAndWait();
                            txtCapacities.setText(null);
                            cmbCenter.setValue(null);
                            txtRoomAdd.setText(null);
                            txtBuilding.setText(null);

                        } else {
                            Alert al = new Alert(Alert.AlertType.ERROR);
                            al.setTitle(null);
                            al.setContentText("Added Failed!!");
                            al.setHeaderText(null);
                            al.showAndWait();
                        }

                    } else {
                        Alert al = new Alert(Alert.AlertType.ERROR);
                        al.setTitle(null);
                        al.setContentText("Empty capacity field!");
                        al.setHeaderText(null);
                        al.showAndWait();

                    }
                }else{
                    Alert al = new Alert(Alert.AlertType.ERROR);
                    al.setTitle(null);
                    al.setContentText("Empty room field!");
                    al.setHeaderText(null);
                    al.showAndWait();
                }


            }else{
                Alert al = new Alert(Alert.AlertType.ERROR);
                al.setTitle(null);
                al.setContentText("Please select Building!");
                al.setHeaderText(null);
                al.showAndWait();
            }
        }else{
            Alert al = new Alert(Alert.AlertType.ERROR);
            al.setTitle(null);
            al.setContentText("Please select Center !");
            al.setHeaderText(null);
            al.showAndWait();
        }
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}



