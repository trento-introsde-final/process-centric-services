package introsde.processcentric.model.incoming;

import java.sql.Timestamp;

public class GoalStatus {

	private String type;
	
	private String name;
	
	private String units;
	
	private Float target;
	
	private String period;
	
	private Timestamp period_start;
	
	private Timestamp period_end;
	
	private Boolean goal_met;
	
	private Float count;
	
	public GoalStatus(){
		
	}

	public String getType() {
		return type;
	}

	public String getName() {
		return name;
	}

	public String getUnits() {
		return units;
	}

	public Float getTarget() {
		return target;
	}

	public String getPeriod() {
		return period;
	}

	public Timestamp getPeriod_start() {
		return period_start;
	}

	public Timestamp getPeriod_end() {
		return period_end;
	}

	public Boolean getGoal_met() {
		return goal_met;
	}

	public Float getCount() {
		return count;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setUnits(String units) {
		this.units = units;
	}

	public void setTarget(Float target) {
		this.target = target;
	}

	public void setPeriod(String period) {
		this.period = period;
	}

	public void setPeriod_start(Timestamp period_start) {
		this.period_start = period_start;
	}

	public void setPeriod_end(Timestamp period_end) {
		this.period_end = period_end;
	}

	public void setGoal_met(Boolean goal_met) {
		this.goal_met = goal_met;
	}

	public void setCount(Float count) {
		this.count = count;
	}
	
}


