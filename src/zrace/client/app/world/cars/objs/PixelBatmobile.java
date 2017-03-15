package zrace.client.app.world.cars.objs;

import java.util.Random;

import com.interactivemesh.jfx.importer.ModelImporter;

import javafx.geometry.Point3D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Box;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;
import zrace.client.app.Xform;
import zrace.client.app.world.cars.CarResources;
import zrace.client.app.world.cars.objs.abstracts.Car;
import zrace.client.app.world.cars.objs.pixels.Pixel;

public class PixelBatmobile extends Car{
	public static int uid = 2004;
    
	public PixelBatmobile() {
		super(CarResources.PixelBatmobile);
	}

	@Override
	public Xform loadCar() {
		carForm = new Xform();
        Random random = new Random();
		
        Box body = Pixel.getPixelBox(Color.rgb(random.nextInt(255), random.nextInt(255), random.nextInt(255)), 10, 5, 20);
        
        Box wheelLeftFront = Pixel.getPixelBox(Color.BLACK, 2, 3, 3, new Translate(5, -2.5, 5));
        Box wheelLeftRear = Pixel.getPixelBox(Color.BLACK, 2, 3, 3, new Translate(5, -2.5, -5));
        Box wheelRightRear = Pixel.getPixelBox(Color.BLACK, 2, 3, 3, new Translate(-5, -2.5, -5));
        Box wheelRightFront = Pixel.getPixelBox(Color.BLACK, 2, 3, 3, new Translate(-5, -2.5, 5));
        
        Color rgbOfUpperBodyAndGant = Color.rgb(random.nextInt(255), random.nextInt(255), random.nextInt(255));
		Box upperCar = Pixel.getPixelBox(rgbOfUpperBodyAndGant, 8, 4, 1, new Translate(0, 3, 3));
		upperCar.setRotate(-45);
		upperCar.setRotationAxis(new Point3D(1, 0, 0));
		
		Box backSeat = Pixel.getPixelBox(rgbOfUpperBodyAndGant, 8, 1, 4, new Translate(0, 3, -6));
		
		Box leftDoor = Pixel.getPixelBox(rgbOfUpperBodyAndGant, 0.5, 1, 7, new Translate(3.75, 3, -1));
		Box rightDoor = Pixel.getPixelBox(rgbOfUpperBodyAndGant, 0.5, 1, 7, new Translate(-3.75, 3, -1));

        Box gantLeftFront = Pixel.getPixelBox(rgbOfUpperBodyAndGant, 1, 1, 1, new Translate(6, -2.5, 5));
        Box gantLeftRear = Pixel.getPixelBox(rgbOfUpperBodyAndGant, 1, 1, 1, new Translate(6, -2.5, -5));
        Box gantRightRear = Pixel.getPixelBox(rgbOfUpperBodyAndGant, 1, 1, 1, new Translate(-6, -2.5, -5));
        Box gantRightFront = Pixel.getPixelBox(rgbOfUpperBodyAndGant, 1, 1, 1, new Translate(-6, -2.5, 5));
        
        Box headLightR = Pixel.getPixelBox(Color.WHITE, 1, 1, 1, new Translate(4, 0, 10));
        Box headLightL = Pixel.getPixelBox(Color.WHITE, 1, 1, 1, new Translate(-4, 0, 10));
        
        Box rearLightR = Pixel.getPixelBox(Color.RED, 2, 1, 1, new Translate(4, 0, -10));
        Box rearLightL = Pixel.getPixelBox(Color.RED, 2, 1, 1, new Translate(-4, 0, -10));

        
		carForm.getChildren().add(body);
		carForm.getChildren().add(wheelLeftFront);
		carForm.getChildren().add(wheelLeftRear);
		carForm.getChildren().add(wheelRightRear);
		carForm.getChildren().add(wheelRightFront);
		carForm.getChildren().add(upperCar);
		carForm.getChildren().add(headLightR);
		carForm.getChildren().add(headLightL);
		carForm.getChildren().add(rearLightR);
		carForm.getChildren().add(rearLightL);
		carForm.getChildren().add(gantLeftFront);
		carForm.getChildren().add(gantLeftRear);
		carForm.getChildren().add(gantRightRear);
		carForm.getChildren().add(gantRightFront);
		carForm.getChildren().add(backSeat);
		carForm.getChildren().add(leftDoor);
		carForm.getChildren().add(rightDoor);
		
		double hightScale = getRandomScaleOnLoad();
		carForm.setScale(getRandomScaleOnLoad(), hightScale, getRandomScaleOnLoad());
		
		carHightFromGround = 8.5*hightScale;
		
        return carForm;
    }

	@Override
	public double getRandomScaleOnLoad() {
		return new Random().nextFloat() * (0.5) + 0.6;
	}

	@Override
	public Rotate getRotateOnLoad() {
		return new Rotate(0, 0, 0, 0);
	}

	@Override
	public String getModelLocation() {
		throw new NotImplementedException();
	}

	@Override
	public Class<? extends ModelImporter> getModelClassType() {
		throw new NotImplementedException();
	}

}
