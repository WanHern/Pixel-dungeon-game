package unsw.dungeon;

public class Portal extends Entity {
	
    public Portal(int x, int y) {
        super(x, y, "portal", false);
    }
    
    @Override
    public void playerCollision(Player player) {
		player.teleport();
    }
    
}