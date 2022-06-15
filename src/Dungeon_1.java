/**
 *
 */
package unsw.dungeon;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

/**
 * A dungeon in the interactive dungeon player.
 *
 * A dungeon can contain many entities, each occupy a square. More than one
 * entity can occupy the same square.
 *
 * @author Robert Clifton-Everest
 *
 */
public class Dungeon implements Observer {
	
	/*
	 * Attributes
	 */
	Subject subject;
	
    private int width, height;
    private List<Entity> entities;
    private Player player;
    private int initialTreasure;
    private int initialEnemies;
    private List<String> subgoals;
    private Goal goal;
    private BooleanProperty enemiesGoalCompleted;
    private BooleanProperty switchesGoalCompleted;
    private BooleanProperty treasureGoalCompleted;
    private BooleanProperty exitGoalCompleted;
    private BooleanProperty allGoalsCompleted;
    
    
	/*
	 * Constructor
	 */    
    public Dungeon(int width, int height, List<String> subgoals) {
        this.width = width;
        this.height = height;
        this.entities = new ArrayList<>();
        this.player = null;
        this.initialTreasure = 0;
        this.initialEnemies = 0;
        this.subgoals = subgoals;
        this.goal = null;
    	this.enemiesGoalCompleted = new SimpleBooleanProperty(false);
    	this.switchesGoalCompleted = new SimpleBooleanProperty(false);
    	this.treasureGoalCompleted = new SimpleBooleanProperty(false);
    	this.exitGoalCompleted = new SimpleBooleanProperty(false);
    	this.allGoalsCompleted = new SimpleBooleanProperty(false);
        initGoal();
    }
    
	/*
	 * Update method (Observer-related method)
	 * - Something needs to be updated
	 * - Checks if level completion goal has been met
	 * - Remove object from dungeon
	 */
	@Override
	public void update(Subject obj) {
		goal.check(this);
		// For UI
		checkEnemiesCompleted();
		checkTreasureCompleted();
		
		List<Entity> copy = new ArrayList<Entity>(entities);
		copy.remove(obj);
		entities = copy;
	}

	/*
	 * Getters
	 */
    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Player getPlayer() {
        return player;
    }
    
    public List<Entity> getEntities() {
    	return entities;
    }
    
    public Goal getGoal() {
    	return goal;
    }
    
    public List<String> getSubgoals() {
    	return subgoals;
    }
    
    public int getInitialTreasure() {
    	return initialTreasure;
    }
    
    public int getInitialEnemies() {
    	return initialEnemies;
    }
    
    public int getEnemies() {
    	int count = 0;
    	for (Entity e: entities) {
    		if ((e.getName().equals("enemy") || e.getName().equals("enemyGhost")) && e.isActivated()) {
    			count++;
    		}
    	}
    	return count;
    }
    
    /*
     * Setters
     */
    public void setPlayer(Player player) {
        this.player = player;
    }

    public void addEntity(Entity entity) {
        entities.add(entity);
        if (entity != null) {
	        if (entity.getName().equals("treasure")) {
	        	initialTreasure++;
	        }
	        else if (entity.getName().equals("enemy") || entity.getName().equals("enemyGhost")) {
	        	initialEnemies++;
	        }
        }
    }
    
    public void removeEntity(Entity entity) {
    	entities.remove(entity);
    }
    
	// Initialise goal
    public void initGoal() {
    	// Find logic
    	String logic = "";
    	for (String g: subgoals) {
    		if (g.equals("AND")) {
    			logic = "and";
    		}
    		else if (g.equals("OR")) {
    			logic = "or";
    		}
    	}
    	
    	this.goal = new Goal(logic, this);
    	
    	// Add subgoals
    	for (String s: subgoals) {
    		if (s.equals("enemies")) {
    			CheckEnemies newEnemyCheck = new CheckEnemies();
    			goal.addSubgoal(newEnemyCheck);
    		} else if (s.equals("treasure")) {
    			CheckTreasure newTreasureCheck = new CheckTreasure();
    			goal.addSubgoal(newTreasureCheck);
    		} else if (s.equals("boulders")) {
    			CheckSwitches newSwitchCheck = new CheckSwitches();
    			goal.addSubgoal(newSwitchCheck);
    		} else if (s.equals("exit")) {
    			CheckExit newExitCheck = new CheckExit();
    			goal.addSubgoal(newExitCheck);
    		}
    	}
	}
    
    public void checkEnemiesCompleted() {
    	for (GoalChecker gc: goal.getGoals()) {
    		if (gc.getName().equals("enemies")) {
    			if (gc.check(this)) {
    		    	enemiesGoalCompleted.set(true);
    			}
    			else {
    		    	enemiesGoalCompleted.set(false);
    			}
    			break;
    		}
    	}
    }
    
    public BooleanProperty getEnemiesCompleted() {
    	return enemiesGoalCompleted;
    }
    
    public void checkSwitchesCompleted() {
    	for (GoalChecker gc: goal.getGoals()) {
    		if (gc.getName().equals("switches")) {
    			if (gc.check(this)) {
    		    	switchesGoalCompleted.set(true);
    			}
    			else {
    				switchesGoalCompleted.set(false);
    			}
    			break;
    		}
    	}
    }
    
    public BooleanProperty getSwitchesCompleted() {
    	return switchesGoalCompleted;
    }
    
    public void checkTreasureCompleted() {
    	for (GoalChecker gc: goal.getGoals()) {
    		if (gc.getName().equals("treasure")) {
    			if (gc.check(this)) {
    		    	treasureGoalCompleted.set(true);
    			}
    			else {
    				treasureGoalCompleted.set(false);
    			}
    			break;
    		}
    	}
    }
    
    public BooleanProperty getTreasureCompleted() {
    	return treasureGoalCompleted;
    }
    
    public void checkExitCompleted() {
    	for (GoalChecker gc: goal.getGoals()) {
    		if (gc.getName().equals("exit")) {
    			if (gc.check(this)) {
    		    	exitGoalCompleted.set(true);
    			}
    			else {
    				exitGoalCompleted.set(false);
    			}
    			break;
    		}
    	}
    }
    
    public BooleanProperty getExitCompleted() {
    	return exitGoalCompleted;
    }
    
    public BooleanProperty getAllCompleted() {
    	return allGoalsCompleted;
    }
    
    /*
     * Returns a list of entities at a certain position
     */
    public List<Entity> entitiesHere(int x, int y) {
    	List<Entity> l = new ArrayList<Entity>();
    	for (Entity e: entities) {
    		if (e.getX() == x && e.getY() == y) {
    			l.add(e);
    		}
    	}
    	return l;
    }
    
}
