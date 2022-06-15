package unsw.dungeon;

public class CheckTreasure implements GoalChecker {

	public boolean check(Dungeon dungeon) {
		if (dungeon.getInitialTreasure() == dungeon.getPlayer().numTreasure()) {
			return true;
		}
		return false;
	}
	
	public String getName() {
		return "treasure";
	}
	
}
