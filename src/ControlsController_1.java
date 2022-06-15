package unsw.dungeon;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class ControlsController {

    @FXML
    private Button backButton;

    private MenuScreen menuScreen;

    @FXML
    public void handleBackButton(ActionEvent event) {
    	menuScreen.start();
    }

    public void setMenuScreen(MenuScreen menuScreen) {
        this.menuScreen = menuScreen;
    }

    @FXML
    public void initialize() {
    }

}
