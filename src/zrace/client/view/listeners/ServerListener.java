package zrace.client.view.listeners;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;

import javafx.application.Platform;
import zrace.client.ZRaceGameController;
import zrace.dbModels.Race;
import zrace.dbModels.RaceRun;
import zrace.dbModels.User;
import zrace.protocol.Message;
import zrace.protocol.UpdateRaceRunsMsg;
import zrace.protocol.UpdateRacesMsg;
import zrace.protocol.UserDetailsMsg;
import zrace.protocol.WinnerCarMsg;

/**
 * The class provides API for {@link ServerListener}
 * 
 * @author Zina K
 *
 */
public class ServerListener extends Thread {
	private ObjectInputStream in;
	private ZRaceGameController gameController;

	/**
	 * Initialize {@link ServerListener}
	 * @param in
	 * @param gameController
	 */
	public ServerListener(ObjectInputStream in,ZRaceGameController gameController) {
		this.in = in;
		this.gameController = gameController;
	}

	/* (non-Javadoc)
	 * @see java.lang.Thread#run()
	 * Run listener for outcomming messages
	 */
	@Override
	public void run() {
		
		gameController.setServerListenerActivated(true);
		while (true) {
			
			try {
				Message message = (Message) in.readObject();
				
				if (message instanceof UpdateRacesMsg) {
					ArrayList<Race> activaRaces = (((UpdateRacesMsg) message).getRaces());
					gameController.setActiveRaces(activaRaces);
					gameController.setGotRacesFromServer(true);
					Platform.runLater(() -> gameController.getClientView().setRacesNamesInView());
					
				} else if (message instanceof UserDetailsMsg) {
					
					User user = ((UserDetailsMsg) message).getUser();
					gameController.setUserDetails(user);
					gameController.setGotUserFromServer(true);
					Platform.runLater(() -> gameController.getClientView()
							.setUserDetailsInView());
					

				} else if (message instanceof UpdateRaceRunsMsg) {
					
					ArrayList<RaceRun> raceRuns = (((UpdateRaceRunsMsg) message)
							.getRaceRuns());
					gameController.setRaceRuns(raceRuns);
					gameController.setGotRacesRunsFromServer(true);
					Platform.runLater(() -> gameController.getClientView()
							.setRacesStatusInView());
					
				}

				else if (message instanceof WinnerCarMsg) {
					
					gameController.setLastWinnerCarId(((WinnerCarMsg) message)
							.getCarId());
					Platform.runLater(() -> gameController.getClientView().setUserDetailsInView());
				}
				
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				break;
			}
		}
	}

}
