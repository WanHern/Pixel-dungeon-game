package unsw.dungeon;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

class TestSword {

	/*
	 * 
	 * x  x  x
	 * s  x  e
	 * s  x  x
	 * 
	 */
	
	@Test
	void test() {
		List<String> goals = new ArrayList<String>();
		Dungeon dungeon = new Dungeon(3, 3, goals);
		Sword sword1 = new Sword(0, 1);
		Sword sword2 = new Sword(0, 2);
		sword1.registerObserver(dungeon);
		sword2.registerObserver(dungeon);
		Enemy enemy = new Enemy(2, 1);
		enemy.registerObserver(dungeon);
		
		dungeon.addEntity(sword1);
		dungeon.addEntity(sword2);
		dungeon.addEntity(enemy);
		
		Player player = new Player(dungeon, 0, 0);
		dungeon.setPlayer(player);

		assertEquals(false, player.hasSword());
		
		player.moveDown();
		
		assertEquals(true, player.hasSword());
		
		player.moveDown();
		player.moveUp();
		
		player.attack();
		player.attack();
		
		// Edge case: attack on position outside of dungeon
		player.moveLeft();
		player.attack();
		
		player.moveRight();
		
		player.attack();

		assertEquals(true, player.hasSword());
		
		player.moveLeft();

		player.attack();
		
		assertEquals(false, player.hasSword());
		
		player.moveRight();
		player.moveLeft();
		
		assertEquals(false, player.hasSword());
		
		player.moveDown();
		
		assertEquals(true, player.hasSword());

	}

}
