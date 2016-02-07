package introsde.processcentric.model.response;

import java.util.List;

public class SetGoalResponseContainer {
	
	private List<Message> messages;
	
	public SetGoalResponseContainer(){
		
	}

	public List<Message> getMessages() {
		return messages;
	}

	public void setMessages(List<Message> messages) {
		this.messages = messages;
	}
}
