package zrace.client.view.listeners;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;






import zrace.client.ZRaceGameController;
import zrace.protocol.Message;
import zrace.protocol.UpdateRaceRunsMsg;
import zrace.protocol.UpdateRacesMsg;
import zrace.protocol.UserDetailsMsg;
import dbModels.Race;
import dbModels.User;
import dbModels.RaceRun;
import javafx.application.Platform;
import javafx.collections.ObservableList;


public class ServerListener extends Thread{
		private ObjectInputStream in;
//		private ObservableList<Race> activeRaces;
		private ZRaceGameController gameController;
		
		public ServerListener(ObjectInputStream in,ZRaceGameController gameController) {
			this.in = in;
//			this.activeRaces = activeRaces;
			this.gameController = gameController;
		}

		@Override
		public void run() {
			System.out.println("Client start to listen");
			gameController.setServerListenerActivated(true);
			while (true) {
				System.out.println("client waiting for message from server");
				try {
					Message message = (Message) in.readObject();
					System.out.println("message " + message);
					if (message instanceof UpdateRacesMsg) {
//						new Thread(() -> {
//						Platform.runLater(() -> {
							System.out.println("Client got message " + message);
							ArrayList<Race> activaRaces = (((UpdateRacesMsg) message).getRaces());
							gameController.setActiveRaces(activaRaces);
							System.out.println("client got races from server " + activaRaces);
							gameController.setGotRacesFromServer(true);
//						}).start();
					} else if (message instanceof UserDetailsMsg) {
//						new Thread(() -> {
//						Platform.runLater(() -> {
							System.out.println("Client got message " + message);
							User user = ((UserDetailsMsg)message).getUser();
							gameController.setUserDetails(user);
							System.out.println("client got user details " + user);
							gameController.setGotUserFromServer(true);
//						}).start();
						
						

					
					
				} else if (message instanceof UpdateRaceRunsMsg) {
//					new Thread(() -> {
						System.out.println("Client got message " + message);
						ArrayList<RaceRun> raceRuns = (((UpdateRaceRunsMsg) message).getRaceRuns());
						gameController.setRaceRuns(raceRuns);
						System.out.println("client got races runs from server " + raceRuns);
						gameController.setGotRacesRunsFromServer(true);
//						gameController.getClientView().setRacesStatusInView();
//					}).start();
				}
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					break;
				}
			}
		}

}
