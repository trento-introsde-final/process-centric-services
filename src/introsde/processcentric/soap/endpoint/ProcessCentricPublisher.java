package introsde.processcentric.soap.endpoint;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Properties;

import javax.xml.ws.Endpoint;

import introsde.processcentric.soap.ws.ProcessCentricImpl;

public class ProcessCentricPublisher {
	public static String SERVER_URL = "http://localhost";
    public static String PORT = "6902";
    public static String BASE_URL = "/processCentricServices";

    public static String getEndpointURL() throws UnknownHostException {

    	String port_value = "6900";
        if (String.valueOf(System.getenv("PORT")) != "null"){
            port_value = String.valueOf(System.getenv("PORT"));
        }
        String port = ":" + port_value;
        
        String hostname = InetAddress.getLocalHost().getHostAddress();
        if (hostname.equals("127.0.0.1"))
        {
            hostname = "localhost";
        }
        
        Properties prop = System.getProperties();
		System.out.println("java.class.path now = " + prop.getProperty("java.class.path", null));
		System.out.println("DebugMessage");

        return "http://" + hostname + port + BASE_URL;        
    }

    public static void main(String[] args) throws UnknownHostException {
    	String endpointUrl = getEndpointURL();
    	System.out.println("Starting Process Centric Services...");
    	System.out.println("--> Published at = " + endpointUrl);
    	Endpoint.publish(endpointUrl, new ProcessCentricImpl());
    }
}
