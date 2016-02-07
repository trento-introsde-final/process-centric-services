package introsde.processcentric.soap.ws;

import introsde.processcentric.model.incoming.Goal;
import introsde.processcentric.model.incoming.GoalStatus;
import introsde.processcentric.model.incoming.Run;
import introsde.processcentric.model.outgoing.ReqUser;
import introsde.processcentric.util.UrlInfo;

import java.util.List;

import javax.jws.WebService;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation.Builder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.json.JSONObject;

//Service Implementation

@WebService(
		endpointInterface = "introsde.processcentric.soap.ws.ProcessCentricServices",
		serviceName="ProcessCentricServices")
public class ProcessCentricImpl implements ProcessCentricServices {

	String businessServicesURL;
	String storageServicesURL;
	
	String URLUsers = "%s/users/%";
	
	public ProcessCentricImpl() {
    	UrlInfo u = new UrlInfo();
    	businessServicesURL = u.getBusinessURL();
    	storageServicesURL = u.getStorageURL();
    }
/*
	@Override
	public int initializeUser(String slack_user_id, String name) {
		
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
*/
	/**
	 * 
	 * @param slack_user_id
	 * @param name
	 * @return -1 Bad Params
	 * 		   -2 Error in some called server
	 * 		   -3 No error, but got bad response
	 *         >0 Id of created user
	 */
	@Override
	public int initializeUser(String slack_user_id, String name) {
		if(slack_user_id == null || slack_user_id.isEmpty()){
			return -1;
		}
		Client client = ClientBuilder.newClient();
		WebTarget webTarget = client.target(String.format(URLUsers, storageServicesURL, ""));
		Builder builder = webTarget.request(MediaType.APPLICATION_JSON);
		
		//Build request object
		ReqUser user = new ReqUser();
		user.setSlack_user_id(slack_user_id);
		user.setFirstname(name);
		
		Response res = builder.post(Entity.json(user));
		
		String pp = res.readEntity(String.class);
		
		if(res.getStatus() != 201){
			return -2;
		}
		
		int resId = -3;
		try {
			JSONObject jO = new JSONObject(pp);
			if(jO.getString("status").equals("ERROR")){
				return -2;
			}
			String[] locParts  = res.getLocation().toString().split("/");
			resId = Integer.parseInt(locParts[locParts.length-1]);
		} catch(Exception e) {
			return -3;
		}
		
		return resId;
	}

	@Override
	public List<GoalStatus> checkGoalStatus(String slack_user_id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updateRunInfo(String slack_user_id, Run run) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setGoal(String slack_user_id, Goal goal) {
		// TODO Auto-generated method stub
		
	}

	
}
