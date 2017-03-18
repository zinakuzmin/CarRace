package zrace.protocol;

public class ClientConnectMsg extends Message{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int userId;
	private String userFullName;
	
	public ClientConnectMsg(int messageId, int userId, String userFullName){
		super(messageId);
		this.userId = userId;
		this.userFullName = userFullName;
		
	}
	public String getUserFullName() {
		return userFullName;
	}

	public void setUserFullName(String userFullName) {
		this.userFullName = userFullName;
	}

	
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	@Override
	public String toString() {
		return "ClientConnectMsg [userId=" + userId + ", userFullName="
				+ userFullName + "]";
	}
	
	

}
