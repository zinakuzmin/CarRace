package zrace.client.app.world.cars.objs;

import com.interactivemesh.jfx.importer.ModelImporter;
import com.interactivemesh.jfx.importer.tds.TdsModelImporter;

import javafx.scene.transform.Rotate;
import zrace.client.app.world.cars.CarResources;
import zrace.client.app.world.cars.objs.abstracts.Car;

public class MercedesCLKGTR extends Car {

	public MercedesCLKGTR() {
		super(CarResources.MercedesCLKGTR);
	}

	@Override
	public double getRandomScaleOnLoad() {
		return 0.15;
	}

	@Override
	public Rotate getRotateOnLoad() {
		return new Rotate(0, 180, 180, 0);
	}

	@Override
	public String getModelLocation() {
		return "resources/models/NFSHP2 - Mercedes CLK GTR/clkgtrBlue.3ds";
	}

	@Override
	public Class<? extends ModelImporter> getModelClassType() {
		return TdsModelImporter.class;
	}

}
