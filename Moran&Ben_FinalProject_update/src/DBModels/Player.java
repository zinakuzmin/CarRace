package DBModels;

import java.io.Serializable;

/**
 * This class provides an API compatible with Player.
 * 
 * @author Moran Tenzer & Ben Nave
 *
 */
public class Player implements Serializable{

	/**
	 * Static value of long serialVersionUID {@value}
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * {@code playerName} - the name of the player.
	 */
	private String playerFullName;
	/**
	 * {@code playerID} - the id of the player.
	 */
	private int playerID;
	/**
	 * This Constructor create a new instance of a Player Class and assign it's values.
	 * 
	 * @param playerName - the player's name.
	 * @param playerID - the player's id.
	 */
	public Player(String playerName, int playerID) {
		this.playerFullName = playerName;
		this.playerID = playerID;
	}
	/**
	 * @return the player's name.
	 */
	public String getPlayerFullName() {
		return playerFullName;
	}
	/**
	 * This method sets the player's name.
	 * 
	 * @param playerName - the player's name.
	 */
	public void setPlayerFullName(String playerFullName) {
		this.playerFullName = playerFullName;
	}
	
	/**
	 * @return the player's id.
	 */
	public int getplayerID() {
		return playerID;
	}
	
	/**
	 * This method sets the player's id.
	 * 
	 * @param playerID - the player's id.
	 */
	public void setplayerID(int playerID) {
		this.playerID = playerID;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object other) {
		if(other == null)
			return false;
		if(  !(other instanceof Player) )
			return false;
		Player player = (Player)other;
		if(this.getplayerID() != player.getplayerID())
			return false;
		if(!this.getPlayerFullName().equals(player.getPlayerFullName()))
			return false;
		
		return true;
	}

	
	
	
}
