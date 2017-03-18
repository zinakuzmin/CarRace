package zrace.client.app.world.cars.objs.abstracts;

import java.io.File;
import java.util.ArrayList;
import java.util.Random;

import com.interactivemesh.jfx.importer.ModelImporter;

import javafx.animation.KeyFrame;
import javafx.animation.RotateTransition;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;
import javafx.util.Duration;
import zrace.client.app.Xform;
import zrace.client.app.world.cars.CarResources;

public abstract class Car {

    public static final int timeToChangeSpeed = 5_000;
	CarResources carRes;
	protected Xform carForm;
	private double totalDrivingMileage = 0;
	private int orbitRadius;
	protected double carHightFromGround = 5;
	
	public abstract double getRandomScaleOnLoad();
	public abstract Rotate getRotateOnLoad();
	public abstract String getModelLocation();
	public abstract Class<? extends ModelImporter> getModelClassType();
	
	public CarResources getCarResource() {
		return carRes;
	}
	
	public Car(CarResources car) {
		this.carRes = car;
	}
	
	public Xform loadCar() throws InstantiationException, IllegalAccessException {
		return loadCar(new Translate(0, 0 ,0));
	}
	
    public Xform loadCar(Translate translate) throws InstantiationException, IllegalAccessException {
		ModelImporter tdsImporter = getModelClassType().newInstance();
		
		tdsImporter.read(new File(getModelLocation()));
		Node[] tdsMesh = (Node[]) tdsImporter.getImport();

		tdsImporter.close();
		
		carForm = new Xform();
    	
    	carForm.getChildren().addAll(tdsMesh);

    	carForm.setScale(getRandomScaleOnLoad(), getRandomScaleOnLoad(), getRandomScaleOnLoad());
    	carForm.setRotate(getRotateOnLoad().getPivotX(), getRotateOnLoad().getPivotY(), getRotateOnLoad().getPivotZ());
    	
    	carForm.setTranslate(translate.getX(), translate.getY(), translate.getZ());
    	
    	return carForm;
    }
    
    public String getCarName() {
    	return carRes.toString();
    }

	public Xform getNode() {
		return carForm;
	}
	
	public void addToMileageDrove(double droveFor) {
		totalDrivingMileage  += droveFor;
	}
	
	public double getTotalDrivingMileage() {
		return totalDrivingMileage;
	}
	public void addOrbit(int orbitRadius) {
		this.orbitRadius = orbitRadius;
	}
	
	public int getOrbitRadius() {
		return orbitRadius;
	}
    
	// Number of steps for a circle during one orbit -> car speed [currentSpeed]
    public static int STEP_DURATION_IN_MILLISECONDS = 100;
	public static int firstCarRadius = 80;
	Timeline timeline;
	private CarRadialMove speedList;
	public long totalNumOfLoops = 0;

	@SuppressWarnings({ "unchecked", "rawtypes" })
    public void startCar(CarRadialMove speed) {
		this.speedList = speed;
		timeline = new Timeline(new KeyFrame(Duration.ZERO, new EventHandler() {
	        float movingStep = 0;
	        float movingPoints = ((float)getOrbitRadius()/(float)firstCarRadius)*speed.getRadialPoint();
	        float lastMovingPoint = movingPoints;
	        long timeInMillis = 0;
	
	        @Override
	        public void handle(Event event) {
	        	totalNumOfLoops++;
	        	movingStep++;
	
	        	if (lastMovingPoint != movingPoints) {
	        		movingStep = (movingPoints * movingStep)/lastMovingPoint;
	        		if (movingPoints < lastMovingPoint) //slowing
	        			movingStep += 1;
	        	}

	        	float degree = 360f/movingPoints;
				double angleAlpha = movingStep * Math.toRadians(degree);

                // p(x) = x(0) + r * sin(a)
                // p(y) = y(0) - r * cos(a)
            	double moveX = -orbitRadius * Math.cos(angleAlpha);
				double moveZ = orbitRadius * Math.sin(angleAlpha);
				moveCar(moveX, moveZ);
            	lastMovingPoint = movingPoints;

            	rotateCar(degree, movingStep);
            	addToMileageDrove(2*Math.PI*orbitRadius/movingPoints);
 
	            // Reset after one orbit.
	            if (movingStep > movingPoints) {
	            	movingStep = 0;
	            }
				if (timeInMillis > timeToChangeSpeed) {
	            	movingPoints = ((float)getOrbitRadius()/(float)firstCarRadius)*speed.getRadialPoint();
	            	timeInMillis = 0;
	            }
	            timeInMillis += STEP_DURATION_IN_MILLISECONDS;
	        }
		}), new KeyFrame(Duration.millis(STEP_DURATION_IN_MILLISECONDS)));
	
		timeline.setCycleCount(Timeline.INDEFINITE);
		timeline.play();
	}
    
    public void stopCar() {
    	timeline.stop();
    	System.out.println("run loops:" + totalNumOfLoops);
    }
    
    private void moveCar(double x, double z) {
    	TranslateTransition move = new TranslateTransition();
    	move.setNode(carForm);
    	move.setToX(x);
    	move.setToZ(z);
    	move.setDuration(Duration.millis(STEP_DURATION_IN_MILLISECONDS));
    	
    	move.play();
    }

    // rotate car and place it
    private void rotateCar(double angle, float movingStep) {
    	RotateTransition rotate = new RotateTransition();
    	rotate.setFromAngle(movingStep*angle);
    	rotate.setByAngle(angle);
    	rotate.setAxis(Rotate.Y_AXIS);
    	rotate.setNode(carForm);
	    rotate.setDuration(Duration.millis(STEP_DURATION_IN_MILLISECONDS));

	    rotate.play();
    }
	
	public static Car getRandomCarInstance() throws InstantiationException, IllegalAccessException {
		int carI = new Random().nextInt(CarResources.values().length);
		return CarResources.values()[carI].getKlass().newInstance();
	}
	
	public double getCarHightFromGround() {
		return carHightFromGround;
	}
	
	public void translateCar(long raceDuration) {
		
	}
	public ArrayList<Integer> getSpeedList() {
		return speedList.getList();
	}
	
}
