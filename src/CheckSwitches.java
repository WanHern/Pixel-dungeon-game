package unsw.dungeon;

public class CheckSwitches implements GoalChecker {
	
	public boolean check(Dungeon dungeon) {
    	boolean found = false;
    	for (Entity e: dungeon.getEntities()) {
    		if (e != null) {
    			if (e.getName().equals("floorSwitch")) {
    		    	found = false;
    		    	e.deactivate();
    				for (Entity extra: dungeon.entitiesHere(e.getX(), e.getY())) {
    					if (extra.getName().equals("boulder")) {
    						found = true;
    						e.activate();
    					}
    				}
    				if (found == false) {
    					break;
    				}
    			}
    		}
    	}
    	if (found) {
    	}
    	return found;
	}
	
	public String getName() {
		return "switches";
	}
	
}
