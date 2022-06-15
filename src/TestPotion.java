package unsw.dungeon;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.Test;

class TestPotion {

	/*
	 * 
	 * x  x  p
	 * x  x  x
	 * p  x  e
	 * 
	 */
	
	@Test
	void test() {
		List<String> goals = new ArrayList<String>();
		Dungeon dungeon = new Dungeon(3, 3, goals);
		Potion potion1 = new Potion(0, 2);
		Potion potion2 = new Potion(2, 0);
		potion1.registerObserver(dungeon);
		potion2.registerObserver(dungeon);
		Enemy enemy = new Enemy(2, 2);
		
		dungeon.addEntity(potion1);
		dungeon.addEntity(potion2);
		dungeon.addEntity(enemy);
		
		Player player = new Player(dungeon, 0, 0);
		dungeon.setPlayer(player);
		
		assertEquals(false, player.isInvincible());
		
		player.moveDown();
		player.moveDown();
		
		assertEquals(true, player.isInvincible());
		
		// Wait for 6 seconds before player moves right
    	CompletableFuture.delayedExecutor(6, TimeUnit.SECONDS).execute(() -> {
    		assertEquals(false, player.isInvincible());
    		
        	player.moveRight();
    		player.moveRight();
        	
        	// Player "dies"
        	assertEquals(false, player.isActivated());
        	// Enemy still "alive"
        	assertEquals(true, enemy.isActivated());
        	
        	player.moveUp();
        	player.moveUp();
        	
    		assertEquals(true, player.isInvincible());

        	player.moveDown();
        	player.moveDown();
        	
        	// Player "defeats" the enemy
        	assertEquals(true, player.isActivated());
        	// Enemy "dies"
        	assertEquals(false, enemy.isActivated());
        	
        	player.moveUp();
        	player.moveUp();
        	
    		assertEquals(false, player.isInvincible());
    	});
		
	}

}
