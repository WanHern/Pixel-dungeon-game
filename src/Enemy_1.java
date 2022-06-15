package unsw.dungeon;

public class Enemy extends Entity {
	
    public Enemy(int x, int y) {
        super(x, y, "enemy", true);
    }
    
    @Override
    public void playerCollision(Player player) {
    	if (player.isInvincible()) {
    		this.deactivate();
    		notifyObservers();
    	}
    	else {
    		player.deactivate();
    	}
    }
    
    @Override
    public void startMoving(Dungeon dungeon) {
        Thread enemyMovement = new Thread(new EnemyAutoMovement(dungeon, this, "basic"));
        enemyMovement.start();
    }
    
}
