package introsde.processcentric.soap.ws;

import introsde.processcentric.model.internal.BasicResponse;
import introsde.processcentric.model.internal.GoalStatusObject;
import introsde.processcentric.model.internal.GoalStatusResponse;
import introsde.processcentric.model.internal.MotivationQuoteResponse;
import introsde.processcentric.model.internal.PrettyPicResponse;
import introsde.processcentric.model.internal.SlackIdResponse;
import introsde.processcentric.model.request.Goal;
import introsde.processcentric.model.request.Run;
import introsde.processcentric.model.request.User;
import introsde.processcentric.model.response.GoalStatusResponseContainer;
import introsde.processcentric.model.response.InternalCommunicationException;
import introsde.processcentric.model.response.InternalCommunicationExceptionBean;
import introsde.processcentric.model.response.Message;
import introsde.processcentric.util.UrlInfo;

import java.util.ArrayList;
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
	
	String URLUsers = "%s/users/%s";
	String URLGoalStatus = "%s/users/%s/goal-status";
	String URLSlackUser = "%s/user-id/%s";
	String URLPictures = "%s/pretty-pic";
	String URLQuotes = "%s/motivation-quote";
	
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
		User user = new User();
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
	public GoalStatusResponseContainer checkGoalStatus(String slack_user_id) throws InternalCommunicationException {
		if(slack_user_id == null || slack_user_id.isEmpty()){
			return null;
		}
		
		//############################################
		// Step 1: Get user id, from slack_user_id
		//###########################################
		Client client = ClientBuilder.newClient();
		WebTarget webTarget = client.target(String.format(URLSlackUser, businessServicesURL, slack_user_id));
		Builder builder = webTarget.request(MediaType.APPLICATION_JSON);
		Response res  = builder.get();
		SlackIdResponse slackIdResp = parseResponse(res, 200, SlackIdResponse.class);
		Integer user_id = slackIdResp.getId();
		
		//############################################
		// Step 2: Get goal status for user
		//###########################################
		webTarget = client.target(String.format(URLGoalStatus, businessServicesURL, user_id.toString()));
		builder = webTarget.request(MediaType.APPLICATION_JSON);
		res = builder.get();
		GoalStatusResponse gStatusResp = parseResponse(res, 200, GoalStatusResponse.class);
		List<GoalStatusObject> goals = gStatusResp.getGoal_status();
		
		//Count how many goals are met
		int metGoals = 0;
		int totGoals = goals.size();
		String message = "";
		for(GoalStatusObject g : goals){
			if(g.getGoal_met())
				metGoals++;
		}
		if(totGoals == 0){
			message = "You haven't set any goals yet. We are sad. Maybe this can get you going?";
		} else if (totGoals == metGoals){
			message = "You've set all your goals! You are amazing. Keep up the good work. Here's your reward.";
		} else {
			message = String.format("You've already achieved %d out of %d goals. There's no stopping you now :)."+
					"\nIf you continue like this, soon you'll be writing your own motivational quotes.", metGoals, totGoals);
		}
		
		//#############################################
		// Step 3: Get pretty picture
		//############################################
		webTarget = client.target(String.format(URLPictures, storageServicesURL));
		builder = webTarget.request(MediaType.APPLICATION_JSON);
		res = builder.get();
		PrettyPicResponse picResp = parseResponse(res, 200, PrettyPicResponse.class);

		
		//############################################
		// Step 4: Get motivational quote
		//############################################
		webTarget = client.target(String.format(URLQuotes, storageServicesURL));
		builder = webTarget.request(MediaType.APPLICATION_JSON);
		res = builder.get();
		MotivationQuoteResponse mQuoteResp = parseResponse(res, 200, MotivationQuoteResponse.class);
		
		//build and send response
		ArrayList<Message> messages = new ArrayList<Message>();
		messages.add(new Message("text", message));
		messages.add(new Message("image", picResp.getPicture().url));
		messages.add(new Message("quote", 
				String.format("\"%s\" \n -%s", mQuoteResp.getResult().quote, mQuoteResp.getResult().author)));
		
		GoalStatusResponseContainer resp = new GoalStatusResponseContainer();
		resp.setGoalStatusList(goals);
		resp.setMessages(messages);
		return resp;
	}

	@Override
	public void updateRunInfo(String slack_user_id, Run run) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setGoal(String slack_user_id, Goal goal) {
		// TODO Auto-generated method stub
		
	}

	private <T extends BasicResponse> T parseResponse(
			Response response, int expected, Class<T> classType) 
					throws InternalCommunicationException{
		int status = response.getStatus();
		InternalCommunicationExceptionBean throwable = new InternalCommunicationExceptionBean();
		if(status == 500 || status == 503){
			throw new InternalCommunicationException("Could not contact underlying servers. Got ERROR: "+status, throwable);
		}
		try{
			T respObj = response.readEntity(classType);
			if(respObj.getStatus().equals("ERROR") || status!=expected){
				throw new InternalCommunicationException("Dependent servers could not execute request. Error "+status+": "+respObj.getError(), throwable);
			}
			return classType.cast(respObj);
		} catch (Exception e){
			throw new  InternalCommunicationException("Got incorrect response from underlying servers. Got ERROR: "+status, throwable);
		}
	}
}
