package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.net.URL;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        URL resource =getClass().getResource("sample.fxml");
        Parent root = FXMLLoader.load(resource);
        primaryStage.setTitle("MyFTP");
        primaryStage.setScene(new Scene(root, 600, 700));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
