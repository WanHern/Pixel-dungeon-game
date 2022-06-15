package unsw.dungeon;

public class EnemyGhost extends Entity {
	
    public EnemyGhost(int x, int y) {
        super(x, y, "enemyGhost", true);
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
        Thread enemyMovement = new Thread(new EnemyAutoMovement(dungeon, this, "ghost"));
        enemyMovement.start();
    }
    
}
