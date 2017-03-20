package dbModels;

/**
 * The Class provides API for RaceResult - controls the race run process.
 * @author Zina K
 */
public class RaceResult {
	
	/**
	 * The race id.
	 */
	private int raceId;
	
	/**
	 * The bet id.
	 */
	private int betId;
	
	/**
	 * The is winner.
	 */
	private boolean isWinner;
	
	/**
	 * The user revenue.
	 */
	private double userRevenue;
	
	/**
	 * The system revenue.
	 */
	private double systemRevenue;
	
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
	 * Gets the checks if is winner.
	 *
	 * @return the checks if is winner
	 */
	public boolean getIsWinner() {
		return isWinner;
	}
	
	/**
	 * Sets the winner.
	 *
	 * @param isWinner the new winner
	 */
	public void setWinner(boolean isWinner) {
		this.isWinner = isWinner;
	}
	
	/**
	 * Gets the user revenue.
	 *
	 * @return the user revenue
	 */
	public double getUserRevenue() {
		return userRevenue;
	}
	
	/**
	 * Sets the user revenue.
	 *
	 * @param userRevenue the new user revenue
	 */
	public void setUserRevenue(double userRevenue) {
		this.userRevenue = userRevenue;
	}
	
	/**
	 * Gets the system revenue.
	 *
	 * @return the system revenue
	 */
	public double getSystemRevenue() {
		return systemRevenue;
	}
	
	/**
	 * Sets the system revenue.
	 *
	 * @param systemRevenue the new system revenue
	 */
	public void setSystemRevenue(double systemRevenue) {
		this.systemRevenue = systemRevenue;
	}
	
	

}
