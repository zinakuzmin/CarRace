package zrace.client.app.world.cars.objs;

import com.interactivemesh.jfx.importer.ModelImporter;
import com.interactivemesh.jfx.importer.tds.TdsModelImporter;

import javafx.scene.transform.Rotate;
import zrace.client.app.world.cars.CarResources;
import zrace.client.app.world.cars.objs.abstracts.Car;

public class AlfaRomeo extends Car{

	public AlfaRomeo() {
		super(CarResources.Alfa_Romeo);
	}

	@Override
	public double getRandomScaleOnLoad() {
		return 2;
	}

	@Override
	public Rotate getRotateOnLoad() {
		return new Rotate(0, 180, 0, 0);
	}

	@Override
	public String getModelLocation() {
		return "resources/models/alfa-romeo/Alfa_Romeo.3ds";
	}

	@Override
	public Class<? extends ModelImporter> getModelClassType() {
		return TdsModelImporter.class;
	}
	
}
