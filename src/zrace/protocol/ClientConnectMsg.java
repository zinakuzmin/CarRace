package zrace.protocol;

/**
 * This class supplies API for {@link ClientConnectMsg}
 * @author Zina K
 *
 */
public class ClientConnectMsg extends Message{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Connected user ID
	 */
	private int userId;
	
	/**
	 * Connected user name
	 */
	private String userFullName;
	
	/**
	 * Initialize {@link ClientConnectMsg}
	 * @param messageId
	 * @param userId
	 * @param userFullName
	 */
	public ClientConnectMsg(int messageId, int userId, String userFullName){
		super(messageId);
		this.userId = userId;
		this.userFullName = userFullName;
		
	}
	/**
	 * Get user name
	 * @return String
	 */
	public String getUserFullName() {
		return userFullName;
	}

	/**
	 * Set user name
	 * @param userFullName
	 */
	public void setUserFullName(String userFullName) {
		this.userFullName = userFullName;
	}

	
	/**
	 * Get User ID
	 * @return int
	 */
	public int getUserId() {
		return userId;
	}
	/**
	 * Set user ID
	 * @param userId
	 */
	public void setUserId(int userId) {
		this.userId = userId;
	}
	@Override
	public String toString() {
		return "ClientConnectMsg [userId=" + userId + ", userFullName="
				+ userFullName + "]";
	}
	
	

}
