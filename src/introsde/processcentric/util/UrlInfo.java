package introsde.processcentric.util;

public class UrlInfo {
	
	public UrlInfo() {}
	
	static final String storageUrl = "https://storage-services.herokuapp.com";
	static final String businessUrl = "https://business-logic-services.herokuapp.com";
	
	public String getStorageURL() {
		return storageUrl;
	}
	
	public String getBusinessURL() {
		return businessUrl;
	}
}
