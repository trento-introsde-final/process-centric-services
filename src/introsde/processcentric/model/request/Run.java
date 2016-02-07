package introsde.processcentric.model.request;

import java.sql.Timestamp;


public class Run {

	private Timestamp start_date;
	
	private float distance;
	
	private float calories;
	
	private int moving_time;
	
	private float elevation_gain;

	private float max_speed;
	
	private float avg_speed;
	
	private int steps;

	public Run(){
		distance = 0;
		calories = 0;
		moving_time = 0;
		elevation_gain = 0;
		max_speed = 0;
		avg_speed = 0;
		steps = 0;
	}

	public Timestamp getStart_date() {
		return start_date;
	}

	public float getDistance() {
		return distance;
	}

	public float getCalories() {
		return calories;
	}

	public int getMoving_time() {
		return moving_time;
	}

	public float getElevation_gain() {
		return elevation_gain;
	}

	public float getMax_speed() {
		return max_speed;
	}

	public float getAvg_speed() {
		return avg_speed;
	}

	public int getSteps() {
		return steps;
	}
	
	public void setStart_date(Timestamp start_date) {
		this.start_date = start_date;
	}

	public void setDistance(float distance) {
		this.distance = distance;
	}

	public void setCalories(float calories) {
		this.calories = calories;
	}

	public void setMoving_time(int moving_time) {
		this.moving_time = moving_time;
	}

	public void setElevation_gain(float elevation_gain) {
		this.elevation_gain = elevation_gain;
	}

	public void setMax_speed(float max_speed) {
		this.max_speed = max_speed;
	}

	public void setAvg_speed(float avg_speed) {
		this.avg_speed = avg_speed;
	}
	
	public void setSteps(int steps){
		this.steps = steps;
	}
}
