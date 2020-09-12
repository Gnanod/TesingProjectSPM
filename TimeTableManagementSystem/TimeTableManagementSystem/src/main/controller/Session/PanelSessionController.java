package main.controller.Session;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import main.controller.MainController;

public class PanelSessionController implements Initializable {



    @FXML
    private Button btnSession;

    @FXML
    private Button btnNotAvailable;

    @FXML
    private BorderPane pnlSession;

    @FXML
    private Button btnConsectiveSession;

    @FXML
    void handleEvents(ActionEvent event) {
            try{
                if(event.getSource()== btnSession){

                }else if(event.getSource()== btnNotAvailable){
                    pnlSession.getChildren().removeAll();
                    Parent root = FXMLLoader.load(getClass().getResource("../../views/Session/NotAvailableSession.fxml"));
                    pnlSession.setCenter(root);
                    String currentUrl = MainController.urlName;
                    MainController.urlName = currentUrl+"/"+"NotAvailableSession";
                }else if(event.getSource() == btnConsectiveSession){
                    pnlSession.getChildren().removeAll();
                    Parent root = FXMLLoader.load(getClass().getResource("../../views/Session/ConsectiveSession.fxml"));
                    pnlSession.setCenter(root);
                    String currentUrl = MainController.urlName;
                    MainController.urlName = currentUrl+"/"+"ConsectiveSession";
                }

            }catch (IOException e){
                e.printStackTrace();
            }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
