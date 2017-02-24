package zrace.client.app.world.cars.objs.abstracts;

import java.util.Random;

public class CarRadialMove {
	
	private static final int speed_end = 180;
	private static final int speed_start = 72;
	

	public static Integer getNewRadialVelue() {
		return new Random().nextInt(speed_end-speed_start)+speed_start;
	}

	private Integer radialPoint;

	public CarRadialMove(int speed) {
		this.radialPoint = speed;
	}
	
	public CarRadialMove() {
		radialPoint = getNewRadialVelue();
	}

	public Integer getRadialPoint() {
		return radialPoint;
	}
}
