package dbModels;

import java.io.Serializable;
import java.sql.Timestamp;

public class Race implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int raceId;
	private String raceFullName;
	private int car1Id;
	private int car2Id;
	private int car3Id;
	private boolean isCompleted;
	private Timestamp startTime;
	private Timestamp endTime;
	private int duration;
	
	
	public Race(){}
	
	public Race(int raceId, String RaceFullName, int car1Id, int car2Id, int car3Id,
			boolean isCompleted, Timestamp startTime, Timestamp endTime,
			int duration) {
		setRaceId(raceId);
		setCar1Id(car1Id);
		setCar2Id(car2Id);
		setCar3Id(car3Id);
		setCompleted(isCompleted);
		setRaceFullName(RaceFullName);
		setDuration(duration);
		setStartTime(startTime);
		setEndTime(endTime);
	}
	public int getRaceId() {
		return raceId;
	}
	public void setRaceId(int raceId) {
		this.raceId = raceId;
	}
	public String getRaceFullName() {
		return raceFullName;
	}
	public void setRaceFullName(String raceFullName) {
		this.raceFullName = raceFullName;
	}
	public int getCar1Id() {
		return car1Id;
	}
	public void setCar1Id(int car1Id) {
		this.car1Id = car1Id;
	}
	public int getCar2Id() {
		return car2Id;
	}
	public void setCar2Id(int car2Id) {
		this.car2Id = car2Id;
	}
	public int getCar3Id() {
		return car3Id;
	}
	public void setCar3Id(int car3Id) {
		this.car3Id = car3Id;
	}
	public boolean isCompleted() {
		return isCompleted;
	}
	public void setCompleted(boolean isCompleted) {
		this.isCompleted = isCompleted;
	}
	public Timestamp getStartTime() {
		return startTime;
	}
	public void setStartTime(Timestamp startTime) {
		this.startTime = startTime;
	}
	public Timestamp getEndTime() {
		return endTime;
	}
	public void setEndTime(Timestamp endTime) {
		this.endTime = endTime;
	}
	public int getDuration() {
		return duration;
	}
	public void setDuration(int duration) {
		this.duration = duration;
	}

	@Override
	public String toString() {
		return "Race [raceId=" + raceId + ", raceFullName=" + raceFullName
				+ ", car1Id=" + car1Id + ", car2Id=" + car2Id + ", car3Id="
				+ car3Id + ", isCompleted=" + isCompleted + ", startTime="
				+ startTime + ", endTime=" + endTime + ", duration=" + duration
				+ "]";
	}
	
	

}
