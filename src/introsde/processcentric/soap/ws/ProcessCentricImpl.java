package introsde.processcentric.soap.ws;

import introsde.processcentric.model.internal.BasicResponse;
import introsde.processcentric.model.internal.GoalStatusObject;
import introsde.processcentric.model.internal.GoalStatusResponse;
import introsde.processcentric.model.internal.MotivationQuoteResponse;
import introsde.processcentric.model.internal.PrettyPicResponse;
import introsde.processcentric.model.internal.SlackIdResponse;
import introsde.processcentric.model.request.Run;
import introsde.processcentric.model.request.User;
import introsde.processcentric.model.response.GoalStatusResponseContainer;
import introsde.processcentric.model.response.InternalCommunicationException;
import introsde.processcentric.model.response.InternalServiceFault;
import introsde.processcentric.model.response.Message;
import introsde.processcentric.model.response.SetGoalResponseContainer;
import introsde.processcentric.model.response.UpdateRunResponseContainer;
import introsde.processcentric.util.UrlInfo;

import java.util.ArrayList;
import java.util.Date;
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
	String URLRuns = "%s/users/%s/runs";
	String URLGoalStatus = "%s/users/%s/goal-status";
	String URLSlackUser = "%s/user-id/%s";
	String URLPictures = "%s/pretty-pic";
	String URLQuotes = "%s/motivation-quote";
	
	public ProcessCentricImpl() {
    	UrlInfo u = new UrlInfo();
    	businessServicesURL = u.getBusinessURL();
    	storageServicesURL = u.getStorageURL();
    }

	/**
	 * 
	 * @param slack_user_id
	 * @param name
	 * @return -1 Bad Params
	 * 		   -2 Error in some called server
	 * 		   -3 No error, but got bad response
	 * 		   -4 Already registered
	 *         >0 Id of created user
	 */
	@Override
	public int initializeUser(String slack_user_id, String name) {
		if(slack_user_id == null || slack_user_id.isEmpty()){
			return -1;
		}
		
		//############################################
		// Call 1: Check if already registered	
		//###########################################
		Client client = ClientBuilder.newClient();
		WebTarget webTarget = client.target(String.format(URLSlackUser, businessServicesURL, slack_user_id));
		Builder builder = webTarget.request(MediaType.APPLICATION_JSON);
		Response res  = builder.get();
		SlackIdResponse slackIdResp = res.readEntity(SlackIdResponse.class);
		if(!slackIdResp.getStatus().equals("ERROR") && slackIdResp.getId() != null){
			return -4;
		}
		
		//############################################
		// Call 2: Register user
		//###########################################		
		client = ClientBuilder.newClient();
		webTarget = client.target(String.format(URLUsers, storageServicesURL, ""));
		builder = webTarget.request(MediaType.APPLICATION_JSON);
		
		//Build request object
		User user = new User();
		user.setSlack_user_id(slack_user_id);
		user.setFirstname(name);
		
		res = builder.post(Entity.json(user));
		
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
		System.out.println(slack_user_id);
		if(slack_user_id == null || slack_user_id.isEmpty()){
			return null;
		}
		
		//############################################
		// Call 1: Get user id, from slack_user_id
		//###########################################
		Client client = ClientBuilder.newClient();
		WebTarget webTarget = client.target(String.format(URLSlackUser, businessServicesURL, slack_user_id));
		Builder builder = webTarget.request(MediaType.APPLICATION_JSON);
		Response res  = builder.get();
		System.out.println("Call1: "+res.getStatus());
		SlackIdResponse slackIdResp = parseResponse(res, 200, SlackIdResponse.class);
		Integer user_id = slackIdResp.getId();
		
		//############################################
		// Call 2: Get goal status for user
		//###########################################
		webTarget = client.target(String.format(URLGoalStatus, businessServicesURL, user_id.toString()));
		builder = webTarget.request(MediaType.APPLICATION_JSON);
		res = builder.get();
		System.out.println("Call2: "+res.getStatus());
		GoalStatusResponse gStatusResp = parseResponse(res, 200, GoalStatusResponse.class);
		List<GoalStatusObject> goals = gStatusResp.getGoal_status();
		System.out.println("Number of goals: "+goals.size());
		//Count how many goals are met
		int metGoals = 0;
		int totGoals = goals.size();
		String message = "";
		for(GoalStatusObject g : goals){
			if(g.getGoal_met())
				metGoals++;
		}
		System.out.println("Met goals: "+metGoals);
		if(totGoals == 0){
			message = "You haven't set any goals yet. We are sad. Maybe this can get you going?";
		} else if (totGoals == metGoals){
			message = "You've set all your goals! You are amazing. Keep up the good work. Here's your reward.";
		} else {
			message = String.format("You've already achieved %d out of %d goals. There's no stopping you now :)."+
					"\nIf you continue like this, soon you'll be writing your own motivational quotes.", metGoals, totGoals);
		}
		
		//#############################################
		// Call 3: Get pretty picture
		//############################################
		webTarget = client.target(String.format(URLPictures, storageServicesURL));
		builder = webTarget.request(MediaType.APPLICATION_JSON);
		res = builder.get();
		System.out.println("Call3: "+res.getStatus());
		PrettyPicResponse picResp = parseResponse(res, 200, PrettyPicResponse.class);

		
		//############################################
		// Call 4: Get motivational quote
		//############################################
		webTarget = client.target(String.format(URLQuotes, storageServicesURL));
		builder = webTarget.request(MediaType.APPLICATION_JSON);
		res = builder.get();
		System.out.println("Call4: "+res.getStatus());
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
		System.out.println(String.format("%d messages, %d goals", resp.getMessages().size(), resp.getGoalStatusList().size()));
		return resp;
	}

	@Override
	/**
	 * 
	 * @param slack_user_id
	 * @param name
	 * @return -1 Bad Params
	 * 		   -2 Could not get user id 
	 * 		   -3 User id does not exist
	 * 		   -4 Could not get goal status
	 * 		   -5 Could not save run
	 *          0 Id of created run
	 */
	public UpdateRunResponseContainer updateRunInfo(String slack_user_id, Float distance, Float time, Float calories) throws InternalCommunicationException{
		if(slack_user_id == null || slack_user_id.isEmpty() || (distance==null && time == null && calories == null)){
			//response.setError(-1, "Invalid arguments.");
			return null;
		}
		Run run = new Run();
		run.setCalories(calories);
		run.setDistance(distance);
		run.setMoving_time(time.intValue());
		run.setStart_date((new Date()).getTime());
		
		//############################################
		// Call 1: Get user id, from slack_user_id
		//###########################################
		Client client = ClientBuilder.newClient();
		WebTarget webTarget = client.target(String.format(URLSlackUser, businessServicesURL, slack_user_id));
		Builder builder = webTarget.request(MediaType.APPLICATION_JSON);
		Response res  = builder.get();
		if(res.getStatus() != 200){
			//response.setError(-2, "Cannot commuincate with ")
		}
		SlackIdResponse slackIdResp = res.readEntity(SlackIdResponse.class);
		if(slackIdResp.getStatus().equals("ERROR")){
			return null;
		}
		Integer user_id = slackIdResp.getId();
		
		//############################################
		// Call 2: Check goals before Run is added
		//###########################################
		webTarget = client.target(String.format(URLGoalStatus, businessServicesURL, user_id.toString()));
		builder = webTarget.request(MediaType.APPLICATION_JSON);
		res = builder.get();
		if(res.getStatus() != 200){
			return null;
		}
		GoalStatusResponse gStatusResp = res.readEntity(GoalStatusResponse.class);
		if(gStatusResp.getStatus().equals("ERROR")){
			return null;
		}
		List<GoalStatusObject> goals = gStatusResp.getGoal_status();

		//############################################
		// Call 3: Save run
		//###########################################
		webTarget = client.target(String.format(URLRuns, storageServicesURL, user_id.toString()));
		builder = webTarget.request(MediaType.APPLICATION_JSON);
		res = builder.post(Entity.json(run));
		if(res.getStatus() != 201){
			return null;
		}
		
		//Check if any goal is met with run
		List<GoalStatusObject> newlyMetGoals = checkNewGoalsMet(goals, run);
		ArrayList<Message> messages = new ArrayList<Message>();
		
		if(!newlyMetGoals.isEmpty()){
			String message = "Congratulations! ";
			for(int i=0; i<newlyMetGoals.size(); i++){
				message += "You've achieved your "
						+newlyMetGoals.get(0).getPeriod()+" "
						+newlyMetGoals.get(0).getType()+" goal. "
						+newlyMetGoals.get(0).getCount()+" "
						+newlyMetGoals.get(0).getUnits()+"\n";
			}
			messages.add(new Message("text", "Enjoy this nice pic."));
			//############################################
			// Call 4.A: Get rewards: Pretty pic
			//###########################################
			webTarget = client.target(String.format(URLPictures, storageServicesURL));
			builder = webTarget.request(MediaType.APPLICATION_JSON);
			res = builder.get();
			PrettyPicResponse picResp = parseResponse(res, 200, PrettyPicResponse.class);
			messages.add(new Message("image", picResp.getPicture().url));
		} else {
			//############################################
			// Call 4.B: Get motivational quote
			//############################################
			messages.add(new Message("text", "Congratulations for your effort :D! Here's a little something to keep you going."));
			webTarget = client.target(String.format(URLQuotes, storageServicesURL));
			builder = webTarget.request(MediaType.APPLICATION_JSON);
			res = builder.get();
			MotivationQuoteResponse mQuoteResp = parseResponse(res, 200, MotivationQuoteResponse.class);
			messages.add(new Message("quote", String.format("\"%s\" \n -%s", mQuoteResp.getResult().quote, mQuoteResp.getResult().author)));
		}
		
		
		//build response message 
		UpdateRunResponseContainer updateRunResp = new UpdateRunResponseContainer();
		updateRunResp.setMessages(messages);
		return updateRunResp;
	}

	@Override
	public SetGoalResponseContainer setGoal(String slack_user_id, Float target, String period) {
		if(slack_user_id == null || slack_user_id.isEmpty() || target == null || period==null){
			return null;
		}
		
		//############################################
		// Call 1: Get user id, from slack_user_id
		//###########################################
		Client client = ClientBuilder.newClient();
		WebTarget webTarget = client.target(String.format(URLSlackUser, businessServicesURL, slack_user_id));
		Builder builder = webTarget.request(MediaType.APPLICATION_JSON);
		Response res  = builder.get();
		if(res.getStatus() != 200){
			//response.setError(-2, "Cannot commuincate with ")
		}
		SlackIdResponse slackIdResp = res.readEntity(SlackIdResponse.class);
		if(slackIdResp.getStatus().equals("ERROR")){
			return null;
		}
		Integer user_id = slackIdResp.getId();
		
		return new SetGoalResponseContainer();
	}

	private List<GoalStatusObject> checkNewGoalsMet(List<GoalStatusObject> goalStatus, Run run){
		ArrayList<GoalStatusObject> newlyMetGoals = new ArrayList<GoalStatusObject>();
		for(GoalStatusObject g: goalStatus){
			if(!g.getGoal_met().booleanValue()){
				float missing = g.getTarget() - g.getCount();
				float current = 0;
				switch(g.getType()){
				case "distance":
					current = run.getDistance();
					break;
				case "calories":
					current = run.getCalories();
					break;
				case "moving_time":
					current = run.getMoving_time();
					break;
				case "elevation_gain":
					current = run.getElevation_gain();
					break;
				case "max_speed":
					current = run.getMax_speed();
					break;
				case "avg_speed":
					current = run.getAvg_speed();
					break;
				}
				if(current > missing){
					g.setCount(g.getCount()+current);
					newlyMetGoals.add(g);
				}
			}
		}
		return newlyMetGoals;
	}
	
	private <T extends BasicResponse> T parseResponse(
			Response response, int expected, Class<T> classType) 
					throws InternalCommunicationException{
		int status = response.getStatus();
		InternalServiceFault throwable = new InternalServiceFault();
		throwable.setFaultCode("1233");
		throwable.setFaultString("Could not contact underlying servers.");
		if(status == 500 || status == 503){
			throw new InternalCommunicationException("Could not contact underlying servers. Got Response: "+status, throwable);
		}
		T respObj;
		try{
			respObj = response.readEntity(classType);
		} catch (Exception e){
			throw new  InternalCommunicationException("Got incorrect response from underlying servers. Got ERROR: "+status, throwable);
		}
		if(respObj == null || respObj.getStatus().equals("ERROR") || status!=expected){
			String message = "Dependent servers could not execute request. Status "+status+": "+respObj.getError();
			throw new InternalCommunicationException(message, throwable);
		}
		return classType.cast(respObj);
	
	}
}
