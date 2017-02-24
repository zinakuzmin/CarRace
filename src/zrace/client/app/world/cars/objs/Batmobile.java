package zrace.client.app.world.cars.objs;

import com.interactivemesh.jfx.importer.ModelImporter;
import com.interactivemesh.jfx.importer.obj.ObjModelImporter;

import javafx.scene.transform.Rotate;
import zrace.client.app.world.cars.CarResources;
import zrace.client.app.world.cars.objs.abstracts.Car;

public class Batmobile extends Car {

	public Batmobile() {
		super(CarResources.Batmobile);
	}

	@Override
	public double getRandomScaleOnLoad() {
		return 3;
	}

	@Override
	public Rotate getRotateOnLoad() {
		return new Rotate(0, 180, 0, 0);
	}

	@Override
	public String getModelLocation() {
		return "resources/models/Batmobile/Batmobile.obj";
	}

	@Override
	public Class<? extends ModelImporter> getModelClassType() {
		return ObjModelImporter.class;
	}

}
