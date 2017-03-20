package dbModels;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * The Class Race.
 * @author Zina K
 */
public class Race implements Serializable{
	
	/**
	 * The Constant serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * The race id.
	 */
	private int raceId;
	
	/**
	 * The race full name.
	 */
	private String raceFullName;
	
	/**
	 * The car 1 id.
	 */
	private int car1Id;
	
	/**
	 * The car 2 id.
	 */
	private int car2Id;
	
	/**
	 * The car 3 id.
	 */
	private int car3Id;
	
	/**
	 * The car 4 id.
	 */
	private int car4Id;
	
	/**
	 * The car 5 id.
	 */
	private int car5Id;
	
	/**
	 * The is completed.
	 */
	private boolean isCompleted;
	
	/**
	 * The start time.
	 */
	private Timestamp startTime;
	
	/**
	 * The end time.
	 */
	private Timestamp endTime;
	
	/**
	 * The duration.
	 */
	private int duration;
	
	/**
	 * The winner car id.
	 */
	private int winnerCarId;
	
	
	/**
	 * Instantiates a new race.
	 */
	public Race(){}
	
	/**
	 * Instantiates a new race.
	 *
	 * @param raceId the race id
	 * @param RaceFullName the race full name
	 * @param car1Id the car 1 id
	 * @param car2Id the car 2 id
	 * @param car3Id the car 3 id
	 * @param car4Id the car 4 id
	 * @param car5Id the car 5 id
	 * @param isCompleted the is completed
	 * @param startTime the start time
	 * @param endTime the end time
	 * @param duration the duration
	 */
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
	
	/**
	 * Instantiates a new race.
	 *
	 * @param raceId the race id
	 * @param RaceFullName the race full name
	 * @param car1Id the car 1 id
	 * @param car2Id the car 2 id
	 * @param car3Id the car 3 id
	 * @param car4Id the car 4 id
	 * @param car5Id the car 5 id
	 * @param isCompleted the is completed
	 * @param startTime the start time
	 * @param endTime the end time
	 * @param duration the duration
	 * @param winnerCarId the winner car id
	 */
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
	
	
	/**
	 * Gets the race id.
	 *
	 * @return the race id
	 */
	public int getRaceId() {
		return raceId;
	}
	
	/**
	 * Sets the race id.
	 *
	 * @param raceId the new race id
	 */
	public void setRaceId(int raceId) {
		this.raceId = raceId;
	}
	
	/**
	 * Gets the race full name.
	 *
	 * @return the race full name
	 */
	public String getRaceFullName() {
		return raceFullName;
	}
	
	/**
	 * Sets the race full name.
	 *
	 * @param raceFullName the new race full name
	 */
	public void setRaceFullName(String raceFullName) {
		this.raceFullName = raceFullName;
	}
	
	/**
	 * Gets the car 1 id.
	 *
	 * @return the car 1 id
	 */
	public int getCar1Id() {
		return car1Id;
	}
	
	/**
	 * Sets the car 1 id.
	 *
	 * @param car1Id the new car 1 id
	 */
	public void setCar1Id(int car1Id) {
		this.car1Id = car1Id;
	}
	
	/**
	 * Gets the car 2 id.
	 *
	 * @return the car 2 id
	 */
	public int getCar2Id() {
		return car2Id;
	}
	
	/**
	 * Sets the car 2 id.
	 *
	 * @param car2Id the new car 2 id
	 */
	public void setCar2Id(int car2Id) {
		this.car2Id = car2Id;
	}
	
	/**
	 * Gets the car 3 id.
	 *
	 * @return the car 3 id
	 */
	public int getCar3Id() {
		return car3Id;
	}
	
	/**
	 * Sets the car 3 id.
	 *
	 * @param car3Id the new car 3 id
	 */
	public void setCar3Id(int car3Id) {
		this.car3Id = car3Id;
	}
	
	/**
	 * Checks if is completed.
	 *
	 * @return true, if is completed
	 */
	public boolean isCompleted() {
		return isCompleted;
	}
	
	/**
	 * Sets the completed.
	 *
	 * @param isCompleted the new completed
	 */
	public void setCompleted(boolean isCompleted) {
		this.isCompleted = isCompleted;
	}
	
	/**
	 * Gets the start time.
	 *
	 * @return the start time
	 */
	public Timestamp getStartTime() {
		return startTime;
	}
	
	/**
	 * Sets the start time.
	 *
	 * @param startTime the new start time
	 */
	public void setStartTime(Timestamp startTime) {
		this.startTime = startTime;
	}
	
	/**
	 * Gets the end time.
	 *
	 * @return the end time
	 */
	public Timestamp getEndTime() {
		return endTime;
	}
	
	/**
	 * Sets the end time.
	 *
	 * @param endTime the new end time
	 */
	public void setEndTime(Timestamp endTime) {
		this.endTime = endTime;
	}
	
	/**
	 * Gets the duration.
	 *
	 * @return the duration
	 */
	public int getDuration() {
		return duration;
	}
	
	/**
	 * Sets the duration.
	 *
	 * @param duration the new duration
	 */
	public void setDuration(int duration) {
		this.duration = duration;
	}


	/**
	 * Gets the car 4 id.
	 *
	 * @return the car 4 id
	 */
	public int getCar4Id() {
		return car4Id;
	}

	/**
	 * Sets the car 4 id.
	 *
	 * @param car4Id the new car 4 id
	 */
	public void setCar4Id(int car4Id) {
		this.car4Id = car4Id;
	}

	/**
	 * Gets the car 5 id.
	 *
	 * @return the car 5 id
	 */
	public int getCar5Id() {
		return car5Id;
	}

	/**
	 * Sets the car 5 id.
	 *
	 * @param car5Id the new car 5 id
	 */
	public void setCar5Id(int car5Id) {
		this.car5Id = car5Id;
	}
	


	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Race [raceId=" + raceId + ", raceFullName=" + raceFullName
				+ ", car1Id=" + car1Id + ", car2Id=" + car2Id + ", car3Id="
				+ car3Id + ", car4Id=" + car4Id + ", car5Id=" + car5Id
				+ ", isCompleted=" + isCompleted + ", startTime=" + startTime
				+ ", endTime=" + endTime + ", duration=" + duration
				+ ", winnerCarId=" + winnerCarId + "]";
	}

	/**
	 * Gets the winner car id.
	 *
	 * @return the winner car id
	 */
	public int getWinnerCarId() {
		return winnerCarId;
	}

	/**
	 * Sets the winner car id.
	 *
	 * @param winnerCarId the new winner car id
	 */
	public void setWinnerCarId(int winnerCarId) {
		this.winnerCarId = winnerCarId;
	}
	
	

}
