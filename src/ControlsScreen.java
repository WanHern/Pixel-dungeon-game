package unsw.dungeon;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ControlsScreen {
    private Stage stage;
    private String title;
    private ControlsController controller;

    private Scene scene;

    public ControlsScreen(Stage stage) throws IOException {
        this.stage = stage;
        title = "Dungeon Controls";

        controller = new ControlsController();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ControlsView.fxml"));
        loader.setController(controller);

        // load into a Parent node called root
        Parent root = loader.load();
        scene = new Scene(root, 500, 300);
    }

    public void start() {
        stage.setTitle(title);
        stage.setScene(scene);
        stage.show();
    }

    public ControlsController getController() {
        return controller;
    }
}

