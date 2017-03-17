package zrace.server.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.sun.swing.internal.plaf.synth.resources.synth;

import dbModels.*;

/**
 * This Class provides an API compatible with DataBaseHandler to control
 * database information.
 * 
 * @author Zina Kuzmin
 *
 */

public class DBHandler {

	/**
	 * {@code connection} - the connection with the sql database server.
	 */
	private Connection theConnection;

	/**
	 * Create an Handler type DBHandler.
	 */
	public DBHandler() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			theConnection = DriverManager.getConnection(
					"jdbc:mysql://localhost/zracedb?useSSL=false", "scott",
					"tiger");
			System.out.println("Database successfully connected");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Add User Object to the DB.
	 * 
	 * @param theUser
	 *            - User Class Object.
	 * @return return Boolean if the user added to the database.
	 */
	public synchronized User insertUser(User theUser) {
		String theQuery = " insert into Users (userId, userFullName, userRevenue)"
				+ " values (?, ?, ?)";
		try {
			PreparedStatement preparedStmt = theConnection.prepareStatement(
					theQuery, Statement.RETURN_GENERATED_KEYS);
			preparedStmt.setInt(1, 0);
			preparedStmt.setString(2, theUser.getUserFullName().toLowerCase());
			preparedStmt.setDouble(3, theUser.getUserRevenue());

			preparedStmt.execute();
			ResultSet rs = preparedStmt.getGeneratedKeys();
			rs.next();
			int auto_id = rs.getInt(1);
			theUser.setUserID(auto_id);

			preparedStmt.close();
			return theUser;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return theUser;

	}

	/**
	 * Add Race Object to the DB.
	 * 
	 * @param theRace
	 *            - Race Class Object.
	 * @return return Boolean if the race added to the database.
	 */
	public synchronized Race insertRace(Race theRace) {
		String theQuery = " insert into Races (raceId, raceFullName, car1Id, car2Id, car3Id, car4Id, car5Id, isCompleted, startTime, endTime, duration)"
				+ " values (?, ?, ?, ?, ?, ?, ?, ?, ? ,? ,?)";
		try {
			PreparedStatement preparedStmt = theConnection
					.prepareStatement(theQuery, Statement.RETURN_GENERATED_KEYS);
			preparedStmt.setInt(1, 0);
			preparedStmt.setString(2, theRace.getRaceFullName());
			preparedStmt.setInt(3, theRace.getCar1Id());
			preparedStmt.setInt(4, theRace.getCar2Id());
			preparedStmt.setInt(5, theRace.getCar3Id());
			preparedStmt.setInt(6, theRace.getCar4Id());
			preparedStmt.setInt(7, theRace.getCar5Id());
			preparedStmt.setBoolean(8, theRace.isCompleted());
			preparedStmt.setTimestamp(9, theRace.getStartTime());
			preparedStmt.setTimestamp(10, theRace.getEndTime());
			preparedStmt.setInt(11, theRace.getDuration());

			preparedStmt.execute();
			ResultSet rs = preparedStmt.getGeneratedKeys();
			rs.next();
			int auto_id = rs.getInt(1);
			theRace.setRaceId(auto_id);
			preparedStmt.close();
			return theRace;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return theRace;
	}

	/**
	 * Add Car Object to the DB.
	 * 
	 * @param theCar
	 *            - Car Class Object.
	 * @return return Boolean if the car added to the database.
	 */
	public synchronized boolean insertCar(Car theCar) {
		String theQuery = " insert into Cars (carId, carFullName)"
				+ " values (?, ?)";
		try {
			PreparedStatement preparedStmt = theConnection
					.prepareStatement(theQuery);
			preparedStmt.setInt(1, theCar.getCarId());
			preparedStmt.setString(2, theCar.getCarFullName());

			preparedStmt.execute();
			preparedStmt.close();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * Add Bet Object to the DB.
	 * 
	 * @param theBet
	 *            - Bet Class Object.
	 * @return return Boolean if the bet added to the database.
	 */
	public synchronized boolean insertBet(Bet theBet) {
		String theQuery = " insert into Bets (betId, raceId, carId, userId, amount, betTime)"
				+ " values (?, ?, ?, ?, ?, ?)";
		try {
			PreparedStatement preparedStmt = theConnection
					.prepareStatement(theQuery);
			preparedStmt.setInt(1, 0);
			preparedStmt.setInt(2, theBet.getRaceId());
			preparedStmt.setInt(3, theBet.getCarId());
			preparedStmt.setInt(4, theBet.getUserId());
			preparedStmt.setDouble(5, theBet.getAmount());
			preparedStmt.setTimestamp(6, theBet.getBetTime());

			preparedStmt.execute();
			preparedStmt.close();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * Add RaceResult Object to the DB.
	 * 
	 * @param theRaceResult
	 *            - RaceResult Class Object.
	 * @return Boolean if the raceResult added to the database.
	 */
	public synchronized boolean insertRaceResult(RaceResult theRaceResult) {
		String theQuery = " insert into RaceResults (raceId, betId, isWinner, userRevenue, systemRevenue)"
				+ " values (?, ?, ?, ?, ?)";
		try {
			PreparedStatement preparedStmt = theConnection
					.prepareStatement(theQuery);
			preparedStmt.setInt(1, theRaceResult.getRaceId());
			preparedStmt.setInt(2, theRaceResult.getBetId());
			preparedStmt.setBoolean(3, theRaceResult.getIsWinner());
			preparedStmt.setDouble(4, theRaceResult.getUserRevenue());
			preparedStmt.setDouble(5, theRaceResult.getSystemRevenue());

			preparedStmt.execute();
			preparedStmt.close();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * @param theUser
	 *            - a User Class Object.
	 * @return return Boolean if userID is already exist in DB.
	 */
	public synchronized boolean isIdExist(User theUser) {
		String theQuery = " select  * from Users where userId = '"
				+ theUser.getUserID() + "'";
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
	 * @param theUser
	 *            - a User Class Object.
	 * @return return Boolean checking user name & id if already exist in DB.
	 */
	public synchronized boolean isUserExist(User theUser) {
		String theQuery = " select  * from Users where userId = '"
				+ theUser.getUserID() + "' and userFullName = '"
				+ theUser.getUserFullName().toLowerCase() + "'";
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
	 * @param userId
	 *            - unique id of user.
	 * @return return User if exists in DB otherwise return null.
	 */

	public synchronized User getUserById(int userId) {
		String theQuery;
		theQuery = "select * from Users where userId = " + userId;
		ResultSet result = executeQuery(theQuery);

		ArrayList<User> users = new ArrayList<>();

		try {
			if (!result.next()) {
				System.out.println("no data found");
			} else {

				do {
					User user;

					user = new User(result.getString("userFullName"),
							result.getInt("userId"),
							result.getDouble("userRevenue"));
					users.add(user);
				} while (result.next());
				return users.get(0);
			}
		}

		catch (Exception e) {
			e.printStackTrace();
		}

		// if (result != null) {
		// try {
		// while (!result.next()) {
		// User user;
		//
		// user = new User(result.getString("userFullName"),
		// result.getInt("userId"),
		// result.getDouble("userRevenue"));
		// users.add(user);
		// }
		// return users.get(0);
		// }
		//
		// catch (SQLException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		// } else
		// System.out.println("Result is null");

		return null;

	}

	public synchronized ZraceSystem getSystem() {
		String theQuery;
		theQuery = "select * from System";
		ResultSet result = executeQuery(theQuery);

		ArrayList<ZraceSystem> system = new ArrayList<>();
		if (result != null) {
			try {
				while (result.next()) {
					ZraceSystem systemObj;

					systemObj = new ZraceSystem(result.getInt("systemIdId"),
							result.getDouble("systemRevenue"));
					system.add(systemObj);
				}
				return system.get(0);
			}

			catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else
			System.out.println("Result is null");

		return null;

	}

	@SuppressWarnings("unchecked")
	public synchronized ArrayList<Bet> getUserBetsAsArray(int userId) {
		try {
			return (ArrayList<Bet>) convertResultSetToArraylist(
					getUserBets(userId), "Bet");
		} catch (Exception e) {
			System.out.println(e.getStackTrace());
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public synchronized ArrayList<Bet> getRaceBetsAsArray(int raceId) {
		try {
			return (ArrayList<Bet>) convertResultSetToArraylist(
					getRaceBets(raceId), "Bet");
		} catch (Exception e) {
			System.out.println(e.getStackTrace());
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public synchronized ArrayList<Bet> getRaceBetsByUserID(int raceId,
			int userId) {
		String theQuery;
		theQuery = "select * from Bets where raceId = " + raceId
				+ "and userId = " + userId;
		ResultSet result = executeQuery(theQuery);

		return (ArrayList<Bet>) convertResultSetToArraylist(result, "Bet");

	}

	public synchronized ResultSet getBetsByRaceID(int raceId) {
		String theQuery;
		theQuery = "select * from Bets where raceId = " + raceId;
		ResultSet result = executeQuery(theQuery);
		return result;
	}

	public synchronized ArrayList<Bet> getBetsByRaceIDAsArray(int raceId) {
		return (ArrayList<Bet>) convertResultSetToArraylist(
				getBetsByRaceID(raceId), "Bet");
	}

	/**
	 * @param carId
	 *            - unique id of car.
	 * @return return Bets of specified car if exist in DB otherwise return
	 *         null.
	 */

	public synchronized ArrayList<Bet> getCarBets(int carId) {
		String theQuery;
		theQuery = "select * from Bets where carId = " + carId;
		ResultSet result = executeQuery(theQuery);

		return (ArrayList<Bet>) convertResultSetToArraylist(result, "Bet");
	}

	public synchronized ResultSet getRaceById(int raceId) {
		String theQuery;

		if (raceId != -1) {
			theQuery = "select * from Races where raceId = " + raceId;

		} else {
			theQuery = "select * from Races";
		}

		return executeQuery(theQuery);
	}
	

	public synchronized Race getRaceByIdAsObject(int raceId) {
		return (Race) convertResultSetToObject(getRaceById(raceId), "Race");
	}

	public synchronized int updateUserRevenue(User user) {
		String theQuery = " update Users set userRevenue = ? where userId = ?";

		try {
			PreparedStatement preparedStmt = theConnection
					.prepareStatement(theQuery);
			preparedStmt.setDouble(1, user.getUserRevenue());
			preparedStmt.setInt(2, user.getUserID());

			int updateResult = preparedStmt.executeUpdate();
			preparedStmt.close();
			return updateResult;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
	}

	public synchronized int updateRaceCompleted(Race race) {
		String theQuery = " update Races set isCompleted = ? where raceId = ?";

		try {
			PreparedStatement preparedStmt = theConnection
					.prepareStatement(theQuery);
			preparedStmt.setBoolean(1, true);
			preparedStmt.setInt(2, race.getRaceId());

			int insertResult = preparedStmt.executeUpdate();

			preparedStmt.close();
			return insertResult;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
	}

	public synchronized int updateSystemRevenue(Double amount) {
		ZraceSystem system = getSystem();
		if (system != null) {
			double currentRevenue = system.getSystemRevenue();
			currentRevenue = currentRevenue + amount;
			String theQuery = "update System set systemRevenue = "
					+ currentRevenue;

			try {
				PreparedStatement preparedStmt = theConnection
						.prepareStatement(theQuery);
				// preparedStmt.setBoolean(1, true);
				// preparedStmt.setInt(2, race.getRaceId());

				int insertResult = preparedStmt.executeUpdate();

				preparedStmt.close();
				return insertResult;
			} catch (Exception e) {
				e.printStackTrace();
			}
			return -1;
		}
		return -1;

	}

	

	/**
	 * @param gameEventsList
	 *            - a ArrayList The Contains GameEvent Class Objects.
	 * @return return Boolean if the gameEvetsList added to the DB.
	 */
	// public synchronized boolean insertEvent(ArrayList<GameEvent>
	// gameEventsList) {
	// boolean hasInsetred = true;
	// for(GameEvent gameEvent : gameEventsList)
	// if( !insertGameEvent(gameEvent) )
	// hasInsetred = false;
	// return hasInsetred;
	// }

	/**
	 * @param theGameEvent
	 *            - a GameEvent Class Object.
	 * @return return Boolean if the gameEvent added to DB.
	 */
	// public synchronized boolean insertGameEvent(GameEvent theGameEvent) {
	// String theQuery =
	// " insert into GamesEvents (userId, gameId, eventType , eventTime)" +
	// " values (?, ?, ?, ?)";
	// try {
	// PreparedStatement preStat = theConnection.prepareStatement(theQuery);
	// preStat.setInt(1, theGameEvent.getUserID());
	// preStat.setInt(2, theGameEvent.getGameID());
	// preStat.setString(3, theGameEvent.getEventType());
	// java.sql.Timestamp theDate = new
	// java.sql.Timestamp(theGameEvent.getEventTime().getTime());
	// preStat.setTimestamp(4, theDate);
	//
	//
	// preStat.execute();
	// preStat.close();
	// return true;
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// return false;
	// }

	/**
	 * @param userId
	 *            - a user id.
	 * @param nameToUpdate
	 *            - new user name value.
	 * @return return true in the name was updated successfully in the database.
	 */
	
	/**
	 * Boolean if a game already exist in DB & updating the gameScore database
	 * value.
	 * 
	 * @param theGame
	 *            - a Game Class Object.
	 * @return return true in the game.gameScore was updated successfully in the
	 *         database.
	 */
	
	/**
	 * @return return a ResultSet that Contains all games of all users in the
	 *         database.
	 */
	public synchronized ResultSet getAllGames() {
		String theQuery;
		theQuery = "select * from Games order by gameScore";
		return executeQuery(theQuery);
	}

	/**
	 * @param userId
	 *            - a user id.
	 * @return return a ResultSet that Contains all games that fit to the user
	 *         userId.
	 */
	public synchronized ResultSet getAllUserGames(int userId) {
		String theQuery;
		theQuery = "select * from Games where userId = '" + userId
				+ "' order by startedTime";
		return executeQuery(theQuery);
	}

	/**
	 * @return return a ResultSet that Contains all users in the database.
	 */
	public synchronized ResultSet getAllUsers() {
		return getUserByName("");
	}

	public synchronized ResultSet getAllCars() {
		return getCarByID(-1);
	}

	public synchronized ArrayList<Car> getAllCarsAsArray() {
		return (ArrayList<Car>) convertResultSetToArraylist(getAllCars(), "Car");
	}

	public synchronized ResultSet getAllRaces() {
		return getRaceById(-1);
	}

	@SuppressWarnings("unchecked")
	public synchronized ArrayList<Race> getAllRacesAsArray(String raceID) {
		return (ArrayList<Race>) convertResultSetToArraylist(getAllRaces(),
				"Race");
	}

	public synchronized ResultSet getAllActiveRaces(boolean isCompleted) {

		String theQuery = "select * from Races where isCompleted = "
				+ isCompleted;

		return executeQuery(theQuery);
	}

	public synchronized ArrayList<Race> getAllActiveRacesAsArray(
			boolean isCompleted) {
		return (ArrayList<Race>) convertResultSetToArraylist(
				getAllActiveRaces(isCompleted), "Race");
	}

	/**
	 * @param userFullName
	 *            - a string name value.
	 * @return return a ResultSet that Contains a user with same name as
	 *         userFullName.
	 */
	public synchronized ResultSet getUserByName(String userFullName) {

		String theQuery;
		if (!userFullName.equals(""))
			theQuery = "select userId,userFullName from Users where userFullName = '"
					+ userFullName + "' order by userFullName";
		else
			theQuery = "select userId,userFullName from Users order by userFullName";
		return executeQuery(theQuery);

	}

	/**
	 * @param userFullName
	 *            - a string name value.
	 * @return return a ResultSet that Contains a user with same name as
	 *         userFullName.
	 */
	public synchronized ResultSet getCarByID(int carID) {

		String theQuery;
		if (carID != -1)
			theQuery = "select * from Cars where carId = '" + carID
					+ "' order by carId";
		else
			theQuery = "select * from Cars order by carId";
		return executeQuery(theQuery);

	}

	/**
	 * @param theQuery
	 *            - a query that expects to return a result set.
	 * @return return a ResultSet according to the query. else return null.
	 */
	public synchronized ResultSet executeQuery(String theQuery) {
		try {
			Statement stat = theConnection.createStatement();
			System.out.println(theQuery);
			return stat.executeQuery(theQuery);
		} catch (SQLException e) {

			e.printStackTrace();
		}
		return null;
	}

	/**
	 * @param theQuery
	 *            - a query that doesn't expects to return a result set.
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
	 * this method delete all information the bind to userID.
	 * 
	 * @param userID
	 *            - a user id .
	 */
	public synchronized void delUser(int userID) {

		String delQueryFromUsers = "delete from Users where userId = '"
				+ userID + "'";
		executeSqlQuery(delQueryFromUsers);

	}

	/**
	 * @return return a ResultSet that contains all users ,that played 3 times
	 *         or more, best 3 game score average.
	 */
	public synchronized ResultSet getTopThree() {
		String theQuery;
		theQuery = "select users.userId,users.userFullName,avg(new_table.scores) as avg_score "
				+ "from users,"
				+ "(select ranker.r_ids as ids,ranker.r_score as scores "
				+ "from "
				+ "(select games.userId as r_ids,games.gameScore as r_score,"
				+ "@game_counter := IF(@game_id = games.userId,@game_counter + 1,1) as game_counter,"
				+ "@game_id := games.userId "
				+ "from games "
				+ "order by games.userId,games.gameScore DESC"
				+ ") as ranker,"
				+ "(select games.userId as id,count(games.gameId) as ctr "
				+ "from games "
				+ "group by games.userId) as gamecounter "
				+ "where ranker.r_ids = gamecounter.id and game_counter <= 3 and gamecounter.ctr >= 3 ) as new_table "
				+ "where users.userId = new_table.ids "
				+ "group by users.userId";
		try {
			Statement stat = theConnection.createStatement();
			stat.execute(theQuery);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return executeQuery(theQuery);
	}

	public synchronized ArrayList<?> convertResultSetToArraylist(
			ResultSet result, String classType) {

		if (classType.equals("Car")) {
			ArrayList<Car> cars = new ArrayList<>();
			if (result != null) {
				try {
					while (result.next()) {
						Car car;

						car = new Car(result.getInt("carId"),
								result.getString("carFullName"));
						cars.add(car);
					}
					return cars;
				}

				catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else
				System.out.println("Result is null");

			return null;
		}

		else if (classType.equals("Race")) {
			if (result != null) {
				ArrayList<Race> races = new ArrayList<>();
				if (result != null) {
					try {
						while (result.next()) {
							Race race;

							race = new Race(result.getInt("raceId"),
									result.getString("raceFullName"),
									result.getInt("car1Id"),
									result.getInt("car2Id"),
									result.getInt("car3Id"),
									result.getInt("car4Id"),
									result.getInt("car5Id"),
									result.getBoolean("isCompleted"),
									result.getTimestamp("startTime"),
									result.getTimestamp("endTime"),
									result.getInt("duration"));
							races.add(race);

						}
						return races;
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}

		else if (classType.equals("Bet")) {
			ArrayList<Bet> bets = new ArrayList<>();
			if (result != null) {
				try {
					while (result.next()) {
						Bet bet;

						bet = new Bet(result.getInt("betId"),
								result.getInt("raceId"),
								result.getInt("carId"),
								result.getInt("userId"),
								result.getDouble("amount"),
								result.getTimestamp("betTime"));
						bets.add(bet);
					}
					return bets;
				}

				catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else
				System.out.println("Result is null");
		}

		return null;

	}

	public synchronized Object convertResultSetToObject(ResultSet result,
			String objectType) {
		Object object = null;

		if (result != null) {
			object = convertResultSetToArraylist(result, objectType).get(0);
		}

		return object;
	}

	/** DB viewer **/
	public synchronized ResultSet getAllSystemRevenue() {
		// Return race results
		String theQuery = "select raceId, systemRevenue from RaceResults group by raceId order by systemRevenue desc";
		return executeQuery(theQuery);
	}

	public synchronized ResultSet getAllUsersRevenue() {
		// Return race results
		String theQuery = "select raceId, userRevenue from RaceResults group by raceId order by userRevenue desc";
		return executeQuery(theQuery);
	}

	public synchronized ResultSet getRacesStatus() {
		String theQuery = "select * from Races";
		return executeQuery(theQuery);
	}

	public synchronized ResultSet getRaceBets(int raceId) {
		String theQuery = "select * from Bets where raceId = " + raceId;
		return executeQuery(theQuery);
	}

	public synchronized ResultSet getUserBets(int userId) {
		String theQuery = "select * from Bets where userId = " + userId;
		return executeQuery(theQuery);
	}

	public synchronized ResultSet getAllBets() {
		String theQuery = "select * from Bets";
		return executeQuery(theQuery);
	}

	// public synchronized ResultSet getCarsStatistics() {
	// String theQuery = "select carId, carFullName from Cars where userId = " +
	// userId;
	// return executeQuery(theQuery);
	// }

}
