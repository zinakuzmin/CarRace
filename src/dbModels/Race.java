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
	private int car4Id;
	private int car5Id;
	private boolean isCompleted;
	private Timestamp startTime;
	private Timestamp endTime;
	private int duration;
	private int winnerCarId;
	
	
	public Race(){}
	
	public Race(int raceId, String RaceFullName, int car1Id, int car2Id, int car3Id, int car4Id, int car5Id,
			boolean isCompleted, Timestamp startTime, Timestamp endTime,
			int duration) {
		setRaceId(raceId);
		setCar1Id(car1Id);
		setCar2Id(car2Id);
		setCar3Id(car3Id);
		setCar4Id(car4Id);
		setCar5Id(car5Id);
		setCompleted(isCompleted);
		setRaceFullName(RaceFullName);
		setDuration(duration);
		setStartTime(startTime);
		setEndTime(endTime);
	}
	
	public Race(int raceId, String RaceFullName, int car1Id, int car2Id, int car3Id, int car4Id, int car5Id,
			boolean isCompleted, Timestamp startTime, Timestamp endTime,
			int duration, int winnerCarId) {
		setRaceId(raceId);
		setCar1Id(car1Id);
		setCar2Id(car2Id);
		setCar3Id(car3Id);
		setCar4Id(car4Id);
		setCar5Id(car5Id);
		setCompleted(isCompleted);
		setRaceFullName(RaceFullName);
		setDuration(duration);
		setStartTime(startTime);
		setEndTime(endTime);
		setWinnerCarId(winnerCarId);
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


	public int getCar4Id() {
		return car4Id;
	}

	public void setCar4Id(int car4Id) {
		this.car4Id = car4Id;
	}

	public int getCar5Id() {
		return car5Id;
	}

	public void setCar5Id(int car5Id) {
		this.car5Id = car5Id;
	}
	


	@Override
	public String toString() {
		return "Race [raceId=" + raceId + ", raceFullName=" + raceFullName
				+ ", car1Id=" + car1Id + ", car2Id=" + car2Id + ", car3Id="
				+ car3Id + ", car4Id=" + car4Id + ", car5Id=" + car5Id
				+ ", isCompleted=" + isCompleted + ", startTime=" + startTime
				+ ", endTime=" + endTime + ", duration=" + duration
				+ ", winnerCarId=" + winnerCarId + "]";
	}

	public int getWinnerCarId() {
		return winnerCarId;
	}

	public void setWinnerCarId(int winnerCarId) {
		this.winnerCarId = winnerCarId;
	}
	
	

}
