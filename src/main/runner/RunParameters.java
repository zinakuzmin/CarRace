package main.runner;


/**
 * Global parameters for Zrace program
 * @author Zina K
 *
 */
public class RunParameters {
	
	/**
	 * Background image path
	 */
	public static String IMG_RUNNER_BACKGROUND = "resources/images/runnerbck.jpg";
	
	
	/**
	 * Path of 3D models
	 */
	public static String MODEL_PLANE = "resources/models/seymourplane-150-im.dae";
	
	
	/**
	 * Create DB on app start
	 */
	public static boolean SHOULD_INIT_DB = true;
	
	/**
	 * Number of cars in each race
	 */
	public static int NUMBER_OF_CARS_IN_RACE = 5; //Do not change
	
	/**
	 * System commision from each bet
	 */
	public static double SYSTEM_COMISSION = 0.05;
	
	/**
	 * Condition for considering if race is ready to run
	 */
	public static int NUMBER_OF_CARS_IN_RACE_THAT_HAVE_BET = 3;
	
	/**
	 * Initial amount of money for new created user
	 */
	public static double USER_INITIAL_AMOUNT_OF_MONEY = 100.0;	
	
	/**
	 * Number of songs for races run
	 */
	public static int NUMBER_OF_SONGS = 3;
	
	/**
	 * How lond race is in state ready_to_start
	 */
	public static int RACE_START_DELAY_IN_MILLISECONDS = 5_000;
	
	/**
	 * Number of active races in client view
	 */
	public static int NUMBER_OF_ACTIVE_RACES = 3;
	
	/**
	 * On app start server started automatically if value is true
	 */
	public static boolean AUTOMATIC_SERVER_START = false;
	
	/**
	 * How long completed race will be displayed on client view
	 */
	public static int DISPLAY_COMPLETED_RACE_IN_MILLISECONDS = 10_000;
	
	
	
	
	/**
	 * Consider which 3D objects to use in race
	 */
	public static boolean BUILD_PIXEL_CARS = true;
	}
