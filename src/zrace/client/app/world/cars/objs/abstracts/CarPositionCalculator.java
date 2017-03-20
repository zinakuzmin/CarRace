package zrace.client.app.world.cars.objs.abstracts;

import java.util.ArrayList;

/**
 * The Class CarPositionCalculator.
 */
public class CarPositionCalculator {
	
	/**
	 * Calculate total milage of car.
	 *
	 * @param orbitRadius the orbit radius
	 * @param speedList the speed list
	 * @param raceDurationInMillis the race duration in millis
	 * @return the calculated car in race
	 */
	public static CalculatedCarInRace calculateTotalMilageOfCar(float orbitRadius, ArrayList<Integer> speedList, long raceDurationInMillis) {
		float totalMilage = 0;
		double lastXPos = 0;
		double lastZPos = 0;
		float lastDegree = 0;
		float lastMovingStep = 0;
		int indexOfSpeed = 0;
		
		int speedArrayPos = 0;
		float movingStep = 0;
		float movingPoints = ((float)orbitRadius/(float)Car.firstCarRadius)*speedList.get(speedArrayPos);
        float lastMovingPoint = movingPoints;
        @SuppressWarnings("unused")
		long totalNumOfLoops = 0;
        

//		System.out.println("Speed changed:" + car.getSpeedList().get(speedArrayPos));
        
        long timePassedInLap=0;
		double flooredTotalTime = Math.floor(raceDurationInMillis/100)*100;
//		System.out.println("Floored time:" + flooredTotalTime);
		for(long i=0 ; i < flooredTotalTime ; i+=Car.STEP_DURATION_IN_MILLISECONDS) {
			totalNumOfLoops++;
			movingStep++;
			
        	if (lastMovingPoint != movingPoints) {
        		movingStep = (movingPoints * movingStep)/lastMovingPoint;
        		if (movingPoints < lastMovingPoint) //slowing
        			movingStep += 1;
        	}
        	float degree = 360f/movingPoints;
			double angleAlpha = movingStep * Math.toRadians(degree);

            // p(x) = x(0) + r * sin(a)
            // p(y) = y(0) - r * cos(a)
        	lastXPos = -orbitRadius * Math.cos(angleAlpha);
			lastZPos = orbitRadius * Math.sin(angleAlpha);
//			moveCar(moveX, moveZ);

			lastDegree = degree;
			lastMovingStep = movingStep;
//        	rotateCar(degree, movingStep);
        	totalMilage += 2*Math.PI*orbitRadius/movingPoints;
        	lastMovingPoint = movingPoints;

            // Reset after one orbit.
            if (movingStep > movingPoints) {
            	movingStep = 0;
            }
			if (timePassedInLap > Car.timeToChangeSpeed) {
				speedArrayPos++;
//				System.out.println("Speed changed new:" + speedList.get(speedArrayPos));
            	movingPoints = ((float)orbitRadius/(float)Car.firstCarRadius)*speedList.get(speedArrayPos);
            	timePassedInLap = 0;
            	indexOfSpeed++;
            }
			timePassedInLap += Car.STEP_DURATION_IN_MILLISECONDS;
		}
//		System.out.println("Calculated milage:" + totalMilage);
//		System.out.println("Calculated loops:" + totalNumOfLoops);
		
		return new CalculatedCarInRace(totalMilage, lastXPos, lastZPos, lastDegree, lastMovingStep, movingPoints, indexOfSpeed);
	}
	
	/**
	 * The Class CalculatedCarInRace.
	 */
	public static class CalculatedCarInRace{

		/** The total milage. */
		private float totalMilage;
		
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
		
		/** The run from start. */
		private boolean runFromStart;
		
		/** The index of speed. */
		private int indexOfSpeed;

		/**
		 * Instantiates a new calculated car in race.
		 *
		 * @param totalMilage the total milage
		 * @param lastXPos the last X pos
		 * @param lastZPos the last Z pos
		 * @param lastDegree the last degree
		 * @param lastMovingStep the last moving step
		 * @param lastMovingPoints the last moving points
		 * @param indexOfSpeed the index of speed
		 */
		public CalculatedCarInRace(float totalMilage, double lastXPos, double lastZPos, float lastDegree, float lastMovingStep, float lastMovingPoints, int indexOfSpeed) {
			this.totalMilage = totalMilage;
			this.lastXPos = lastXPos;
			this.lastZPos = lastZPos;
			this.lastDegree = lastDegree;
			this.lastMovingStep = lastMovingStep;
			this.lastMovingPoints = lastMovingPoints;
			this.indexOfSpeed = indexOfSpeed;
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
		 * Gets the total milage.
		 *
		 * @return the total milage
		 */
		public float getTotalMilage() {
			return totalMilage;
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
		 * Run from start.
		 *
		 * @param runFromStart the run from start
		 */
		public void runFromStart(boolean runFromStart) {
			this.runFromStart = runFromStart;
		}

		/**
		 * Checks if is run from start.
		 *
		 * @return true, if is run from start
		 */
		public boolean isRunFromStart() {
			return runFromStart;
		}
		
		/**
		 * Gets the index of speed.
		 *
		 * @return the index of speed
		 */
		public int getIndexOfSpeed() {
			return indexOfSpeed;
		}
		
	}
}
