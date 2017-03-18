package zrace.server;

import java.io.File;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;

import zrace.protocol.UpdateRaceRunsMsg;
import zrace.protocol.UpdateRacesMsg;
import main.runner.RunParameters;

import com.sun.xml.internal.bind.v2.runtime.unmarshaller.XsiNilLoader.Array;

import dbModels.Race;
import dbModels.RaceRun;
import dbModels.RaceRun.RaceStatus;
import javafx.scene.media.Media;

public class RacesMonitor implements Runnable {
	private ServerController controller;
	private boolean shouldRun = true;

	public RacesMonitor(ServerController controller) {
		this.setController(controller);

	}

	@Override
	public void run() {

		while (shouldRun) {

			try {
				startRace();
				setRaceCompleted();
//				addNewActiveRace();

				Thread.sleep(10_000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	public ServerController getController() {
		return controller;
	}

	public void setController(ServerController controller) {
		this.controller = controller;
	}

	public boolean isShouldStopRun() {
		return shouldRun;
	}

	public void setShouldRun(boolean shouldRun) {
		this.shouldRun = shouldRun;
	}

	public void startRace() {
		/** if there is no running races
		 * try to find race that is ready to run
		 * update race startTime to now + 10 seconds
		 * update DB (?)
		 * update raceRun of this race to status ready to run
		 * send updated activeRaces and raceRuns to client*/
		if (!controller.isRunningRace()) {
			Race race = controller.checkIfOneOfRacesCanRun();
			if (race != null) {
				System.out.println("the ready race is " + race);
				int raceIndex = controller.getActiveRaces().lastIndexOf(race);
//				race.setStartTime(new Timestamp(System.currentTimeMillis()));
				controller.getActiveRaces().get(raceIndex).setStartTime(new Timestamp(System.currentTimeMillis() + RunParameters.RACE_DELAY_IN_MILLISECONDS));
				
				RaceRun raceRun = controller.getRaceRunByRaceId(race.getRaceId());
				if (raceRun != null){
					int raceRunIndex = controller.getRaceRuns().lastIndexOf(raceRun);
					controller.getRaceRuns().get(raceRunIndex).setRaceStatus(RaceStatus.ready_to_run);
				}
				controller.sendBroadcastMessage(new UpdateRacesMsg(controller.getActiveRaces()));
				controller.sendBroadcastMessage(new UpdateRaceRunsMsg(controller.getRaceRuns()));
			}
		}
	}

	public void setRaceCompleted() {
		/**if now time is start time + song duration + 2 sec
		 * set active race completed
		 * set active race end time start time + song duration + 2 sec
		 * set run race after_run
		 * update DB
		 * remove race from active race
		 * call addNewActiveRace
		 * send new activeRaces and raceRuns to client
		 * update raceResult **/
		
		Race race = null;
		RaceRun raceRun = controller.getRaceRunByStatus(RaceStatus.in_progress);
		if (raceRun != null){
			int raceRunIndex = controller.getRaceRuns().lastIndexOf(raceRun);
			race = controller.getActiveRaceByRaceId(raceRun.getRaceId());
			int raceIndex = controller.getActiveRaces().lastIndexOf(race);
			
			if (race.getStartTime().compareTo(new Timestamp(System.currentTimeMillis())) < 0){
				long startTimeInMillis = race.getStartTime().getTime();
				long songDurationInMillis = controller.getSongDuration(raceRun.getSong().getId());
				if (System.currentTimeMillis() - (songDurationInMillis + 2000) >= startTimeInMillis){
					controller.getRaceRuns().get(raceRunIndex).setRaceStatus(RaceStatus.completed);
					controller.sendBroadcastMessage(new UpdateRaceRunsMsg(controller.getRaceRuns()));
					controller.getActiveRaces().get(raceIndex).setCompleted(true);
					controller.getActiveRaces().get(raceIndex).setEndTime(new Timestamp(System.currentTimeMillis()));
					controller.getActiveRaces().get(raceIndex).setDuration((int) ((songDurationInMillis + 2000)/1000));
					int winnerCarId = controller.getRaceWinnerCarID();
					
					controller.completeRace(race, winnerCarId);
					controller.getActiveRaces().remove(raceIndex);
					controller.getRaceRuns().remove(raceRunIndex);
					addNewActiveRace();
				}
			}
			
			
			
		}
		
		
		
	}

	public void addNewActiveRace() {
		try {
			controller.getActiveRaces().add(controller.generateRace());
			controller.sendBroadcastMessage(new UpdateRacesMsg(controller.getActiveRaces()));
			controller.sendBroadcastMessage(new UpdateRaceRunsMsg(controller.getRaceRuns()));
//			controller.ge
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
