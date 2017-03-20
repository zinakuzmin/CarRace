package zrace.client.view;

import java.util.ArrayList;

import zrace.client.ZRaceGameController;
import zrace.client.app.world.cars.CarResources;
import dbModels.Bet;
import dbModels.Race;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class BetView extends Application{
	private ZRaceGameController gameController;
	private Race race;
	private int userID;
	private TextField car1BetAmount;
	private TextField car2BetAmount;
	private TextField car3BetAmount;
	private TextField car4BetAmount;
	private TextField car5BetAmount;
	private Button betBtn;
	
	
	
	public BetView(ZRaceGameController gameController,Race race,int userID ) {
		this.gameController = gameController;
		this.race = race;
		this.userID = userID;
		car1BetAmount = new TextField();
		car2BetAmount = new TextField();
		car3BetAmount = new TextField();
		car4BetAmount = new TextField();
		car5BetAmount = new TextField();
		
	}
	
	
	public static void main(String[] args){
		Application.launch(args);
	}
	

	@Override
	public void start(Stage stage) throws Exception {
		// TODO Auto-generated method stub
		buildBetView(stage);
		
	}
	
	private void buildBetView(Stage stage){
		GridPane grid = new GridPane();
		grid.setAlignment(Pos.CENTER);
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(25, 25, 25, 25));
		
		
		
		Label raceNamelbl = new Label(race.getRaceFullName() + race.getRaceId());
		Label car1Namelbl = new Label("Car1: " + race.getCar1Id() + " " + CarResources.getCarByUid(race.getCar1Id()));
		Label car2Namelbl = new Label("Car2: " + race.getCar2Id() + " " + CarResources.getCarByUid(race.getCar2Id()));
		Label car3Namelbl = new Label("Car3: " + race.getCar3Id() + " " + CarResources.getCarByUid(race.getCar3Id()));
		Label car4Namelbl = new Label("Car4: " + race.getCar4Id() + " " + CarResources.getCarByUid(race.getCar4Id()));
		Label car5Namelbl = new Label("Car5: " + race.getCar5Id() + " " + CarResources.getCarByUid(race.getCar5Id()));

		betBtn = new Button("Make a bet!");
		betBtn.setDisable(true);
		
		grid.add(raceNamelbl, 0, 0, 2, 1);
		grid.add(car1Namelbl, 0, 1);
		grid.add(car1BetAmount, 1, 1);
		grid.add(car2Namelbl, 0, 2);
		grid.add(car2BetAmount, 1, 2);
		grid.add(car3Namelbl, 0, 3);
		grid.add(car3BetAmount, 1, 3);
		grid.add(car4Namelbl, 0, 4);
		grid.add(car4BetAmount, 1, 4);
		grid.add(car5Namelbl, 0, 5);
		grid.add(car5BetAmount, 1, 5);
		
		
		HBox hbBtn = new HBox(10);
		hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
		hbBtn.getChildren().add(betBtn);
		grid.add(hbBtn, 1, 6);
		
		
		car1BetAmount.textProperty().addListener((observable, oldValue, newValue) -> {
//		    System.out.println("textfield changed from " + oldValue + " to " + newValue);
		    if (!newValue.isEmpty()){
		    	try {
		    		Integer.parseInt(newValue);
		    		betBtn.setDisable(false);
		    	}
		    	catch (Exception e){
		    		System.out.println(e.getStackTrace());
		    	}
		    }
		});
		
		car2BetAmount.textProperty().addListener((observable, oldValue, newValue) -> {
//		    System.out.println("textfield changed from " + oldValue + " to " + newValue);
		    if (!newValue.isEmpty()){
		    	try {
		    		Integer.parseInt(newValue);
		    		betBtn.setDisable(false);
		    	}
		    	catch (Exception e){
		    		System.out.println(e.getStackTrace());
		    	}
		    }
		});
		
		car3BetAmount.textProperty().addListener((observable, oldValue, newValue) -> {
//		    System.out.println("textfield changed from " + oldValue + " to " + newValue);
		    if (!newValue.isEmpty()){
		    	try {
		    		Integer.parseInt(newValue);
		    		betBtn.setDisable(false);
		    	}
		    	catch (Exception e){
		    		System.out.println(e.getStackTrace());
		    	}
		    }
		});
		
		car4BetAmount.textProperty().addListener((observable, oldValue, newValue) -> {
//		    System.out.println("textfield changed from " + oldValue + " to " + newValue);
		    if (!newValue.isEmpty()){
		    	try {
		    		Integer.parseInt(newValue);
		    		betBtn.setDisable(false);
		    	}
		    	catch (Exception e){
		    		System.out.println(e.getStackTrace());
		    	}
		    }
		});
		
		car5BetAmount.textProperty().addListener((observable, oldValue, newValue) -> {
//		    System.out.println("textfield changed from " + oldValue + " to " + newValue);
		    if (!newValue.isEmpty()){
		    	try {
		    		Integer.parseInt(newValue);
		    		betBtn.setDisable(false);
		    	}
		    	catch (Exception e){
		    		System.out.println(e.getStackTrace());
		    	}
		    }
		});
		
		
		betBtn.setOnAction(e -> createBets(stage));
		
		
		Scene scene = new Scene(grid, 400, 375);
		stage.setScene(scene);
		stage.show();
	}
	
	public synchronized void createBets(Stage stage){
		ArrayList<Bet> bets = new ArrayList<Bet>();
		if (!car1BetAmount.getText().isEmpty()){
			Bet bet = generateBet(car1BetAmount.getText(), race.getRaceId(), race.getCar1Id());
			if (bet != null)
				bets.add(bet);
		}
		
		if (!car2BetAmount.getText().isEmpty()){
			Bet bet = generateBet(car2BetAmount.getText(), race.getRaceId(), race.getCar2Id());
			if (bet != null)
				bets.add(bet);
		}
		
		if (!car3BetAmount.getText().isEmpty()){
			Bet bet = generateBet(car3BetAmount.getText(), race.getRaceId(), race.getCar3Id());
			if (bet != null)
				bets.add(bet);
		}
		
		if (!car4BetAmount.getText().isEmpty()){
			Bet bet = generateBet(car4BetAmount.getText(), race.getRaceId(), race.getCar4Id());
			if (bet != null)
				bets.add(bet);
		}
		
		if (!car5BetAmount.getText().isEmpty()){
			Bet bet = generateBet(car5BetAmount.getText(), race.getRaceId(), race.getCar5Id());
			if (bet != null)
				bets.add(bet);
		}
		
		gameController.sendBetsToServer(bets);
		betBtn.setDisable(true);
		stage.close();
		
	}
	
	public synchronized Bet generateBet(String betAmount, int raceId, int carId){
		Bet bet = null;
		try{
			double amount = Integer.parseInt(betAmount);
			if (amount > 0){
				bet = new Bet(0, race.getRaceId(), carId, userID, amount, null);	
			}	
		}
		catch (Exception e){
    		System.out.println(e.getStackTrace());
    	}
		return bet;
	}
	

}
