package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("views/SamplePnlLoad/SamplePanelLoad.fxml"));
        primaryStage.setScene(new Scene(root));
        primaryStage.setTitle("Time Table Management System");
        primaryStage.initStyle(StageStyle.UNDECORATED);
//        primaryStage.initStyle(StageStyle.TRANSPARENT);
//        primaryStage.setScene(new Scene(root, 800, 600));

        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
