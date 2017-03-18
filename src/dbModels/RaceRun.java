package dbModels;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import zrace.client.app.world.cars.objs.Songs;
import zrace.client.app.world.cars.objs.abstracts.Car;

public class RaceRun implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int raceId;
	private RaceStatus raceStatus;
	private Songs song;
	private ArrayList<CarInRace> carsInRace;

	public RaceRun(RaceStatus raceStatus, Songs song, ArrayList<CarInRace> carsInRace) {
		this.setRaceStatus(raceStatus);
		this.song = song;
		this.carsInRace = carsInRace;
	}
	
	public RaceStatus getRaceStatus() {
		return raceStatus;
	}

	public Songs getSong() {
		return song;
	}

	public ArrayList<CarInRace> getCarsInRace() {
		return carsInRace;
	}
	

	@Override
	public String toString() {
		return "RaceRun [raceStatus=" + getRaceStatus() + ", songUid=" + getSong().getId()
				+ ", carsInRace=" + carsInRace + "]";
	}


	public int getRaceId() {
		return raceId;
	}

	public void setRaceId(int raceId) {
		this.raceId = raceId;
	}




	public void setRaceStatus(RaceStatus raceStatus) {
		this.raceStatus = raceStatus;
	}






	public enum RaceStatus{
		before_start, ready_to_run, in_progress, completed;
	}
	
	public static class CarInRace implements Serializable{
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private int carId;
		private int radius;
		private ArrayList<Integer> speedList;
		private static final int speed_end = 180;
		private static final int speed_start = 72;
		private static final int num_of_speeds = 10;
		private static final int first_car_radius = 80;
		
		public CarInRace(int uid, ArrayList<Integer> speedList) {
			this.carId = uid;
			this.speedList = speedList;
		}
		
//		public CarInRace(int uid, Integer... speedList) {
//			this.carId = uid;
//			this.speedList = new ArrayList<Integer>(Arrays.asList(speedList));
//		}
		
		//orbit 0 - 4
		public CarInRace(int uid, int orbit){
			this.carId = uid;
			this.speedList = generateCarSpeedVector();
//			this.radius = first_car_radius+(orbit+1)*20;
		}
		
		
		public static ArrayList<Integer> generateCarSpeedVector(){
			ArrayList<Integer> carSpeedVector = new ArrayList<>();
			for (int i = 0; i < num_of_speeds; i++){
				carSpeedVector.add(new Random().nextInt(speed_end-speed_start)+speed_start);
				
			}
			return carSpeedVector;
		}

		public int getUid() {
			return carId;
		}

		public ArrayList<Integer> getSpeedList() {
			return speedList;
		}

		@Override
		public String toString() {
			return "CarInRace [carId=" + carId + ", speedList=" + speedList
					+ "]";
		}

		public int getRadius() {
			return radius;
		}

		public void setRadius(int radius) {
			this.radius = radius;
		}
		
		
	}
}
