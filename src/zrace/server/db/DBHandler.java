package zrace.server.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import dbModels.Bet;
import dbModels.Car;
import dbModels.Race;
import dbModels.RaceResult;
import dbModels.User;
import dbModels.ZraceSystem;

/**
 * This Class provides an API compatible with DDHandler to control database
 * information.
 * 
 * @author Zina K
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

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Add User Object to the DB.
	 * 
	 * @param theUser
	 *            - User Class Object.
	 * @return return User or null if action failed.
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
	 * @return return Race or null if action failed.
	 */
	public synchronized Race insertRace(Race theRace) {
		String theQuery = " insert into Races (raceId, raceFullName, car1Id, car2Id, car3Id, car4Id, car5Id, isCompleted, startTime, endTime, duration, winnerCarId)"
				+ " values (?, ?, ?, ?, ?, ?, ?, ?, ? ,? ,?, ?)";
		try {
			PreparedStatement preparedStmt = theConnection.prepareStatement(
					theQuery, Statement.RETURN_GENERATED_KEYS);
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
			preparedStmt.setInt(12, theRace.getWinnerCarId());
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

		return null;

	}

	/**
	 * Get the main system information
	 * 
	 * @return
	 */
	public synchronized ZraceSystem getSystem() {
		String theQuery;
		theQuery = "select * from zracesystem";
		ResultSet result = executeQuery(theQuery);

		ArrayList<ZraceSystem> system = new ArrayList<>();
		if (result != null) {
			try {
				while (result.next()) {
					ZraceSystem systemObj;

					systemObj = new ZraceSystem(result.getInt("systemId"),
							result.getDouble("totalRevenue"));
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

	/**
	 * Return all bets that user made as array of objects
	 * 
	 * @param userId
	 * @return
	 */
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

	/**
	 * Return all bets that made for specific race
	 * 
	 * @param raceId
	 * @return
	 */
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

	/**
	 * Return all bets that made specific user for specific race
	 * 
	 * @param raceId
	 * @param userId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public synchronized ArrayList<Bet> getRaceBetsByUserID(int raceId,
			int userId) {
		String theQuery;
		theQuery = "select * from Bets where raceId = " + raceId
				+ "and userId = " + userId;
		ResultSet result = executeQuery(theQuery);

		return (ArrayList<Bet>) convertResultSetToArraylist(result, "Bet");

	}

	/**
	 * Return all bets that made for specific race
	 * 
	 * @param raceId
	 * @return
	 */
	public synchronized ResultSet getBetsByRaceID(int raceId) {
		String theQuery;
		theQuery = "select * from Bets where raceId = " + raceId;
		ResultSet result = executeQuery(theQuery);
		return result;
	}

	/**
	 * Return all bets that made for specific race
	 * 
	 * @param raceId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public synchronized ArrayList<Bet> getBetsByRaceIDAsArray(int raceId) {
		return (ArrayList<Bet>) convertResultSetToArraylist(
				getBetsByRaceID(raceId), "Bet");
	}

	/**
	 * Return bets of specific car
	 * 
	 * @param carId
	 * @return ArrayList<Bet>
	 */
	@SuppressWarnings("unchecked")
	public synchronized ArrayList<Bet> getCarBets(int carId) {
		String theQuery;
		theQuery = "select * from Bets where carId = " + carId;
		ResultSet result = executeQuery(theQuery);

		return (ArrayList<Bet>) convertResultSetToArraylist(result, "Bet");
	}

	/**
	 * Return race by race ID
	 * 
	 * @param raceId
	 * @return ResultSet
	 */
	public synchronized ResultSet getRaceById(int raceId) {
		String theQuery;

		if (raceId != -1) {
			theQuery = "select * from Races where raceId = " + raceId;

		} else {
			theQuery = "select * from Races";
		}

		return executeQuery(theQuery);
	}

	/**
	 * Return race by race ID
	 * 
	 * @param raceId
	 * @return Race
	 */
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

	/**
	 * Update races set isCompleted = true
	 * 
	 * @param race
	 * @return number of updated rows
	 */
	public synchronized int updateRaceCompleted(Race race) {
		String theQuery = " update Races set isCompleted = ? ,startTime = ?, endTime = ?, duration = ?, winnerCarId = ? where raceId = ?";

		/**
		 * update races set isCompleted = true, startTime = '2017-10-10
		 * 10:00:00', endTime = '2017-10-11 10:00:00' where raceId = 1;
		 */
		try {
			PreparedStatement preparedStmt = theConnection
					.prepareStatement(theQuery);
			preparedStmt.setBoolean(1, true);
			preparedStmt.setTimestamp(2, race.getStartTime());
			preparedStmt.setTimestamp(3, race.getEndTime());
			preparedStmt.setInt(4, race.getDuration());
			preparedStmt.setInt(5, race.getWinnerCarId());
			preparedStmt.setInt(6, race.getRaceId());

			int insertResult = preparedStmt.executeUpdate();

			preparedStmt.close();
			return insertResult;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
	}

	/**
	 * Update race name
	 * 
	 * @param race
	 * @return number of updated rows
	 */
	public synchronized int updateRaceName(Race race) {
		String theQuery = " update Races set raceFullName = ? where raceId = ?";

		try {
			PreparedStatement preparedStmt = theConnection
					.prepareStatement(theQuery);
			preparedStmt.setString(1, race.getRaceFullName());
			preparedStmt.setInt(2, race.getRaceId());

			int insertResult = preparedStmt.executeUpdate();

			preparedStmt.close();
			return insertResult;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
	}

	/**
	 * Update system revenue
	 * 
	 * @param amount
	 * @return number of updated rows
	 */
	public synchronized int updateSystemRevenue(Double amount) {
		ZraceSystem system = getSystem();
		if (system != null) {
			double currentRevenue = system.getSystemRevenue();
			currentRevenue = currentRevenue + amount;
			String theQuery = "update zracesystem set systemRevenue = "
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
	 * @return return a ResultSet that Contains all users in the database.
	 */
	public synchronized ResultSet getAllUsers() {
		return getUserByName("");
	}

	public synchronized ResultSet getAllCars() {
		return getCarByID(-1);
	}

	/**
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public synchronized ArrayList<Car> getAllCarsAsArray() {
		return (ArrayList<Car>) convertResultSetToArraylist(getAllCars(), "Car");
	}

	public synchronized ResultSet getAllRaces() {
		return getRaceById(-1);
	}

	/**
	 * @param raceID
	 * @return
	 */
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

	/**
	 * Return all not completed races 
	 * @param isCompleted
	 * @return ArrayList<Race>
	 */
	@SuppressWarnings("unchecked")
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

		String theQuery = "select * from Users where userFullName = '"
				+ userFullName + "'";
		return executeQuery(theQuery);

	}

	/**
	 * @param userFullName
	 * @return User
	 */
	public synchronized User getUserByNameAsObject(String userFullName) {
		return (User) convertResultSetToObject(getUserByName(userFullName),
				"User");
	}

	
	/**
	 * Return car by ID
	 * @param carID
	 * @return ResultSet
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
			// System.out.println(theQuery);
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
	 * Convert result set of car, user, race, bet to array of objects
	 * 
	 * @param result
	 * @param classType
	 * @return ArrayList<?>
	 */
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
									result.getInt("duration"),
									result.getInt("winnerCarId"));
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

		else if (classType.equals("User")) {
			ArrayList<User> users = new ArrayList<>();
			try {
				if (!result.next()) {
					System.out.println("no data found");
				} else {
					do {
						User user;

						user = new User(result.getInt("userId"),
								result.getString("userFullName"),
								result.getDouble("userRevenue"));

						users.add(user);
						System.out.println("found users " + users);

					} while (result.next());
					return users;
				}

			}

			catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return null;
		}
		return null;
	}

	/**
	 * Convert result set to Object
	 * 
	 * @param result
	 * @param objectType
	 * @return Object (Car, User, Bet, Race)
	 */
	public synchronized Object convertResultSetToObject(ResultSet result,
			String objectType) {
		@SuppressWarnings("unchecked")
		ArrayList<Object> object = (ArrayList<Object>) convertResultSetToArraylist(
				result, objectType);
		System.out.println("object is " + object);

		if (object != null)
			return object.get(0);

		return object;
	}

	/** DB viewer queries **/

	/**
	 * Return system balance
	 * 
	 * @return ResultSet
	 */
	public synchronized ResultSet getAllSystemRevenue() {
		// Return race results
		String theQuery = "select totalRevenue from zracesystem";
		return executeQuery(theQuery);
	}

	/**
	 * Return users balance
	 * 
	 * @return ResultSet
	 */
	public synchronized ResultSet getAllUsersRevenue() {
		String theQuery = "select * from users";
		return executeQuery(theQuery);
	}

	/**
	 * Get all races
	 * @return ResultSet
	 */
	public synchronized ResultSet getRacesStatus() {
		String theQuery = "select * from Races";
		return executeQuery(theQuery);
	}

	/**
	 * Return all bets of race
	 * @param raceId
	 * @return ResultSet
	 */
	public synchronized ResultSet getRaceBets(int raceId) {
		String theQuery = "select * from Bets where raceId = " + raceId;
		return executeQuery(theQuery);
	}

	/**
	 * Return all bets of user
	 * @param userId
	 * @return ResultSet
	 */
	public synchronized ResultSet getUserBets(int userId) {
		String theQuery = "select * from Bets where userId = " + userId;
		return executeQuery(theQuery);
	}

	/**
	 * Return all existing bets
	 * @return ResultSet
	 */
	public synchronized ResultSet getAllBets() {
		String theQuery = "select * from Bets";
		return executeQuery(theQuery);
	}

	/**
	 * Return cars info, number of participated races and wins
	 * @return ResultSet
	 */
	public synchronized ResultSet getAllCarsStatistics() {

		String theQuery = "select cars.carId, cars.carFullName, count(*) as number_of_races, temp.number_of_wins from cars "
				+ "left join races on (carId = car1Id or carId = car2Id or carId = car3Id or "
				+ "carId = car4Id or carId = car5Id) "
				+ "left join (select cars.carId, count(*) as number_of_wins from cars "
				+ "join races on cars.carId = races.winnerCarId group by cars.carId) as temp "
				+ "on cars.carId = temp.carId "
				+ "where races.isCompleted = true " + "group by cars.carId";
		return executeQuery(theQuery);
	}

	/**
	 * Get users balance
	 * @return ResultSet
	 */
	public synchronized ResultSet getUsersBalance() {
		String theQuery = "select users.userId, users.userFullName, users.userRevenue as total_balance from users";
		return executeQuery(theQuery);
	}

	/**
	 * Return user and system revenue by race
	 * @return ResultSet
	 */
	public synchronized ResultSet getRevenueByRace() {
		String theQuery = "select bets.raceId, races.raceFullName, users.userId, users.userFullName, raceresults.userRevenue, raceresults.systemRevenue from users "
				+ "join bets on users.userId = bets.userId "
				+ "join raceresults on bets.betId = raceresults.betId "
				+ "join races on bets.raceId = races.raceId "
				+ "group by bets.raceId order by bets.raceId";
		return executeQuery(theQuery);
	}

	/**
	 * Race bets number per car in race 
	 * @return ResultSet
	 */
	public synchronized ResultSet getRacesBets() {
		String theQuery = "select bets.raceId, races.raceFullName, total.total_bets, total.total_amount , car1.bets_on_car1, "
				+ "car2.bets_on_car2, car3.bets_on_car3,  "
				+ "car4.bets_on_car4,  car5.bets_on_car5 from bets "
				+ "join races on bets.raceId = races.raceId "
				+ "join (select bets.raceId, count(*) as total_bets, sum(bets.amount) as total_amount from bets group by bets.raceId) as total "
				+ "on bets.raceId = total.raceId                                                               "
				+ "left join                                                                                   "
				+ "(select bets.raceId, count(*) as bets_on_car1, sum(bets.amount) as car1_amount from bets    "
				+ "join races on bets.raceId = races.raceId                                                    "
				+ "where bets.carId = races.car1Id group by raceId) as car1                                    "
				+ "on bets.raceId = car1.raceId                                                                "
				+ "left join                                                                                   "
				+ "(select bets.raceId, count(*) as bets_on_car2, sum(bets.amount) as car2_amount from bets    "
				+ "join races on bets.raceId = races.raceId                                                    "
				+ "where bets.carId = races.car2Id group by raceId) as car2                                    "
				+ "on bets.raceId = car2.raceId                                                                "
				+ "left join                                                                                   "
				+ "(select bets.raceId, count(*) as bets_on_car3, sum(bets.amount) as car3_amount from bets    "
				+ "join races on bets.raceId = races.raceId                                                    "
				+ "where bets.carId = races.car3Id group by raceId) as car3                                    "
				+ "on bets.raceId = car3.raceId                                                                "
				+ "left join                                                                                   "
				+ "(select bets.raceId, count(*) as bets_on_car4, sum(bets.amount) as car4_amount from bets    "
				+ "join races on bets.raceId = races.raceId                                                    "
				+ "where bets.carId = races.car4Id group by raceId) as car4                                    "
				+ "on bets.raceId = car4.raceId                                                                "
				+ "left join                                                                                   "
				+ "(select bets.raceId, count(*) as bets_on_car5, sum(bets.amount) as car5_amount from bets    "
				+ "join races on bets.raceId = races.raceId                                                    "
				+ "where bets.carId = races.car5Id group by raceId) as car5                                    "
				+ "on bets.raceId = car5.raceId                                                                "
				+ "group by bets.raceId";
		return executeQuery(theQuery);
	}

}
