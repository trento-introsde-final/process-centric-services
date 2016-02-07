package introsde.processcentric.model.response;

public class InternalServiceFault 	{
	/**
	 * Fault Code
	 */
	private String faultCode;
	/**
	 * Fault String
	 */
	private String faultString;
	/**
	 * @return the faultCode
	 */
	public String getFaultCode() {
		return faultCode;
	}
	/**
	 * @param faultCode the faultCode to set
	 */
	public void setFaultCode(String faultCode) {
		this.faultCode = faultCode;
	}
	/**
	 * @return the faultString
	 */
	public String getFaultString() {
		return faultString;
	}
	/**
	 * @param faultString the faultString to set
	 */
	public void setFaultString(String faultString) {
		this.faultString = faultString;
	}
}