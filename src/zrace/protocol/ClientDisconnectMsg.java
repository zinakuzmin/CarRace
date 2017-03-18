package zrace.protocol;

import dbModels.User;

public class ClientDisconnectMsg extends Message{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private User user;
	
	public ClientDisconnectMsg(User user) {
		this.user = user;
	}
	
	
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	@Override
	public String toString() {
		return "ClientDisconnectMsg [user=" + user + "]";
	}
	
	

}
