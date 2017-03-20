package zrace.protocol;

import zrace.dbModels.User;

/**
 * This class supplies API for {@link ClientDisconnectMsg}
 * @author Zina K
 *
 */
public class ClientDisconnectMsg extends Message{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * User details
	 */
	private User user;
	
	/**
	 * Initialize {@link ClientDisconnectMsg}
	 * @param messageId
	 * @param user
	 */
	public ClientDisconnectMsg(int messageId, User user) {
		super(messageId);
		this.user = user;
	}
	
	
	/**
	 * Get user details
	 * @return User
	 */
	public User getUser() {
		return user;
	}
	/**
	 * Set user details
	 * @param user
	 */
	public void setUser(User user) {
		this.user = user;
	}
	@Override
	public String toString() {
		return "ClientDisconnectMsg [user=" + user + "]";
	}
	
	

}
