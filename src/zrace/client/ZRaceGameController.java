package zrace.client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

import dbModels.Bet;
import dbModels.Race;
import dbModels.RaceRun;
import dbModels.User;
import javafx.stage.Stage;
import zrace.client.view.ClientView;
import zrace.client.view.listeners.ServerListener;
import zrace.protocol.ClientBetMsg;
import zrace.protocol.ClientConnectMsg;
import zrace.protocol.ClientDisconnectMsg;

public class ZRaceGameController {
	private Socket socket;
	private ObjectInputStream in;
	private ObjectOutputStream out;
	private ArrayList<Race> activeRaces;
	private ArrayList<RaceRun> raceRuns;
	private User user;
	private boolean gotUserFromServer = false;
	private boolean gotRacesFromServer = false;
	private boolean gotRacesRunsFromServer = false;
	private boolean serverListenerActivated = false;
	private ClientView clientView;

	public ZRaceGameController(Stage primaryStage) {
		activeRaces = new ArrayList<Race>();
		
		new Thread(() -> {
			try {
				
				System.out.println("client start game");
				setSocket(new Socket("localhost", 8000));
				System.out.println("Client Socket inited");
				setOut(new ObjectOutputStream(getSocket().getOutputStream()));
				getOut().flush();
				System.out.println("client init out");
				in = new ObjectInputStream(getSocket().getInputStream());
				System.out.println("client init in");
				//Start listen to server
				System.out.println("client - start serverListener");
				new Thread(new ServerListener(in, this)).start();
			
				
			} catch (IOException ex) {
				System.err.println(ex);
			}
		}).start();
		
		
		
		
		try {
			while (!serverListenerActivated){
				Thread.sleep(200);
				
			}
			System.out.println("start GUI");
			clientView = new ClientView(this);
			clientView.start(new Stage());
			//thread
			

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	
	public synchronized void sendLoginOrRegisterMessage(String userFullName){
		ClientConnectMsg msg = new ClientConnectMsg(0, userFullName.toLowerCase());
		
		System.out.println("client: send login msg");
		try {
			getOut().writeObject(msg);
			getOut().reset();
		    getOut().flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public synchronized void setActiveRaces(ArrayList<Race> races){
//		activeRaces.clear();
		this.activeRaces = races;
	}
	
	public synchronized ArrayList<Race> getActiveRaces(){
		return activeRaces;
	}
	
	public synchronized void setUserDetails(User user){
		this.user = user;
	}
	
	
	public synchronized User getUser(){
		return user;
	}
	
//	public synchronized void getRacesFromServer(){
//		new Thread(() -> {
//			out.writeObject(new ClientGetRaces());
//			in.readObject().
//			
//		}).start();
//		
//	}
	
	public synchronized Race findRaceByID(int raceId){
		for (Race race : activeRaces) {
			if (race.getRaceId() == raceId)
				return race;
		}
		
		return null;
	}
	
	public synchronized void sendBetsToServer(ArrayList<Bet> bets){
		new Thread(() -> {
			
			try {
				getOut().writeObject(new ClientBetMsg(bets));
				System.out.println("client sent bets " + bets);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}).start();
	}


	public boolean isGotUserFromServer() {
		return gotUserFromServer;
	}


	public void setGotUserFromServer(boolean gotUserFromServer) {
		this.gotUserFromServer = gotUserFromServer;
	}


	public boolean isGotRacesFromServer() {
		return gotRacesFromServer;
	}


	public void setGotRacesFromServer(boolean gotRacesFromServer) {
		this.gotRacesFromServer = gotRacesFromServer;
	}


	public boolean isServerListenerActivated() {
		return serverListenerActivated;
	}


	public void setServerListenerActivated(boolean serverListenerActivated) {
		this.serverListenerActivated = serverListenerActivated;
	}
	
	
	
	
	public void disconnectClient(){
		try {
			getOut().writeObject(new ClientDisconnectMsg(user));
			in.close();
			getOut().close();
			getSocket().close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	public ArrayList<RaceRun> getRaceRuns() {
		return raceRuns;
	}


	public void setRaceRuns(ArrayList<RaceRun> raceRuns) {
		this.raceRuns = raceRuns;
	}


	public boolean isGotRacesRunsFromServer() {
		return gotRacesRunsFromServer;
	}


	public void setGotRacesRunsFromServer(boolean gotRacesRunsFromServer) {
		this.gotRacesRunsFromServer = gotRacesRunsFromServer;
	}


	public ObjectOutputStream getOut() {
		return out;
	}


	public void setOut(ObjectOutputStream out) {
		this.out = out;
	}


	public Socket getSocket() {
		return socket;
	}


	public void setSocket(Socket socket) {
		this.socket = socket;
	}


	public ClientView getClientView() {
		return clientView;
	}


	public void setClientView(ClientView clientView) {
		this.clientView = clientView;
	}

}
