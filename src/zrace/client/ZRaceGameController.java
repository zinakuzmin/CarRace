package zrace.client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

import javafx.stage.Stage;
import zrace.client.view.ClientView;
import zrace.client.view.listeners.ServerListener;
import zrace.dbModels.Bet;
import zrace.dbModels.Race;
import zrace.dbModels.RaceRun;
import zrace.dbModels.User;
import zrace.protocol.ClientBetMsg;
import zrace.protocol.ClientConnectMsg;
import zrace.protocol.ClientDisconnectMsg;

/**
 * The class provides API for {@link ZRaceGameController}
 * @author Zina K
 *
 */
public class ZRaceGameController {
	
	/**
	 * Socket to server
	 */
	private Socket socket;
	
	/**
	 * Input stream to server
	 */
	private ObjectInputStream in;
	
	/**
	 * Output steam to server
	 */
	private ObjectOutputStream out;
	
	/**
	 * List of active races
	 */
	private ArrayList<Race> activeRaces;
	
	/**
	 * List of race run details
	 */
	private ArrayList<RaceRun> raceRuns;
	
	/**
	 * User of the client
	 */
	private User user;
	
	/**
	 * Controller for UI actions
	 */
	private boolean gotUserFromServer = false;
	
	/**
	 * Controller for UI actions
	 */
	private boolean gotRacesFromServer = false;
	
	/**
	 * Controller for UI actions
	 */
	private boolean gotRacesRunsFromServer = false;
	
	/**
	 * Controller for UI actions
	 */
	private boolean serverListenerActivated = false;
	
	/**
	 * Instance of {@link ClientView}
	 */
	private ClientView clientView;
	
	/**
	 * Last completed race winner 
	 */
	private int lastWinnerCarId = 0;

	/**
	 * Initialize {@link ZRaceGameController}
	 * @param primaryStage
	 */
	public ZRaceGameController(Stage primaryStage) {
		activeRaces = new ArrayList<Race>();

		new Thread(() -> {
			try {

				setSocket(new Socket("localhost", 8000));
				setOut(new ObjectOutputStream(getSocket().getOutputStream()));
				getOut().flush();
				in = new ObjectInputStream(getSocket().getInputStream());
				new Thread(new ServerListener(in, this)).start();

			} catch (IOException ex) {
				System.err.println(ex);
			}
		}).start();

		try {
			while (!serverListenerActivated) {
				Thread.sleep(200);

			}
			System.out.println("start client GUI");
			clientView = new ClientView(this);
			clientView.start(new Stage());

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * Once input from login screen approved send them to server
	 * @param userFullName
	 */
	public synchronized void sendLoginOrRegisterMessage(String userFullName) {
		ClientConnectMsg msg = new ClientConnectMsg(0, 0,
				userFullName.toLowerCase());
		try {
			getOut().writeObject(msg);
			getOut().reset();
			getOut().flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Set active races
	 * @param races
	 */
	public synchronized void setActiveRaces(ArrayList<Race> races) {
		this.activeRaces = races;
	}

	/**
	 * Get active races
	 * @return ArrayList
	 */
	public synchronized ArrayList<Race> getActiveRaces() {
		return activeRaces;
	}

	/**
	 * Set user details
	 * @param user
	 */
	public synchronized void setUserDetails(User user) {
		this.user = user;
	}

	/**
	 * Get client user
	 * @return User
	 */
	public synchronized User getUser() {
		return user;
	}



	/**
	 * Find race by id
	 * @param raceId
	 * @return Race
	 */
	public synchronized Race findRaceByID(int raceId) {
		for (Race race : activeRaces) {
			if (race.getRaceId() == raceId)
				return race;
		}

		return null;
	}

	/**
	 * Send bets to server
	 * @param bets
	 */
	public synchronized void sendBetsToServer(ArrayList<Bet> bets) {
		new Thread(() -> {

			try {
				getOut().writeObject(new ClientBetMsg(0, bets));
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}).start();
	}

	/**
	 * @return true if user details updated by server
	 */
	public boolean isGotUserFromServer() {
		return gotUserFromServer;
	}

	/**
	 * @param gotUserFromServer
	 */
	public void setGotUserFromServer(boolean gotUserFromServer) {
		this.gotUserFromServer = gotUserFromServer;
	}

	/**
	 * @return true if races details arrived from server
	 */
	public boolean isGotRacesFromServer() {
		return gotRacesFromServer;
	}

	/**
	 * @param gotRacesFromServer
	 */
	public void setGotRacesFromServer(boolean gotRacesFromServer) {
		this.gotRacesFromServer = gotRacesFromServer;
	}

	/**
	 * @return true if {@link ServerListener} activated
	 */
	public boolean isServerListenerActivated() {
		return serverListenerActivated;
	}

	/**
	 * @param serverListenerActivated
	 */
	public void setServerListenerActivated(boolean serverListenerActivated) {
		this.serverListenerActivated = serverListenerActivated;
	}

	/**
	 * Send disconnect message to server and close socket 
	 */
	public void disconnectClient() {
		try {
			getOut().writeObject(new ClientDisconnectMsg(0, user));
			in.close();
			getOut().close();
			getSocket().close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Get race run
	 * @return Arraylist
	 */
	public ArrayList<RaceRun> getRaceRuns() {
		return raceRuns;
	}

	/**
	 * Set race runs
	 * @param raceRuns
	 */
	public void setRaceRuns(ArrayList<RaceRun> raceRuns) {
		this.raceRuns = raceRuns;
	}

	/**
	 * @return true if message from server arrived
	 */
	public boolean isGotRacesRunsFromServer() {
		return gotRacesRunsFromServer;
	}

	/**
	 * Set true if message from server arrived
	 * @param gotRacesRunsFromServer
	 */
	public void setGotRacesRunsFromServer(boolean gotRacesRunsFromServer) {
		this.gotRacesRunsFromServer = gotRacesRunsFromServer;
	}

	/**
	 * Return output stream
	 * @return ObjectOutputStream
	 */
	public ObjectOutputStream getOut() {
		return out;
	}

	/**
	 * Set output stream
	 * @param out
	 */
	public synchronized void setOut(ObjectOutputStream out) {
		this.out = out;
	}

	/**
	 * Get socker to server
	 * @return
	 */
	public Socket getSocket() {
		return socket;
	}

	/**
	 * Set socket to server
	 * @param socket
	 */
	public void setSocket(Socket socket) {
		this.socket = socket;
	}

	/**
	 * @return ClientView
	 */
	public ClientView getClientView() {
		return clientView;
	}

	/**
	 * @param clientView
	 */
	public synchronized void setClientView(ClientView clientView) {
		this.clientView = clientView;
	}

	/**
	 * Get winner car id of last completed race
	 * @return int
	 */
	public int getLastWinnerCarId() {
		return lastWinnerCarId;
	}

	/**
	 * Set winner car id of last completed race
	 * @param lastWinnerCarId
	 */
	public synchronized void setLastWinnerCarId(int lastWinnerCarId) {
		this.lastWinnerCarId = lastWinnerCarId;
	}

}
