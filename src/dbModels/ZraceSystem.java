package dbModels;

public class ZraceSystem {
	private int systemId;
	private double systemRevenue;
	
	public ZraceSystem(){
		
	}
	
	public ZraceSystem(int systemId, double systemRevenue){
		setSystemId(systemId);
		setSystemRevenue(systemRevenue);
	}
	
	public int getSystemId() {
		return systemId;
	}
	public void setSystemId(int systemId) {
		this.systemId = systemId;
	}
	public double getSystemRevenue() {
		return systemRevenue;
	}
	public void setSystemRevenue(double systemRevenue) {
		this.systemRevenue = systemRevenue;
	}
	@Override
	public String toString() {
		return "System [systemId=" + systemId + ", systemRevenue="
				+ systemRevenue + "]";
	}
	
	

}
