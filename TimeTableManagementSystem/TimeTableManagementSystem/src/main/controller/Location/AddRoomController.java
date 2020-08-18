package main.controller.Location;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import main.model.Room;
import main.service.RoomService;
import main.service.impl.RoomServiceImpl;

import java.net.URL;
import java.sql.SQLException;
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
    private ComboBox<?> cmbCenter;

    @FXML
    private ComboBox<?> cmbBuilding;

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
        String center = (String) cmbCenter.getValue();
        String building = (String) cmbBuilding.getValue();


        if(center != null){
            if(building != null){
                if(room != null) {
                    if (capacity == 0) {



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



