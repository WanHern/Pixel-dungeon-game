package unsw.dungeon;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

class TestTreasure {

	/*
	 * 
	 * x  x  t
	 * x  x  x
	 * t  x  x
	 * 
	 */
	
	@Test
	void test() {
		List<String> goals = new ArrayList<String>();
		Dungeon dungeon = new Dungeon(3, 3, goals);
		Treasure treasure1 = new Treasure(0, 2);
		Treasure treasure2 = new Treasure(2, 0);
		treasure1.registerObserver(dungeon);
		treasure2.registerObserver(dungeon);
		
		dungeon.addEntity(treasure1);
		dungeon.addEntity(treasure2);
		
		Player player = new Player(dungeon, 0, 0);
		dungeon.setPlayer(player);
		
		assertEquals(0, player.getTreasure());
		
		player.moveRight();
		player.moveRight();
		
		assertEquals(1, player.getTreasure());

		player.moveDown();
		player.moveDown();
		player.moveLeft();
		player.moveLeft();
		
		assertEquals(2, player.getTreasure());
		
		player.moveUp();
		player.moveDown();
		
		assertEquals(2, player.getTreasure());
	}

}
