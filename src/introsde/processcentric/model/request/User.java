package introsde.processcentric.model.request;


public class User {

	private String slack_user_id;
	
	private String firstname;
	
	private String lastname;
	
	private String email;
	
	public User(){
		
	}

	public String getSlack_user_id() {
		return slack_user_id;
	}

	public String getFirstname() {
		return firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public String getEmail() {
		return email;
	}

	public void setSlack_user_id(String slack_user_id) {
		this.slack_user_id = slack_user_id;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	

}
