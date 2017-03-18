package zrace.server;

import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Random;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;

import com.sun.swing.internal.plaf.synth.resources.synth;

import main.runner.RunParameters;
import zrace.client.app.world.cars.objs.Songs;
import zrace.client.app.world.cars.objs.abstracts.CarPositionCalculator;
import zrace.client.app.world.cars.objs.abstracts.CarPositionCalculator.CalculatedCarInRace;
import zrace.protocol.Message;
import zrace.protocol.UserDetailsMsg;
import zrace.protocol.WinnerCarMsg;
import zrace.server.db.DBHandler;
import zrace.server.view.ServerMainView;
import dbModels.Bet;
import dbModels.Car;
import dbModels.Race;
import dbModels.RaceResult;
import dbModels.RaceRun;
import dbModels.RaceRun.CarInRace;
import dbModels.RaceRun.RaceStatus;
import dbModels.User;
import dbModels.ZraceSystem;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.control.TextArea;
import javafx.scene.media.Media;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class ServerController {
	private DBHandler db;
	private ArrayList<Race> activeRaces;
	private ArrayList<ClientHandler> activeClients;
	private ServerSocket serverSocket;
	private Socket socket;
	private TextArea logActivity;
	private ServerLogger logger;
	private ArrayList<RaceRun> raceRuns;
	private RacesMonitor racesMonitorThread;

	public ServerController(Stage primaryStage) {
		try {
			serverSocket = new ServerSocket(8000);
			logActivity = new TextArea();
			setLogger(new ServerLogger(logActivity));
			racesMonitorThread = new RacesMonitor(this);
			new ServerMainView(logActivity).start(new Stage());
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
			public void handle(WindowEvent event) {

				try {
					disconnectAllConnectedClients();
					racesMonitorThread.setShouldRun(false);
					serverSocket.close();
				} catch (IOException e) {
				}
			}
		});

		db = new DBHandler();
		activeRaces = new ArrayList<Race>();
		raceRuns = new ArrayList<RaceRun>();
		setActiveRaces();
		System.out.println("Active races " + activeRaces);

//		for (int i = 0; i < activeRaces.size(); i++) {
//			raceRuns.add(generateRaceRun(activeRaces.get(i)));
//		}
		initServer();
	}

	public void initServer() {

		activeClients = new ArrayList<>();

		new Thread(() -> {
			try {

				Platform.runLater(() -> {
					logActivity.appendText("Zrace Game Server connected at "
							+ new Date() + '\n');
				});
				while (true) {
					System.out.println("server: waiting for connection");
					socket = serverSocket.accept();
					System.out.println("server: accepted connection");
					ClientHandler client = new ClientHandler(socket, this);
					// activeClients.add(client);
				System.out.println("init server: run client handler thread");
				new Thread(client).start();
			}
		} catch (IOException ex) {
			System.err.println(ex);
		}
	}	).start();

		new Thread(racesMonitorThread).start();
	}

	public void setActiveRaces() {

		// Get from DB where isCompleted = false
		ArrayList<Race> notCompletedRaces = db.getAllActiveRacesAsArray(false);

		System.out.println("not completed races " + notCompletedRaces);
		if (notCompletedRaces.size() < RunParameters.NUMBER_OF_ACTIVE_RACES) {
			int missingRaces = RunParameters.NUMBER_OF_ACTIVE_RACES
					- notCompletedRaces.size();
//			activeRaces.addAll(notCompletedRaces);
			
			for (int i = 0; i < notCompletedRaces.size(); i++){
				activeRaces.add(notCompletedRaces.get(i));
				raceRuns.add(generateRaceRun(activeRaces.get(i)));
			}
			
			for (int i = 0; i < missingRaces; i++) {
				try {
					activeRaces.add(generateRace());
				} catch (Exception e) {
					System.out.println("Could not add new race");
					e.printStackTrace();
				}
			}
		} else {
			for (int i = 0; i < RunParameters.NUMBER_OF_ACTIVE_RACES; i++) {
				activeRaces.add(notCompletedRaces.get(i));
				raceRuns.add(generateRaceRun(activeRaces.get(i)));
			}
		}

	}

	public Race generateRace() throws Exception {
		ArrayList<Car> cars = db.getAllCarsAsArray();
		System.out.println(cars);

		ArrayList<Car> selectedCars = new ArrayList<Car>();

		// Choose randomly cars for race
		if (cars.size() >= RunParameters.NUMBER_OF_CARS_IN_RACE) {
			while (selectedCars.size() < RunParameters.NUMBER_OF_CARS_IN_RACE) {
				int randomNum = 0 + (int) (Math.random() * (cars.size()));
				if (!selectedCars.contains(cars.get(randomNum))) {
					selectedCars.add(cars.get(randomNum));
				}
			}
			System.out.println("cars selected for race " + selectedCars);

			Race race = new Race();
			race.setCar1Id(selectedCars.get(0).getCarId());
			race.setCar2Id(selectedCars.get(1).getCarId());
			race.setCar3Id(selectedCars.get(2).getCarId());
			race.setCar4Id(selectedCars.get(3).getCarId());
			race.setCar5Id(selectedCars.get(4).getCarId());
			race.setCompleted(false);
			race.setRaceId(0);
			race.setRaceFullName("Race");


			race = db.insertRace(race);
			System.out.println("generated race " + race);
			
			race.setRaceFullName("Race-" + race.getRaceId());
			db.updateRaceName(race);
			raceRuns.add(generateRaceRun(race));
			return race;
		}

		else {
			throw new Exception("Not enough cars available for race");
		}

	}

	public synchronized User userLoginOrRegister(int userId, String userFullName) {
		User user = db.getUserByNameAsObject(userFullName);
		// User user = db.getUserById(userId);
		if (user != null) {
			if (!isUserAlreadyLoggedIn(userFullName)) {
				System.out.println("login of existing user submitted");
				return user;
			}

			else {
				System.out.println("login failed - user already logged in");
				new Exception("login failed - user already logged in");
				return null;
			}
		}

		else {
			User newUser = new User(userFullName, userId,
					RunParameters.USER_INITIAL_AMOUNT_OF_MONEY);
			newUser = db.insertUser(newUser);
			System.out.println("Created a new user " + newUser);
			return newUser;
		}
	}

	public synchronized void addClientToActiveClients(ClientHandler client) {
		activeClients.add(client);
	}

	public synchronized boolean isUserAlreadyLoggedIn(String userFullName) {
		boolean isLoggedIn = false;
		for (ClientHandler clientHandler : activeClients) {
			if (clientHandler != null) {
				if (!clientHandler.getUserFullName().isEmpty()) {
					if (clientHandler.getUserFullName().equals(userFullName)) {
						isLoggedIn = true;
						return isLoggedIn;
					}

				}
			}
		}
		return isLoggedIn;
	}

	public synchronized void removeClientFromActiveClients(ClientHandler client) {
		activeClients.remove(client);
	}

	public synchronized void registerBet(Bet bet) {
		db.insertBet(bet);
	}

	public synchronized void completeRace(Race race, int winnerCarId) {
		ArrayList<Bet> bets = db.getBetsByRaceIDAsArray(race.getRaceId());
		if (bets != null) {
			for (Bet bet : bets) {
				User user = db.getUserById(bet.getUserId());
				Double betAmount = bet.getAmount();
				RaceResult raceResult = new RaceResult();
				raceResult.setWinner(true);
				raceResult.setRaceId(race.getRaceId());
				raceResult.setBetId(bet.getBetId());
				if (bet.getCarId() == winnerCarId) {
					raceResult.setUserRevenue(betAmount - betAmount * RunParameters.SYSTEM_COMISSION);
					raceResult.setSystemRevenue(betAmount * RunParameters.SYSTEM_COMISSION);
					user.setUserRevenue(user.getUserRevenue() + raceResult.getUserRevenue());
					
				} else {
					raceResult.setUserRevenue(0);
					raceResult.setSystemRevenue(betAmount);
					user.setUserRevenue(user.getUserRevenue() - betAmount);
					
				}
				sendUserDetailsUpdateToClient(user);
				db.insertRaceResult(raceResult);
				db.updateUserRevenue(user);
				db.updateRaceCompleted(race);
				db.updateSystemRevenue(raceResult.getSystemRevenue());
				
			}
		}

	}

	public synchronized Race checkIfOneOfRacesCanRun() {
		// int numberOfCarsWithBet = 0;
		double totalSumInBets = 0;
		// int raceReadyToRunIndex = -1;
		double maxBetAmount = 0;
		Race runRace = null;

		HashMap<Race, Double> possibleReadyRace = new HashMap<>();

		// Loop over the active races (isCompleted = false) and check if is has
		// enough bets
		for (Race race : activeRaces) {
			HashMap<Integer, Integer> carsWithBet = new HashMap<>();
			ArrayList<Bet> bets = db.getBetsByRaceIDAsArray(race.getRaceId());
			for (Bet bet : bets) {
				totalSumInBets += bet.getAmount();
				carsWithBet.put(bet.getCarId(), 1);
			}
			if (carsWithBet.size() >= RunParameters.NUMBER_OF_CARS_IN_RACE_THAT_HAVE_BET)
				possibleReadyRace.put(race, totalSumInBets);
		}

		// If there are several ready to run races, the one with more bets
		// amount is chosen
		Iterator<Race> iterator = possibleReadyRace.keySet().iterator();
		while (iterator.hasNext()) {
			Race readyRace = iterator.next();
			if (maxBetAmount < possibleReadyRace.get(readyRace)) {
				maxBetAmount = possibleReadyRace.get(readyRace);
				runRace = readyRace;
			}
		}
		return runRace;
	}

	public synchronized boolean isRunningRace() {
		boolean foundRunningRace = false;
		for (Race race : activeRaces) {
			if (race.getStartTime() != null) {
				if (race.getStartTime().compareTo(
						new Timestamp(System.currentTimeMillis())) <= 0 && getRaceRunByRaceId(race.getRaceId()).getRaceStatus().equals(RaceStatus.in_progress)) {
					foundRunningRace = true;
					System.out.println("there is running race "
							+ foundRunningRace);
					return foundRunningRace;
				}
			}
		}
		System.out.println("there is running race " + foundRunningRace);
		return foundRunningRace;
	}

	public void disconnectClient(ClientHandler client) {
		activeClients.remove(client);
	}

	public void disconnectAllConnectedClients() {
		for (ClientHandler hc : activeClients) {
			// if(hc.isClientConnected())
			// hc.closeConnection();

		}
	}

	public ServerLogger getLogger() {
		return logger;
	}

	public void setLogger(ServerLogger logger) {
		this.logger = logger;
	}

	public synchronized ArrayList<Race> getActiveRaces() {
		return activeRaces;
	}

	public synchronized RaceRun generateRaceRun(Race race) {
		int songId = 0 + (int) (Math.random() * (RunParameters.NUMBER_OF_SONGS));
		ArrayList<CarInRace> cars = new ArrayList<RaceRun.CarInRace>();
		cars.add(new CarInRace(race.getCar1Id(), 0));
		cars.add(new CarInRace(race.getCar2Id(), 1));
		cars.add(new CarInRace(race.getCar3Id(), 2));
		cars.add(new CarInRace(race.getCar4Id(), 3));
		cars.add(new CarInRace(race.getCar5Id(), 4));
		RaceRun raceRun = new RaceRun(race.getRaceId(), RaceStatus.waiting,
				Songs.getSongByUid(songId), cars);
		return raceRun;
	}

	public synchronized ArrayList<RaceRun> getRaceRuns() {
		return raceRuns;
	}

	public synchronized RaceRun getRaceRunByRaceId(int raceId) {
		for (RaceRun raceRun : raceRuns) {
			if (raceRun.getRaceId() == raceId)
				return raceRun;
		}
		return null;
	}

	public synchronized Race getActiveRaceByRaceId(int raceId) {
		for (Race race : activeRaces) {
			if (race.getRaceId() == raceId)
				return race;
		}
		return null;
	}

	public synchronized RaceRun getRaceRunByStatus(RaceStatus status) {
		for (RaceRun raceRun : raceRuns) {
			if (raceRun.getRaceStatus().equals(status))
				return raceRun;
		}
		return null;
	}

	public synchronized void setRaceRuns(ArrayList<RaceRun> raceRuns) {
		this.raceRuns = raceRuns;
	}

