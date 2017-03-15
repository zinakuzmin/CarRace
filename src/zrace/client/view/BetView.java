package zrace.client.view;

import java.util.ArrayList;

import zrace.client.ZRaceGameController;
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
	
	
	public BetView(ZRaceGameController gameController,Race race,int userID ) {
		this.gameController = gameController;
		this.race = race;
		this.userID = userID;
		car1BetAmount = new TextField();
		car2BetAmount = new TextField();
		car3BetAmount = new TextField();
		
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
		
		
		Label raceNamelbl = new Label(race.getRaceFullName());
		Label car1Namelbl = new Label("Car1");
		Label car2Namelbl = new Label("Car2");
		Label car3Namelbl = new Label("Car3");
//		TextField car1BetAmount = new TextField();
//		TextField car2BetAmount = new TextField();
//		TextField car3BetAmount = new TextField();
		Button betBtn = new Button("Make a bet!");
		betBtn.setDisable(true);
		
		grid.add(raceNamelbl, 0, 0, 2, 1);
		grid.add(car1Namelbl, 0, 1);
		grid.add(car1BetAmount, 1, 1);
		grid.add(car2Namelbl, 0, 2);
		grid.add(car2BetAmount, 1, 2);
		grid.add(car3Namelbl, 0, 3);
		grid.add(car3BetAmount, 1, 3);
		
		
		HBox hbBtn = new HBox(10);
		hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
		hbBtn.getChildren().add(betBtn);
		grid.add(hbBtn, 1, 5);
		
		
		car1BetAmount.textProperty().addListener((observable, oldValue, newValue) -> {
		    System.out.println("textfield changed from " + oldValue + " to " + newValue);
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
		    System.out.println("textfield changed from " + oldValue + " to " + newValue);
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
		    System.out.println("textfield changed from " + oldValue + " to " + newValue);
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
		
		
		Scene scene = new Scene(grid, 300, 275);
		stage.setScene(scene);
		stage.show();
	}
	
	public synchronized void createBets(Stage stage){
		ArrayList<Bet> bets = new ArrayList<Bet>();
		if (!car1BetAmount.getText().isEmpty()){
			try{
				double amount = Integer.parseInt(car1BetAmount.getText());
				Bet bet = new Bet(3005, race.getRaceId(), race.getCar1Id(), userID, amount, null);
				bets.add(bet);
				stage.close();
			}
			catch (Exception e){
	    		System.out.println(e.getStackTrace());
	    	}
		}
		
		if (!car2BetAmount.getText().isEmpty()){
			try{
				double amount = Integer.parseInt(car2BetAmount.getText());
				Bet bet = new Bet(3006, race.getRaceId(), race.getCar2Id(), userID, amount, null);
				bets.add(bet);
			}
			catch (Exception e){
	    		System.out.println(e.getStackTrace());
	    	}
		}
		
		if (!car3BetAmount.getText().isEmpty()){
			try{
				double amount = Integer.parseInt(car3BetAmount.getText());
				Bet bet = new Bet(3007, race.getRaceId(), race.getCar2Id(), userID, amount, null);
				bets.add(bet);
			}
			catch (Exception e){
	    		System.out.println(e.getStackTrace());
	    	}
		}
		
		
		gameController.sendBetsToServer(bets);
		
	}

}
