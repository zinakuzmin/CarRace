package zrace.client.app;

import java.util.ArrayList;

import dbModels.RaceRun.CarInRace;
import dbModels.RaceRun.RaceStatus;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;
import zrace.client.ZRaceGameController;
import zrace.client.app.world.cars.objs.abstracts.Car;
import zrace.client.app.world.cars.objs.abstracts.CarPositionCalculator;
import zrace.client.app.world.cars.objs.abstracts.CarRadialMove;
import zrace.client.app.world.cars.objs.abstracts.CarPositionCalculator.CalculatedCarInRace;

public class RaceMonitor implements Runnable{
	private boolean shoudRun = true;
	private ZRaceGameController gameController;
	private int raceNumber;
	private MediaPlayer mediaPlayer;
	private ArrayList<Car> cars;
	private ArrayList<CarInRace> carsInRace;
	private boolean carsStarted = false;
	
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
				try {
					Thread.sleep(200);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				continue;
			}

			long raceDurationInMillis = System.currentTimeMillis()-gameController.getActiveRaces().get(raceNumber).getStartTime().getTime();
			mediaPlayer.setStartTime(Duration.millis(raceDurationInMillis));
			mediaPlayer.play();
			for (int i=0; i<cars.size() ; i++) {
				Car car = cars.get(i);
				CalculatedCarInRace carCurrentPos = CarPositionCalculator.
						calculateTotalMilageOfCar(car.getOrbitRadius(), carsInRace.get(i).getSpeedList(), raceDurationInMillis);
//				System.out.println("Done calculation for car:" + car.getCarName());
				carCurrentPos.runFromStart(false);
				car.startCar(new CarRadialMove(carsInRace.get(i).getSpeedList(), carCurrentPos));
			}
			carsStarted  = true;
			return;
		}
	}
	
	public void stopThread(){
		shoudRun = false;
	}
	
	public boolean isCarsStarted() {
		return carsStarted;
	}
}
