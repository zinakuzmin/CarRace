package zrace.client.app.world.cars.objs;

import com.interactivemesh.jfx.importer.ModelImporter;
import com.interactivemesh.jfx.importer.tds.TdsModelImporter;

import javafx.scene.transform.Rotate;
import zrace.client.app.world.cars.CarResources;
import zrace.client.app.world.cars.objs.abstracts.Car;

public class Nissan350Zcoupe extends Car {

	public Nissan350Zcoupe() {
		super(CarResources.Nissan350Zcoupe);
	}

	@Override
	public double getRandomScaleOnLoad() {
		return 0.18;
	}

	@Override
	public Rotate getRotateOnLoad() {
		return new Rotate(0, 180, 0, 0);
	}

	@Override
	public String getModelLocation() {
		return "resources/models/Nissan 350Z coupe/Nissan 350 Z.3ds";
	}

	@Override
	public Class<? extends ModelImporter> getModelClassType() {
		return TdsModelImporter.class;
	}

}
