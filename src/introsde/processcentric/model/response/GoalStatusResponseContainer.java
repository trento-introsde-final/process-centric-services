package introsde.processcentric.model.response;

import introsde.processcentric.model.internal.GoalStatusObject;

import java.util.List;

public class GoalStatusResponseContainer {
	
	private List<GoalStatusObject> goalStatusList;
	
	private List<Message> messages;
	
	public GoalStatusResponseContainer(){
		
	}
	
	public List<GoalStatusObject> getGoalStatusList() {
		return goalStatusList;
	}

	public List<Message> getMessages() {
		return messages;
	}
	
	public void setGoalStatusList(List<GoalStatusObject> goalStatus) {
		this.goalStatusList = goalStatus;
	}

	public void setMessages(List<Message> messages) {
		this.messages = messages;
	}

}
