package dbModels;

public class RaceResult {
	
	private int raceId;
	private int betId;
	private boolean isWinner;
	private double userRevenue;
	private double systemRevenue;
	public int getRaceId() {
		return raceId;
	}
	public void setRaceId(int raceId) {
		this.raceId = raceId;
	}
	public int getBetId() {
		return betId;
	}
	public void setBetId(int betId) {
		this.betId = betId;
	}
	public boolean getIsWinner() {
		return isWinner;
	}
	public void setWinner(boolean isWinner) {
		this.isWinner = isWinner;
	}
	public double getUserRevenue() {
		return userRevenue;
	}
	public void setUserRevenue(double userRevenue) {
		this.userRevenue = userRevenue;
	}
	public double getSystemRevenue() {
		return systemRevenue;
	}
	public void setSystemRevenue(double systemRevenue) {
		this.systemRevenue = systemRevenue;
	}
	
	

}
