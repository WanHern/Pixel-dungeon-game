package unsw.dungeon;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

/**
 * A JavaFX controller for the dungeon.
 * @author Robert Clifton-Everest
 *
 */
public class DungeonController {
	
    @FXML
    private GridPane squares;
    @FXML
    private HBox utility;
    @FXML
    private HBox info;
    @FXML
    private Label victory;
    @FXML
    private Label swordCount;
    @FXML
    private Label keyCount;
    @FXML
    private Label treasureCount;
    @FXML
    private Label logic;
    @FXML
    private Label subgoal1;
    @FXML
    private Label subgoal2;
    @FXML
    private Label subgoal3;
    @FXML
    private Label subgoal4;
    @FXML
    private Button menuButton;
    @FXML
    private Button restartButton;

    private MenuScreen menuScreen;
    
    private List<ImageView> initialEntities;

    private Player player;

    private Dungeon dungeon;

    /*
     * Constructor
     */
    public DungeonController(Dungeon dungeon, List<ImageView> initialEntities) {
        this.dungeon = dungeon;
        this.player = dungeon.getPlayer();
        this.initialEntities = new ArrayList<>(initialEntities);
    }
    
    @FXML
    public void handleMenuButton(ActionEvent event) {
        menuScreen.start();
    }
    
    @FXML
    public void handleRestartButton(ActionEvent event) throws IOException {
        menuScreen.restartCurrentLevel();
    }

    public void setMenuScreen(MenuScreen menuScreen) {
        this.menuScreen = menuScreen;
    }
    
    public void levelCompletedUpdate() {
    	menuScreen.levelCompletedUpdate();
    }

