package introsde.processcentric.soap.ws;

import introsde.processcentric.model.request.Goal;
import introsde.processcentric.model.request.Run;
import introsde.processcentric.model.response.GoalStatusResponseContainer;
import introsde.processcentric.model.response.InternalCommunicationException;
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
    public UpdateRunResponseContainer updateRunInfo(@WebParam(name="slack_user_id") String slack_user_id, Float distance, Float time, Float calories) throws InternalCommunicationException;
	
	@WebMethod(operationName="setGoal")
    @WebResult(name="person") 
    public void setGoal(@WebParam(name="slack_user_id") String slack_user_id, Goal goal);
	
	
}
