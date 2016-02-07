package introsde.processcentric.model.response;

public class Message {
	
	private String type;
	private String content;
	
	public Message(){
		
	}
	
	public Message(String t, String c){
		type = t;
		content = c;
	}

	public String getType() {
		return type;
	}

	public String getContent() {
		return content;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	
}