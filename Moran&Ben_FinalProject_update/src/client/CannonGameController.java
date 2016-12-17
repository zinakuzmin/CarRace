package client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Date;

import DBModels.Game;
import DBModels.GameEvent;
import DBModels.Player;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 * API compatible with Client Controller.
 * 
 * @author Moran Tenzer & Ben Nave
 *
 */
public class CannonGameController implements ViewControllerEvents {
	/**
	 * disconnection message during a game.
	 */
	public static final String CLIENT_WHILE_GAME_DISCONNECT_MASSEGE = "\nThe details didn't send to server.";
	/**
	 * disconnection message.
	 */
	public static final String CLIENT_DISCONNECT_MASSEGE = "\nYou have been disconnected from server because of server problem.";
	/**
	 * server closed/shut down message.
	 */
	public static final String SERVER_CLOSED_MASSEGE = "\nServer is closed. Please try again.";
	/**
	 * no mode has been set yet.
	 */
	public static final int NO_MODE = -1;
	/**
	 * practice mode.
	 */
	public static final int PRACTICE_MODE = 0;
	/**
	 * game mode.
	 */
	public static final int GAME_MODE = 1;
	/**
	 * no game level yet.
	 */
	public static final double NO_GAME_LEVEL = 0;
	/**
	 * client's model listener.
	 */
	private CannonGameModel modelListener;

	/**
	 * view for practice mode.
	 */
	private CannonGameView practiceMode = null;
	/**
	 * the game's mode.
	 */
	private int mode = NO_MODE;
	/**
	 * the client's login status.
	 */
	private LoginView loginView = null;
	/**
	 * the player's details
	 */
	private Player cannonGamePlayer = null;
	/**
	 * a message handler thread.
	 */
	private Thread messageHandler;
	/**
	 * the to server channel.
	 */
	private ObjectOutputStream toServer = null;
	/**
	 * the from server channel.
	 */
	private ObjectInputStream fromServer = null;
	/**
	 * the socket.
	 */
	private Socket socket = null;
	/**
	 * host's name.
	 */
	private String host = "localhost";
	/**
	 * the client's main stage
	 */
	private Stage primaryStage;
	/**
	 * view for game level.
	 */
	private LevelView levelView = null;
	/**
	 * connection status.
	 */
	private Boolean isConnected;
	/**
	 * in game status.
	 */
	private Boolean whileGame;

