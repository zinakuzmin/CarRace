package zrace.server;

import java.io.File;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;

import zrace.protocol.UpdateRaceRunsMsg;
import zrace.protocol.UpdateRacesMsg;
import zrace.protocol.WinnerCarMsg;
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
		int messageId = 1;
		while (shouldRun) {

			try {
				System.out.println("Current loop " + messageId);
				startRace(messageId);
//				setRaceCompleted();
				messageId++;
				// addNewActiveRace();

				Thread.sleep(1_000);
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

	public void startRace(int messageId) {
		/**
		 * if there is no running races try to find race that is ready to run
		 * update race startTime to now + 10 seconds update DB (?) update
		 * raceRun of this race to status ready to run send updated activeRaces
		 * and raceRuns to client
		 */

		if (!controller.isRunningRace()) {
			Race race = controller.checkIfOneOfRacesCanRun();
			if (race != null) {
				System.out.println("the ready race is " + race);
				int raceIndex = controller.getActiveRaces().lastIndexOf(race);
				// race.setStartTime(new Timestamp(System.currentTimeMillis()));

				RaceRun raceRun = controller.getRaceRunByRaceId(race
						.getRaceId());
				if (raceRun != null) {
					int raceRunIndex = controller.getRaceRuns().lastIndexOf(
							raceRun);
					if (controller.getRaceRuns().get(raceRunIndex)
							.getRaceStatus().equals(RaceStatus.waiting)) {
						controller
								.getActiveRaces()
								.get(raceIndex)
								.setStartTime(
										new Timestamp(
												System.currentTimeMillis()
														+ RunParameters.RACE_START_DELAY_IN_MILLISECONDS));
						controller.getRaceRuns().get(raceRunIndex)
								.setRaceStatus(RaceStatus.ready_to_run);
					} else if (controller.getRaceRuns().get(raceRunIndex)
							.getRaceStatus().equals(RaceStatus.ready_to_run)) {
						if (controller
								.getActiveRaces()
								.get(raceIndex)
								.getStartTime()
								.compareTo(
										new Timestamp(System
												.currentTimeMillis())) <= 0) {
							controller.getRaceRuns().get(raceRunIndex)
									.setRaceStatus(RaceStatus.in_progress);
							controller.getLogger().logStringMessage("Race " + race.getRaceFullName() + "is started \n");
						}

					}
					// else if
					// (controller.getRaceRuns().get(raceRunIndex).getRaceStatus().equals(RaceStatus.in_progress)){
					// if
					// (controller.getActiveRaces().get(raceIndex).getStartTime().compareTo(new
					// Timestamp(System.currentTimeMillis())) >= 0){
					// controller.getRaceRuns().get(raceRunIndex).setRaceStatus(RaceStatus.completed);
					// }
					// }

				}
				controller.sendBroadcastMessage(new UpdateRacesMsg(messageId,
						controller.getActiveRaces()));
				controller.sendBroadcastMessage(new UpdateRaceRunsMsg(
						messageId, controller.getRaceRuns()));
				// messageId++;
			}
		} else
			setRaceCompleted();
	}

	public void setRaceCompleted() {
		/**
		 * if now time is start time + song duration + 2 sec set active race
		 * completed set active race end time start time + song duration + 2 sec
		 * set run race after_run update DB remove race from active race call
		 * addNewActiveRace send new activeRaces and raceRuns to client update
		 * raceResult
		 **/

		Race race = null;
		RaceRun raceRun = controller.getRaceRunByStatus(RaceStatus.in_progress);
		if (raceRun != null) {
			int raceRunIndex = controller.getRaceRuns().lastIndexOf(raceRun);
			race = controller.getActiveRaceByRaceId(raceRun.getRaceId());
			int raceIndex = controller.getActiveRaces().lastIndexOf(race);

			if (race.getStartTime().compareTo(
					new Timestamp(System.currentTimeMillis())) < 0) {
				long startTimeInMillis = race.getStartTime().getTime();
				long songDurationInMillis = raceRun.getSong().getDuraionInSeconds()*1000;
				System.out.println("currect time " + System.currentTimeMillis());
				System.out.println("duration of song time " + songDurationInMillis);
				if (System.currentTimeMillis() - (songDurationInMillis + 2000) >= startTimeInMillis) {
					controller.getRaceRuns().get(raceRunIndex)
							.setRaceStatus(RaceStatus.completed);
					controller.sendBroadcastMessage(new UpdateRaceRunsMsg(0,
							controller.getRaceRuns()));
					controller.getActiveRaces().get(raceIndex)
							.setCompleted(true);
					controller
							.getActiveRaces()
							.get(raceIndex)
							.setEndTime(
									new Timestamp(System.currentTimeMillis()));
					controller
							.getActiveRaces()
							.get(raceIndex)
							.setDuration(
									(int) ((songDurationInMillis + 2000) / 1000));
					int winnerCarId = controller.getRaceWinnerCarID(raceRun);
					controller.getLogger().logStringMessage("Race " + race.getRaceFullName() + "is completed " + ". Won car " + winnerCarId);
					controller.sendBroadcastMessage(new WinnerCarMsg(0, winnerCarId, raceRun.getRaceId()));
					controller.completeRace(race, winnerCarId);
					controller.getActiveRaces().remove(raceIndex);
					controller.getRaceRuns().remove(raceRunIndex);
					new Thread(() -> {
						try {
							Thread.sleep(RunParameters.DISPLAY_COMPLATED_RACE_IN_MILLISECONDS);
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						addNewActiveRace();
					}).start();					
				}
			}

		}

	}

	public void addNewActiveRace() {
		try {
			controller.getActiveRaces().add(controller.generateRace());
			controller.sendBroadcastMessage(new UpdateRacesMsg(0, controller
					.getActiveRaces()));
			controller.sendBroadcastMessage(new UpdateRaceRunsMsg(0, controller
					.getRaceRuns()));
			// controller.ge
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
