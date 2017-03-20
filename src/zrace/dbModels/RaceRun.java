package zrace.dbModels;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;
import zrace.client.app.world.cars.objs.Songs;

/**
 * This class provides an API compatible with {@link RaceRun}.
 *
 * @author Zina K
 */
public class RaceRun implements Serializable {

	/**
	 * The Constant serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * The race id.
	 */
	private int raceId;

	/**
	 * The race status.
	 */
	private RaceStatus raceStatus;

	/**
	 * The song.
	 */
	private Songs song;

	/**
	 * The cars in race.
	 */
	private ArrayList<CarInRace> carsInRace;

	/**
	 * Instantiates a new race run.
	 *
	 * @param raceId
	 *            the race id
	 * @param raceStatus
	 *            the race status
	 * @param song
	 *            the song
	 * @param carsInRace
	 *            the cars in race
	 */
	public RaceRun(int raceId, RaceStatus raceStatus, Songs song,
			ArrayList<CarInRace> carsInRace) {
		this.setRaceStatus(raceStatus);
		this.song = song;
		this.carsInRace = carsInRace;
		this.raceId = raceId;
	}

	/**
	 * Gets the race status.
	 *
	 * @return the race status
	 */
	public RaceStatus getRaceStatus() {
		return raceStatus;
	}

	/**
	 * Gets the song.
	 *
	 * @return the song
	 */
	public Songs getSong() {
		return song;
	}

	/**
	 * Gets the cars in race.
	 *
	 * @return the cars in race
	 */
	public ArrayList<CarInRace> getCarsInRace() {
		return carsInRace;
	}

	/**
	 * Gets the race id.
	 *
	 * @return the race id
	 */
	public int getRaceId() {
		return raceId;
	}

	/**
	 * Sets the race id.
	 *
	 * @param raceId
	 *            the new race id
	 */
	public void setRaceId(int raceId) {
		this.raceId = raceId;
	}

	/**
	 * Sets the race status.
	 *
	 * @param raceStatus
	 *            the new race status
	 */
	public void setRaceStatus(RaceStatus raceStatus) {
		this.raceStatus = raceStatus;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "RaceRun [raceId=" + raceId + ", raceStatus=" + raceStatus
				+ ", song=" + song + ", carsInRace=" + carsInRace + "]";
	}

	/**
	 * The Enum RaceStatus.
	 *
	 * @author Zina K
	 */
	public enum RaceStatus {

		/**
		 * The waiting.
		 */
		waiting,
		/**
		 * The ready to run.
		 */
		ready_to_run,
		/**
		 * The in progress.
		 */
		in_progress,
		/**
		 * The completed.
		 */
		completed;
	}

	/**
	 * The Class CarInRace.
	 *
	 * @author Zina K
	 */
	public static class CarInRace implements Serializable {

		/**
		 * The Constant serialVersionUID.
		 */
		private static final long serialVersionUID = 1L;

		/**
		 * The car id.
		 */
		private int carId;

		/**
		 * The radius.
		 */
		private int radius;

		/**
		 * The speed list.
		 */
		private ArrayList<Integer> speedList;

		/**
		 * The Constant speed_end.
		 */
		private static final int speed_end = 180;

		/**
		 * The Constant speed_start.
		 */
		private static final int speed_start = 72;

		/**
		 * The Constant num_of_speeds.
		 */
		private static final int num_of_speeds = 10;

		/**
		 * The Constant first_car_radius.
		 */
		private static final int first_car_radius = 80;

		/**
		 * Instantiates a new car in race.
		 *
		 * @param uid
		 *            the uid
		 * @param speedList
		 *            the speed list
		 */
		public CarInRace(int uid, ArrayList<Integer> speedList) {
			this.carId = uid;
			this.speedList = speedList;
		}

		/**
		 * Instantiates a new car in race.
		 *
		 * @param uid
		 *            the uid
		 * @param orbit
		 *            the orbit
		 */
		// orbit 0 - 4
		public CarInRace(int uid, int orbit) {
			this.carId = uid;
			this.speedList = generateCarSpeedVector();
			this.radius = first_car_radius + (orbit + 1) * 20;
		}

		/**
		 * Generate car speed vector.
		 *
		 * @return the array list
		 */
		public static ArrayList<Integer> generateCarSpeedVector() {
			ArrayList<Integer> carSpeedVector = new ArrayList<>();
			for (int i = 0; i < num_of_speeds; i++) {
				carSpeedVector.add(new Random()
						.nextInt(speed_end - speed_start) + speed_start);

			}
			return carSpeedVector;
		}

		/**
		 * Gets the uid.
		 *
		 * @return the uid
		 */
		public int getUid() {
			return carId;
		}

		/**
		 * Gets the speed list.
		 *
		 * @return the speed list
		 */
		public ArrayList<Integer> getSpeedList() {
			return speedList;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.lang.Object#toString()
		 */
		@Override
		public String toString() {
			return "CarInRace [carId=" + carId + ", speedList=" + speedList
					+ "]";
		}

		/**
		 * Gets the radius.
		 *
		 * @return the radius
		 */
		public int getRadius() {
			return radius;
		}

		/**
		 * Sets the radius.
		 *
		 * @param radius
		 *            the new radius
		 */
		public void setRadius(int radius) {
			this.radius = radius;
		}

	}
}
