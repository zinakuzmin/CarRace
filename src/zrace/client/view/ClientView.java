package zrace.client.view;

import java.io.FileNotFoundException;

import com.mysql.jdbc.StringUtils;

import zrace.client.ZRaceGameController;
import zrace.client.app.MainClientApp;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class ClientView extends Application {
	private ZRaceGameController gameController;
	private TextField userTextField;
	private TextField userIDtextField;

	// public static void main(String[] args) {
	// launch(args);
	// }

	public ClientView(ZRaceGameController gameController) {
		this.gameController = gameController;
	}

	@Override
	public void start(Stage stage) throws Exception {
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

//		TextField userTextField = new TextField();
		userTextField = new TextField();
		grid.add(userTextField, 1, 1);

		Label userID = new Label("User ID:");
		grid.add(userID, 0, 2);

//		PasswordField pwBox = new PasswordField();
//		grid.add(pwBox, 1, 2);
//		TextField userIDtextField = new TextField();
		userIDtextField = new TextField();
		grid.add(userIDtextField, 1, 2);
		

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
				if (isLoginInputValid(userIDtextField, userTextField)){
					gameController.sendLoginOrRegisterMessage(Integer.parseInt(userIDtextField.getText()), userTextField.getText());
					createClientView(primaryStage);
					
				}
				else {
					
					actiontarget.setFill(Color.FIREBRICK);
					actiontarget.setText("Sign in button pressed");
					
				}
			}
		});

		Scene scene = new Scene(grid, 300, 275);
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	public void createClientView(Stage primaryStage) {
		BorderPane pane = new BorderPane();

		VBox vbox = new VBox(4);
		Label raceViewLbl = new Label("Race Viewer");
		Group group = new Group();
		RadioButton radio1 = new RadioButton("Race1");
		RadioButton radio2 = new RadioButton("Race2");
		RadioButton radio3 = new RadioButton("Race3");

		Button viewRace1 = new Button("View Race1");
		Button viewRace2 = new Button("View Race2");
		Button viewRace3 = new Button("View Race3");
		Button betBtn1 = new Button("Make a bet for race1");
		Button betBtn2 = new Button("Make a bet for race3");
		Button betBtn3 = new Button("Make a bet for race3");

		GridPane grid = new GridPane();
		// grid.add(viewRace1, 0, 0, 2, 1);
		grid.add(viewRace1, 0, 1);
		grid.add(viewRace2, 0, 2);
		grid.add(viewRace3, 0, 3);
		grid.add(betBtn1, 1, 1);
		grid.add(betBtn2, 1, 2);
		grid.add(betBtn3, 1, 3);

		group.getChildren().addAll(radio1, radio2, radio3);
		vbox.getChildren().addAll(radio1, radio2, radio3);

		pane.setRight(grid);

		Pane racePane = new Pane();
		pane.setCenter(racePane);

		racePane.getChildren().add(new Label("place race here"));

		pane.getChildren().add(raceViewLbl);
		// pane.getChildren().add(group);

		Scene scene = new Scene(pane, 300, 275);
		primaryStage.setScene(scene);
		primaryStage.show();

		viewRace1.setOnAction(e -> showRace(viewRace1.getText(), racePane));
		viewRace2.setOnAction(e -> showRace(viewRace2.getText(), racePane));
		viewRace3.setOnAction(e -> showRace(viewRace3.getText(), racePane));

		betBtn1.setOnAction(e -> showBettingPage(viewRace1.getText()));
		betBtn2.setOnAction(e -> showBettingPage(viewRace2.getText()));
		betBtn3.setOnAction(e -> showBettingPage(viewRace3.getText()));
	}

	private static void showRace(String raceID, Pane racePane) {
		racePane.getChildren().clear();
		racePane.getChildren().add(new Label("Show race " + raceID));
		try {
			new MainClientApp().start(new Stage());
		} catch (InstantiationException | IllegalAccessException
				| FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private static void showBettingPage(String raceID) {
		try {
			new BetView().start(new Stage());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public boolean isLoginInputValid(TextField userIdTF, TextField userNameTF) {
		if (userIdTF.getText().isEmpty() || userNameTF.getText().isEmpty()) {
			return false;
		} else {

			try {
				Integer.parseInt(userIdTF.getText());
				return true;
			} catch (Exception e) {
				return false;
			}

		}

	}
}
