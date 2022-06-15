package unsw.dungeon;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MenuScreen {

    private Stage stage;
    private String title;
    private MenuController controller;
    private Scene scene;

    public MenuScreen(Stage stage) throws IOException {
        this.stage = stage;
        title = "Dungeon Menu";

        controller = new MenuController();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("MenuView.fxml"));
        loader.setController(controller);
        controller.setStage(stage);
        controller.setScreen(this);

        // load into a Parent node called root
        Parent root = loader.load();
        scene = new Scene(root, 600, 400);
    }

    public void start() {
        stage.setTitle(title);
        stage.setScene(scene);
        stage.show();
    }
    
    public void restartCurrentLevel() throws IOException {
    	getController().restartCurrentLevel();
    }
    
    public void levelCompletedUpdate() {
    	getController().levelCompletedUpdate();
    }

    public MenuController getController() {
        return controller;
    }
}
