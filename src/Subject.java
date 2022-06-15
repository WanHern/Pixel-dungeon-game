package unsw.dungeon;

public interface Subject {

	public void registerObserver(Observer o);
	public void removeObserver(Observer o);
	public void notifyObservers();
	public int getX();
	public int getY();
	
}
