package unsw.dungeon;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

class EnemyAutoMovement implements Runnable {
	
	private Dungeon dungeon;
	private Entity enemy;
	private String type;
	
    public EnemyAutoMovement(Dungeon dungeon, Entity enemy, String type) {
    	this.dungeon = dungeon;
    	this.enemy = enemy;
    	this.type = type;
	}

	public void run()
    {
        try {
            Runnable moveRunnable = new Runnable() {
                public void run() {
                	if (enemy.isActivated()) {
	                	// Enemy position
	                    int eX = enemy.getX();
	                    int eY = enemy.getY();
	                    // Player position
	                    int pX = dungeon.getPlayer().getX();
	                    int pY = dungeon.getPlayer().getY();
	                    // Current distance
	                    int currDistance = Math.abs(eX-pX) + Math.abs(eY-pY);
	                    // If player is not invincible, move towards player
	                	if (!dungeon.getPlayer().isInvincible()) {
		                    // Try shortening distance between enemy and player
		                    // In order of up, left, down, right
		                    // Try: Up
		                    int newDistance = Math.abs(eX-pX) + Math.abs(eY-1-pY);
		                    if ((newDistance >= currDistance) || !move(eX, eY, "up")) {
		                    	// Try: Left
		                    	newDistance = Math.abs(eX-1-pX) + Math.abs(eY-pY);
		                        if ((newDistance >= currDistance) || !move(eX, eY, "left")) {
		                        	// Try: Down
		                        	newDistance = Math.abs(eX-pX) + Math.abs(eY+1-pY);
		                            if ((newDistance >= currDistance) || !move(eX, eY, "down")) {
		                            	// Try: Right
		                            	newDistance = Math.abs(eX+1-pX) + Math.abs(eY-pY);
		                                if ((newDistance >= currDistance) || !move(eX, eY, "right")) {
		                                }
		                            }
		                        }
		                    }
	                	}
	                    // If player is invincible, move away from player
	                	else {
		                    // Try extending distance between enemy and player
		                    // In order of up, left, down, right
		                    // Try: Up
		                    int newDistance = Math.abs(eX-pX) + Math.abs(eY-1-pY);
		                    if ((newDistance <= currDistance) || !move(eX, eY, "up")) {
		                    	// Try: Left
		                    	newDistance = Math.abs(eX-1-pX) + Math.abs(eY-pY);
		                        if ((newDistance <= currDistance) || !move(eX, eY, "left")) {
		                        	// Try: Down
		                        	newDistance = Math.abs(eX-pX) + Math.abs(eY+1-pY);
		                            if ((newDistance <= currDistance) || !move(eX, eY, "down")) {
		                            	// Try: Right
		                            	newDistance = Math.abs(eX+1-pX) + Math.abs(eY-pY);
		                                if ((newDistance <= currDistance) || !move(eX, eY, "right")) {
		                                }
		                            }
		                        }
		                    }
	                	}
                	}
                }
            };
            // Enemy moves once every second
            ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
            executor.scheduleAtFixedRate(moveRunnable, 0, 1, TimeUnit.SECONDS);
  
        }
        catch (Exception e) {
            // Throw an exception
            System.out.println ("Exception is caught");
        }
    }
	
	private boolean move(int x, int y, String direction) {
		
		// Decide which cell to move to
		if (direction.equals("up")) {
			y -= 1;
		}
		else if (direction.equals("left")) {
			x -= 1;
		}
		else if (direction.equals("down")) {
			y += 1;
		}
		else if (direction.equals("right")) {
			x += 1;
		}
		
		// Move if possible
        boolean canMove = true;
        if (type.equals("basic")) {
	        for (Entity e: dungeon.entitiesHere(x, y)) {
	        	if (e.isObstacle() || e.getName().equals("enemy")) {
	        		canMove = false;
	        	}
	        }
        }
        if (x < 0 || x > dungeon.getWidth() || y < 0 || y > dungeon.getHeight()) {
        	canMove = false;
        }
        if (canMove == true) {
        	enemy.move(direction);
        	if (enemy.getX() == dungeon.getPlayer().getX() && enemy.getY() == dungeon.getPlayer().getY()) {
        		enemy.playerCollision(dungeon.getPlayer());
        	}
        }
        return canMove;
	}
    
}