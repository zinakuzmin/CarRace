package zrace.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;

import main.runner.RunParameters;
import zrace.server.db.DBHandler;
import zrace.server.view.ServerMainView;
import dbModels.Bet;
import dbModels.Car;
import dbModels.Race;
import dbModels.RaceResult;
import dbModels.User;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.control.TextArea;
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
	
	
	
	

	public ServerController(Stage primaryStage) {
		try {
			serverSocket = new ServerSocket(8000);
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		logActivity = new TextArea();
		setLogger(new  ServerLogger(logActivity));
		try {
			new ServerMainView(logActivity).start(new Stage());
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
			public void handle(WindowEvent event) {
				disconnectAllConnectedClients();
				try {
					serverSocket.close();
				} catch (IOException e) {
				}
			}
		});
		
		
		
		db = new DBHandler();
		activeRaces = new ArrayList<Race>();
		initServer();
	}
	
	
	public void initServer(){

		
		activeClients = new ArrayList<>();
		
		
		new Thread(() -> {
			try {
				
				Platform.runLater(() -> {
					logActivity.appendText("Zrace Game Server connected at " + new Date() + '\n');
				});
				while (true) {
					System.out.println("server: waiting for connection");
					socket = serverSocket.accept();
					System.out.println("server: accepted connection");
					ClientHandler client = new ClientHandler(socket, this);
					activeClients.add(client);
					System.out.println("init server: run client handler thread");
					new Thread(client).start();
				}
			} catch (IOException ex) {
				System.err.println(ex);
			}
		}).start();
	}
	
	
	public void setActiveRaces(){
		ArrayList<Race> notCompletedRaces = db.getAllActiveRacesAsArray(false);
		if (notCompletedRaces.size() < RunParameters.NUMBER_OF_CARS_IN_RACE){
			int missingRaces = RunParameters.NUMBER_OF_CARS_IN_RACE - notCompletedRaces.size();
			activeRaces.addAll(notCompletedRaces);
			for (int i = 0; i < missingRaces; i++){
				try {
					activeRaces.add(generateRace());
				} catch (Exception e) {
					System.out.println("Could not add new race");
					e.printStackTrace();
				}
			}
		}
		
	}
	
	//Number of cars in Race class should be configurable!
	//Add ID generator
	public Race generateRace() throws Exception{
		ArrayList<Car> cars = db.getAllCarsAsArray();
		System.out.println(cars);
		
		ArrayList<Car> selectedCars = new ArrayList<Car>();
		
		//Choose randomly cars for race
		if (cars.size() >= RunParameters.NUMBER_OF_CARS_IN_RACE){
			while (selectedCars.size() < 3){
				int randomNum = 0 + (int)(Math.random() * (RunParameters.NUMBER_OF_CARS_IN_RACE)); 
				if (!selectedCars.contains(cars.get(randomNum))){
					selectedCars.add(cars.get(randomNum));
				}
			}
			System.out.println(selectedCars);
			
			Race race = new Race();
			race.setCar1Id(selectedCars.get(0).getCarId());
			race.setCar2Id(selectedCars.get(1).getCarId());
			race.setCar3Id(selectedCars.get(2).getCarId());
			race.setCompleted(false);
			race.setRaceId(1004);
			race.setRaceFullName("race-" + race.getRaceId());
			
			db.insertRace(race);
			return race;
		}
		
		else{
			throw new Exception("Not enough cars available for race");
		}			
		
	}
	
	
	public synchronized void userLoginOrRegister(int userId, String userFullName){
		User user = db.getUserById(userId);
		if (user != null){
			if (user.getUserFullName().equals(userFullName))
				System.out.println("login of existing user submitted");
			else{
				System.out.println("login failed - existing userID with different name");
				new Exception("login failed - existing userID with different name");
			}
		}
		
		else {
			User newUser = new User(userFullName, userId, 0);
			db.insertUser(newUser);
			System.out.println("Created a new user " + newUser);
		}
	}
	
	
	public synchronized void addClientToActiveClients(ClientHandler client){
		activeClients.add(client);
	}
	
	public synchronized void removeClientFromActiveClients(ClientHandler client){
		activeClients.remove(client);
	}
	
	public synchronized void registerBet(Bet bet){
		db.insertBet(bet);
	}
	
	public synchronized void completeRace(Race race, int winnerCarId){
		ArrayList<Bet> bets = db.getBetsByRaceIDAsArray(race.getRaceId());
		if (bets != null){
			for (Bet bet : bets) {
				Double betAmount = bet.getAmount();
				RaceResult raceResult = new RaceResult();
				raceResult.setWinner(true);
				raceResult.setRaceId(race.getRaceId());
				raceResult.setBetId(bet.getBetId());
				if (bet.getCarId() == winnerCarId){
					raceResult.setUserRevenue(betAmount - betAmount*0.1);
					raceResult.setSystemRevenue(betAmount*0.1);
				}
				else{
					raceResult.setUserRevenue(betAmount - betAmount*RunParameters.SYSTEM_COMISSION);
					raceResult.setSystemRevenue(betAmount*RunParameters.SYSTEM_COMISSION);
				}
				db.insertRaceResult(raceResult);
				db.updateUserRevenue(db.getUserById(bet.getUserId()));
				db.updateRaceCompleted(race);
				db.updateSystemRevenue(raceResult.getSystemRevenue());
			}
		}
		
		
	}
	
	
	public Race checkIfOneOfRacesCanRun(){
//		int numberOfCarsWithBet = 0;
		double totalSumInBets = 0;
//		int raceReadyToRunIndex = -1;
		double maxBetAmount = 0;
		Race runRace = null;
		
		HashMap<Race, Double> possibleReadyRace = new HashMap<>();
		
		//Loop over the active races (isCompleted = false) and check if is has enough bets
		for (Race race : activeRaces) {
			HashMap<Integer, Integer> carsWithBet = new HashMap<>();
			ArrayList<Bet>  bets = db.getBetsByRaceIDAsArray(race.getRaceId());
			for (Bet bet : bets) {
				totalSumInBets += bet.getAmount();
				carsWithBet.put(bet.getCarId(), 1);
			}
			if (carsWithBet.size() >= RunParameters.NUMBER_OF_CARS_IN_RACE_THAT_HAVE_BET)
				possibleReadyRace.put(race, totalSumInBets);
		}
		
		//If there are several ready to run races, the one with more bets amount is chosen
		Iterator<Race> iterator = possibleReadyRace.keySet().iterator();
		while (iterator.hasNext()){
			Race readyRace = iterator.next();
			if (maxBetAmount < possibleReadyRace.get(readyRace)){
				maxBetAmount = possibleReadyRace.get(readyRace);
				runRace = readyRace;
			}
		}
		return runRace;
	}
	
	public void disconnectAllConnectedClients(){
		for(ClientHandler hc : activeClients){
//			if(hc.isClientConnected())
//				hc.closeConnection();
			
		}
	}


	public ServerLogger getLogger() {
		return logger;
	}


	public void setLogger(ServerLogger logger) {
		this.logger = logger;
	}
	


}
