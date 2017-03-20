package zrace.client.app.world.cars.objs;

import com.interactivemesh.jfx.importer.ModelImporter;
import com.interactivemesh.jfx.importer.tds.TdsModelImporter;

import javafx.scene.transform.Rotate;
import zrace.client.app.world.cars.CarResources;
import zrace.client.app.world.cars.objs.abstracts.Car;

/**
 * The Class AlfaRomeo.
 */
public class AlfaRomeo extends Car{
	
	/** The uid. */
	public static int uid = 2001;
	
	/**
	 * Instantiates a new alfa romeo.
	 */
	public AlfaRomeo() {
		super(CarResources.Alfa_Romeo);
	}

	/* (non-Javadoc)
	 * @see zrace.client.app.world.cars.objs.abstracts.Car#getRandomScaleOnLoad()
	 */
	@Override
	public double getRandomScaleOnLoad() {
		return 2;
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
		return "resources/models/alfa-romeo/Alfa_Romeo.3ds";
	}

	/* (non-Javadoc)
	 * @see zrace.client.app.world.cars.objs.abstracts.Car#getModelClassType()
	 */
	@Override
	public Class<? extends ModelImporter> getModelClassType() {
		return TdsModelImporter.class;
	}
	
}