	/**
	 * Builds the clients controller main menu and initialized the client's main
	 * stage.
	 * 
	 * @param primaryStage
	 *            - client's main stage.
	 */
	public CannonGameController(Stage primaryStage) {
		isConnected = false;
		whileGame = false;
		this.primaryStage = primaryStage;
		//this.primaryStage.setOnCloseRequest(value);
		
		levelView = new LevelView(primaryStage, this);
		levelView.setStyle("-fx-border-color: black;");
		
		loginView = new LoginView();
		loginView.setControllerListener(this);

		loginView.setLayoutX(180);
		
		BorderPane mainPane = new BorderPane();
		mainPane.setCenter(levelView);
		mainPane.setTop(loginView);

		Scene scene = new Scene(mainPane, levelView.getLayoutX() + 250,
				levelView.getLayoutY() + loginView.getLayoutY() + 300);
		this.primaryStage.setTitle("Cannon"); // Set the stage title
		this.primaryStage.setScene(scene); // Place the scene in the stage
		this.primaryStage.show(); // Display the stage
		
		this.primaryStage.setAlwaysOnTop(true);
		this.primaryStage.setResizable(false);
		this.primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
			public void handle(WindowEvent event) {
				try {
					if (isConnected) {
						Game tempGame = new Game(0, -1, new Date(), 0, "Miss");
						toServer.writeObject(tempGame);
						toServer.flush();
						toServer.close();
						
					}
					if (fromServer != null)
						fromServer.close();
					if (socket != null)
						socket.close();
				} catch (IOException e) {
				}
			}
		});
	}

	/**
	 * this method handles with all the messages that the server delivers to the
	 * client.
	 */
	public void createMessageHandler() {
		messageHandler = new Thread(new Runnable() {

			@Override
			public void run() {
				Object msg;
				while (true) {
					try {
						if (isConnected) {
							msg = fromServer.readObject();

							if (msg == null) {
								String disconnectedString = CLIENT_DISCONNECT_MASSEGE;
								if (whileGame) {
									whileGame = false;
									disconnectedString += CLIENT_WHILE_GAME_DISCONNECT_MASSEGE;

								}

								disconnectClient(disconnectedString);
								break;
							}

							if (msg instanceof Double) {
							}
						}
					} catch (IOException e) {
					} catch (ClassNotFoundException e) {
					}
				}

			}
		});
	}

	/**
	 * @return return the client's model listener.
	 */
	public CannonGameModel getModelListener() {
		return modelListener;
	}

	/**
	 * this method sets the client's model listener.
	 * 
	 * @param modelListener
	 *            - the client's model listener.
	 */
	public void setModelListener(CannonGameModel modelListener) {
		this.modelListener = modelListener;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see client.ViewControllerEvents#viewClosed()
	 */
	@Override
	public void viewClosed() {
		this.primaryStage.show();
		switch (mode) {

		case PRACTICE_MODE:
			setPracticeMode(null);
			mode = NO_MODE;
			break;

		case GAME_MODE:
			levelView.setGameMode(null);
			mode = NO_MODE;
			break;
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see client.ViewControllerEvents#connect(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public void connect(String playerName, String playerID) {
		if (!isConnected) {
			try {
				socket = new Socket(host, 8000);
				toServer = new ObjectOutputStream(socket.getOutputStream());
				fromServer = new ObjectInputStream(socket.getInputStream());
			} catch (Exception e1) {
				loginView.getTextArea().appendText(SERVER_CLOSED_MASSEGE);
				return;
			}
			
			cannonGamePlayer = new Player(playerName, Integer.parseInt(playerID));
						
			try {
				toServer.writeObject(cannonGamePlayer);
				toServer.flush();
				loginView.getTextArea().appendText("\n" + fromServer.readUTF() + "\n");
				int flag = fromServer.readInt();

				if (flag > 0) {
					isConnected = true;
					loginView.getNameField().setEditable(false);
					loginView.getIdField().setEditable(false);
					loginView.setIsActionEnabled(false);
					levelView.setLoggedIn(true);

					createMessageHandler();
					messageHandler.start();
				} else {
					cannonGamePlayer = null;
					socket.close();
					toServer.close();
					fromServer.close();

				}

			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	/**
	 * this method handles with disconnection from the server.
	 * 
	 * @param text
	 *            - a disconnection message.
	 */
	public void disconnectClient(String text) {
		isConnected = false;
		loginView.getNameField().setEditable(true);
		loginView.getNameField().clear();
		loginView.getIdField().setEditable(true);
		loginView.getIdField().clear();
		loginView.setIsActionEnabled(true);

		levelView.setLoggedIn(false);
		loginView.getTextArea().appendText(text);

		try {
			toServer.close();
			fromServer.close();
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void sendGameEventsToServer(ArrayList<GameEvent> gameEvents , double score) {
		
		if (whileGame) {
			try {
				toServer.writeObject(gameEvents);
				toServer.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		whileGame = false;
	}
		
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see client.ViewControllerEvents#startGame(logic.Game)
	 */
	public void startGame(Game game) {
		whileGame = true;
		try {
			Game newGame = (Game) game;
			toServer.writeObject(newGame);
			toServer.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * this method sets the difficulty mode in the controller.
	 * 
	 * @param mode
	 *            - difficulty mode.
	 */
	public void setMode(int mode) {
		this.mode = mode;
	}
	public Player getPlayer() {
		return cannonGamePlayer;
	}

	/**
	 * @return return the practice mode view.
	 */
	public CannonGameView getPracticeMode() {
		return practiceMode;
	}

	/**
	 * this method sets the practice mode view.
	 * 
	 * @param practiceMode
	 *            - the practice mode view.
	 */
	public void setPracticeMode(CannonGameView practiceMode) {
		this.practiceMode = practiceMode;
	}
}
