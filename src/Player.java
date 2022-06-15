package unsw.dungeon;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;

/**
 * The player entity
 * @author Robert Clifton-Everest
 *
 */
public class Player extends Entity {

	/*
	 * Attributes
	 */
    private Dungeon dungeon;
    
    private String direction;
    private IntegerProperty sword;
    private IntegerProperty treasure;
    private BooleanProperty key;
    private BooleanProperty invincible;
    

    /**
     * Create a player positioned in square (x,y)
     * @param x
     * @param y
     */
    public Player(Dungeon dungeon, int x, int y) {
        super(x, y, "player", true);
        this.dungeon = dungeon;
        this.direction = "up";
        this.sword = new SimpleIntegerProperty(0);
        this.treasure = new SimpleIntegerProperty(0);
        this.key = new SimpleBooleanProperty(false);
        this.invincible = new SimpleBooleanProperty(false);
    }
    
    /*
     * Player movement and direction
     */
    public void moveUp() {
        if (getY() > 0) {
        	if (!obstaclePresent(getX(), getY()-1)) {
        		y().set(getY() - 1);
        		interactWithEntity();
        		dungeon.checkExitCompleted();
        	}
        }
    	faceUp();
    }

    public void moveLeft() {
        if (getX() > 0) {
        	if (!obstaclePresent(getX()-1, getY())) {
        		x().set(getX() - 1);
        		interactWithEntity();
        		dungeon.checkExitCompleted();
        	}
        }
        faceLeft();
    }
        
    public void moveDown() {
        if (getY() < dungeon.getHeight() - 1) {
        	if (!obstaclePresent(getX(), getY()+1)) {
    			y().set(getY() + 1);
        		interactWithEntity();
        		dungeon.checkExitCompleted();
        	}
        }
        faceDown();
    }

    public void moveRight() {
        if (getX() < dungeon.getWidth() - 1) {
        	if (!obstaclePresent(getX()+1, getY())) {
        		x().set(getX() + 1);
        		interactWithEntity();
        		dungeon.checkExitCompleted();
        	}
        }
        faceRight();
    }
    
    public void faceUp() {
    	direction = "up";
    }
    
    public void faceLeft() {
    	direction = "left";
    }
    
    public void faceDown() {
    	direction = "down";
    }
    
    public void faceRight() {
    	direction = "right";
    }
    
    /*
     * Initiate interaction with entities on current grid
     */
    private void interactWithEntity() {
    	for (Entity e: dungeon.entitiesHere(getX(), getY())) {
        	e.playerCollision(this);
    	}
    }
    
    /*
     * Player actions
     */
    // Uses sword to attack an enemy
    public void attack() {
    	if (sword.get() > 0) {
    		sword.set(sword.get()-1);
    		int x = getX();
    		int y = getY();
    		if (direction.equals("up")) {
    			if (y > 0) {
    				y -= 1;
    			}
    		}
    		else if (direction.equals("left")) {
    			if (x > 0) {
    				x -= 1;
    			}
    		}
    		else if (direction.equals("down")) {
    			if (y < dungeon.getHeight()-1) {
    				y += 1;
    			}
    		}
			else if (direction.equals("right")) {
    			if (x < dungeon.getWidth()-1) {
    				x += 1;
    			}
			}
        	for (Entity e: dungeon.entitiesHere(x, y)) {
        		if (e.getName().equals("enemy") || e.getName().equals("enemyGhost")) {
        			e.deactivate();
        			e.notifyObservers();
        			dungeon.checkEnemiesCompleted();
        		}
        	}
    	}
    }
    
    // Opens a door
    public void openDoor() {
    	if (key.get()) {
    		int x = getX();
    		int y = getY();
    		if (direction.equals("up")) {
    			if (y > 0) {
    				y -= 1;
    			}
    		}
    		else if (direction.equals("left")) {
    			if (x > 0) {
    				x -= 1;
    			}
    		}
    		else if (direction.equals("down")) {
    			if (y < dungeon.getHeight()-1) {
    				y += 1;
    			}
    		}
    		else if (direction.equals("right")) {
    			if (x < dungeon.getWidth()-1) {
    				x += 1;
    			}
    		}
	    	for (Entity e: dungeon.entitiesHere(x, y)) {
	    		if (e.getName().equals("door")) {
	    			e.deactivate();
	    			e.notifyObservers();
	    			key.set(false);
	    			break;
	    		}
	    	}
    	}
    }
    
