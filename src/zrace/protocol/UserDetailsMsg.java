package zrace.protocol;

import dbModels.User;

/**
 * This class supplies API for {@link UserDetailsMsg}
 * @author Zina K
 *
 */
public class UserDetailsMsg extends Message{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private User user;
	
	/**
	 * Initialize {@link UserDetailsMsg}
	 * @param messageId
	 * @param user
	 */
	public UserDetailsMsg(int messageId, User user) {
		super(messageId);
		this.setUser(user);
	}

	/**
	 * @return User
	 */
	public User getUser() {
		return user;
	}

	/**
	 * @param user
	 */
	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public String toString() {
		return "UserDetailsMsg [user=" + user + ", getMessageId()="
				+ getMessageId() + "]";
	}

	
  
	
}
