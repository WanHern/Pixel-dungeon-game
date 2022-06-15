package unsw.dungeon;

import java.util.ArrayList;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;

/**
 * An entity in the dungeon.
 * @author Robert Clifton-Everest
 *
 */
public class Entity implements Subject {
	
	/*
	 * Attributes
	 */
	ArrayList<Observer> observers = new ArrayList<Observer>();
	
    // IntegerProperty is used so that changes to the entities position can be
    // externally observed.
    private IntegerProperty x, y;
    private String name;
    private BooleanProperty activated;

    /**
     * Create an entity positioned in square (x,y)
     * @param x
     * @param y
     */
    public Entity(int x, int y, String name, boolean activated) {
        this.x = new SimpleIntegerProperty(x);
        this.y = new SimpleIntegerProperty(y);
        this.name = name;
        this.activated = new SimpleBooleanProperty(activated);
    }
    
    /*
     * Subject-related methods
     */
	@Override
	public void registerObserver(Observer o) {
		if(! observers.contains(o)) { observers.add(o); }
	}

	@Override
	public void removeObserver(Observer o) {
		observers.remove(o);
	}

	@Override
	public void notifyObservers() {
		for( Observer obs : observers) {
			obs.update(this);
		}
	}

    public IntegerProperty x() {
        return x;
    }

    public IntegerProperty y() {
        return y;
    }

	/*
	 * Getters
	 */
    public int getX() {
        return x().get();
    }
    
    public int getY() {
        return y().get();
    }
    
    public String getName() {
    	return name;
    }
    
    public BooleanProperty getActivated() {
    	return activated;
    }
    
    /*
     * Actions
     */
    // To be overwritten by classes extending Entity if needed
    public void playerCollision(Player player) {}
    
    // (Up, left, down, right) Movement for enemies and boulders
    public void move(String direction) {
    	if (direction.equals("up")) {
    		y().set(getY()-1);
    	}
    	else if (direction.equals("left")) {
    		x().set(getX()-1);
    	}
    	else if (direction.equals("down")) {
    		y().set(getY()+1);
    	}
    	else if (direction.equals("right")) {
    		x().set(getX()+1);
    	}
    }
    
    // To be overriden by enemy
    public void startMoving(Dungeon dungeon) {}
    
    /*
     * State-changers
     * Used for enemies and player
     */
    public void activate() {
    	activated.set(true);
    }
    
    public void deactivate() {
    	activated.set(false);
    }
    
    /*
     * Checks if entity is an obstacle
     */
    public boolean isObstacle() {
    	if (name.equals("wall") || name.equals("boulder") || name.equals("door")) {
    		return true;
    	}
    	return false;
    }
    
    /*
     * Returns true if entity is activated,
     * false otherwise
     */
    public boolean isActivated() {
    	return activated.get();
    }
    
}
