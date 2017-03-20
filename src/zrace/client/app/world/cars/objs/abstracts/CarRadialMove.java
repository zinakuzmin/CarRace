package zrace.client.app.world.cars.objs.abstracts;

import java.util.ArrayList;

import zrace.client.app.world.cars.objs.abstracts.CarPositionCalculator.CalculatedCarInRace;

/**
 * The Class CarRadialMove - Setting parameters for the car movement in orbit
 */
public class CarRadialMove {
	
	/** The speed list. */
	private ArrayList<Integer> speedList;
	
	/** The counter in speed list. */
	private int counter = 0;
	
	/** The last X pos. */
	private double lastXPos;
	
	/** The last Z pos. */
	private double lastZPos;
	
	/** The last degree. */
	private float lastDegree;
	
	/** The last moving step. */
	private float lastMovingStep;
	
	/** The last moving points. */
	private float lastMovingPoints;
	
	/** The is run from start. */
	private boolean isRunFromStart;

	
	/**
	 * Gets the last X pos.
	 *
	 * @return the last X pos
	 */
	public double getLastXPos() {
		return lastXPos;
	}

	/**
	 * Gets the last Z pos.
	 *
	 * @return the last Z pos
	 */
	public double getLastZPos() {
		return lastZPos;
	}

	/**
	 * Gets the last degree.
	 *
	 * @return the last degree
	 */
	public float getLastDegree() {
		return lastDegree;
	}

	/**
	 * Gets the last moving step.
	 *
	 * @return the last moving step
	 */
	public float getLastMovingStep() {
		return lastMovingStep;
	}

	/**
	 * Instantiates a new car radial move.
	 *
	 * @param speedList the speed list
	 */
	public CarRadialMove(ArrayList<Integer> speedList) {
		this.speedList = speedList;
	}

	/**
	 * Instantiates a new car radial move.
	 *
	 * @param speedList the speed list
	 * @param carCurrentPos the car current pos
	 */
	public CarRadialMove(ArrayList<Integer> speedList, CalculatedCarInRace carCurrentPos) {
		this.speedList = speedList;
		lastXPos = carCurrentPos.getLastXPos();
		lastZPos = carCurrentPos.getLastZPos();
		lastDegree = carCurrentPos.getLastDegree();
		lastMovingStep = carCurrentPos.getLastMovingStep();
		lastMovingPoints = carCurrentPos.getLastMovingPoints();
		isRunFromStart = carCurrentPos.isRunFromStart();
		counter = carCurrentPos.isRunFromStart() ? 0 : carCurrentPos.getIndexOfSpeed();
	}

	/**
	 * Gets the radial point.
	 *
	 * @return the radial point
	 */
	public Integer getRadialPoint() {
		Integer integer = speedList.get(counter++);
//		System.out.println("Using new speed:" + integer);
		return integer;
	}

	/**
	 * Gets the list.
	 *
	 * @return the list
	 */
	public ArrayList<Integer> getList() {
		return speedList;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "CarRadialMove [speedList=" + speedList + ", counter=" + counter + ", lastXPos=" + lastXPos
				+ ", lastZPos=" + lastZPos + ", lastDegree=" + lastDegree + ", lastMovingStep=" + lastMovingStep + "]";
	}

	/**
	 * Gets the last moving points.
	 *
	 * @return the last moving points
	 */
	public float getLastMovingPoints() {
		return lastMovingPoints;
	}

	/**
	 * Checks if is should start from start or from middle of race
	 *
	 * @return true, if is run from start
	 */
	public boolean isRunFromStart() {
		return isRunFromStart;
	}
	
	
}
