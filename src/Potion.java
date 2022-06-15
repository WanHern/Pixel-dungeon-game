package unsw.dungeon;

public class Potion extends Entity {
	
    public Potion(int x, int y) {
        super(x, y, "potion", true);
    }
    
    @Override
    public void playerCollision(Player player) {
		player.obtainPotion();
		this.deactivate();
		notifyObservers();
    }
    
}
