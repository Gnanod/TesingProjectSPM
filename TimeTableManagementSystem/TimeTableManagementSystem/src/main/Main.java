package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.awt.*;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("views/Main.fxml"));
        primaryStage.setScene(new Scene(root));
        primaryStage.setTitle("Time Table Management System");
//        primaryStage.initStyle(StageStyle.UNDECORATED);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        double width = screenSize.getWidth();
        double height = screenSize.getHeight();
        primaryStage.setWidth(width);
        primaryStage.setHeight(height);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
