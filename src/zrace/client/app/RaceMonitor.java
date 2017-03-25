package zrace.client.app;

import java.io.File;
import java.util.ArrayList;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;
import zrace.client.ZRaceGameController;
import zrace.client.app.world.cars.objs.abstracts.Car;
import zrace.client.app.world.cars.objs.abstracts.CarPositionCalculator;
import zrace.client.app.world.cars.objs.abstracts.CarRadialMove;
import zrace.client.app.world.cars.objs.abstracts.CarPositionCalculator.CalculatedCarInRace;
import zrace.dbModels.RaceRun.CarInRace;
import zrace.dbModels.RaceRun.RaceStatus;

/**
 * @author Zina K
 *
 */
public class RaceMonitor implements Runnable{
	/**
	 * stops the thread, in case like race switched to other
	 */
	private boolean shoudRun = true;
	/**
	 * Control client behavior 
	 */
	private ZRaceGameController gameController;
	/**
	 * race id
	 */
	private int raceNumber;
	/**
	 * media player
	 */
	private MediaPlayer mediaPlayer;
	/**
	 * cars in race
	 */
	private ArrayList<Car> cars;
	/**
	 * cars in race - info
	 */
	private ArrayList<CarInRace> carsInRace;
	/**
	 * If cars already in middle of race
	 */
	private boolean carsStarted = false;
	/**
	 * cars waited in this thread
	 */
	boolean waited = false;
	
	/**
	 * Constructor of the thread
	 * @param gameController	client behavior 
	 * @param raceNumber		race id
	 * @param mediaPlayer		player
	 * @param cars				cars array
	 * @param carsInRace		cars info
	 */
	public RaceMonitor(ZRaceGameController gameController, int raceNumber, MediaPlayer mediaPlayer,
			ArrayList<Car> cars, ArrayList<CarInRace> carsInRace) {
		this.gameController = gameController;
		this.raceNumber = raceNumber;
		this.mediaPlayer = mediaPlayer;
		this.cars = cars;
		this.carsInRace = carsInRace;
	}

	@Override
	public void run() {
		while(shoudRun) {
			if (!gameController.getRaceRuns().get(raceNumber).getRaceStatus().equals(RaceStatus.in_progress)) {
				waited = true;
				try {
					Thread.sleep(200);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				continue;
			}
			
			if (waited) {
				Media songToPlay = new Media(new File("race_start.mp3").toURI().toString());
				new MediaPlayer(songToPlay).play();
				try {
					Thread.sleep(3_300);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			long raceDurationInMillis = System.currentTimeMillis()-gameController.getActiveRaces().get(raceNumber).getStartTime().getTime();
			mediaPlayer.setStartTime(Duration.millis(raceDurationInMillis));
			mediaPlayer.play();
			System.out.println("Started from begining:" + waited);
			for (int i=0; i<cars.size() ; i++) {
				Car car = cars.get(i);
				CalculatedCarInRace carCurrentPos = CarPositionCalculator.
						calculateTotalMilageOfCar(car.getOrbitRadius(), carsInRace.get(i).getSpeedList(), raceDurationInMillis);
				//				System.out.println("Done calculation for car:" + car.getCarName());
				carCurrentPos.runFromStart(waited);
				car.startCar(new CarRadialMove(carsInRace.get(i).getSpeedList(), carCurrentPos));
			}
			carsStarted  = true;
			return;
		}
	}
	
	/**
	 * Stops the thread
	 */
	public void stopThread(){
		shoudRun = false;
	}
	
	/**
	 * @return were the cars started
	 */
	public boolean isCarsStarted() {
		return carsStarted;
	}
}
