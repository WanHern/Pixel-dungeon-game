package unsw.dungeon;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class DungeonApplication extends Application {

    @Override
    public void start(Stage primaryStage) throws IOException {
        MenuScreen menuScreen = new MenuScreen(primaryStage);
        ControlsScreen controlsScreen = new ControlsScreen(primaryStage);
        DungeonScreen dungeonScreen = new DungeonScreen(primaryStage,"");

        // Both controllers need to know about the other screen.

        menuScreen.getController().setButtons();
        menuScreen.getController().setDungeonScreen(dungeonScreen);
        menuScreen.getController().setControlsScreen(controlsScreen);
        
        controlsScreen.getController().setMenuScreen(menuScreen);
        
        dungeonScreen.getController().setMenuScreen(menuScreen);

        menuScreen.start();
    }


    public static void main(String[] args) {
        launch(args);
    }

}
