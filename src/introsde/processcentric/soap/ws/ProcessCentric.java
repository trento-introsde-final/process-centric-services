package introsde.processcentric.soap.ws;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;
import javax.jws.soap.SOAPBinding.Use;

@WebService
@SOAPBinding(style = Style.DOCUMENT, use=Use.LITERAL)
public interface ProcessCentric {

	@WebMethod(operationName="initializeUser")
    @WebResult(name="id") 
    public int initializeUser(@WebParam(name="slack_user_id") String slack_user_id) throws Exception;
	
	/*@WebMethod(operationName="checkGoalStatus")
    @WebResult(name="goal") 
    public int checkGoalStatus(@WebParam(name="user_id") int user_id);*/
	
	/*@WebMethod(operationName="updateRunInfo")
    @WebResult(name="person") 
    public void updateRunInfo(@WebParam(name="slack_user_id") int slack_user_id);*/
}
