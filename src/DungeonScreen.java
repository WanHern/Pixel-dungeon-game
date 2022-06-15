package unsw.dungeon;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class DungeonScreen {
    private Stage stage;
    private String title;
    private DungeonController controller;
    private String filename;

    private Scene scene;

    public DungeonScreen(Stage stage, String filename) throws IOException {
        this.stage = stage;
        
        if (filename.equals("")) {
        	filename = "lvl1.json";
        }
        this.filename = filename;
        
        String level = filename.split("[.]")[0];
        title = "Dungeon - "+level;
        
        DungeonControllerLoader dungeonLoader = new DungeonControllerLoader(filename);

        controller = dungeonLoader.loadController();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("DungeonView.fxml"));
        loader.setController(controller);
        Parent root = loader.load();
        scene = new Scene(root);
        root.requestFocus();
    }

    public void start() {
        stage.setTitle(title);
        stage.setScene(scene);
        stage.show();
    }

    public DungeonController getController() {
        return controller;
    }
    
    public String getLevel() {
    	return filename;
    }
}
