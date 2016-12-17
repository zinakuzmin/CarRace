package DBModels;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

/**
 * This class provides an API compatible with Game.
 * 
 * @author Moran Tenzer & Ben Nave
 *
 */
public class Game implements Serializable{
	/**
	 * use to calculate game score when the game level is "Beginner".
	 */
	private static final double BEGINNER_LEVEL_PARAM = 100;
	/**
	 * use to calculate game score when the game level is "Advanced".
	 */
	private static final double ADVANCED_LEVEL_PARAM = 150;
	/**
	 * use to calculate game score when the game level is "Expert".
	 */
	private static final double EXPERT_LEVEL_PARAM = 200;
	
	/**
	 * a game optional game level - "Beginner".
	 */
	private static final String BEGINNER_LEVEL = "Beginner";
	/**
	 * a game optional game level - "Advanced".
	 */
	private static final String ADVANCED_LEVEL = "Advanced";
	/**
	 * a game optional game level - "Expert".
	 */
	private static final String EXPERT_LEVEL = "Expert";
	/**
	 * Static value of long serialVersionUID {@value}
	 */ 
	static final long serialVersionUID = 1L;
	/**
	 * {@code playerID} - the id of the player.
	 */
	private int playerID;
	/**
	 * {@code gameID} - the id of the game.
	 */
	private int gameID;
	/**
	 * {@code startedTime} - the time game started.
	 */
	private	Date startedTime;
	/**
	 * {@code gameScore} - the game score - calculated only when game is finished.
	 */
	private double gameScore;
	/**
	 * {@code difficulty} - the game level. can be one of : "Beginner" , "Advanced" , "Expert".
	 */
	private String gameLevel;
	
	
	
	/**
	 * This Constructor create a new instance of a Game and assign it's values.
	 * 
	 * @param playerID - the player's id.
	 * @param gameID - the games's id.
	 * @param startedTime - the game's start time.
	 * @param gameScore - the game's score.
	 * @param difficulty - the game's difficulty.
	 */ 
	public Game(int playerID, int gameID, Date startedTime, double gameScore, String gameLevel) {
		this.playerID 	 = playerID;
		this.gameID 	 = gameID;
		this.startedTime = startedTime;
		this.gameScore 	 = gameScore;
		this.gameLevel 	 = gameLevel;
	}

	/**
	 * @return the player's id.
	 */
	public int getPlayerID() {
		return playerID;
	}
	
	/**
	 * This method sets the player's id.
	 * 
	 * @param playerID - the players's id.
	 */
	public void setPlayerID(int playerID) {
		this.playerID = playerID;
	}
	
	/**
	 * @return the game's id.
	 */
	public int getGameID() {
		return gameID;
	}
	
	/**
	 * This method sets the game's id.
	 * 
	 * @param gameID - the game's id.
	 */
	public void setGameID(int gameID) {
		this.gameID = gameID;
	}
	
	/**
	 * @return the game's start time.
	 */
	public Date getStartedTime() {
		return startedTime;
	}
	
	/**
	 * This method sets the game's start time.
	 * 
	 * @param startedTime - the game's start time.
	 */
	public void setStartedTime(Date startedTime) {
		this.startedTime = startedTime;
	}
	
	/**
	 * @return the game's score.
	 */
	public double getGameScore() {
		return gameScore;
	}
	
	/**
	 * This method sets the game's score.
	 * 
	 * @param gameScore - the game's score.
	 */
	public void setGameScore(double gameScore) {
		this.gameScore = gameScore;
	}
	
	/**
	 * @return the game's gameLevel.
	 */
	public String getGameLevel() {
		return gameLevel;
	}
	
	/**
	 * This method sets the game's gameLevel.
	 * 
	 * @param difficulty - the game's gameLevel.
	 */
	public void setGameLevel(String gameLevel) {
		this.gameLevel = gameLevel;
	}

	/**
	 * This method calculates the final score of this game according to hit/miss rate and the game's lavel.
	 * 
	 * @param gameEventsList - list of all the events in the game(hit/miss events).
	 */
	public void calcGameScore(ArrayList<GameEvent> gameEventsList) {
		double hitCounter = 0;
		double totalEventsCounter = 0;
		for(GameEvent gameEvent : gameEventsList) {
			totalEventsCounter++;
			if(gameEvent.getEventType().equals(GameEvent.EVENT_TYPE_HIT))
				hitCounter++;
		}
		
		setGameScore( (hitCounter / totalEventsCounter) * fromDifficultyToParam()  );
	}
	
	/**
	 * @return the value that's matches the game's level.
	 */
	private double fromDifficultyToParam() {
		
		if(getGameLevel().equals(BEGINNER_LEVEL))
			return BEGINNER_LEVEL_PARAM;
		else if(getGameLevel().equals(ADVANCED_LEVEL))
			return ADVANCED_LEVEL_PARAM;
		else if(getGameLevel().equals(EXPERT_LEVEL))
			return EXPERT_LEVEL_PARAM;
		
		return 0;
		
	}
}
