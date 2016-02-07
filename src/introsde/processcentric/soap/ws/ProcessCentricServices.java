package introsde.processcentric.soap.ws;

import introsde.processcentric.model.response.GoalStatusResponseContainer;
import introsde.processcentric.model.response.InternalCommunicationException;
import introsde.processcentric.model.response.SetGoalResponseContainer;
import introsde.processcentric.model.response.UpdateRunResponseContainer;

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
    public GoalStatusResponseContainer checkGoalStatus(@WebParam(name="slack_user_id") String slack_user_id) throws InternalCommunicationException;
	
	@WebMethod(operationName="updateRunInfo")
    @WebResult(name="person") 
    public UpdateRunResponseContainer updateRunInfo(@WebParam(name="slack_user_id") String slack_user_id, @WebParam(name="distance") Float distance, @WebParam(name="moving_time") Float time, @WebParam(name="calories") Float calories) throws InternalCommunicationException;
	
	@WebMethod(operationName="setGoal")
    @WebResult(name="person") 
    public SetGoalResponseContainer setGoal(@WebParam(name="slack_user_id") String slack_user_id, @WebParam(name="goal-type") String goal_type, @WebParam(name="target") Float target, @WebParam(name="period") String period);
	
	
}
