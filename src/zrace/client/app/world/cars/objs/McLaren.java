package zrace.client.app.world.cars.objs;

import com.interactivemesh.jfx.importer.ModelImporter;
import com.interactivemesh.jfx.importer.col.ColModelImporter;

import javafx.scene.transform.Rotate;
import zrace.client.app.world.cars.CarResources;
import zrace.client.app.world.cars.objs.abstracts.Car;

public class McLaren extends Car {
	public static int uid = 2007;

	public McLaren() {
		super(CarResources.McLaren);
	}

	@Override
	public double getRandomScaleOnLoad() {
		return 0.007;
	}

	@Override
	public Rotate getRotateOnLoad() {
		return new Rotate(0, 180, 0, 0);
	}

	@Override
	public String getModelLocation() {
		return "resources/models/McLaren/Car McLaren.dae";
	}

	@Override
	public Class<? extends ModelImporter> getModelClassType() {
		return ColModelImporter.class;
	}

}
