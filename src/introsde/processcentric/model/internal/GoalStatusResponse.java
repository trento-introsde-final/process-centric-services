package introsde.processcentric.model.internal;

import java.util.List;

public class GoalStatusResponse extends BasicResponse {

	private List<GoalStatusObject> goal_status;
	
	public GoalStatusResponse(){
		super();
	}
	
	public GoalStatusResponse(String message){
		super(message);
	}

	public List<GoalStatusObject> getGoal_status() {
		return goal_status;
	}

	public void setGoal_status(List<GoalStatusObject> goal_status) {
		this.goal_status = goal_status;
	}
	
	
}
