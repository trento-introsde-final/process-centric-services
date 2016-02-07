package introsde.processcentric.model.internal;

public class PrettyPicResponse extends BasicResponse {

	private Picture picture;
	
	public PrettyPicResponse(){
		super();
	}
	
	public PrettyPicResponse(String message){
		super(message);
	}
	
	public Picture getPicture() {
		return picture;
	}

	public void setPicture(Picture picture) {
		this.picture = picture;
	}

	public class Picture {
		
		public String url;
		public String thumbUrl;
		
		public Picture(){
			
		}
		
	}
}
