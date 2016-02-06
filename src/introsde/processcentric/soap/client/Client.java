package introsde.processcentric.soap.client;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;

import introsde.processcentric.soap.ws.ProcessCentric;

import java.net.URL;

public class Client {

	public static void main(String[] args) throws Exception {
		
		try{
			URL url = new URL("http://192.168.0.103:6900/ws/introsdefinal?wsdl");
	        QName qname = new QName("http://ws.soap.processcentric.introsde/", "ProcessCentricService");
	        Service service = Service.create(url, qname);
	
	        ProcessCentric proc = service.getPort(ProcessCentric.class);
	        
	        System.out.println(proc.initializeUser("UA22049"));
		} catch(Exception e){
			System.out.println("Exception: " + e);
		}
	}
}
