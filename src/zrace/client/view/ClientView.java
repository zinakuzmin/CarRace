package zrace.client.view;

import java.io.FileNotFoundException;
import java.io.IOException;

import dbModels.Race;
import dbModels.RaceRun;
import dbModels.RaceRun.RaceStatus;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.effect.Glow;
import javafx.scene.effect.Reflection;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Duration;
import zrace.client.ZRaceGameController;
import zrace.client.app.MainClientApp;
import zrace.protocol.ClientDisconnectMsg;

public class ClientView extends Application {
	private ZRaceGameController gameController;
	private TextField userTextField;
	private TextField userIDtextField;
	private static MainClientApp mainClientApp;
	private Label raceStatus1;
	private Label raceStatus2;
	private Label raceStatus3;

	public ClientView(ZRaceGameController gameController) {
		this.gameController = gameController;

		raceStatus1 = new Label(); // gameController.getRaceRuns().get(0).getRaceStatus().toString());
		raceStatus2 = new Label(); // gameController.getRaceRuns().get(1).getRaceStatus().toString());
		raceStatus3 = new Label(); // gameController.getRaceRuns().get(2).getRaceStatus().toString());

	}

	@Override
	public void start(Stage stage) throws Exception {

		stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
			public void handle(WindowEvent event) {
				try {
					gameController.getOut().writeObject(
							new ClientDisconnectMsg(gameController.getUser()));
					gameController.getSocket().close();
				} catch (IOException e) {
				}
			}
		});

		createLoginPage(stage);

	}

	private void createLoginPage(Stage primaryStage) {
		primaryStage.setTitle("ZRace client");
		GridPane grid = new GridPane();
		grid.setAlignment(Pos.CENTER);
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(25, 25, 25, 25));

		Text scenetitle = new Text("Welcome");
		scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
		grid.add(scenetitle, 0, 0, 2, 1);

		Label userName = new Label("User Name:");
		grid.add(userName, 0, 1);

		userTextField = new TextField();
		grid.add(userTextField, 1, 1);
		Button signInButton = new Button("Sign in");
		HBox hbBtn = new HBox(10);
		hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
		hbBtn.getChildren().add(signInButton);
		grid.add(hbBtn, 1, 4);

		final Text actiontarget = new Text();
		grid.add(actiontarget, 1, 6);

		signInButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent e) {

				// if (isLoginInputValid(userIDtextField, userTextField)){
				if (isLoginInputValid(userTextField)) {
					gameController.sendLoginOrRegisterMessage(userTextField
							.getText());

					while (!gameController.isGotUserFromServer()) {

						try {
							Thread.sleep(200);
						} catch (InterruptedException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
					if (gameController.getUser() != null)
						createClientView(primaryStage);

					else {

						actiontarget.setFill(Color.FIREBRICK);
						actiontarget.setText("User already logged in");

					}
				}
			}
		});

		Scene scene = new Scene(grid, 300, 275);
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	public void createClientView(Stage primaryStage) {
		BorderPane pane = new BorderPane();
		Label userNameLabel = new Label();

		while (!(gameController.isGotUserFromServer()
				&& gameController.isGotRacesFromServer() && gameController
					.isGotRacesRunsFromServer())) {
			try {
				// System.out.println("client get user " +
				// gameController.isGotUserFromServer());
				// System.out.println("client get races " +
				// gameController.isGotRacesFromServer());

				Thread.sleep(1000);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}

		userNameLabel.setText("Welcome "
				+ gameController.getUser().getUserFullName());
		userNameLabel.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
		pane.setTop(userNameLabel);

		// raceStatus1 = new Label();
		// //gameController.getRaceRuns().get(0).getRaceStatus().toString());
		// raceStatus2 = new Label();
		// //gameController.getRaceRuns().get(1).getRaceStatus().toString());
		// raceStatus3 = new Label();
		// //gameController.getRaceRuns().get(2).getRaceStatus().toString());

		Button viewRace1 = new Button("View Race1 "
				+ gameController.getActiveRaces().get(0).getRaceFullName());
		Button viewRace2 = new Button("View Race2 "
				+ gameController.getActiveRaces().get(1).getRaceFullName());
		Button viewRace3 = new Button("View Race3 "
				+ gameController.getActiveRaces().get(2).getRaceFullName());
		Button betBtn1 = new Button("Make a bet for race1");
		Button betBtn2 = new Button("Make a bet for race3");
		Button betBtn3 = new Button("Make a bet for race3");

		GridPane paneRace1 = new GridPane();
		GridPane paneRace2 = new GridPane();
		GridPane paneRace3 = new GridPane();
		
//		ColumnConstraints column1 = new ColumnConstraints();
//	    column1.setPercentWidth(50);
//	    ColumnConstraints column2 = new ColumnConstraints();
//	    column2.setPercentWidth(50);
//	    paneRace1.getColumnConstraints().addAll(column1, column2);
//	    paneRace2.getColumnConstraints().addAll(column1, column2);
//	    paneRace3.getColumnConstraints().addAll(column1, column2);
	    
		paneRace1.setHgap(10); //horizontal gap in pixels => that's what you are asking for
		paneRace1.setVgap(10); //vertical gap in pixels
		paneRace1.setPadding(new Insets(10, 10, 10, 10));
		
		paneRace2.setHgap(10); //horizontal gap in pixels => that's what you are asking for
		paneRace2.setVgap(10); //vertical gap in pixels
		paneRace2.setPadding(new Insets(10, 10, 10, 10));
		
		paneRace3.setHgap(10); //horizontal gap in pixels => that's what you are asking for
		paneRace3.setVgap(10); //vertical gap in pixels
		paneRace3.setPadding(new Insets(10, 10, 10, 10));
		
		
	

		paneRace1.add(viewRace1, 0, 1);
		paneRace2.add(viewRace2, 0, 3);
		paneRace3.add(viewRace3, 0, 5);
		paneRace1.add(betBtn1, 1, 1);
		paneRace2.add(betBtn2, 1, 3);
		paneRace3.add(betBtn3, 1, 5);
		paneRace1.add(raceStatus1, 2, 1);
		paneRace2.add(raceStatus2, 2, 3);
		paneRace3.add(raceStatus3, 2, 5);

		GridPane grid = new GridPane();
		
//		ColumnConstraints column1 = new ColumnConstraints();
//	    column1.setPercentWidth(50);
//	    ColumnConstraints column2 = new ColumnConstraints();
//	    column2.setPercentWidth(50);
//	    grid.getColumnConstraints().addAll(column1, column2); 
		
		grid.setHgap(10); //horizontal gap in pixels => that's what you are asking for
	    grid.setVgap(10); //vertical gap in pixels
	    grid.setPadding(new Insets(10, 10, 10, 10)); //margins around the whole grid
	                                                 //(top/right/bottom/left)
		
		
		grid.add(paneRace1, 0, 1);
		grid.add(paneRace2, 0, 3);
		grid.add(paneRace3, 0, 5);

		// grid.add(viewRace1, 0, 1);
		// grid.add(viewRace2, 0, 3);
		// grid.add(viewRace3, 0, 5);
		// grid.add(betBtn1, 1, 1);
		// grid.add(betBtn2, 1, 3);
		// grid.add(betBtn3, 1, 5);
		// grid.add(raceStatus1, 2, 1);
		// grid.add(raceStatus2, 2, 3);
		// grid.add(raceStatus3, 2, 5);

		pane.setRight(grid);

		Pane racePane = new Pane();
		pane.setCenter(racePane);

		Scene scene = new Scene(pane, 1160, 600);
		primaryStage.setScene(scene);
		primaryStage.show();

		viewRace1.setOnAction(e -> showRace(gameController.getActiveRaces()
				.get(0), racePane, primaryStage, gameController.getRaceRuns()
				.get(0)));
		viewRace2.setOnAction(e -> showRace(gameController.getActiveRaces()
				.get(1), racePane, primaryStage, gameController.getRaceRuns()
				.get(1)));
		viewRace3.setOnAction(e -> showRace(gameController.getActiveRaces()
				.get(2), racePane, primaryStage, gameController.getRaceRuns()
				.get(2)));

		betBtn1.setOnAction(e -> showBettingPage(gameController
				.getActiveRaces().get(0).getRaceId()));
		betBtn2.setOnAction(e -> showBettingPage(gameController
				.getActiveRaces().get(1).getRaceId()));
		betBtn3.setOnAction(e -> showBettingPage(gameController
				.getActiveRaces().get(2).getRaceId()));

		primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {

			@Override
			public void handle(WindowEvent event) {
				if (mainClientApp != null)
					mainClientApp.closeApp();
			}
		});
	}

	private static void showRace(Race activeRace, Pane racePane,
			Stage primaryStage, RaceRun raceRun) {
		if (mainClientApp != null) {
			mainClientApp.closeApp();
		}
		racePane.getChildren().clear();
		try {
			boolean raceStarted = activeRace.getStartTime() != null;
			long raceDurationInMilis = 0;

			mainClientApp = new MainClientApp(racePane, raceRun.getCarsInRace());

			if (raceStarted)
				raceDurationInMilis = activeRace.getEndTime().getTime()
						- activeRace.getStartTime().getTime();

			mainClientApp.setIsRaceStarted(raceStarted, raceDurationInMilis);
			mainClientApp.setMusic(raceRun.getSong(),
					Duration.millis(raceDurationInMilis));
			mainClientApp.start(primaryStage);
		} catch (InstantiationException | IllegalAccessException
				| FileNotFoundException | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private synchronized void showBettingPage(int raceID) {
		try {
			BetView betView = new BetView(gameController,
					gameController.findRaceByID(raceID), gameController
							.getUser().getUserID());
			betView.start(new Stage());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// public boolean isLoginInputValid(TextField userIdTF, TextField
	// userNameTF) {
	// if (userIdTF.getText().isEmpty() || userNameTF.getText().isEmpty()) {
	// return false;
	// } else {
	//
	// try {
	// Integer.parseInt(userIdTF.getText());
	// return true;
	// } catch (Exception e) {
	// return false;
	// }
	//
	// }
	//
	// }

	public boolean isLoginInputValid(TextField userNameTF) {
		if (userNameTF.getText().isEmpty())
			return false;

		return true;
	}

	public synchronized void setRacesStatusInView() {
		if (!gameController.getRaceRuns().isEmpty()
				&& gameController.getRaceRuns().size() >= 3) {
			String statusRace1 = gameController.getRaceRuns().get(0)
					.getRaceStatus().toString();
			raceStatus1.setText(statusRace1);

			// raceStatus1.setStyle("-fx-font-size: 20px; -fx-text-fill: red;");
			// raceStatus1.setEffect(new Reflection());
			// raceStatus1.setMaxWidth(250);
			// raceStatus1.setWrapText(true);
			// raceStatus1.setFont(Font.font("Verdana", 20));
			raceStatus2.setText(gameController.getRaceRuns().get(1)
					.getRaceStatus().toString());
			raceStatus3.setText(gameController.getRaceRuns().get(2)
					.getRaceStatus().toString());
			setStatusLabelStyle(raceStatus1, gameController.getRaceRuns()
					.get(0).getRaceStatus().toString());
			setStatusLabelStyle(raceStatus2, gameController.getRaceRuns()
					.get(1).getRaceStatus().toString());
			setStatusLabelStyle(raceStatus3, gameController.getRaceRuns()
					.get(2).getRaceStatus().toString());

		}
	}

	public void setStatusLabelStyle(Label label, String status) {
//		label.setText(status);
		if (status.equals(RaceStatus.waiting)) {
			System.out.println("waiting status");
			label.setStyle("-fx-font-size: 30px; -fx-text-fill: blue;");
		} else if (status.equals(RaceStatus.completed)) {
			label.setStyle("-fx-font-size: 20px; -fx-text-fill: red;");
		} else if (status.equals(RaceStatus.in_progress)) {
			label.setStyle("-fx-font-size: 20px; -fx-text-fill: green;");

		} else if (status.equals(RaceStatus.ready_to_run)) {
			label.setStyle("-fx-font-size: 20px; -fx-text-fill: orange;");
		}
	}
}
