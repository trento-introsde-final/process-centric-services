package introsde.processcentric.soap.endpoint;

import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.xml.ws.Endpoint;

import introsde.processcentric.soap.ws.ProcessCentricImpl;

public class IntroSDEPublisher {
	public static String SERVER_URL = "http://localhost";
    public static String PORT = "6902";
    public static String BASE_URL = "/ws/introsdefinal";

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

        return "http://" + hostname + port + BASE_URL;        
    }

    public static void main(String[] args) throws UnknownHostException {
    	String endpointUrl = getEndpointURL();
    	System.out.println("Starting ProcessCentric Service...");
    	System.out.println("--> Published at = " + endpointUrl);
    	Endpoint.publish(endpointUrl, new ProcessCentricImpl());
    }
}
