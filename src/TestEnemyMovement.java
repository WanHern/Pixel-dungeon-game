package unsw.dungeon;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.Test;

class TestEnemyMovement {

	/*
	 * 
	 * pl  x   e
	 * x   x   po
	 * x   x   x
	 * 
	 */
	
	@Test
	void test() {
		List<String> goals = new ArrayList<String>();
		Dungeon dungeon = new Dungeon(3, 3, goals);
		Enemy enemy = new Enemy(2, 0);
		Potion potion = new Potion(2, 1);
		enemy.registerObserver(dungeon);
		potion.registerObserver(dungeon);
		dungeon.addEntity(enemy);
		dungeon.addEntity(potion);

		Player player = new Player(dungeon, 0, 0);
		dungeon.setPlayer(player);
	
		assertEquals(2, enemy.getX());
		assertEquals(0, enemy.getY());
	
		/*
		 * 
		 * pl  e   x
		 * x   x   po
		 * x   x   x
		 * 
		 */
		
		// After 1 second, enemy moves left towards player
    	CompletableFuture.delayedExecutor(1, TimeUnit.SECONDS).execute(() -> {
    		assertEquals(1, enemy.getX());
    		assertEquals(0, enemy.getY());
        	player.moveDown();
    	});
    	
		/*
		 * 
		 * e   x   x
		 * pl  x   po
		 * x   x   x
		 * 
		 */
    	    	
		// After 2 seconds, enemy moves left towards player
    	CompletableFuture.delayedExecutor(2, TimeUnit.SECONDS).execute(() -> {
    		assertEquals(0, enemy.getX());
    		assertEquals(0, enemy.getY());
    		player.moveRight();
    	});
    	
		/*
		 * 
		 * x   e   x
		 * e   pl  po
		 * x   x   x
		 * 
		 */
    	
		// After 3 seconds, enemy moves down towards player
    	CompletableFuture.delayedExecutor(2, TimeUnit.SECONDS).execute(() -> {
    		assertEquals(0, enemy.getX());
    		assertEquals(1, enemy.getY());
    		player.moveRight();
    	});
	
		/*
		 * 
		 * x   x   x
		 * x   e   pl
		 * x   x   x
		 * 
		 */
    	
		// After 4 seconds, enemy moves right towards player
    	// Player picks up invincibility potion
    	CompletableFuture.delayedExecutor(2, TimeUnit.SECONDS).execute(() -> {
    		assertEquals(1, enemy.getX());
    		assertEquals(1, enemy.getY());
    		player.moveUp();
    	});
    	
		/*
		 * 
		 * x   x   pl
		 * e   x   x
		 * x   x   x
		 * 
		 */

    	// Player is invincible
		// After 5 seconds, enemy moves left away from player
    	CompletableFuture.delayedExecutor(2, TimeUnit.SECONDS).execute(() -> {
    		assertEquals(0, enemy.getX());
    		assertEquals(1, enemy.getY());
    	});
    	
		/*
		 * 
		 * x   x   pl
		 * x   x   x
		 * e   x   x
		 * 
		 */
    	
    	// Player is invincible
		// After 6 seconds, enemy moves down away from player
    	CompletableFuture.delayedExecutor(2, TimeUnit.SECONDS).execute(() -> {
    		assertEquals(0, enemy.getX());
    		assertEquals(2, enemy.getY());
    	});
    	
	}

}
