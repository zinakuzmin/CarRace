package main.runner;

import java.util.ArrayList;



import com.sun.corba.se.impl.activation.ServerMain;



import dbModels.Bet;
import dbModels.Car;
import dbModels.Race;
import dbModels.RaceResult;
import dbModels.User;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import zrace.client.ZRaceGameController;
import zrace.client.ZRaceMVCClient;
import zrace.client.app.MainClientApp;
import zrace.server.ServerController;
import zrace.server.ZRaceMVCServer;
import zrace.server.db.DBHandler;
import zrace.server.db.ZRaceGameDBScript;
import zrace.server.view.ServerMainView;

public class Runner extends Application {
	
	/**
	 * Create the client and open the client view
	 */
	private Button clientBtn = new Button("Start Client");

	/**
	 * Create the server socket and open the Server view
	 */
	private Button serverBtn = new Button("Start Server");
	
	/**
	 * runs the main stage of the application
	 * 
	 * @param primaryStage - main stage of the application.
	 * @throws Exception
	 */
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		
		if (RunParameters.SHOULD_INIT_DB)
			ZRaceGameDBScript.runScript();
		
		dbTests();
		
		serverBtn.setOnAction(e -> {
//			new ZRaceMVCServer().start(new Stage());
			try {
				ServerController controller = new ServerController(new Stage());
//				new ServerMainView().start(new Stage());
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			serverBtn.setDisable(true);
		});
		
		clientBtn.setOnAction(e -> {
			try {
//				new MainClientApp().start(new Stage());
				ZRaceGameController raceClient = new ZRaceGameController(new Stage());
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
//			new ZRaceMVCClient().start(new Stage());
		});
		
		HBox btnHBox = new HBox(50);
		btnHBox.getChildren().addAll(serverBtn,clientBtn);
		
		BorderPane pane = new BorderPane();
		
		pane.setCenter(btnHBox);
		
		BackgroundImage myBI= new BackgroundImage(new Image(RunParameters.IMG_RUNNER_BACKGROUND),
				BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,
				new BackgroundSize(500, 150, false, false, false, false));
		pane.setBackground(new Background(myBI));
		
		btnHBox.translateXProperty().bind(pane.widthProperty().divide(4));
		btnHBox.translateYProperty().bind(pane.heightProperty().add(-100));
		
		
		Scene scene = new Scene(pane, 500, 150);
		primaryStage.setTitle("ZRace Game!"); //main pane title
		primaryStage.setScene(scene);
		primaryStage.show(); //to display the stage
//		primaryStage.setAlwaysOnTop(true);
		primaryStage.setResizable(false);
		primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
			public void handle(WindowEvent event) {	
				Platform.exit();
				System.exit(0);
			}
		});
	}
	
	
	public void dbTests(){
		System.out.println("----create user------");
		
		DBHandler db = new DBHandler();
		User user = new User("newUser", 10003, 10.2);
		db.insertUser(user);
		System.out.println("user created" + user.toString());
		
		System.out.println("----check is user exist------");
		System.out.println(db.isUserExist(user));
		
		System.out.println("----get user by id -----");
		System.out.println(db.getUserById(user.getUserID()));
		
		
		System.out.println("------create race-----");
		Race race = new Race();
		race.setRaceId(1003);
		race.setRaceFullName("insertedRace");
		race.setCar1Id(2001);
		race.setCar2Id(2002);
		race.setCar3Id(2003);
		race.setEndTime(null);
		race.setStartTime(null);
		System.out.println("race is inserted " + db.insertRace(race));
		
		System.out.println("------insert car -----");
		Car car = new Car();
		car.setCarId(2005);
		car.setCarFullName("dima best car");
		System.out.println("car inserted " + db.insertCar(car));
		
		System.out.println("----insert bet------");
		Bet bet = new Bet();
		bet.setBetId(3003);
		bet.setRaceId(1003);
		bet.setCarId(2005);
		bet.setUserId(10003);
		System.out.println("bet inserted " + db.insertBet(bet));
		
		
		System.out.println("---insert raceResult----");
		RaceResult rr = new RaceResult();
		rr.setRaceId(1003);
		rr.setBetId(3003);
		rr.setWinner(true);
		rr.setUserRevenue(22.4);
		rr.setSystemRevenue(1.2);
		System.out.println("inserted raceresult " + db.insertRaceResult(rr));
		
		System.out.println("---get user bets----");
		System.out.println(db.getUserBetsAsArray(10003));
		
		System.out.println("---get race bets----");
		System.out.println(db.getRaceBets(1003));
		
		System.out.println("---get car bets----");
		System.out.println(db.getCarBets(2005));
		
		
		System.out.println("-----get all cars----");
		ArrayList<Car> cars = db.getAllCarsAsArray();
		System.out.println(cars);
		
		ArrayList<Car> selectedCars = new ArrayList<Car>();
		
		if (cars.size() >= RunParameters.NUMBER_OF_CARS_IN_RACE){
			while (selectedCars.size() < 3){
				int randomNum = 0 + (int)(Math.random() * (RunParameters.NUMBER_OF_CARS_IN_RACE)); 
				System.out.println("Random num " + randomNum);
				if (!selectedCars.contains(cars.get(randomNum))){
					selectedCars.add(cars.get(randomNum));
				}
			}
			System.out.println("Selected cars " + selectedCars);
		}
		
		
		Race race1 = new Race();
		race1.setCar1Id(selectedCars.get(0).getCarId());
		race1.setCar2Id(selectedCars.get(1).getCarId());
		race1.setCar3Id(selectedCars.get(2).getCarId());
		race1.setCompleted(false);
		race1.setRaceId(1004);
		race1.setRaceFullName("race-" + race1.getRaceId());
		
		db.insertRace(race1);
		System.out.println("---car inserted----");
		System.out.println(db.getRaceByIdAsObject(race1.getRaceId()));
		
		
		
		
	}
	
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		launch(args);
	}

}
