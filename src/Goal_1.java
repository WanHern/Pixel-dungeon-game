package unsw.dungeon;

import java.util.List;
import java.util.ArrayList;

public class Goal implements GoalChecker {
	
	private String logic;
	private List<GoalChecker> subgoals;
	
	/*
	 *  Constructor
	 */
	public Goal(String logic, Dungeon dungeon) {
		this.logic = logic;
		this.subgoals = new ArrayList<GoalChecker>();
	}
	
	/*
	 *  Add subgoals
	 */
	public void addSubgoal(GoalChecker subgoal) {
		subgoals.add(subgoal);
	}
	
	/*
	 *  Remove subgoals
	 */
	public void removeGoal(GoalChecker subgoal) {
		subgoals.remove(subgoal);
	}
	
    /*
     * Checks if goals are completed
     */
    public boolean check(Dungeon dungeon) {
    	boolean allCompleted = true;
    	// Single goal, "and" logic
    	if (logic.equals("") || logic.equals("and")) {
    		for (GoalChecker gc: subgoals) {
    			allCompleted = allCompleted & gc.check(dungeon);
    		}
    	}
    	// "or" logic
    	else {
    		allCompleted = false;
    		for (GoalChecker gc: subgoals) {
    			allCompleted = allCompleted | gc.check(dungeon);
    		}
    	}
    	if (allCompleted) {
    		dungeon.getAllCompleted().set(true);
    	}
    	else {
    		dungeon.getAllCompleted().set(false);
    	}
    	return allCompleted;
    }
	
	/*
	 * Getters
	 */
	public String getLogic() {
		return logic;
	}
	
	public List<GoalChecker> getGoals() {
		return subgoals;
	}
	
	public String getName() {
		return "";
	}

}
