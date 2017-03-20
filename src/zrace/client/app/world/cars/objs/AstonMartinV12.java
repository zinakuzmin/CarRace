package zrace.client.app.world.cars.objs;

import com.interactivemesh.jfx.importer.ModelImporter;
import com.interactivemesh.jfx.importer.tds.TdsModelImporter;

import javafx.scene.transform.Rotate;
import zrace.client.app.world.cars.CarResources;
import zrace.client.app.world.cars.objs.abstracts.Car;

/**
 * The Class AstonMartinV12.
 */
public class AstonMartinV12 extends Car{
	
	/** The uid. */
	public static int uid = 2002;

	/**
	 * Instantiates a new aston martin V 12.
	 */
	public AstonMartinV12() {
		super(CarResources.AstonMartinV12);
	}

	/* (non-Javadoc)
	 * @see zrace.client.app.world.cars.objs.abstracts.Car#getRandomScaleOnLoad()
	 */
	@Override
	public double getRandomScaleOnLoad() {
		return 8;
	}

	/* (non-Javadoc)
	 * @see zrace.client.app.world.cars.objs.abstracts.Car#getRotateOnLoad()
	 */
	@Override
	public Rotate getRotateOnLoad() {
		return new Rotate(0, 180, 0, 0);
	}

	/* (non-Javadoc)
	 * @see zrace.client.app.world.cars.objs.abstracts.Car#getModelLocation()
	 */
	@Override
	public String getModelLocation() {
		return "resources/models/Aston Martin V12/vanq.3DS";
	}

	/* (non-Javadoc)
	 * @see zrace.client.app.world.cars.objs.abstracts.Car#getModelClassType()
	 */
	@Override
	public Class<? extends ModelImporter> getModelClassType() {
		return TdsModelImporter.class;
	}

}
