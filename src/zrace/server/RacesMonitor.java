package zrace.server;

import java.sql.Timestamp;

import dbModels.Race;
import dbModels.RaceRun;
import dbModels.RaceRun.RaceStatus;
import main.runner.RunParameters;
import zrace.protocol.UpdateRaceRunsMsg;
import zrace.protocol.UpdateRacesMsg;

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
				setRaceCompleted();
				messageId++;
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

	public void startRace(int messageId) {
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
				
				RaceRun raceRun = controller.getRaceRunByRaceId(race.getRaceId());
				if (raceRun != null){
					int raceRunIndex = controller.getRaceRuns().lastIndexOf(raceRun);
					if (controller.getRaceRuns().get(raceRunIndex).getRaceStatus().equals(RaceStatus.waiting)){
						controller.getActiveRaces().get(raceIndex).setStartTime(new Timestamp(System.currentTimeMillis() + RunParameters.RACE_START_DELAY_IN_MILLISECONDS));
						controller.getRaceRuns().get(raceRunIndex).setRaceStatus(RaceStatus.ready_to_run);
					}
					else if (controller.getRaceRuns().get(raceRunIndex).getRaceStatus().equals(RaceStatus.ready_to_run)){
						if (controller.getActiveRaces().get(raceIndex).getStartTime().compareTo(new Timestamp(System.currentTimeMillis())) <= 0){
							controller.getRaceRuns().get(raceRunIndex).setRaceStatus(RaceStatus.in_progress);
						}
							
						
					}
//					else if (controller.getRaceRuns().get(raceRunIndex).getRaceStatus().equals(RaceStatus.in_progress)){
//						if (controller.getActiveRaces().get(raceIndex).getStartTime().compareTo(new Timestamp(System.currentTimeMillis())) >= 0){
//							controller.getRaceRuns().get(raceRunIndex).setRaceStatus(RaceStatus.completed);
//						}
//					}
					
				}
				controller.sendBroadcastMessage(new UpdateRacesMsg(messageId, controller.getActiveRaces()));
				controller.sendBroadcastMessage(new UpdateRaceRunsMsg(messageId, controller.getRaceRuns()));
//				messageId++;
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
		if (controller.isRunningRace()){
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
						controller.sendBroadcastMessage(new UpdateRaceRunsMsg(0, controller.getRaceRuns()));
						controller.getActiveRaces().get(raceIndex).setCompleted(true);
						controller.getActiveRaces().get(raceIndex).setEndTime(new Timestamp(System.currentTimeMillis()));
						controller.getActiveRaces().get(raceIndex).setDuration((int) ((songDurationInMillis + 2000)/1000));
						int winnerCarId = controller.getRaceWinnerCarID(raceRun);
						
						controller.completeRace(race, winnerCarId);
						controller.getActiveRaces().remove(raceIndex);
						controller.getRaceRuns().remove(raceRunIndex);
						addNewActiveRace();
					}
				}
				
				
				
			}
			
		}
		
		
		
	}

	public void addNewActiveRace() {
		try {
			controller.getActiveRaces().add(controller.generateRace());
			controller.sendBroadcastMessage(new UpdateRacesMsg(0, controller.getActiveRaces()));
			controller.sendBroadcastMessage(new UpdateRaceRunsMsg(0, controller.getRaceRuns()));
//			controller.ge
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
