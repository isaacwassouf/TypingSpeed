package sample;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("MainGUI.fxml"));
        primaryStage.setTitle("Test your Typing Speed");
        primaryStage.setScene(new Scene(root, 700, 700));
        //Image image = new Image("/Users/isaacwassouf/Desktop/TypingSpeed/icon.png");
        //primaryStage.getIcons().add(image);
        primaryStage.show();

    }


    public static void main(String[] args) {
        launch(args);
    }

}
