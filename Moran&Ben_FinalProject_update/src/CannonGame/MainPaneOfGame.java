package CannonGame;

import client.CannonGameMVC;
import server.ServerMVC;
import db.CannonGameDBScript;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 * Main panel for client and server use.
 * 
 * @author Moran Tenzer & Ben Nave
 *
 */

public class MainPaneOfGame extends Application {
	
	/**
	 * Create the client and open the client view
	 */
	private Button clientBtn = new Button("Cilent (Player)");

	/**
	 * Create the server socket and open the Server view
	 */
	private Button serverBtn = new Button("Server Connection");
	
	/**
	 * runs the main stage of the application
	 * 
	 * @param primaryStage - main stage of the application.
	 * @throws Exception
	 */
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		CannonGameDBScript.runScript();
		
		serverBtn.setOnAction(e -> {
			new ServerMVC().start(new Stage());
			serverBtn.setDisable(true);
		});
		
		clientBtn.setOnAction(e -> {
			new CannonGameMVC().start(new Stage());
		});
		
		HBox btnHBox = new HBox(15);
		btnHBox.getChildren().addAll(serverBtn,clientBtn);
		
		BorderPane pane = new BorderPane();
		
		pane.setCenter(btnHBox);
		
		pane.setStyle("-fx-background-image: url(\"/imagesAndSound/image0.jpg\");"
				+ "-fx-background-size: 500;-fx-background-repeat: no-repeat;");
		
		btnHBox.translateXProperty().bind(pane.widthProperty().divide(4));
		btnHBox.translateYProperty().bind(pane.heightProperty().add(-100));
		
		Scene scene = new Scene(pane, 500, 150);
		primaryStage.setTitle("Cannon Game!"); //main pane title
		primaryStage.setScene(scene);
		primaryStage.show(); //to display the stage
		primaryStage.setAlwaysOnTop(true);
		primaryStage.setResizable(false);
		primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
			public void handle(WindowEvent event) {	
				Platform.exit();
				System.exit(0);
			}
		});
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		launch(args);
	}

}
