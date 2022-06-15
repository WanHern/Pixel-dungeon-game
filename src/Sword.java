package unsw.dungeon;

public class Sword extends Entity {
	
    public Sword(int x, int y) {
        super(x, y, "sword", true);
    }
    
    @Override
    public void playerCollision(Player player) {
    	if (!player.hasSword()) {
    		player.obtainSword();
    		this.deactivate();
    		notifyObservers();
    	}
    }
    
}
