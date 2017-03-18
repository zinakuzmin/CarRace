package zrace.client.app.world.cars.objs.abstracts;

import java.util.ArrayList;

import zrace.client.app.world.cars.objs.abstracts.CarPositionCalculator.CalculatedCarInRace;

public class CarRadialMove {
	
	private ArrayList<Integer> speedList;
	private int counter = 0;
	private double lastXPos;
	private double lastZPos;
	private float lastDegree;
	private float lastMovingStep;
	private float lastMovingPoints;
	private boolean isRunFromStart;

	
	public double getLastXPos() {
		return lastXPos;
	}

	public double getLastZPos() {
		return lastZPos;
	}

	public float getLastDegree() {
		return lastDegree;
	}

	public float getLastMovingStep() {
		return lastMovingStep;
	}

	public CarRadialMove(ArrayList<Integer> speedList) {
		this.speedList = speedList;
	}

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

	public Integer getRadialPoint() {
		Integer integer = speedList.get(counter++);
//		System.out.println("Using new speed:" + integer);
		return integer;
	}

	public ArrayList<Integer> getList() {
		return speedList;
	}

	@Override
	public String toString() {
		return "CarRadialMove [speedList=" + speedList + ", counter=" + counter + ", lastXPos=" + lastXPos
				+ ", lastZPos=" + lastZPos + ", lastDegree=" + lastDegree + ", lastMovingStep=" + lastMovingStep + "]";
	}

	public float getLastMovingPoints() {
		return lastMovingPoints;
	}

	public boolean isRunFromStart() {
		return isRunFromStart;
	}
	
	
}
