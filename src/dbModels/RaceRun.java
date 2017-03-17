package dbModels;

import java.util.ArrayList;
import java.util.Arrays;

public class RaceRun {
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


	public enum RaceStatus{
		before_start, in_progress, completed;
	}
	
	public static class CarInRace{
		private int uid;
		private ArrayList<Integer> speedList;
		
		public CarInRace(int uid, ArrayList<Integer> speedList) {
			this.uid = uid;
			this.speedList = speedList;
		}
		
		public CarInRace(int uid, Integer... speedList) {
			this.uid = uid;
			this.speedList = new ArrayList<Integer>(Arrays.asList(speedList));
		}

		public int getUid() {
			return uid;
		}

		public ArrayList<Integer> getSpeedList() {
			return speedList;
		}
	}
}
