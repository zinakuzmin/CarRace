package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import DBModels.Game;
import DBModels.GameEvent;
import DBModels.Player;

/**
 * This Class provides an API compatible with DataBaseHandler to control database information.
 * 
 * @author Moran Tenzer & Ben Nave
 *
 */
public class DataBaseHandler {

	/**
	 * {@code connection} - the connection with the sql database server.
	 */
	private Connection theConnection;

	/**
	 * Create an Handler type DataBaseHandler. 
	 */
	public DataBaseHandler() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			theConnection = DriverManager.getConnection("jdbc:mysql://localhost/cannongamedb?useSSL=false", "scott",
					"tiger");
			System.out.println("Database successfully connected");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Adding a Player Object to the DB.
	 * 
	 * @param thePlayer - Player Class Object.
	 * @return return Boolean if the player added to the database.
	 */
	public synchronized boolean insertPlayer(Player thePlayer) {
		String theQuery = " insert into Players (playerId, playerFullName)" + " values (?, ?)";
		try {
			PreparedStatement preparedStmt = theConnection.prepareStatement(theQuery);
			preparedStmt.setInt(1, thePlayer.getplayerID());
			preparedStmt.setString(2, thePlayer.getPlayerFullName());

			preparedStmt.execute();
			preparedStmt.close();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * @param thePlayer - a Player Class Object.
	 * @return return Boolean if playerID is already exist in DB.
	 */
	public synchronized boolean isIdExist(Player thePlayer) {
		String theQuery = " select  * from Players where playerId = '" + thePlayer.getplayerID() + "'";
		ResultSet res = executeQuery(theQuery);
		try {
			if (res.first())
				return true;
		} catch (SQLException ex) {
			ex.printStackTrace();
		}

		return false;
	}

	/**
	 * @param thePlayer - a Player Class Object.
	 * @return return Boolean checking player name & id if already exixt in DB.
	 */
	public synchronized boolean isPlayerExist(Player thePlayer) {
		String theQuery = " select  * from Players where playerId = '" + thePlayer.getplayerID() + "' and playerFullName = '"
				+ thePlayer.getPlayerFullName() + "'";
		ResultSet res = executeQuery(theQuery);
		try {
			if (res.first())
				return true;
		} catch (SQLException ex) {
			ex.printStackTrace();
		}

		return false;
	}

	/**
	 * @param theGame - a Game Class Object.
	 * @return return Boolean if the game  added to the DB.
	 */
	public synchronized boolean insertGame(Game theGame) {
		String theQuery = " insert into Games (playerId, gameId,startedTime,gameScore,difficulty)"
				+ " values (?, ?, ?, ?, ?)";
		try {
			PreparedStatement preStat = theConnection.prepareStatement(theQuery);
			preStat.setInt(1, theGame.getPlayerID());
			preStat.setInt(2, theGame.getGameID());
			java.sql.Timestamp date = new java.sql.Timestamp(theGame.getStartedTime().getTime());
			preStat.setTimestamp(3, date);
			preStat.setDouble(4, theGame.getGameScore());
			preStat.setString(5, theGame.getGameLevel());

			preStat.execute();
			preStat.close();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * @param gameEventsList - a ArrayList The Contains GameEvent Class Objects.
	 * @return return Boolean if the gameEvetsList added to the DB.
	 */
	public synchronized boolean insertEvent(ArrayList<GameEvent> gameEventsList) {
		boolean hasInsetred = true;
		for(GameEvent gameEvent : gameEventsList)
			if( !insertGameEvent(gameEvent) )
				hasInsetred = false;
		return hasInsetred;
	}
	
	/**
	 * @param theGameEvent - a GameEvent Class Object.
	 * @return return Boolean if the gameEvent added to DB.
	 */
	public synchronized boolean insertGameEvent(GameEvent theGameEvent) {
		String theQuery = " insert into GamesEvents (playerId, gameId, eventType , eventTime)" + " values (?, ?, ?, ?)";
		try {
			PreparedStatement preStat = theConnection.prepareStatement(theQuery);
			preStat.setInt(1, theGameEvent.getPlayerID());
			preStat.setInt(2, theGameEvent.getGameID());
			preStat.setString(3, theGameEvent.getEventType());
			java.sql.Timestamp theDate = new java.sql.Timestamp(theGameEvent.getEventTime().getTime());
			preStat.setTimestamp(4, theDate);
			

			preStat.execute();
			preStat.close();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * @param playerId - a player id.
	 * @param nameToUpdate - new player name value.
	 * @return return true in the name was updated successfully in the database.
	 */
	public synchronized boolean updatePlayerFullName(int playerId,String nameToUpdate) {
		String theQuery = "UPDATE Players SET playerFullName = ? WHERE playerId = ? ";
		try {
			PreparedStatement preStat = theConnection.prepareStatement(theQuery);

			preStat.setString(1, nameToUpdate);
			preStat.setInt(2, playerId);

			preStat.executeUpdate();
			preStat.close();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * Boolean if a game already exist in DB & updating the gameScore database value.
	 * 
	 * @param theGame - a Game Class Object.
	 * @return return true in the game.gameScore was updated successfully in the database.
	 */
	public synchronized boolean updateGameScore(Game theGame) {
		String theQuery = "UPDATE Games SET gameScore = ? WHERE playerId = ? AND gameId = ?";
		try {
			PreparedStatement preStat = theConnection.prepareStatement(theQuery);

			preStat.setDouble(1, theGame.getGameScore());
			preStat.setInt(2, theGame.getPlayerID());
			preStat.setInt(3, theGame.getGameID());

			preStat.executeUpdate();
			preStat.close();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * This Method get's a player and return a new value of gameID. 
	 * 
	 * @param thePlayer - a Player Class Object.
	 * @return  return a new gameID(int) value.
	 */
	public synchronized int getPlayerNewGameID(Player thePlayer) {
		String theQuery = " select gameId from Games where playerId = '" + thePlayer.getplayerID() + "'"
				+ "order by gameId DESC limit 1";
		ResultSet res = executeQuery(theQuery);
		try {
			if (res.first()) {
				int gameID = res.getInt(1);
				gameID++;
				return gameID;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return 1;
	}

	/**
	 * @return return a ResultSet that Contains all games of all players in the database.
	 */
	public synchronized ResultSet getAllGames() {
		String theQuery;
		theQuery = "select * from Games order by gameScore";
		return executeQuery(theQuery);
	}

	/**
	 * @param playerId - a player id.
	 * @return return a ResultSet that Contains all games that fit to the player playerId.
	 */
	public synchronized ResultSet getAllPlayerGames(int playerId) {
		String theQuery;
		theQuery = "select * from Games where playerId = '" + playerId + "' order by startedTime";
		return executeQuery(theQuery);
	}

	/**
	 * @return return a ResultSet that Contains all players in the database.
	 */
	public synchronized ResultSet getAllPlayers() {
		return getPlayerByName("");
	}

	/**
	 * @param playerFullName - a string name value.
	 * @return return a ResultSet that Contains a player with same name as playerFullName.
	 */
	public synchronized ResultSet getPlayerByName(String playerFullName) {

		String theQuery;
		if (!playerFullName.equals(""))
			theQuery = "select playerId,playerFullName from Players where playerFullName = '" + playerFullName
					+ "' order by playerFullName";
		else
			theQuery = "select playerId,playerFullName from Players order by playerFullName";
		return executeQuery(theQuery);

	}

	/**
	 * @param theQuery - a query that expects to return a result set.
	 * @return return a ResultSet according to the query. else return null.
	 */
	public synchronized ResultSet executeQuery(String theQuery) {
		try {
			Statement stat = theConnection.createStatement();
			return stat.executeQuery(theQuery);
		} catch (SQLException e) {

			e.printStackTrace();
		}
		return null;
	}

	/**
	 * @param theQuery - a query that doesn't expects to return a result set.
	 */
	public synchronized void executeSqlQuery(String theQuery) {
		try {
			Statement stat = theConnection.createStatement();
			stat.execute(theQuery);
		} catch (SQLException e) {

			e.printStackTrace();
		}
	}

	/**
	 * this method delete all information the bind to playerID.
	 * 
	 * @param playerID - a player id .
	 */
	public synchronized void delPlayer(int playerID) {

		String delQueryFromPlayers = "delete from Players where playerId = '" + playerID + "'";
		executeSqlQuery(delQueryFromPlayers);

	}

	/**
	 * this method delete a game that match the gameID and it's player id match playerID.
	 * 
	 * @param playerID -  a player id.
	 * @param gameID - a game id.
	 */
	public synchronized void delGame(int playerID, int gameID) {

		String delQueryFromGames = "delete from Games where playerId = '" + playerID + "' and gameId = '" + gameID
				+ "'";
		executeSqlQuery(delQueryFromGames);
	}

	/**
	 * @param playerID - a player id.
	 * @param gameID - a game id.
	 * @return return a ResultSet that contains all gameEvents and it's player id match playerID and game id match gameID.
	 */
	public synchronized ResultSet getPlayerGameEvents(int playerID, int gameID) {
		String theQuery;
		theQuery = "select * from GamesEvents where playerId = '" + playerID + "' and gameId = '" + gameID
				+ "' order by eventTime";

		return executeQuery(theQuery);
	}

	/**
	 * @return return a ResultSet that contains all players ,that played 3 times or more, best 3 game score average. 
	 */
	public synchronized ResultSet getTopThree() {
		String theQuery;
		theQuery =
				"select players.playerId,players.playerFullName,avg(new_table.scores) as avg_score "+
				"from players,"+
				"(select ranker.r_ids as ids,ranker.r_score as scores "+
				"from "+
				"(select games.playerId as r_ids,games.gameScore as r_score,"+
						"@game_counter := IF(@game_id = games.playerId,@game_counter + 1,1) as game_counter,"+
						"@game_id := games.playerId "+
				        "from games "+
				        "order by games.playerId,games.gameScore DESC"+
				        ") as ranker,"+
				"(select games.playerId as id,count(games.gameId) as ctr "+
						"from games "+
						"group by games.playerId) as gamecounter "+
				"where ranker.r_ids = gamecounter.id and game_counter <= 3 and gamecounter.ctr >= 3 ) as new_table "+
				"where players.playerId = new_table.ids "+
				"group by players.playerId";
		try {
			Statement stat = theConnection.createStatement();
			stat.execute(theQuery);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return executeQuery(theQuery);
	}
}
