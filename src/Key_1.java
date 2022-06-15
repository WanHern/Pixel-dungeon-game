package unsw.dungeon;

public class Key extends Entity {
	
    public Key(int x, int y) {
        super(x, y, "key", true);
    }
    
    @Override
    public void playerCollision(Player player) {
    	if (!player.hasKey()) {
			player.obtainKey();
			this.deactivate();
			notifyObservers();
    	}
    }
    
}
