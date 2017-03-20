package zrace.client.app.world.cars;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import dbModels.RaceRun.CarInRace;
import zrace.client.app.world.cars.objs.AlfaRomeo;
import zrace.client.app.world.cars.objs.McLaren;
import zrace.client.app.world.cars.objs.Nissan350Zcoupe;
import zrace.client.app.world.cars.objs.abstracts.Car;

public enum CarResources {

	Alfa_Romeo(AlfaRomeo.class, false, 20001), 
	AudiTT(zrace.client.app.world.cars.objs.AudiTT.class, false, 20002), 
	Batmobile(zrace.client.app.world.cars.objs.Batmobile.class, false, 20003), 
	AstonMartinV12(zrace.client.app.world.cars.objs.AstonMartinV12.class, false, 20004), 
	ChryslerDodgeRam(zrace.client.app.world.cars.objs.ChryslerDodgeRam.class, false, 20005), 
	MercedesCLKGTR(zrace.client.app.world.cars.objs.MercedesCLKGTR.class, false, 20006), 
	Nissan350Zcoupe(Nissan350Zcoupe.class, false, 20007), 
	ButgattyVeyronSS(zrace.client.app.world.cars.objs.ButgattyVeyronSS.class, false, 20008), 
	McLaren(McLaren.class, false, 20009), 
	
	PixelAlfaRomeo(zrace.client.app.world.cars.objs.PixelAlfaRomeo.class, true, 20001),
	PixelAudiTT(zrace.client.app.world.cars.objs.PixelAudiTT.class, true, 20002),
	PixelBatmobile(zrace.client.app.world.cars.objs.PixelBatmobile.class, true, 20003),
	PixelAstonMartin(zrace.client.app.world.cars.objs.PixelAstonMartin.class, true, 20004),
	PixelChryDodgeRam(zrace.client.app.world.cars.objs.PixelChryDodgeRam.class, true, 20005),
	PixelMrcedesCLKGTR(zrace.client.app.world.cars.objs.PixelMrcedesCLKGTR.class, true, 20006),
	PixelNissan350Z(zrace.client.app.world.cars.objs.PixelNissan350Z.class, true, 20007),
	PixelBugatty(zrace.client.app.world.cars.objs.PixelBugatty.class, true, 20008),
	PixelMcLaren(zrace.client.app.world.cars.objs.PixelMcLaren.class, true, 20009),
	
//	PixelFamilyCar(zrace.client.app.world.cars.objs.PixelFamilyCar.class, true), 
//	PixelSportCar(zrace.client.app.world.cars.objs.PixelSportCar.class, true),
//	PixelConvertibleCar(zrace.client.app.world.cars.objs.PixelConvertibleCar.class, true),
	;

	private Class<? extends Car> klass;
	private boolean isPixelCar;
	private int carUid;


	private CarResources(Class<? extends Car> klass, boolean isPixelCar, int carUid) {
		this.klass = klass;
		this.isPixelCar = isPixelCar;
		this.carUid = carUid;
	}
	
	public boolean isPixelCar() {
		return isPixelCar;
	}
	
	public Class<? extends Car> getKlass() {
		return klass;
	}
	
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

	public static Car getCarByUid(CarInRace carInRace) throws InstantiationException, IllegalAccessException {
		for (CarResources carResources : CarResources.getPixelCarResources()) {
			if (carResources.carUid == carInRace.getUid())
				return carResources.getKlass().newInstance();
		}
		
		throw new RuntimeException("Didn't found car uid in emum CarResources:" + carInRace.getUid());
	}
	
	
	
	public static String getCarByUid(int carId)  {
		for (CarResources carResources : CarResources.getPixelCarResources()) {
			if (carResources.carUid == carId)
				return carResources.name().toString();
		}
		
		return null;
	}
	
}
