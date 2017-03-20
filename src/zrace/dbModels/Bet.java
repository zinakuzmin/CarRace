package zrace.dbModels;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * The Class Bet 
 * @author Zina K.
 */
public class Bet implements Serializable{

	/**
	 * The Constant serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * The bet id.
	 */
	private int betId;
	
	/**
	 * The race id.
	 */
	private int raceId;
	
	/**
	 * The car id.
	 */
	private int carId;
	
	/**
	 * The user id.
	 */
	private int userId;
	
	/**
	 * The amount.
	 */
	private double amount;
	
	/**
	 * The bet time.
	 */
	private Timestamp betTime;
	
	/**
	 * Instantiates a new bet.
	 */
	public Bet(){
		
	}
	
	/**
	 * Instantiates a new bet.
	 *
	 * @param betId the bet id
	 * @param raceId the race id
	 * @param carId the car id
	 * @param userId the user id
	 * @param amount the amount
	 * @param betTime the bet time
	 */
	public Bet(int betId, int raceId, int carId, int userId, double amount,
			Timestamp betTime) {
		this.betId = betId;
		this.raceId = raceId;
		this.carId = carId;
		this.userId = userId;
		this.amount = amount;
		this.betTime = betTime;
	}
	
	/**
	 * Instantiates a new bet.
	 *
	 * @param raceId the race id
	 * @param carId the car id
	 * @param userId the user id
	 * @param amount the amount
	 * @param betTime the bet time
	 */
	public Bet(int raceId, int carId, int userId, double amount,
			Timestamp betTime) {
		this.raceId = raceId;
		this.carId = carId;
		this.userId = userId;
		this.amount = amount;
		this.betTime = betTime;
	}
	
	/**
	 * Gets the bet id.
	 *
	 * @return the bet id
	 */
	public int getBetId() {
		return betId;
	}
	
	/**
	 * Sets the bet id.
	 *
	 * @param betId the new bet id
	 */
	public void setBetId(int betId) {
		this.betId = betId;
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
	 * Gets the car id.
	 *
	 * @return the car id
	 */
	public int getCarId() {
		return carId;
	}
	
	/**
	 * Sets the car id.
	 *
	 * @param carId the new car id
	 */
	public void setCarId(int carId) {
		this.carId = carId;
	}
	
	/**
	 * Gets the user id.
	 *
	 * @return the user id
	 */
	public int getUserId() {
		return userId;
	}
	
	/**
	 * Sets the user id.
	 *
	 * @param userId the new user id
	 */
	public void setUserId(int userId) {
		this.userId = userId;
	}
	
	/**
	 * Gets the amount.
	 *
	 * @return the amount
	 */
	public double getAmount() {
		return amount;
	}
	
	/**
	 * Sets the amount.
	 *
	 * @param amount the new amount
	 */
	public void setAmount(double amount) {
		this.amount = amount;
	}
	
	/**
	 * Gets the bet time.
	 *
	 * @return the bet time
	 */
	public Timestamp getBetTime() {
		return betTime;
	}
	
	/**
	 * Sets the bet time.
	 *
	 * @param betTime the new bet time
	 */
	public void setBetTime(Timestamp betTime) {
		this.betTime = betTime;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Bet [betId=" + betId + ", raceId=" + raceId + ", carId="
				+ carId + ", userId=" + userId + ", amount=" + amount
				+ ", betTime=" + betTime + "]";
	}
	
	
	
}
