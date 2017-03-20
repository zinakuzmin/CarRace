package zrace.client.app.world.cars;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import zrace.client.app.world.cars.objs.AlfaRomeo;
import zrace.client.app.world.cars.objs.McLaren;
import zrace.client.app.world.cars.objs.Nissan350Zcoupe;
import zrace.client.app.world.cars.objs.abstracts.Car;
import zrace.dbModels.RaceRun.CarInRace;

/**
 * This enum holds cars and info.
 *
 * @author Zina K
 */
public enum CarResources {

	/**
	 * The Alfa romeo.
	 */
	Alfa_Romeo(AlfaRomeo.class, false, 20001), 
	
	/**
	 * The Audi TT.
	 */
	AudiTT(zrace.client.app.world.cars.objs.AudiTT.class, false, 20002), 
	
	/**
	 * The Batmobile.
	 */
	Batmobile(zrace.client.app.world.cars.objs.Batmobile.class, false, 20003), 
	
	/**
	 * The Aston martin V 12.
	 */
	AstonMartinV12(zrace.client.app.world.cars.objs.AstonMartinV12.class, false, 20004), 
	
	/**
	 * The Chrysler dodge ram.
	 */
	ChryslerDodgeRam(zrace.client.app.world.cars.objs.ChryslerDodgeRam.class, false, 20005), 
	
	/**
	 * The Mercedes CLKGTR.
	 */
	MercedesCLKGTR(zrace.client.app.world.cars.objs.MercedesCLKGTR.class, false, 20006), 
	
	/**
	 * The Nissan 350 zcoupe.
	 */
	Nissan350Zcoupe(Nissan350Zcoupe.class, false, 20007), 
	
	/**
	 * The Butgatty veyron SS.
	 */
	ButgattyVeyronSS(zrace.client.app.world.cars.objs.ButgattyVeyronSS.class, false, 20008), 
	
	/**
	 * The Mc laren.
	 */
	McLaren(McLaren.class, false, 20009), 
	
	/**
	 * The Pixel alfa romeo.
	 */
	PixelAlfaRomeo(zrace.client.app.world.cars.objs.PixelAlfaRomeo.class, true, 20001),
	
	/**
	 * The Pixel audi TT.
	 */
	PixelAudiTT(zrace.client.app.world.cars.objs.PixelAudiTT.class, true, 20002),
	
	/**
	 * The Pixel batmobile.
	 */
	PixelBatmobile(zrace.client.app.world.cars.objs.PixelBatmobile.class, true, 20003),
	
	/**
	 * The Pixel aston martin.
	 */
	PixelAstonMartin(zrace.client.app.world.cars.objs.PixelAstonMartin.class, true, 20004),
	
	/**
	 * The Pixel chry dodge ram.
	 */
	PixelChryDodgeRam(zrace.client.app.world.cars.objs.PixelChryDodgeRam.class, true, 20005),
	
	/**
	 * The Pixel mrcedes CLKGTR.
	 */
	PixelMrcedesCLKGTR(zrace.client.app.world.cars.objs.PixelMrcedesCLKGTR.class, true, 20006),
	
	/**
	 * The Pixel nissan 350 Z.
	 */
	PixelNissan350Z(zrace.client.app.world.cars.objs.PixelNissan350Z.class, true, 20007),
	
	/**
	 * The Pixel bugatty.
	 */
	PixelBugatty(zrace.client.app.world.cars.objs.PixelBugatty.class, true, 20008),
	
	/**
	 * The Pixel mc laren.
	 */
	PixelMcLaren(zrace.client.app.world.cars.objs.PixelMcLaren.class, true, 20009),
	
//	PixelFamilyCar(zrace.client.app.world.cars.objs.PixelFamilyCar.class, true), 
//	PixelSportCar(zrace.client.app.world.cars.objs.PixelSportCar.class, true),
//	PixelConvertibleCar(zrace.client.app.world.cars.objs.PixelConvertibleCar.class, true),
	;

	/**
	 * The klass type
	 */
	private Class<? extends Car> klass;
	
	/**
	 * Is pixel car.
	 */
	private boolean isPixelCar;
	
	/**
	 * The car uid.
	 */
	private int carUid;


	/**
	 * Instantiates a new car resources.
	 *
	 * @param klass the klass
	 * @param isPixelCar the is pixel car
	 * @param carUid the car uid
	 */
	private CarResources(Class<? extends Car> klass, boolean isPixelCar, int carUid) {
		this.klass = klass;
		this.isPixelCar = isPixelCar;
		this.carUid = carUid;
	}
	
	/**
	 * Checks if is pixel car.
	 *
	 * @return true, if is pixel car
	 */
	public boolean isPixelCar() {
		return isPixelCar;
	}
	
	/**
	 * Gets the klass.
	 *
	 * @return the klass
	 */
	public Class<? extends Car> getKlass() {
		return klass;
	}
	
	/**
	 * Gets the pixel car resources.
	 *
	 * @return the pixel car resources
	 */
	public static List<CarResources> getPixelCarResources(){
		ArrayList<CarResources> cars = new ArrayList<>();
		
		List<CarResources> asList = Arrays.asList(CarResources.values());
		Collections.shuffle(asList);
		
		for (CarResources carResources : asList) {
			if (carResources.isPixelCar)
				cars.add(carResources);
		}
		
		return cars;
	}

	/**
	 * Gets the model car resources.
	 *
	 * @return the model car resources
	 */
	public static List<CarResources> getModelCarResources(){
		ArrayList<CarResources> cars = new ArrayList<>();

		List<CarResources> asList = Arrays.asList(CarResources.values());
		Collections.shuffle(asList);
		
		for (CarResources carResources : asList) {
			if (!carResources.isPixelCar)
				cars.add(carResources);
		}
		
		return cars;
	}

	/**
	 * Gets the car by uid.
	 *
	 * @param carInRace the car in race
	 * @return the car by uid
	 * @throws InstantiationException the instantiation exception
	 * @throws IllegalAccessException the illegal access exception
	 */
	public static Car getCarByUid(CarInRace carInRace) throws InstantiationException, IllegalAccessException {
		for (CarResources carResources : CarResources.getPixelCarResources()) {
			if (carResources.carUid == carInRace.getUid())
				return carResources.getKlass().newInstance();
		}
		
		throw new RuntimeException("Didn't found car uid in emum CarResources:" + carInRace.getUid());
	}
	
	
	
	/**
	 * Gets the car by uid.
	 *
	 * @param carId the car id
	 * @return the car by uid
	 */
	public static String getCarByUid(int carId)  {
		for (CarResources carResources : CarResources.getPixelCarResources()) {
			if (carResources.carUid == carId)
				return carResources.name().toString();
		}
		
		return null;
	}
	
}
