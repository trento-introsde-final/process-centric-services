package introsde.processcentric.model.internal;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

public class BasicResponse {

	private String status;
	
	@JsonInclude(Include.NON_NULL)
	private String error = null;
	
	public BasicResponse(){
		this.status = "OK";
	}
	
	public BasicResponse(String message){
		this.status = "ERROR";
		this.error = message;
	}

	public String getStatus() {
		return status;
	}

	public String getError() {
		return error;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public void setError(String message) {
		this.status = "ERROR";
		this.error = message;
	}

}
