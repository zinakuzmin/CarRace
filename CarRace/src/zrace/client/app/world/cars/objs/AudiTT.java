package zrace.client.app.world.cars.objs;

import com.interactivemesh.jfx.importer.ModelImporter;
import com.interactivemesh.jfx.importer.tds.TdsModelImporter;

import javafx.scene.transform.Rotate;
import zrace.client.app.world.cars.CarResources;
import zrace.client.app.world.cars.objs.abstracts.Car;

public class AudiTT extends Car {

	public AudiTT() {
		super(CarResources.AudiTT);
	}

	@Override
	public double getRandomScaleOnLoad() {
		return 10;
	}

	@Override
	public Rotate getRotateOnLoad() {
		return new Rotate(0, 180, 180, 0);
	}

	@Override
	public String getModelLocation() {
		return "resources/models/Audi TT/TT.3DS";
	}

	@Override
	public Class<? extends ModelImporter> getModelClassType() {
		return TdsModelImporter.class;
	}

}
