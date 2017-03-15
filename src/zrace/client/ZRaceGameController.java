package zrace.client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Date;

import dbModels.*;
import zrace.client.view.ClientView;
import zrace.client.view.listeners.ServerListener;
import zrace.protocol.ClientBetMsg;
import zrace.protocol.ClientConnectMsg;
import zrace.protocol.ClientGetRaces;
import zrace.server.ClientHandler;
import javafx.application.Platform;
import javafx.stage.Stage;

public class ZRaceGameController {
	private Socket socket;
	private ObjectInputStream in;
	private ObjectOutputStream out;
	private ArrayList<Race> activeRaces;
	private User user;

	public ZRaceGameController(Stage primaryStage) {
		activeRaces = new ArrayList<Race>();
		
		new Thread(() -> {
			try {
				
				System.out.println("start game");
				socket = new Socket("localhost", 8000);
				System.out.println("Socket inited");
				out = new ObjectOutputStream(socket.getOutputStream());
				System.out.println("init out");
//				out.writeObject(new ClientConnectMsg(123245, "blabla"));
				in = new ObjectInputStream(socket.getInputStream());
				System.out.println("init in");
				//Start listen to server
				new Thread(new ServerListener(in, this)).start();
			
				
			} catch (IOException ex) {
				System.err.println(ex);
			}
		}).start();
		
		
		
		
		try {
			System.out.println("start GUI");
			ClientView clientView = new ClientView(this);
			clientView.start(new Stage());
			//thread
			

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	
	public synchronized void sendLoginOrRegisterMessage(int userId, String userFullName){
		ClientConnectMsg msg = new ClientConnectMsg(userId, userFullName);
		
		System.out.println("client: send login msg");
		System.out.println("output stream is " + out);
		try {
			out.writeObject(msg);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public synchronized void setActiveRaces(ArrayList<Race> races){
		activeRaces.clear();
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
				out.writeObject(new ClientBetMsg(bets));
				System.out.println("client sent bets " + bets);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}).start();
	}

}
