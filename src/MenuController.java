package unsw.dungeon;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class MenuController {

    @FXML
    private Button controlsButton;
    @FXML
    private Button resetDataButton;
    @FXML
    private Button lvl1Button;
    @FXML
    private Button lvl2Button;
    @FXML
    private Button lvl3Button;
    @FXML
    private Button lvl4Button;
    @FXML
    private Button lvl5Button;
    @FXML
    private Button lvl6Button;
    @FXML
    private Button lvl7Button;
    @FXML
    private Button lvl8Button;
    @FXML
    private Button lvl9Button;
    
    private List<Button> buttons;

    private DungeonScreen dungeonScreen;
    
    private ControlsScreen controlsScreen;
    
    private Stage stage;
    
    private MenuScreen menuScreen;

    @FXML
    void handleControlsButton(ActionEvent event) throws IOException {
    	controlsScreen.start();
    }
    
    @FXML
    void resetDataButton(ActionEvent event) {
    	for (Button b: buttons) {
			b.setStyle(null);
    	}
    }
    
    @FXML
    void handleLvl1Button(ActionEvent event) throws IOException {
    	startCurrDungeonScreen("lvl1.json");
    }
    @FXML
    void handleLvl2Button(ActionEvent event) throws IOException {
    	startCurrDungeonScreen("lvl2.json");
    }
    @FXML
    void handleLvl3Button(ActionEvent event) throws IOException {
    	startCurrDungeonScreen("lvl3.json");
    }
    @FXML
    void handleLvl4Button(ActionEvent event) throws IOException {
    	startCurrDungeonScreen("lvl4.json");
    }
    @FXML
    void handleLvl5Button(ActionEvent event) throws IOException {
    	startCurrDungeonScreen("lvl5.json");
    }
    @FXML
    void handleLvl6Button(ActionEvent event) throws IOException {
    	startCurrDungeonScreen("lvl6.json");
    }
    @FXML
    void handleLvl7Button(ActionEvent event) throws IOException {
    	startCurrDungeonScreen("lvl7.json");
    }
    @FXML
    void handleLvl8Button(ActionEvent event) throws IOException {
    	startCurrDungeonScreen("lvl8.json");
    }
    @FXML
    void handleLvl9Button(ActionEvent event) throws IOException {
    	startCurrDungeonScreen("lvl9.json");
    }
    
    public void restartCurrentLevel() throws IOException {
    	String level = getDungeonLevel();
        DungeonScreen dungeonScreen = new DungeonScreen(stage,level);
        dungeonScreen.getController().setMenuScreen(menuScreen);
        this.dungeonScreen = dungeonScreen;
    	dungeonScreen.start();
    }
    
    public void levelCompletedUpdate() {
    	String level = getDungeonLevel();
    	level = level.split("[.]")[0];
    	level = level.split("[l]")[2];
    	for (Button b: buttons) {
    		if (b.getText().equals(level)) {
    			b.setStyle("-fx-background-color: #90ee90");
    		}
    	}
    }
    
    private void startCurrDungeonScreen(String filename) throws IOException {
        DungeonScreen dungeonScreen = new DungeonScreen(stage,filename);
        dungeonScreen.getController().setMenuScreen(menuScreen);
        this.dungeonScreen = dungeonScreen;
    	dungeonScreen.start();
    }

    public void setButtons() {
    	buttons = new ArrayList<Button>();
    	buttons.add(lvl1Button);
    	buttons.add(lvl2Button);
    	buttons.add(lvl3Button);
    	buttons.add(lvl4Button);
    	buttons.add(lvl5Button);
    	buttons.add(lvl6Button);
    	buttons.add(lvl7Button);
    	buttons.add(lvl8Button);
    	buttons.add(lvl9Button);
    }
    
    public void setDungeonScreen(DungeonScreen dungeonScreen) {
        this.dungeonScreen = dungeonScreen;
    }
    
    public void setControlsScreen(ControlsScreen controlsScreen) {
        this.controlsScreen = controlsScreen;
    }
    
    public void setStage(Stage stage) {
    	this.stage = stage;
    }
    
    public void setScreen(MenuScreen screen) {
    	this.menuScreen = screen;
    }
    
    public String getDungeonLevel() {
    	return dungeonScreen.getLevel();
    }

}

