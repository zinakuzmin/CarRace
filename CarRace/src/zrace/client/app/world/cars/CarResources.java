package zrace.client.app.world.cars;

import java.util.ArrayList;
import java.util.List;

import zrace.client.app.world.cars.objs.AlfaRomeo;
import zrace.client.app.world.cars.objs.McLaren;
import zrace.client.app.world.cars.objs.Nissan350Zcoupe;
import zrace.client.app.world.cars.objs.abstracts.Car;

public enum CarResources {

	Alfa_Romeo(AlfaRomeo.class, false), 
	AudiTT(zrace.client.app.world.cars.objs.AudiTT.class, false), 
	Batmobile(zrace.client.app.world.cars.objs.Batmobile.class, false), 
	AstonMartinV12(zrace.client.app.world.cars.objs.AstonMartinV12.class, false), 
	ChryslerDodgeRam(zrace.client.app.world.cars.objs.ChryslerDodgeRam.class, false), 
	MercedesCLKGTR(zrace.client.app.world.cars.objs.MercedesCLKGTR.class, false), 
	Nissan350Zcoupe(Nissan350Zcoupe.class, false), 
	ButgattyVeyronSS(zrace.client.app.world.cars.objs.ButgattyVeyronSS.class, false), 
	McLaren(McLaren.class, false), 
	PixelFamilyCar(zrace.client.app.world.cars.objs.PixelFamilyCar.class, true), 
	PixelSportCar(zrace.client.app.world.cars.objs.PixelSportCar.class, true),
	PixelConvertibleCar(zrace.client.app.world.cars.objs.PixelConvertibleCar.class, true),
	;

	private Class<? extends Car> klass;
	private boolean isPixelCar;


	private CarResources(Class<? extends Car> klass, boolean isPixelCar) {
		this.klass = klass;
		this.isPixelCar = isPixelCar;
	}
	
	public boolean isPixelCar() {
		return isPixelCar;
	}
	
	public Class<? extends Car> getKlass() {
		return klass;
	}
	
	public static List<CarResources> getPixelCarResources(){
		ArrayList<CarResources> cars = new ArrayList<>();
		
		CarResources[] values = CarResources.values();
		for (CarResources carResources : values) {
			if (carResources.isPixelCar)
				cars.add(carResources);
		}
		
		return cars;
	}

	public static List<CarResources> getModelCarResources(){
		ArrayList<CarResources> cars = new ArrayList<>();
		
		CarResources[] values = CarResources.values();
		for (CarResources carResources : values) {
			if (!carResources.isPixelCar)
				cars.add(carResources);
		}
		
		return cars;
	}
}
