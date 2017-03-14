package dbModels;

import java.io.Serializable;

/**
 * This class provides an API compatible with User.
 * 
 * @author Zina Kuzmin
 *
 */

public class User implements Serializable {
	/**
	 * Static value of long serialVersionUID {@value}
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * {@code userId} - the id of the user.
	 */
	private int userId;

	
	/**
	 * {@code userFullName} - the name of the user.
	 */
	private String userFullName;
	
	
	/**
	 * {@code userRevenue} - the revenue of the user.
	 */
	private double userRevenue;
	

	/**
	 * This Constructor create a new instance of a Player Class and assign it's
	 * values.
	 * 
	 * * @param userId
	 *            - the user's id.
	 * @param playerFullName
	 *            - the user's name.
	 */
	
	
	public User(String name, int id, double revenue) {
		this.userId = id;
		this.userFullName = name;
		this.userRevenue = revenue;
	}

	/**
	 * @return the user's name.
	 */
	public String getUserFullName() {
		return userFullName;
	}

	/**
	 * This method sets the user's name.
	 * 
	 * @param userFullName
	 *            - the users's name.
	 */
	public void setPlayerFullName(String name) {
		this.userFullName = name;
	}

	/**
	 * @return the user's id.
	 */
	public int getUserID() {
		return userId;
	}

	/**
	 * This method sets the user's id.
	 * 
	 * @param userId
	 *            - the user's id.
	 */
	public void setUserID(int id) {
		this.userId = id;
	}

	
	/**
	 * @return the user's revenue.
	 */
	public double getUserRevenue() {
		return userRevenue;
	}

	/**
	 * This method sets the user's revenue.
	 * 
	 * @param userRevenue
	 *            - the user's revenue.
	 */
	public void setUserRevenue(double revenue) {
		this.userRevenue = revenue;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object other) {
		if (other == null)
			return false;
		if (!(other instanceof User))
			return false;
		User user = (User) other;
		if (this.getUserID() != user.getUserID())
			return false;
		if (!this.getUserFullName().equals(user.getUserFullName()))
			return false;

		return true;
	}

	@Override
	public String toString() {
		return "User [userId=" + userId + ", userFullName=" + userFullName
				+ ", userRevenue=" + userRevenue + "]";
	}

}
