package introsde.processcentric.model.internal;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

public class SlackIdResponse extends BasicResponse{

	@JsonInclude(Include.NON_NULL)
	private Integer id;
	
	public SlackIdResponse(){
		super();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
}
