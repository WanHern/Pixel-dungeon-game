package unsw.dungeon;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

class TestBoulder {

	/*
	 * 
	 * x  x  x  x  x
	 * x  x  b  x  x
	 * s  b  x  x  x
	 * x  x  s  x  x
	 * x  x  x  x  x
	 * 
	 */
	
	@Test
	void test() {
		List<String> goals = new ArrayList<String>();
		goals.add("boulders");
		Dungeon dungeon = new Dungeon(5, 5, goals);
		FloorSwitch floorSwitch1 = new FloorSwitch(2, 3);
		FloorSwitch floorSwitch2 = new FloorSwitch(0, 2);
		Boulder boulder1 = new Boulder(2, 1);
		Boulder boulder2 = new Boulder(1, 2);
		
		dungeon.addEntity(floorSwitch1);
		dungeon.addEntity(floorSwitch2);
		dungeon.addEntity(boulder1);
		dungeon.addEntity(boulder2);
		
		Player player = new Player(dungeon, 0, 0);
		dungeon.setPlayer(player);
		
		assertEquals(false, floorSwitch1.isActivated());
		assertEquals(false, floorSwitch2.isActivated());
		
		player.moveRight();
		player.moveRight();
		player.moveDown();
		player.push();
		player.moveDown();
		player.push();
		
		assertEquals(true, floorSwitch1.isActivated());
		assertEquals(false, floorSwitch2.isActivated());
		
		player.moveDown();
		player.moveLeft();
		player.push();
		
		assertEquals(true, floorSwitch1.isActivated());
		assertEquals(true, floorSwitch2.isActivated());
		
		// Edge case: push boulder outside of dungeon
		player.moveLeft();
		player.push();
		
		assertEquals(true, floorSwitch1.isActivated());
		assertEquals(true, floorSwitch2.isActivated());
		
		player.moveDown();
		player.moveLeft();
		player.moveUp();
		player.push();
		
		assertEquals(true, floorSwitch1.isActivated());
		assertEquals(false, floorSwitch2.isActivated());
	}

}
