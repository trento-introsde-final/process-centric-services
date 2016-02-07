package introsde.processcentric.model.incoming;

import java.sql.Timestamp;

public class Run {
	
	private Timestamp start_date;
	
	private Float distance;
	
	private Float calories;
	
	private Float moving_time;
	
	private Float elevation_gain;
	
	private Float max_speed;
	
	private Float avg_speed;
	
	public Run(){
		
	}

	public Timestamp getStart_date() {
		return start_date;
	}

	public Float getDistance() {
		return distance;
	}

	public Float getCalories() {
		return calories;
	}

	public Float getMoving_time() {
		return moving_time;
	}

	public Float getElevation_gain() {
		return elevation_gain;
	}

	public Float getMax_speed() {
		return max_speed;
	}

	public Float getAvg_speed() {
		return avg_speed;
	}

	public void setStart_date(Timestamp start_date) {
		this.start_date = start_date;
	}

	public void setDistance(Float distance) {
		this.distance = distance;
	}

	public void setCalories(Float calories) {
		this.calories = calories;
	}

	public void setMoving_time(Float moving_time) {
		this.moving_time = moving_time;
	}

	public void setElevation_gain(Float elevation_gain) {
		this.elevation_gain = elevation_gain;
	}

	public void setMax_speed(Float max_speed) {
		this.max_speed = max_speed;
	}

	public void setAvg_speed(Float avg_speed) {
		this.avg_speed = avg_speed;
	}
	
	

}