//	// Return song duration in seconds
//	public synchronized int getSongDuration(Songs song) {
//		
//			String bip = song.getSongName();
//			Media hit = new Media(new File(bip).toURI().toString());
//			
//			int durationInSeconds = (int) hit.getDuration().toSeconds();
//			System.out.println("duration in seconds is " + durationInSeconds);
//			
//			
//		
//		
//	}

	public void sendBroadcastMessage(Message message) {
		for (ClientHandler client : activeClients) {
			try {
				System.out.println("server send to all clients message "
						+ message);
				client.getStreamToClient().reset();
				client.getStreamToClient().writeObject(message);
				client.getStreamToClient().flush();
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public synchronized void sendUserDetailsUpdateToClient(User user){
		for (ClientHandler client : activeClients) {
			try {
				if (client.getUserFullName().equals(user.getUserFullName())){
					UserDetailsMsg message = new UserDetailsMsg(0, user);
					System.out.println("server send to client message " + client + " "
							+ message);
					client.getStreamToClient().reset();
					client.getStreamToClient().writeObject(message);
					client.getStreamToClient().flush();
					return;
				}

				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
//	public synchronized void sendBroadcastWinnerCar(WinnerCarMsg message){
//		for (ClientHandler client : activeClients) {
//			try {
//				
//					
//					System.out.println("server send to client message " + client + " "
//							+ message);
//					client.getStreamToClient().reset();
//					client.getStreamToClient().writeObject(message);
//					client.getStreamToClient().flush();
//					
//				
//
//				
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}
//	}

	public synchronized int getRaceWinnerCarID(RaceRun raceRun) {
		int carWinner = -1;
		float carMilage = -1;
		for (CarInRace car : raceRun.getCarsInRace()) {
			CarPositionCalculator.CalculatedCarInRace carRun = CarPositionCalculator.calculateTotalMilageOfCar(car.getRadius(), car.getSpeedList(), raceRun.getSong().getDuraionInSeconds()*1000);
			System.out.println("Car " + car.getUid() + " passed " + carRun.getTotalMilage() + " in " + raceRun.getSong().getDuraionInSeconds()*1000);
			if (carRun.getTotalMilage() > carMilage){
				carWinner = car.getUid();
				carMilage = carRun.getTotalMilage();	
			}
		}
		
//		CalculatedCarInRace max = Collections.max(calcs, Comparator.comparing(CarPositionCalculator.CalculatedCarInRace::getTotalMilage));
        System.out.println("Car won the race:" + carWinner);

		return carWinner;
	}

}
