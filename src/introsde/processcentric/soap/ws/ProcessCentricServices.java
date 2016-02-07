package introsde.processcentric.soap.ws;

import introsde.processcentric.model.incoming.Goal;
import introsde.processcentric.model.incoming.GoalStatus;
import introsde.processcentric.model.incoming.Run;

import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;
import javax.jws.soap.SOAPBinding.Use;

@WebService
@SOAPBinding(style = Style.DOCUMENT, use=Use.LITERAL)
public interface ProcessCentricServices {

	@WebMethod(operationName="initializeUser")
    @WebResult(name="id") 
    public int initializeUser(@WebParam(name="slack_user_id") String slack_user_id, @WebParam(name="user_name") String name);
	
	@WebMethod(operationName="checkGoalStatus")
    @WebResult(name="goal") 
    public List<GoalStatus> checkGoalStatus(@WebParam(name="slack_user_id") String slack_user_id);
	
	@WebMethod(operationName="updateRunInfo")
    @WebResult(name="person") 
    public void updateRunInfo(@WebParam(name="slack_user_id") String slack_user_id, Run run);
	
	@WebMethod(operationName="setGoal")
    @WebResult(name="person") 
    public void setGoal(@WebParam(name="slack_user_id") String slack_user_id, Goal goal);
	
	
}
