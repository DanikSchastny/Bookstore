package bsu.schastny.lab2;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.text.Text;
import javafx.stage.Stage;



public class JavaFxApplication extends Application {
    public static void main(String[] args){
        Application.launch(args);
    }
    @Override
    public void start(Stage primaryStage) throws Exception {
        Text text = new Text("Laba 2");
        text.setLayoutY(80);
        text.setLayoutX(100);

        Group group = new Group(text);

        Scene scene = new Scene(group);
        primaryStage.setScene(scene);
        primaryStage.setTitle("First Application");
        primaryStage.setWidth(300);
        primaryStage.setHeight(250);
        primaryStage.show();
    }


}
