package client;

import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Pane of game modes in the client's screen.
 * 
 * @author Moran Tenzer & Ben Nave
 *
 */
public class LevelView extends BorderPane {

	/**
	 * game mode = 1, represents a game that counts in the servers calculation.
	 */
	public static final int GAME_MODE = 1;
	/**
	 * practice mode.
	 */
	public static final int PRACTICE_MODE = -1;
	/**
	 * easy mode.
	 */
	public static final double BEGINNER = 1;
	/**
	 * medium mode.
	 */
	public static final double ADVANCED = 1.5;
	/**
	 * difficult mode.
	 */
	public static final double EXPERT = 2;

	/**
	 * the beginner mode button.
	 */
	RadioButton beginnerRadioBtn = new RadioButton("Beginner");
	/**
	 * the advanced mode button.
	 */
	RadioButton advancedRadioBtn = new RadioButton("Advanced");
	/**
	 * the expert mode button.
	 */
	RadioButton expertRadioBtn = new RadioButton("Expert");
	/**
	 * the start game button.
	 */
	private Button startGameBtn = new Button("PLAY");
	/**
	 * the practice mode button.
	 */
	private Button practiceBtn = new Button("Start Practice Mode");
	/**
	 * the view that represents the current difficulty mode.
	 */
	private CannonGameView gameMode = null;
	/**
	 * login status flag of the client.
	 */
	private boolean isLoggedIn = false;

	/**
	 * This Constructor create a the game level pane in the client's stage.
	 * 
	 * @param primaryStage
	 *            - the client's stage.
	 * @param controller
	 *            - the client's controller.
	 */
	LevelView(Stage primaryStage, CannonGameController controller) {
		ToggleGroup b = new ToggleGroup();
		beginnerRadioBtn.setToggleGroup(b);
		advancedRadioBtn.setToggleGroup(b);
		expertRadioBtn.setToggleGroup(b);
		
		startGameBtn.setOnAction(e -> {
			if (isLoggedIn) {
				controller.setMode(GAME_MODE);
				primaryStage.hide();
				if(beginnerRadioBtn.isSelected())
					gameMode = new CannonGameView(GAME_MODE, BEGINNER, controller);
				if(advancedRadioBtn.isSelected())
					gameMode = new CannonGameView(GAME_MODE, ADVANCED, controller);
				if(expertRadioBtn.isSelected())
					gameMode = new CannonGameView(GAME_MODE, EXPERT, controller);
			}
			else {
				ErrorPopUpMessage(primaryStage);
		}
		});

		practiceBtn.setOnAction(e -> {
			controller.setMode(GAME_MODE);
			primaryStage.hide();
			gameMode = new CannonGameView(PRACTICE_MODE, 0, controller);
		});

		VBox btnVBox = new VBox(10);
		btnVBox.getChildren().addAll(beginnerRadioBtn, advancedRadioBtn, expertRadioBtn ,startGameBtn);
		btnVBox.translateXProperty().bind(this.widthProperty().divide(5));
		btnVBox.translateYProperty().bind(this.heightProperty().add(-135));

		VBox btnVBox2 = new VBox(0);
		btnVBox2.getChildren().addAll(practiceBtn);
		btnVBox2.setPadding(new Insets(0, 50, 20, 20));

		practiceBtn.setMinHeight(100);
		btnVBox2.translateYProperty().bind(this.heightProperty().add(-135));

		this.setLeft(btnVBox);
		this.setRight(btnVBox2);

		this.setLayoutX(200);
		this.setLayoutY(70);
	}
	
	private void ErrorPopUpMessage(Stage primaryStage) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setContentText("You should Login to server First!");
		alert.setTitle("Error Login!");
		alert.initOwner(primaryStage);
		alert.showAndWait();
	}
	
	/**
	 * @return return the game mode view.
	 */
	public CannonGameView getGameMode() {
		return gameMode;
	}

	/**
	 * This method sets the game mode view.
	 * 
	 * @param gameMode
	 *            - the game mode view.
	 */
	public void setGameMode(CannonGameView gameMode) {
		this.gameMode = gameMode;
	}

	/**
	 * @return return the login status of the client - if he's in or not.
	 */
	public boolean isLoggedIn() {
		return isLoggedIn;
	}

	/**
	 * This method sets the client's login status.
	 * 
	 * @param isLoggedIn
	 *            - client's login status.
	 */
	public void setLoggedIn(boolean isLoggedIn) {
		this.isLoggedIn = isLoggedIn;
	}
}