    // Pushes a boulder
    public void push() {
    	int x = getX();
    	int y = getY();
    	String dir = "";
    	if (direction.equals("up")) {
    		for (Entity e: dungeon.entitiesHere(getX(), getY()-2)) {
    			if (e.isObstacle() || e.getName().equals("enemy")) {
    				return;
    			}
    		}
    		if (y > 1) {
    			y -= 1;
    		}
    		dir = "up";
    	}
    	else if (direction.equals("left")) {
    		for (Entity e: dungeon.entitiesHere(getX()-2, getY())) {
    			if (e.isObstacle() || e.getName().equals("enemy")) {
    				return;
    			}
    		}
    		if (x > 1) {
    			x -= 1;
    		}
			dir = "left";
    	}
    	else if (direction.equals("down")) {
    		for (Entity e: dungeon.entitiesHere(getX(), getY()+2)) {
    			if (e.isObstacle() || e.getName().equals("enemy")) {
    				return;
    			}
    		}
    		if (y < dungeon.getHeight()-2) {
    			y += 1;
    		}
    		dir = "down";
    	}
    	else if (direction.equals("right")) {
    		for (Entity e: dungeon.entitiesHere(getX()+2, getY())) {
    			if (e.isObstacle() || e.getName().equals("enemy")) {
    				return;
    			}
    		}
			if (x < dungeon.getWidth()-2) {
    			x += 1;
    		}
			dir = "right";
       	}
		for (Entity e: dungeon.entitiesHere(x, y)) {
			if (e.getName().equals("boulder")) {
				e.move(dir);
	        	dungeon.getGoal().check(dungeon);
	        	dungeon.checkSwitchesCompleted();
			}
		}
    }
    
    public void teleport() {
    	for (Entity e: dungeon.getEntities()) {
    		if (e.getName().equals("portal") && (e.getX() != getX() || e.getY() != getY())) {
    			x().set(e.getX());
    			y().set(e.getY());
    			break;
    		}
    	}
    }
    
    /*
     * Setters (player obtaining items)
     */
    public void obtainSword() {
    	sword.set(5);;
    }
    
    public void obtainTreasure() {
    	treasure.set(treasure.get()+1);;
    }
    
    public void obtainKey() {
    	key.set(true);
    }
    
    public void obtainPotion() {
    	invincible.set(true);
    	CompletableFuture.delayedExecutor(5, TimeUnit.SECONDS).execute(() -> {
    		invincible.set(false);
		});
    }
    
    /*
     * Getters
     */
    public String getDirection() {
    	return direction;
    }
    
    public boolean isInvincible() {
    	return invincible.get();
    }
    
    public IntegerProperty getTreasure() {
    	return treasure;
    }
    
    public int numTreasure() {
    	return treasure.get();
    }
    
    public Dungeon getDungeon() {
    	return dungeon;
    }
    
    public BooleanProperty getKey() {
    	return key;
    }
    
    public BooleanProperty getInvincible() {
    	return invincible;
    }
    
    /*
     * Check if player is carrying a certain item
     */
    public boolean hasSword() {
    	if (sword.get() > 0) {
    		return true;
    	}
    	return false;
    }
    
    public IntegerProperty getSword() {
    	return sword;
    }
    
    public boolean hasKey() {
    	return key.get();
    }
    
    /*
     * Returns true if obstacle is present at a certain position
     * Else returns false
     */
    public boolean obstaclePresent(int x, int y) {
    	for (Entity e: dungeon.entitiesHere(x, y)) {
    		if (e.isObstacle()) {
    			return true;
    		}
    	}
    	return false;
    }
    
}