    @FXML
    public void initialize() {
        Image ground = new Image("/dirt_0_new.png");

        // Add the ground first so it is below all other entities
        for (int x = 0; x < dungeon.getWidth(); x++) {
            for (int y = 0; y < dungeon.getHeight(); y++) {
                squares.add(new ImageView(ground), x, y);
            }
        }

        // Add entities
        for (ImageView entity : initialEntities)
            squares.getChildren().add(entity);

        
        /*
        Image swordImage = new Image("/greatsword_1_new.png");
        ImageView swordView = new ImageView(swordImage);
        inventoryHBox.getChildren().add(swordView);
        */
        
        utility.setStyle("-fx-background-color: #000000");
        info.setStyle("-fx-background-color: #000000");
        victory.setVisible(false);
        
        // Add listener for goals completed
        dungeon.getAllCompleted().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable,
            		Boolean oldValue, Boolean newValue) {
            	if (newValue) {
            		victory.setText("Victory! You have completed the level!");
                	victory.setTextFill(Color.web("yellow", 1));
            		victory.setVisible(true);
            		levelCompletedUpdate();
            	}
            	else {
            		victory.setVisible(false);
            	}
            }
        });
        
        // Add listener for sword, key, treasure for UI changes
        player.getSword().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable,
                    Number oldValue, Number newValue) {
            	swordCount.setText(" x"+newValue+" ");
            	swordCount.setTextFill(Color.web("#00ff00", 1));
            	CompletableFuture.delayedExecutor(1, TimeUnit.SECONDS).execute(() -> {
            		swordCount.setTextFill(Color.web("#ffffff", 1));
            	});
            }
        });
        
        player.getKey().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable,
            		Boolean oldValue, Boolean newValue) {
            	if (newValue) {
            		keyCount.setText(" x1 ");
            	}
            	else {
            		keyCount.setText(" x0 ");
            	}
            	keyCount.setTextFill(Color.web("#00ff00", 1));
            	CompletableFuture.delayedExecutor(1, TimeUnit.SECONDS).execute(() -> {
            		keyCount.setTextFill(Color.web("#ffffff", 1));
            	});
            }
        });
        
        player.getTreasure().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable,
            		Number oldValue, Number newValue) {
            	treasureCount.setText(" x"+newValue+" ");
            	treasureCount.setTextFill(Color.web("#00ff00", 1));
            	CompletableFuture.delayedExecutor(1, TimeUnit.SECONDS).execute(() -> {
            		treasureCount.setTextFill(Color.web("#ffffff", 1));
            	});
            }
        });
        
        // Add listener for game over when player dies
        player.getActivated().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable,
            		Boolean oldValue, Boolean newValue) {
            	if (!newValue) {
            		victory.setText("Defeat! Restart level to try again...");
                	victory.setTextFill(Color.web("#fc9b96", 1));
            		victory.setVisible(true);
            	}
            }
        });
        
        // Add goals in UI
        String goalLogic = dungeon.getGoal().getLogic();
        List<String> subgoals = dungeon.getSubgoals();
    	// Single goal, "and" logic
    	if (goalLogic.equals("") || goalLogic.equals("and")) {
    		logic.setText("Mission: Complete ALL of the following goals");
    	}
    	// "or" logic
    	else {
    		logic.setText("Mission: Complete ONE of the following goals");
    	}
    	int i = 1;
		for (i = 1; i < subgoals.size(); i++) {
			if (i == 1) {
				subgoal1.setText(subgoalText(subgoals.get(i)));
				attachSubgoalListener(subgoal1, subgoals.get(i));
			}
			else if (i == 2) {
				subgoal2.setText(subgoalText(subgoals.get(i)));
				attachSubgoalListener(subgoal2, subgoals.get(i));
			}
			else if (i == 3) {
				subgoal3.setText(subgoalText(subgoals.get(i)));
				attachSubgoalListener(subgoal3, subgoals.get(i));
			}
			else if (i == 4) {
				subgoal4.setText(subgoalText(subgoals.get(i)));
				attachSubgoalListener(subgoal4, subgoals.get(i));
			}
		}
        
        // Begin movement for enemies
        for (Entity e: dungeon.getEntities()) {
        	if (e != null) {
	        	if (e.getName().equals("enemy") || e.getName().equals("enemyGhost")) {
	        		e.startMoving(dungeon);
	        	}
        	}
        }
    }

    @FXML
    public void handleKeyPress(KeyEvent event) {
    	// Player can only control if he is alive
    	if (player.isActivated()) {
	        switch (event.getCode()) {
	        // Move up
	        case W:
	    		player.moveUp();
	            break;
	        // Move left
	        case A:
	        	player.moveLeft();
	        	break;
	        // Move down
	        case S:
	    		player.moveDown();
	            break;
	        // Move right
	        case D:
	    		player.moveRight();
	            break;
	        // Attack with sword
	        case K:
	        	player.attack();
	            break;
	        // Open door
	        case O:
	    		player.openDoor();
	            break;
	        // Push boulder
	        case P:
	        	player.push();
	            break;
	        default:
	            break;
	        }
    	}
    }

    private String subgoalText(String subgoal) {
    	String txt = "";
    	if (subgoal.equals("enemies")) {
    		txt = "- Defeat all enemies";
    	}
    	else if (subgoal.equals("boulders")) {
    		txt = "- Activate all switches";
    	}
    	else if (subgoal.equals("treasure")) {
    		txt = "- Obtain all treasure";
    	}
    	else if (subgoal.equals("exit")) {
    		txt = "- Get to the exit";
    	}
    	return txt;
    }
    
	private void attachSubgoalListener(Label label, String subgoal) {
		ChangeListener<Boolean> cl = new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable,
            		Boolean oldValue, Boolean newValue) {
            	if (newValue) {
            		label.setTextFill(Color.web("#00ff00", 1));
            	}
            	else {
            		label.setTextFill(Color.web("#ffffff", 1));
            	}
            }
		};
    	if (subgoal.equals("enemies")) {
    		for (GoalChecker gc: dungeon.getGoal().getGoals()) {
    			if (gc.getName().equals("enemies")) {
    		        dungeon.getEnemiesCompleted().addListener(cl);
    		        break;
		        }
    		}
    	}
    	else if (subgoal.equals("boulders")) {
    		for (GoalChecker gc: dungeon.getGoal().getGoals()) {
    			if (gc.getName().equals("switches")) {
    		        dungeon.getSwitchesCompleted().addListener(cl);
    		        break;
		        }
    		}
    	}
    	else if (subgoal.equals("treasure")) {
    		for (GoalChecker gc: dungeon.getGoal().getGoals()) {
    			if (gc.getName().equals("treasure")) {
    		        dungeon.getTreasureCompleted().addListener(cl);
    		        break;
		        }
    		}
    	}
    	else if (subgoal.equals("exit")) {
    		for (GoalChecker gc: dungeon.getGoal().getGoals()) {
    			if (gc.getName().equals("exit")) {
    		        dungeon.getExitCompleted().addListener(cl);
    		        break;
		        }
    		}
    	}
	}

}

