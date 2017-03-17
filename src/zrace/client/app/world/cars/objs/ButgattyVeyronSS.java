package zrace.client.app.world.cars.objs;

import com.interactivemesh.jfx.importer.ModelImporter;
import com.interactivemesh.jfx.importer.tds.TdsModelImporter;

import javafx.scene.transform.Rotate;
import zrace.client.app.world.cars.CarResources;
import zrace.client.app.world.cars.objs.abstracts.Car;

public class ButgattyVeyronSS extends Car {
	public static int uid = 2005;

	public ButgattyVeyronSS() {
		super(CarResources.ButgattyVeyronSS);
	}

	@Override
	public double getRandomScaleOnLoad() {
		return 0.07;
	}

	@Override
	public Rotate getRotateOnLoad() {
		return new Rotate(0, 180, 0, 0);
	}

	@Override
	public String getModelLocation() {
		return "resources/models/BUG_VeyronSS_11/Butgatti with full parts not rigged.3ds";
	}

	@Override
	public Class<? extends ModelImporter> getModelClassType() {
		return TdsModelImporter.class;
	}

}
