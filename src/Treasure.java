package unsw.dungeon;

public class Treasure extends Entity {
	
    public Treasure(int x, int y) {
        super(x, y, "treasure", true);
    }
    
    @Override
    public void playerCollision(Player player) {
		player.obtainTreasure();
		this.deactivate();
		notifyObservers();
    }
    
}
