package unsw.dungeon;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

class TestKey {

	/*
	 * 
	 * w  w  w  w  w
	 * w  k  x  k  w
	 * w  w  d  w  w
	 * w  x  x  x  w
	 * w  w  w  w  w
	 * 
	 */
	
	@Test
	void test() {
		List<String> goals = new ArrayList<String>();
		Dungeon dungeon = new Dungeon(5, 5, goals);
		Wall wall1 = new Wall(0,0);
		Wall wall2 = new Wall(1,0);
		Wall wall3 = new Wall(2,0);
		Wall wall4 = new Wall(3,0);
		Wall wall5 = new Wall(4,0);
		
		Wall wall6 = new Wall(0,1);
		Wall wall7 = new Wall(0,2);
		Wall wall8 = new Wall(0,3);
		Wall wall9 = new Wall(0,4);
		
		Wall wall10 = new Wall(1,4);
		Wall wall11 = new Wall(2,4);
		Wall wall12 = new Wall(3,4);
		Wall wall13 = new Wall(4,4);
		
		Wall wall14 = new Wall(4,1);
		Wall wall15 = new Wall(4,2);
		Wall wall16 = new Wall(4,3);

		Wall wall17 = new Wall(1,2);
		Wall wall18 = new Wall(3,2);
		
		Door door = new Door(2,2);

		Key key1 = new Key(3,1);
		Key key2 = new Key(1,1);
		key1.registerObserver(dungeon);
		key2.registerObserver(dungeon);
		
		dungeon.addEntity(wall1);
		dungeon.addEntity(wall2);
		dungeon.addEntity(wall3);
		dungeon.addEntity(wall4);
		dungeon.addEntity(wall5);
		dungeon.addEntity(wall6);
		dungeon.addEntity(wall7);
		dungeon.addEntity(wall8);
		dungeon.addEntity(wall9);
		dungeon.addEntity(wall10);
		dungeon.addEntity(wall11);
		dungeon.addEntity(wall12);
		dungeon.addEntity(wall13);
		dungeon.addEntity(wall14);
		dungeon.addEntity(wall15);
		dungeon.addEntity(wall16);
		dungeon.addEntity(wall17);
		dungeon.addEntity(wall18);

		dungeon.addEntity(door);
		dungeon.addEntity(key1);
		dungeon.addEntity(key2);
		
		Player player = new Player(dungeon, 2, 1);
		dungeon.setPlayer(player);
		
		assertEquals(false, player.hasKey());
		
		player.moveRight();
		
		assertEquals(true, player.hasKey());

		player.moveLeft();
		player.openDoor();
		player.moveDown();
		
		// Can't open door without facing it, so failed to open door
		assertEquals(2, player.getX());
		assertEquals(1, player.getY());
		
		player.moveLeft();
		player.moveRight();
		player.moveDown();
		player.openDoor();
		player.moveDown();
		player.moveDown();
		// Player cannot walk through walls
		player.moveDown();
		
		// Door is now open and player has walked through
		assertEquals(2, player.getX());
		assertEquals(3, player.getY());

		assertEquals(false, player.hasKey());
		
		player.moveUp();
		player.moveUp();
		player.moveRight();
		
		assertEquals(false, player.hasKey());

		player.moveLeft();
		player.moveLeft();
		
		assertEquals(true, player.hasKey());
	}

}
