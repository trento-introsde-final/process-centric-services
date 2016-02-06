package introsde.processcentric.soap.ws;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;

import javax.jws.WebService;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import introsde.processcentric.util.UrlInfo;

//Service Implementation

@WebService(endpointInterface = "introsde.processcentric.soap.ws.ProcessCentric",
serviceName="ProcessCentricService")
public class ProcessCentricImpl implements ProcessCentric {
	UrlInfo u;
	String businessUrl = "", storageUrl = "", location = "";
	URI stringlocation = null;
	
	public ProcessCentricImpl() {
    	u = new UrlInfo();
    	businessUrl = u.getBusinessURL();
    	storageUrl = u.getStorageURL();
    }

	@Override
	public int initializeUser(String slack_user_id) throws Exception{
		
		try{
			if(slack_user_id != null && !slack_user_id.isEmpty()){
				DefaultHttpClient httpClient = new DefaultHttpClient();
				HttpPost postRequest = new HttpPost(businessUrl+"users");
				
				StringEntity input = new StringEntity("{\"slack_user_id\": \"UB23324\"}");
				input.setContentType("application/json");
				postRequest.setEntity(input);
				
				
				HttpResponse response = httpClient.execute(postRequest);
				
				System.out.println(response);
				Header[] headers = response.getAllHeaders();
				
				for(Header h: headers) {
					if(h.getName().equals("Location")){
						location = h.getValue();
					}
				}
				stringlocation = new URI(location);
				String path = stringlocation.getPath();
				String idStr = path.substring(path.lastIndexOf('/') + 1);
				int id = Integer.parseInt(idStr);
	
				
				if (response.getStatusLine().getStatusCode() == 400) {
					BufferedReader rd = new BufferedReader(
		                    new InputStreamReader(response.getEntity().getContent()));
	
					StringBuffer result = new StringBuffer();
					String line = "";
					while ((line = rd.readLine()) != null) {
						result.append(line);
					}
					return 0;
				} else if (response.getStatusLine().getStatusCode() == 404) {
					BufferedReader rd = new BufferedReader(
		                    new InputStreamReader(response.getEntity().getContent()));
	
					StringBuffer result = new StringBuffer();
					String line = "";
					while ((line = rd.readLine()) != null) {
						result.append(line);
					}
					return 0;
				} else {
					BufferedReader rd = new BufferedReader(
		                    new InputStreamReader(response.getEntity().getContent()));
	
					StringBuffer result = new StringBuffer();
					String line = "";
					while ((line = rd.readLine()) != null) {
						result.append(line);
					}
					
					httpClient.getConnectionManager().shutdown();
					return id;
				}
			} else {
				return 0;
			}
		} catch(Exception e){
			System.out.println("Exception: " + e);
			return 0;
		}
	}

	
}
