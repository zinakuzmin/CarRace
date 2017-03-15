package zrace.client.view.listeners;

import java.io.IOException;
import java.io.ObjectInputStream;

import java.util.ArrayList;

import com.mysql.jdbc.UpdatableResultSet;


import zrace.client.ZRaceGameController;
import zrace.protocol.UpdateRacesMsg;
import zrace.protocol.UserDetailsMsg;
import dbModels.Race;
import dbModels.User;
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
			while (true) {
				try {
					Object message = in.readObject();

					if (message instanceof UpdateRacesMsg) {
						Platform.runLater(() -> {
							ArrayList<Race> activaRaces = (((UpdateRacesMsg) message).getRaces());
							gameController.setActiveRaces(activaRaces);
							System.out.println("client got races from server " + activaRaces);
						});
					} else if (message instanceof UserDetailsMsg) {
						Platform.runLater(() -> {
							User user = ((UserDetailsMsg)message).getUser();
							gameController.setUserDetails(user);
							System.out.println("client got user details " + user);
						});
						

					}
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					break;
				}
			}
		}

}
