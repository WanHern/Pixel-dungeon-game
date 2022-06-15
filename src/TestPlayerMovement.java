package unsw.dungeon;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

class TestPlayerMovement {

	/*
	 * 
	 * x  x  x
	 * x  x  x
	 * x  x  x
	 * 
	 */
	
	@Test
	void test() {
		List<String> goals = new ArrayList<String>();
		Dungeon dungeon = new Dungeon(3, 3, goals);
		
		Player player = new Player(dungeon, 0, 0);
		dungeon.setPlayer(player);
		
		assertEquals(0, player.getX());
		assertEquals(0, player.getY());
		
		player.moveRight();
		assertEquals(1, player.getX());
		assertEquals(0, player.getY());
		
		player.moveDown();
		assertEquals(1, player.getX());
		assertEquals(1, player.getY());
		
		player.moveRight();
		assertEquals(2, player.getX());
		assertEquals(1, player.getY());
		
		player.moveUp();
		assertEquals(2, player.getX());
		assertEquals(0, player.getY());
		
		player.moveLeft();
		assertEquals(1, player.getX());
		assertEquals(0, player.getY());
		
		// Edge case: Walk to position outside of dungeon
		player.moveUp();
		assertEquals(1, player.getX());
		assertEquals(0, player.getY());
	}

}
