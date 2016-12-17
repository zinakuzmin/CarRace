package server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Date;

import DBModels.Game;
import DBModels.GameEvent;
import DBModels.Player;
import db.DataBaseView;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 * This Class provides an API compatible with ServerController.
 * @author Moran Tenzer & Ben Nave
 *
 */
public class ServerController implements ViewEvents{
	
	/**
	 * {@code modelListener} - a model listener.
	 */
	private ServerModel modelListener;
	/**
	 * {@code textArea} - a javafx component used to present note's and logs.
	 */
	private TextArea textArea;
	/**
	 * {@code dataBaseHandler} - used to control on database.
	 */
	private DataBaseView dataBaseView = null;
	/**
	 * {@code dbViewerBtn} - a javafx component to control the show of DataBaseView (on/off).
	 */
	private Button dbViewerBtn;
	/**
	 * {@code serverSocket} - use as the server portal communicator.
	 */
	private ServerSocket serverSocket;
	/**
	 * {@code socket} - use to accept new client connection.
	 */
	private Socket socket;
	/**
	 * {@code clientArrayList} - an array , use to hold any client that connected to the server.
	 */
	private ArrayList<HandleAClient> clientArrayList;
	
	/**
	 * Builds the server controller main menu and initialized the panel.
	 * 
	 * @param primaryStage - the desired stage of the start.
	 */
	public ServerController(Stage primaryStage){
		Label logLabel = new Label("Server Log");
		logLabel.setTextFill(Color.BLUE);
		logLabel.setFont(Font.font(20));
		logLabel.setPadding(new Insets(10, 0, 20, 200));
		logLabel.setStyle("-fx-underline: true;");
		logLabel.setAlignment(Pos.CENTER);
		
		textArea = new TextArea();
		textArea.setEditable(false);
		textArea.setStyle("-fx-border-color: blue");
		
		dbViewerBtn = new Button("Data Base Viewer");
		dbViewerBtn.setOnAction(e -> {
			if(dataBaseView == null) {
				dataBaseView = new DataBaseView();
				dataBaseView.setViewEventsListner(this);
				dataBaseView.setDataBaseHandler(modelListener.getDataBaseHandler());	
				dbViewerBtn.setDisable(true);
			}
		});
		dbViewerBtn.setAlignment(Pos.CENTER);
		BorderPane pane = new BorderPane();
		dbViewerBtn.translateXProperty().bind(pane.widthProperty().divide(2.5));
		pane.setBottom(dbViewerBtn);
		pane.setTop(logLabel);
		pane.setCenter(new ScrollPane(textArea));
		
		Scene scene = new Scene(pane, 540, 300);
		primaryStage.setTitle("Game Server"); 	
		primaryStage.setScene(scene); 			
		primaryStage.setAlwaysOnTop(true);
		primaryStage.show(); 
		primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
			public void handle(WindowEvent event) {
				disconnectAllConnectedClients();
				try {
					serverSocket.close();
				} catch (IOException e) {
				}
			}
		});
		initServer();
	}
	
	/**
	 * initialize the server clientArrayList and the serverSocket.
	 * this method create a new thread that manage each new client.
	 * 
	 */
	private void initServer() {
		clientArrayList = new ArrayList<>();
		new Thread(() -> {
			try {
				serverSocket = new ServerSocket(8000);
				Platform.runLater(() -> {
					textArea.appendText("Cannon Game Server connected at " + new Date() + '\n');
				});
				while (true) {
					socket = serverSocket.accept();
					HandleAClient handleClients = new HandleAClient(socket);
					clientArrayList.add(handleClients);
					new Thread(handleClients).start();
				}
			} catch (IOException ex) {
				System.err.println(ex);
			}
		}).start();
	}

	
	/**
	 * @return return the ServerModel.
	 */
	public ServerModel getModelListener() {
		return modelListener;
	}

	/**
	 * setting the ServerController Model.
	 * 
	 * @param serverModel - a server model.
	 */
	public void setModelListener(ServerModel serverModel) {
		this.modelListener = serverModel;
	}
	
	/** 
	 * called the dataBaseView is closed.
	 */
	@Override
	public void viewClosed() {
		dataBaseView = null;
		dbViewerBtn.setDisable(false);
		
	}
	
	/**
	 * This class is being used by the server to control every client and each client run on a thread.
	 *
	 */
	class HandleAClient implements Runnable {
		private Socket connectToClient;
		private ObjectOutputStream toClient;
		private ObjectInputStream fromClient;
		private boolean isConnected;
		private Player clientPlayer;
		private boolean whileGame;

		/**
		 * This constructor create a new instance of HandleAClient.
		 * @param socket - a socket.
		 */
		public HandleAClient(Socket socket) {
			this.connectToClient = socket;
			isConnected = false;
			whileGame = false;
		}

		
		public void run() {
			try {

				fromClient = new ObjectInputStream(connectToClient.getInputStream());
				toClient = new ObjectOutputStream(connectToClient.getOutputStream());
				
				try {
					Player player = (Player) fromClient.readObject();
					if (modelListener.getDataBaseHandler().isIdExist(player)) {
						if (modelListener.getDataBaseHandler().isPlayerExist(player)) {
							if(isClientAlreadyConnected(player)) {
								toClient.writeUTF("This Client is Connected!"); 
								toClient.flush();
								toClient.writeInt(-1);
								toClient.flush();
								isConnected = false;
								throw new SocketException();
							}
								
							this.clientPlayer = player;
							toClient.writeUTF("Welcome Back!\n---------------\n"); 
							toClient.flush();
							Platform.runLater(() -> {
								textArea.appendText("Thread Started for client " + player.getPlayerFullName() + " at [" + new Date() + "]\n");
								InetAddress clientInetAddress = socket.getInetAddress();
								textArea.appendText("Client Name [" + player.getPlayerFullName()  + "] host is [" + clientInetAddress.getHostName() + "]\n");
								textArea.appendText("IP Address is [" + clientInetAddress.getHostAddress() + "]\n---------------\n");
							});
							
							isConnected = true;
							toClient.writeInt(10);
							toClient.flush();

						}
						else {
							toClient.writeUTF("This Id is used, Login with different user..."); 
							toClient.flush();
							toClient.writeInt(-1);
							toClient.flush();
							isConnected = false;
							throw new SocketException();
						}
					}
					
					else {
						modelListener.getDataBaseHandler().insertPlayer(player);
						this.clientPlayer = player;
						toClient.writeUTF("Welcome to the Cannon game!\n---------------\n"); 
						toClient.flush();
						Platform.runLater(() -> {
							textArea.appendText("Thread Started for client " + player.getPlayerFullName() + " at [" + new Date() + "]\n");
							InetAddress clientInetAddress = socket.getInetAddress();
							textArea.appendText("Client Name [" + player.getPlayerFullName()  + "] host is [" + clientInetAddress.getHostName() + "]\n");
							textArea.appendText("IP Address is [" + clientInetAddress.getHostAddress() + "]\n---------------\n");
						});
						
						isConnected = true;
						toClient.writeInt(10);
						toClient.flush();
					}
					
					while(isConnected){
						whileGame = false;
						Game game = (Game) fromClient.readObject();
						if (game != null) {
							if(game.getGameID() < 0){
								isConnected = false;
								throw new SocketException();
							}
							
							int gameId = modelListener.getDataBaseHandler().getPlayerNewGameID(clientPlayer);
							game.setGameID(gameId);
							modelListener.getDataBaseHandler().insertGame(game);
							whileGame = true;
							
							@SuppressWarnings("unchecked")
							ArrayList<GameEvent> gameEvents = (ArrayList<GameEvent>) fromClient.readObject();
							if(gameEvents != null) {
								for(GameEvent gameEvent : gameEvents)
									gameEvent.setGameID(game.getGameID());
								game.calcGameScore(gameEvents);
								modelListener.getDataBaseHandler().updateGameScore(game);
								modelListener.getDataBaseHandler().insertEvent(gameEvents);
								toClient.writeObject(new Double(game.getGameScore()));
								toClient.flush();
							}
							else
								modelListener.getDataBaseHandler().delGame(player.getplayerID(), game.getGameID());
						}
						
					}
					
				} catch (ClassNotFoundException e) {
				
				}
			} catch (SocketException ex) {
				closeConnection();
				
			} 
		    catch(IOException ex) 
		    { 
		    	
		    }
		}
		
		/**
		 * @return true if the client is connected.
		 */
		public boolean isClientConnected() {
			return isConnected;
		}
		
		/**
		 * @return return true if the client is while a game.
		 */
		public boolean isWhileGame() {
			return whileGame;
		}
		
		/**
		 * @return the client Player Object.
		 */
		public Player getClientPlayer() {
			return clientPlayer;
		}
		
		/**
		 * This Method close the connection with the client.
		 * its send to the client a NULL object.
		 * 
		 * if the client is being closed while he playing a game this method delete any record of this game from db.
		 */
		public void closeConnection(){
			try {
				if(isConnected) {
					toClient.writeObject(null);
					toClient.flush();
				}
				if(whileGame){
					int lastGameId = modelListener.getDataBaseHandler().getPlayerNewGameID(clientPlayer);
					lastGameId--;
					modelListener.getDataBaseHandler().delGame(clientPlayer.getplayerID(), lastGameId);
				}
				connectToClient.close();
				fromClient.close();
				toClient.close();
				isConnected = false;
				whileGame = false;
			} catch (IOException e) {
				
			}
		}
	}
	
	/**
	 * disconnect the connection with every connected client. 
	 */
	public void disconnectAllConnectedClients(){
		for(HandleAClient hc : clientArrayList)
			if(hc.isClientConnected())
				hc.closeConnection();
	}
	
	/**
	 * @param player - a player.
	 * @return true if the player is already connected.
	 */
	public boolean isClientAlreadyConnected(Player player){
		boolean connected = false;
		for(HandleAClient hc : clientArrayList)
			if(hc.isClientConnected())
					if( ( hc.getClientPlayer() ).equals(player)){
						connected = true;
						break;
					}
		
		return connected;
	}
	
	
}
