package unsw.dungeon;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

/**
 * A DungeonLoader that also creates the necessary ImageViews for the UI,
 * connects them via listeners to the model, and creates a controller.
 * @author Robert Clifton-Everest
 *
 */
public class DungeonControllerLoader extends DungeonLoader {

    private List<ImageView> entities;

    //Images
    private Image playerImage;
    private Image playerInvImage;
    private Image wallImage;
    private Image swordImage;
    private Image exitImage;
    private Image treasureImage;
    private Image doorImage;
    private Image openDoorImage;
    private Image keyImage;
    private Image potionImage;
    private Image portalImage;
    private Image switchImage;
    private Image boulderImage;
    private Image enemyImage;
    private Image ghostImage;
    private Image bloodImage;
    private Image swingSwordImage;
    
    public DungeonControllerLoader(String filename)
            throws FileNotFoundException {
        super(filename);
        entities = new ArrayList<>();
        playerImage = new Image("/human.png");
        playerInvImage = new Image("/human_inv.png");
        wallImage = new Image("/brick_brown_0.png");
        swordImage = new Image("/greatsword_1_new.png");
        exitImage = new Image("/exit.png");
        treasureImage = new Image("/gold_pile.png");
        doorImage = new Image("/closed_door.png");
        openDoorImage = new Image("/open_door.png");
        keyImage = new Image("/key.png");
        potionImage = new Image("/brilliant_blue_new.png");
        portalImage = new Image("/portal.png");
        switchImage = new Image("/pressure_plate.png");
        boulderImage = new Image("/boulder.png");
        enemyImage = new Image("/gnome.png");
        ghostImage = new Image("/ghost.png");
        bloodImage = new Image("/blood.png");
        swingSwordImage = new Image("/swing_sword.png");
    }

    @Override
    public void onLoad(Player player) {
        ImageView view = new ImageView(playerImage);
        player.getSword().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable,
                    Number oldValue, Number newValue) {
            	if (newValue.intValue() < 5) {
                	view.setImage(swingSwordImage);
	            	CompletableFuture.delayedExecutor(100, TimeUnit.MILLISECONDS).execute(() -> {
	                	view.setImage(playerImage);
	            	});
            	}
            }
        });
        player.getInvincible().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable,
            		Boolean oldValue, Boolean newValue) {
            	if (newValue) {
                	view.setImage(playerInvImage);
	            	CompletableFuture.delayedExecutor(5, TimeUnit.SECONDS).execute(() -> {
	                	view.setImage(playerImage);
	            	});
            	}
            }
        });
        addEntity(player, view);
    }

    @Override
    public void onLoad(Wall wall) {
        ImageView view = new ImageView(wallImage);
        addEntity(wall, view);
    }
    
    @Override
    public void onLoad(Sword sword) {
        ImageView view = new ImageView(swordImage);
        sword.getActivated().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable,
            		Boolean oldValue, Boolean newValue) {
            	if (!newValue) {
            		view.visibleProperty().set(false);
            	}
            }
        });
        addEntity(sword, view);
    }

    @Override
    public void onLoad(Exit exit) {
        ImageView view = new ImageView(exitImage);
        addEntity(exit, view);
    }
    
    @Override
    public void onLoad(Treasure treasure) {
        ImageView view = new ImageView(treasureImage);
        treasure.getActivated().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable,
            		Boolean oldValue, Boolean newValue) {
            	if (!newValue) {
            		view.visibleProperty().set(false);
            	}
            }
        });
        addEntity(treasure, view);
    }
    
    @Override
    public void onLoad(Door door) {
        ImageView view = new ImageView(doorImage);
        door.getActivated().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable,
            		Boolean oldValue, Boolean newValue) {
            	if (!newValue) {
            		view.setImage(openDoorImage);
            	}
            }
        });
        addEntity(door, view);
    }
    
    @Override
    public void onLoad(Key key) {
        ImageView view = new ImageView(keyImage);
        key.getActivated().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable,
            		Boolean oldValue, Boolean newValue) {
            	if (!newValue) {
            		view.visibleProperty().set(false);
            	}
            }
        });
        addEntity(key, view);
    }
    
    @Override
    public void onLoad(Potion potion) {
        ImageView view = new ImageView(potionImage);
        potion.getActivated().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable,
            		Boolean oldValue, Boolean newValue) {
            	if (!newValue) {
            		view.visibleProperty().set(false);
            	}
            }
        });
        addEntity(potion, view);
    }
    
    @Override
    public void onLoad(Portal portal) {
        ImageView view = new ImageView(portalImage);
        addEntity(portal, view);
    }
    
    @Override
    public void onLoad(FloorSwitch floorSwitch) {
        ImageView view = new ImageView(switchImage);
        addEntity(floorSwitch, view);
    }
    
    @Override
    public void onLoad(Boulder boulder) {
        ImageView view = new ImageView(boulderImage);
        addEntity(boulder, view);
    }
    
    @Override
    public void onLoad(Enemy enemy) {
        ImageView view = new ImageView(enemyImage);
        enemy.getActivated().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable,
            		Boolean oldValue, Boolean newValue) {
            	if (!newValue) {
            		view.setImage(bloodImage);
                	CompletableFuture.delayedExecutor(1, TimeUnit.SECONDS).execute(() -> {
                		view.visibleProperty().set(false);
                	});
            	}
            }
        });
        addEntity(enemy, view);
    }
    
    @Override
    public void onLoad(EnemyGhost ghost) {
        ImageView view = new ImageView(ghostImage);
        ghost.getActivated().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable,
            		Boolean oldValue, Boolean newValue) {
            	if (!newValue) {
            		view.setImage(bloodImage);
                	CompletableFuture.delayedExecutor(1, TimeUnit.SECONDS).execute(() -> {
                		view.visibleProperty().set(false);
                	});
            	}
            }
        });
        addEntity(ghost, view);
    }
    
    private void addEntity(Entity entity, ImageView view) {
        trackPosition(entity, view);
        entities.add(view);
    }

    /**
     * Set a node in a GridPane to have its position track the position of an
     * entity in the dungeon.
     *
     * By connecting the model with the view in this way, the model requires no
     * knowledge of the view and changes to the position of entities in the
     * model will automatically be reflected in the view.
     * @param entity
     * @param node
     */
    private void trackPosition(Entity entity, Node node) {
        GridPane.setColumnIndex(node, entity.getX());
        GridPane.setRowIndex(node, entity.getY());
        entity.x().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable,
                    Number oldValue, Number newValue) {
                GridPane.setColumnIndex(node, newValue.intValue());
            }
        });
        entity.y().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable,
                    Number oldValue, Number newValue) {
                GridPane.setRowIndex(node, newValue.intValue());
            }
        });
    }

    /**
     * Create a controller that can be attached to the DungeonView with all the
     * loaded entities.
     * @return
     * @throws FileNotFoundException
     */
    public DungeonController loadController() throws FileNotFoundException {
        return new DungeonController(load(), entities);
    }


}
