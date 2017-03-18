package zrace.client.app.world.cars.objs.abstracts;

import java.util.ArrayList;

public class CarRadialMove {
	
	private ArrayList<Integer> speedList;
	private int counter = 0;

	
	public CarRadialMove(ArrayList<Integer> speedList) {
		this.speedList = speedList;
	}

	public Integer getRadialPoint() {
		Integer integer = speedList.get(counter++);
//		System.out.println("Using new speed:" + integer);
		return integer;
	}

	public ArrayList<Integer> getList() {
		return speedList;
	}
}
