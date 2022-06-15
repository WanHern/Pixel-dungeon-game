package unsw.dungeon;

public class CheckExit implements GoalChecker {

	public boolean check(Dungeon dungeon) {
		int x = dungeon.getPlayer().getX();
		int y = dungeon.getPlayer().getY();
		for (Entity e: dungeon.entitiesHere(x, y)) {
			if (e.getName().equals("exit")) {
				return true;
			}
		}
		return false;
	}
	
	public String getName() {
		return "exit";
	}
	
}
