package zrace.client.view;

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
		
		
		Label raceNamelbl = new Label("Race name");
		Label car1Namelbl = new Label("Car1");
		Label car2Namelbl = new Label("Car2");
		Label car3Namelbl = new Label("Car3");
		TextField car1BetAmount = new TextField();
		TextField car2BetAmount = new TextField();
		TextField car3BetAmount = new TextField();
		Button betBtn = new Button("Make a bet!");
		
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
		
		Scene scene = new Scene(grid, 300, 275);
		stage.setScene(scene);
		stage.show();
	}

}
