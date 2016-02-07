package introsde.processcentric.model.response;

import javax.xml.ws.WebFault;

@WebFault(name = "InternalServiceFault", targetNamespace="http://ws.soap.processcentric.introsde/")
public class InternalCommunicationException extends Exception {

	private InternalServiceFault fault;
	
	private static final long serialVersionUID = -7573048377611668178L;

	public  InternalCommunicationException(){
		
	}
	
	protected InternalCommunicationException(InternalServiceFault fault) {
        super(fault.getFaultString()); 
        this.fault = fault;
    }
	
	public InternalCommunicationException(String message, InternalServiceFault faultInfo){
		super(message);
		fault = faultInfo;
	}
	
	public InternalCommunicationException(String message, InternalServiceFault faultInfo, Throwable cause){
		super(message, cause);
		fault = faultInfo;
	}
	
	public InternalServiceFault getFaultInfo(){
        return fault;
    }
	
	
	/**
	 * @param message
	 */
	public InternalCommunicationException(String message) {
		super(message);
	}
	
	/**
	 * @param message
	 */
	public InternalCommunicationException(String code, String message) {
		super(message);
		this.fault = new InternalServiceFault();
	    this.fault.setFaultString(message);
	    this.fault.setFaultCode(code);
	}

	/**
	 * @param cause
	 */
	public InternalCommunicationException(Throwable cause) {
		super(cause);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public InternalCommunicationException(String message, Throwable cause) {
		super(message, cause);
	}
	
}
