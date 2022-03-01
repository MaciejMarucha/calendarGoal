package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/MainFrame.fxml"));
        primaryStage.setTitle("CalendarGoal");
        primaryStage.getIcons().add(new Image("pobrane.jpg"));
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }
}