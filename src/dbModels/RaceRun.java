package dbModels;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class RaceRun {
	private int raceId;
	private RaceStatus raceStatus;
	private int songUid;
	private ArrayList<CarInRace> carsInRace;

	public RaceRun(RaceStatus raceStatus, int songUid, ArrayList<CarInRace> carsInRace) {
		this.raceStatus = raceStatus;
		this.songUid = songUid;
		this.carsInRace = carsInRace;
	}
	
	public RaceStatus getRaceStatus() {
		return raceStatus;
	}

	public int getSongUid() {
		return songUid;
	}

	public ArrayList<CarInRace> getCarsInRace() {
		return carsInRace;
	}
	

	@Override
	public String toString() {
		return "RaceRun [raceStatus=" + raceStatus + ", songUid=" + songUid
				+ ", carsInRace=" + carsInRace + "]";
	}


	public int getRaceId() {
		return raceId;
	}

	public void setRaceId(int raceId) {
		this.raceId = raceId;
	}




	public enum RaceStatus{
		before_start, in_progress, completed;
	}
	
	public static class CarInRace{
		private int carId;
		private ArrayList<Integer> speedList;
		private static final int speed_end = 180;
		private static final int speed_start = 72;
		private static final int num_of_speeds = 10;
		
		public CarInRace(int uid, ArrayList<Integer> speedList) {
			this.carId = uid;
			this.speedList = speedList;
		}
		
		public CarInRace(int uid, Integer... speedList) {
			this.carId = uid;
			this.speedList = new ArrayList<Integer>(Arrays.asList(speedList));
		}
		
		public CarInRace(int uid){
			this.carId = uid;
			this.speedList = generateCarSpeedVector();
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
		
		
	}
}
