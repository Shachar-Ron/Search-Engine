package sample;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{

        primaryStage.setTitle("Best Search Engine ");
        UserInterface userInterface= UserInterface.getInstance();
        userInterface.init(primaryStage);
        Scene scene = userInterface.BuildInterface();
        primaryStage.setScene(scene);
        primaryStage.show();


    }


    public static void main(String[] args) {
        launch(args);
    }
}

