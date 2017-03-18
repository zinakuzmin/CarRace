package zrace.client.app.world.cars.objs.abstracts;

import java.util.ArrayList;

public class CarPositionCalculator {
	
	public static CalculatedCarInRace calculateTotalMilageOfCar(float orbitRadius, ArrayList<Integer> speedList, long raceDurationInMillis) {
		float totalMilage = 0;
		double lastXPos = 0;
		double lastZPos = 0;
		double lastDegree = 0;
		double lastMovingStep = 0;
		
		int speedArrayPos = 0;
		float movingStep = 0;
		float movingPoints = ((float)orbitRadius/(float)Car.firstCarRadius)*speedList.get(speedArrayPos);
        float lastMovingPoint = movingPoints;
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
				System.out.println("Speed changed new:" + speedList.get(speedArrayPos));
            	movingPoints = ((float)orbitRadius/(float)Car.firstCarRadius)*speedList.get(speedArrayPos);
            	timePassedInLap = 0;
            }
			timePassedInLap += Car.STEP_DURATION_IN_MILLISECONDS;
		}
//		System.out.println("Calculated milage:" + totalMilage);
		System.out.println("Calculated loops:" + totalNumOfLoops);
		
		return new CalculatedCarInRace(totalMilage, lastXPos, lastZPos, lastDegree, lastMovingStep);
	}
	
	public static class CalculatedCarInRace{

		private float totalMilage;
		private double lastXPos;
		private double lastZPos;
		private double lastDegree;
		private double lastMovingStep;

		public CalculatedCarInRace(float totalMilage, double lastXPos, double lastZPos, double lastDegree, double lastMovingStep) {
			this.totalMilage = totalMilage;
			this.lastXPos = lastXPos;
			this.lastZPos = lastZPos;
			this.lastDegree = lastDegree;
			this.lastMovingStep = lastMovingStep;
		}

		public double getLastDegree() {
			return lastDegree;
		}

		public double getLastMovingStep() {
			return lastMovingStep;
		}

		public double getLastXPos() {
			return lastXPos;
		}

		public double getLastZPos() {
			return lastZPos;
		}

		public float getTotalMilage() {
			return totalMilage;
		}
		
		
		
	}
}