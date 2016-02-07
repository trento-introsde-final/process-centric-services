package introsde.processcentric.soap.client;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;

import introsde.processcentric.soap.ws.ProcessCentricServices;

import java.net.URL;

public class Client {

	public static void main(String[] args) throws Exception {
		
		try{
			URL url = new URL("http://192.168.0.101:6900/processCentricServices?wsdl");
	        QName qname = new QName("http://ws.soap.processcentric.introsde/", "ProcessCentricServices");
	        Service service = Service.create(url, qname);
	
	        ProcessCentricServices proc = service.getPort(ProcessCentricServices.class);
	        
	        System.out.println(""+proc.initializeUser("UA22049", "Bananiel"));
		} catch(Exception e){
			System.out.println("Exception: " + e);
		}
	}
}
