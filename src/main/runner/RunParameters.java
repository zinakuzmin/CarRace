package main.runner;

import java.util.Observable;

public class RunParameters {
	
	public static String IMG_RUNNER_BACKGROUND = "resources/images/runnerbck.jpg";
	
	
	//3D Models
	public static String MODEL_PLANE = "resources/models/seymourplane-150-im.dae";
	
	//Server params
	public static boolean SHOULD_INIT_DB = true;
	public static int NUMBER_OF_CARS_IN_RACE = 5; //Do not change
	public static double SYSTEM_COMISSION = 0.05;
	public static int NUMBER_OF_CARS_IN_RACE_THAT_HAVE_BET = 3;
	public static double USER_INITIAL_AMOUNT_OF_MONEY = 100.0;	
	public static int NUMBER_OF_SONGS = 3;
	public static int RACE_DELAY_IN_MILLISECONDS = 5_000;
	public static int NUMBER_OF_ACTIVE_RACES = 3;
	
	
	
	
	
	
	//Client params
	public static boolean BUILD_PIXEL_CARS = true;
	}
