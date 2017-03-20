package zrace.client.app.world.cars.objs;

import com.interactivemesh.jfx.importer.ModelImporter;
import com.interactivemesh.jfx.importer.tds.TdsModelImporter;

import javafx.scene.transform.Rotate;
import zrace.client.app.world.cars.CarResources;
import zrace.client.app.world.cars.objs.abstracts.Car;

/**
 * The Class MercedesCLKGTR.
 */
public class MercedesCLKGTR extends Car {
	
	/** The uid. */
	public static int uid = 2008;

	/**
	 * Instantiates a new mercedes CLKGTR.
	 */
	public MercedesCLKGTR() {
		super(CarResources.MercedesCLKGTR);
	}

	/* (non-Javadoc)
	 * @see zrace.client.app.world.cars.objs.abstracts.Car#getRandomScaleOnLoad()
	 */
	@Override
	public double getRandomScaleOnLoad() {
		return 0.15;
	}

	/* (non-Javadoc)
	 * @see zrace.client.app.world.cars.objs.abstracts.Car#getRotateOnLoad()
	 */
	@Override
	public Rotate getRotateOnLoad() {
		return new Rotate(0, 180, 180, 0);
	}

	/* (non-Javadoc)
	 * @see zrace.client.app.world.cars.objs.abstracts.Car#getModelLocation()
	 */
	@Override
	public String getModelLocation() {
		return "resources/models/NFSHP2 - Mercedes CLK GTR/clkgtrBlue.3ds";
	}

	/* (non-Javadoc)
	 * @see zrace.client.app.world.cars.objs.abstracts.Car#getModelClassType()
	 */
	@Override
	public Class<? extends ModelImporter> getModelClassType() {
		return TdsModelImporter.class;
	}

}
