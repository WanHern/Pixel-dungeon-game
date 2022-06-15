package unsw.dungeon;

public class Exit extends Entity {
	
    public Exit(int x, int y) {
        super(x, y, "exit", false);
    }
    
    @Override
    public void playerCollision(Player player) {
    	Dungeon dungeon = player.getDungeon();
		dungeon.getGoal().check(dungeon);
    }
    
}
