package introsde.processcentric.model.response;

import javax.xml.ws.WebFault;

@WebFault(name = "InternalCommunicationFault")
public class InternalCommunicationException extends Exception {

	private InternalCommunicationExceptionBean faultBean;
	
	private static final long serialVersionUID = -7573048377611668178L;

	public InternalCommunicationException(String message, InternalCommunicationExceptionBean faultInfo){
		super(message);
		faultBean = faultInfo;
	}
	
	public InternalCommunicationException(String message, InternalCommunicationExceptionBean faultInfo, Throwable cause){
		super(message, cause);
		faultBean = faultInfo;
	}
	
	public InternalCommunicationExceptionBean getFaultInfo(){
        return faultBean;
    }
	
}
