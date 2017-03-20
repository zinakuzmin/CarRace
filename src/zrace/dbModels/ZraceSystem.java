package zrace.dbModels;

/**
 * This class Provides API for {@link ZraceSystem}
 * @author Zina K
 *
 */
public class ZraceSystem {
	private int systemId;
	private double systemRevenue;
	
	public ZraceSystem(){
		
	}
	
	/**
	 * Initialize {@link ZraceSystem}
	 * @param systemId
	 * @param systemRevenue
	 */
	public ZraceSystem(int systemId, double systemRevenue){
		setSystemId(systemId);
		setSystemRevenue(systemRevenue);
	}
	
	/**
	 * Return system total balance
	 * @return int
	 */
	public int getSystemId() {
		return systemId;
	}
	/**
	 * Set system ID
	 * @param systemId
	 */
	public void setSystemId(int systemId) {
		this.systemId = systemId;
	}
	/**
	 * Return system balance
	 * @return double
	 */
	public double getSystemRevenue() {
		return systemRevenue;
	}
	/**
	 * Set system balance
	 * @param systemRevenue
	 */
	public void setSystemRevenue(double systemRevenue) {
		this.systemRevenue = systemRevenue;
	}
	@Override
	public String toString() {
		return "System [systemId=" + systemId + ", systemRevenue="
				+ systemRevenue + "]";
	}
	
	

}
