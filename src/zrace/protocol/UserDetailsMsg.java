package zrace.protocol;

import dbModels.User;

public class UserDetailsMsg extends Message{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private User user;
	
	public UserDetailsMsg(int messageId, User user) {
		super(messageId);
		this.setUser(user);
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public String toString() {
		return "UserDetailsMsg [user=" + user + ", getMessageId()="
				+ getMessageId() + "]";
	}

	
  
	
}
