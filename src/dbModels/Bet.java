package dbModels;

import java.io.Serializable;
import java.sql.Timestamp;

public class Bet implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int betId;
	private int raceId;
	private int carId;
	private int userId;
	private double amount;
	private Timestamp betTime;
	
	public Bet(){
		
	}
	
	public Bet(int betId, int raceId, int carId, int userId, double amount,
			Timestamp betTime) {
		this.betId = betId;
		this.raceId = raceId;
		this.carId = carId;
		this.userId = userId;
		this.amount = amount;
		this.betTime = betTime;
	}
	public int getBetId() {
		return betId;
	}
	public void setBetId(int betId) {
		this.betId = betId;
	}
	public int getRaceId() {
		return raceId;
	}
	public void setRaceId(int raceId) {
		this.raceId = raceId;
	}
	public int getCarId() {
		return carId;
	}
	public void setCarId(int carId) {
		this.carId = carId;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public Timestamp getBetTime() {
		return betTime;
	}
	public void setBetTime(Timestamp betTime) {
		this.betTime = betTime;
	}

	@Override
	public String toString() {
		return "Bet [betId=" + betId + ", raceId=" + raceId + ", carId="
				+ carId + ", userId=" + userId + ", amount=" + amount
				+ ", betTime=" + betTime + "]";
	}
	
	
	
}
