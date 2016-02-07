package introsde.processcentric.soap.client;

import introsde.processcentric.model.response.GoalStatusResponseContainer;
import introsde.processcentric.soap.ws.ProcessCentricServices;

import java.net.URL;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;

public class Client {

	public static void main(String[] args) throws Exception {
		
		try{
			URL url = new URL("http://192.168.0.101:6900/processCentricServices?wsdl");
	        QName qname = new QName("http://ws.soap.processcentric.introsde/", "ProcessCentricServices");
	        Service service = Service.create(url, qname);
	
	        ProcessCentricServices proc = service.getPort(ProcessCentricServices.class);
	        
	      //  System.out.println(""+proc.initializeUser("UA22049", "Bananiel"));
	        
	        GoalStatusResponseContainer resp = proc.checkGoalStatus("U43F341");
	        boolean Ff = true;
	        boolean Dd = Ff;
		} catch(Exception e){
			System.out.println("Exception: " + e);
		}
	}
}
