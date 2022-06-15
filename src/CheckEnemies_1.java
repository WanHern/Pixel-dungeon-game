package unsw.dungeon;

public class CheckEnemies implements GoalChecker {
		
	public boolean check(Dungeon dungeon) {
		if (dungeon.getEnemies() == 0) {
			return true;
		}
		return false;
	}
	
	public String getName() {
		return "enemies";
	}
	
}
