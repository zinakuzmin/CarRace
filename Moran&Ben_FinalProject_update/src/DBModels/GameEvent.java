package DBModels;

import java.io.Serializable;
import java.util.Date;

/**
 * This class provides an API compatible with GameEvent.
 * 
 * @author Moran Tenzer & Ben Nave
 *
 */
public class GameEvent implements Serializable{
	/**
	 * a type of event that can occur while playing.
	 */
	 public static final String EVENT_TYPE_HIT = "Hit";
	 /**
	  * a type of event that can occur while playing.
	 */
	 public static final String EVENT_TYPE_MISS = "Miss";
	/**
	 * Static value of long serialVersionUID {@value}
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * {@code ballID} - the id of the ball.
	 */
	private int ballID;
	/**
	 * {@code playerID} - the id of the player.
	 */
	private int playerID;
	/**
	 * {@code gameID} - the id of the game.
	 */
	private int gameID;
	/**
	 * {@code eventType} - the type of the event : "Hit" | "Miss".
	 */
	private String eventType;
	/**
	 * {@code eventTime} - the time the event occurred.
	 */
	private Date eventTime;
	
	/**
	 * C'tor : Creates a Object of GameEvent.
	 * 
	 * @param ballID 	- the ball's id.
	 * @param playerID  - the player's id.
	 * @param gameID 	- the game's id.
	 * @param eventType - the gameEvent's type of event.
	 * @param eventTime - the gameEvent's time of event.
	 */
	public GameEvent(int ballID, int playerID, int gameID, String eventType, Date eventTime) {
		setBallID(ballID);
		setPlayerID(playerID);
		setGameID(gameID);
		setEventType(eventType);
		setEventTime(eventTime);
	}

	/**
	 * @return the player id.
	 */
	public int getPlayerID() {
		return playerID;
	}
	
	/**
	 * Sets the player id.
	 * 
	 * @param playerID - the player id.
	 */
	public void setPlayerID(int playerID) {
		this.playerID = playerID;
	}
	
	/**
	 * @return the game id.
	 */
	public int getGameID() {
		return gameID;
	}
	
	/**
	 * Sets the game id.
	 * 
	 * @param gameID - the game's id.
	 */
	public void setGameID(int gameID) {
		this.gameID = gameID;
	}
	
	/**
	 * @return the gameEvent's type of event.
	 */
	public String getEventType() {
		return eventType;
	}
	
	/**
	 * Sets the game type.
	 * 
	 * @param eventType - the event type.
	 */
	public void setEventType(String eventType) {
		this.eventType = eventType;
	}
	
	/**
	 * @return the game time creation.
	 */
	public Date getEventTime() {
		return eventTime;
	}
	
	/**
	 * Sets the game time creation.
	 * 
	 * @param eventTime - the gameEvent's time of event.
	 */
	public void setEventTime(Date eventTime) {
		this.eventTime = eventTime;
	}

	/**
	 * @return the ball's id.
	 */
	public int getBallID() {
		return ballID;
	}

	/**
	 *  Sets the ball's id.
	 * 
	 * @param ballID - the ball's id.
	 */
	public void setBallID(int ballID) {
		this.ballID = ballID;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "GameEvent [ballID=" + ballID + ", playerID=" + playerID + ", gameID=" + gameID + ", eventType="
				+ eventType + ", eventTime=" + eventTime + "]";
	}
}
