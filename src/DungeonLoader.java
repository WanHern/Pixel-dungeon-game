package unsw.dungeon;

import java.io.FileNotFoundException;
import java.io.FileReader;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;
import java.util.ArrayList;
import java.util.List;

/**
 * Loads a dungeon from a .json file.
 *
 * By extending this class, a subclass can hook into entity creation. This is
 * useful for creating UI elements with corresponding entities.
 *
 * @author Robert Clifton-Everest
 *
 */
public abstract class DungeonLoader {

    private static JSONObject json;

    public DungeonLoader(String filename) throws FileNotFoundException {
        json = new JSONObject(new JSONTokener(new FileReader("dungeons/" + filename)));
    }

    /**
     * Parses the JSON to create a dungeon.
     * @return
     */
    public Dungeon load() {
        int width = json.getInt("width");
        int height = json.getInt("height");

        List<String> goals = findGoals();
        
        Dungeon dungeon = new Dungeon(width, height, goals);

        JSONArray jsonEntities = json.getJSONArray("entities");

        for (int i = 0; i < jsonEntities.length(); i++) {
            loadEntity(dungeon, jsonEntities.getJSONObject(i));
        }
        
        return dungeon;
    }

    private void loadEntity(Dungeon dungeon, JSONObject json) {
        String type = json.getString("type");
        int x = json.getInt("x");
        int y = json.getInt("y");

        Entity entity = null;
        switch (type) {
        case "player":
            Player player = new Player(dungeon, x, y);
            dungeon.setPlayer(player);
            onLoad(player);
            entity = player;
            break;
        case "wall":
            Wall wall = new Wall(x, y);
            onLoad(wall);
            entity = wall;
            break;
        case "sword":
            Sword sword = new Sword(x, y);
            onLoad(sword);
            entity = sword;
            break;
        case "exit":
            Exit exit= new Exit(x, y);
            onLoad(exit);
            entity = exit;
            break;
        case "treasure":
            Treasure treasure = new Treasure(x, y);
            onLoad(treasure);
            entity = treasure;
            break;
        case "door":
            Door door = new Door(x, y);
            onLoad(door);
            entity = door;
            break;
        case "key":
            Key key = new Key(x, y);
            onLoad(key);
            entity = key;
            break;
        case "potion":
            Potion potion = new Potion(x, y);
            onLoad(potion);
            entity = potion;
            break;
        case "portal":
            Portal portal = new Portal(x, y);
            onLoad(portal);
            entity = portal;
            break;
        case "floorSwitch":
        	FloorSwitch floorSwitch = new FloorSwitch(x, y);
            onLoad(floorSwitch);
            entity = floorSwitch;
            break;
        case "boulder":
            Boulder boulder = new Boulder(x, y);
            onLoad(boulder);
            entity = boulder;
            break;
        case "enemy":
            Enemy enemy = new Enemy(x, y);
            onLoad(enemy);
            entity = enemy;
            break;
        case "enemyGhost":
            EnemyGhost ghost = new EnemyGhost(x, y);
            onLoad(ghost);
            entity = ghost;
            break;
        // TODO Handle other possible entities
        }
        if (entity != null) {
        	entity.registerObserver(dungeon);
        }
        dungeon.addEntity(entity);
    }
    
    // find out what the goals are for the dungeon
    public List<String> findGoals() {
    	
    	List<String> subgoals = new ArrayList<String>();
    	
    	// if multiple goals
    	JSONObject goalConditionJSON = json.getJSONObject("goal-condition");
    	if (goalConditionJSON.getString("goal").equals("AND") || 
    		goalConditionJSON.getString("goal").equals("OR")) {
    		subgoals.add(goalConditionJSON.getString("goal"));
    		JSONArray subgoalsJSON = goalConditionJSON.getJSONArray("subgoals");
        	for (int i = 0; i < subgoalsJSON.length(); i++) {
        		JSONObject currSubgoal = subgoalsJSON.getJSONObject(i);
        		// if currSubgoal has an AND or OR
        		subgoals.add(currSubgoal.getString("goal"));
        	}
    	// else loop through subgoals
    	} else {
    		subgoals.add(goalConditionJSON.getString("goal"));
    	}
    	
		//System.out.println(subgoals);
    	
    	return subgoals;
        
    }
    
   
    // find exit x-coord
    public int findExitX() {
    	JSONArray entities = json.getJSONArray("entities");
    	int x = 0;
    	
    	for (int i = 0; i < entities.length(); i++) {
    		JSONObject currEntity = entities.getJSONObject(i);
    		if (currEntity.getJSONObject("type").equals("exit")) {
    			x = currEntity.getInt("x");
    		}
    	}
    	
    	return x;
    }
    
    // find exit y-coord
    public int findExitY() {
    	JSONArray entities = json.getJSONArray("entities");
    	int y = 0;
    	
    	for (int i = 0; i < entities.length(); i++) {
    		JSONObject currEntity = entities.getJSONObject(i);
    		if (currEntity.getJSONObject("type").equals("exit")) {
    			y = currEntity.getInt("y");
    		}
    	}
    	
    	return y;
    }

    public abstract void onLoad(Player player);

    public abstract void onLoad(Wall wall);

    public abstract void onLoad(Sword sword);

    public abstract void onLoad(Exit exit);

    public abstract void onLoad(Treasure treasure);

    public abstract void onLoad(Door door);

    public abstract void onLoad(Key key);

    public abstract void onLoad(Potion potion);

    public abstract void onLoad(Portal portal);

    public abstract void onLoad(FloorSwitch floorSwitch);

    public abstract void onLoad(Boulder boulder);

    public abstract void onLoad(Enemy enemy);

    public abstract void onLoad(EnemyGhost ghost);

    // TODO Create additional abstract methods for the other entities

}
