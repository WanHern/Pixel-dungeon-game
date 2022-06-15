package unsw.dungeon;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

class TestPortal {

	/*
	 * 
	 * x  x  p
	 * x  x  x
	 * p  x  x
	 * 
	 */
	
	@Test
	void test() {
		List<String> goals = new ArrayList<String>();
		Dungeon dungeon = new Dungeon(3, 3, goals);
		Portal portal1 = new Portal(0, 2);
		Portal portal2 = new Portal(2, 0);
		
		dungeon.addEntity(portal1);
		dungeon.addEntity(portal2);
		
		Player player = new Player(dungeon, 0, 0);
		dungeon.setPlayer(player);
		
		player.moveRight();
		assertEquals(1, player.getX());
		assertEquals(0, player.getY());
		
		player.moveRight();
		assertEquals(0, player.getX());
		assertEquals(2, player.getY());
		
		player.moveRight();
		player.moveLeft();
		assertEquals(2, player.getX());
		assertEquals(0, player.getY());
	}
}
