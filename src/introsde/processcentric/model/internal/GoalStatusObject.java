package introsde.processcentric.model.internal;


public class GoalStatusObject {

	private String type;
	
	private String name;
	
	private String units;
	
	private float target;
	
	private String period;
	
	private Long period_start;
	
	private Long period_end;
	
	private Boolean goal_met;
	
	private float count;
	
	public GoalStatusObject(){
		
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

	public float getTarget() {
		return target;
	}

	public String getPeriod() {
		return period;
	}

	public Long getPeriod_start() {
		return period_start;
	}

	public Long getPeriod_end() {
		return period_end;
	}

	public Boolean getGoal_met() {
		return goal_met;
	}

	public float getCount() {
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

	public void setTarget(float target) {
		this.target = target;
	}

	public void setPeriod(String period) {
		this.period = period;
	}

	public void setPeriod_start(Long period_start) {
		this.period_start = period_start;
	}

	public void setPeriod_end(Long period_end) {
		this.period_end = period_end;
	}

	public void setGoal_met(Boolean goal_met) {
		this.goal_met = goal_met;
	}

	public void setCount(float count) {
		this.count = count;
	}
	
	
}
