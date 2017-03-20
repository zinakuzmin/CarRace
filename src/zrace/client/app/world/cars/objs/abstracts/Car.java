package zrace.client.app.world.cars.objs.abstracts;

import java.io.File;
import java.util.ArrayList;
import java.util.Random;

import com.interactivemesh.jfx.importer.ModelImporter;

import javafx.animation.Animation.Status;
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

/**
 * The Class Car.
 */
public abstract class Car {

    /** The Constant timeToChangeSpeed. */
    public static final int timeToChangeSpeed = 10_000;
	
	/** The car res. */
	CarResources carRes;
	
	/** The car form. */
	protected Xform carForm;
	
	/** The total driving mileage. */
	private double totalDrivingMileage = 0;
	
	/** The orbit radius. */
	private int orbitRadius;
	
	/** The car hight from ground. */
	protected double carHightFromGround = 5;
	
	/**
	 * Gets the random scale on load.
	 *
	 * @return the random scale on load
	 */
	public abstract double getRandomScaleOnLoad();
	
	/**
	 * Gets the rotate on load.
	 *
	 * @return the rotate on load
	 */
	public abstract Rotate getRotateOnLoad();
	
	/**
	 * Gets the model location.
	 *
	 * @return the model location
	 */
	public abstract String getModelLocation();
	
	/**
	 * Gets the model class type.
	 *
	 * @return the model class type
	 */
	public abstract Class<? extends ModelImporter> getModelClassType();
	
	/**
	 * Gets the car resource.
	 *
	 * @return the car resource
	 */
	public CarResources getCarResource() {
		return carRes;
	}
	
	/**
	 * Instantiates a new car.
	 *
	 * @param car the car
	 */
	public Car(CarResources car) {
		this.carRes = car;
	}
	
	/**
	 * Load car.
	 *
	 * @return the xform
	 * @throws InstantiationException the instantiation exception
	 * @throws IllegalAccessException the illegal access exception
	 */
	public Xform loadCar() throws InstantiationException, IllegalAccessException {
		return loadCar(new Translate(0, 0 ,0));
	}
	
    /**
     * Load car.
     *
     * @param translate the translate
     * @return the xform
     * @throws InstantiationException the instantiation exception
     * @throws IllegalAccessException the illegal access exception
     */
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
    
    /**
     * Gets the car name.
     *
     * @return the car name
     */
    public String getCarName() {
    	return carRes.toString();
    }

	/**
	 * Gets the node.
	 *
	 * @return the node
	 */
	public Xform getNode() {
		return carForm;
	}
	
	/**
	 * Adds the to mileage drove.
	 *
	 * @param droveFor the drove for
	 */
	public void addToMileageDrove(double droveFor) {
		totalDrivingMileage  += droveFor;
	}
	
	/**
	 * Gets the total driving mileage.
	 *
	 * @return the total driving mileage
	 */
	public double getTotalDrivingMileage() {
		return totalDrivingMileage;
	}
	
	/**
	 * Adds the orbit.
	 *
	 * @param orbitRadius the orbit radius
	 */
	public void addOrbit(int orbitRadius) {
		this.orbitRadius = orbitRadius;
	}
	
	/**
	 * Gets the orbit radius.
	 *
	 * @return the orbit radius
	 */
	public int getOrbitRadius() {
		return orbitRadius;
	}
    
	/** The step duration in milliseconds. */
	// Number of steps for a circle during one orbit -> car speed [currentSpeed]
    public static int STEP_DURATION_IN_MILLISECONDS = 100;
	
	/** The first car radius. */
	public static int firstCarRadius = 80;
	
	/** The timeline. */
	Timeline timeline;
	
	/** The radial move params. */
	private CarRadialMove radialMoveParams;
	
	/** The total num of loops. */
	public long totalNumOfLoops = 0;

	/**
	 * Start car.
	 *
	 * @param radialMoveParams the radial move params
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
    public void startCar(CarRadialMove radialMoveParams) {
		this.radialMoveParams = radialMoveParams;
		
		if (!radialMoveParams.isRunFromStart()) {
			carForm.setTranslateX(radialMoveParams.getLastXPos());
			carForm.setTranslateZ(radialMoveParams.getLastZPos());
			carForm.setRotateY(radialMoveParams.getLastDegree());
		}
		
		timeline = new Timeline(new KeyFrame(Duration.ZERO, new EventHandler() {
	        float movingStep = radialMoveParams.isRunFromStart() ? 0 : radialMoveParams.getLastMovingStep();
	        float movingPoints = radialMoveParams.isRunFromStart() ? 
	        		((float)getOrbitRadius()/(float)firstCarRadius)*radialMoveParams.getRadialPoint() : radialMoveParams.getLastMovingPoints();//
	        		
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
	            	movingPoints = ((float)getOrbitRadius()/(float)firstCarRadius)*radialMoveParams.getRadialPoint();
	            	timeInMillis = 0;
	            }
	            timeInMillis += STEP_DURATION_IN_MILLISECONDS;
	        }
		}), new KeyFrame(Duration.millis(STEP_DURATION_IN_MILLISECONDS)));
	
		timeline.setCycleCount(Timeline.INDEFINITE);
		timeline.play();
	}
    
    /**
     * Stop car.
     */
    public void stopCar() {
    	if (timeline.getStatus().equals(Status.RUNNING))
    		timeline.stop();
    }
    
    /**
     * Move car.
     *
     * @param x the x
     * @param z the z
     */
    private void moveCar(double x, double z) {
    	TranslateTransition move = new TranslateTransition();
    	move.setNode(carForm);
    	move.setToX(x);
    	move.setToZ(z);
    	move.setDuration(Duration.millis(STEP_DURATION_IN_MILLISECONDS));
    	
    	move.play();
    }

    /**
     * Rotate car.
     *
     * @param angle the angle
     * @param movingStep the moving step
     */
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
	
	/**
	 * Gets the random car instance.
	 *
	 * @return the random car instance
	 * @throws InstantiationException the instantiation exception
	 * @throws IllegalAccessException the illegal access exception
	 */
	public static Car getRandomCarInstance() throws InstantiationException, IllegalAccessException {
		int carI = new Random().nextInt(CarResources.values().length);
		return CarResources.values()[carI].getKlass().newInstance();
	}
	
	/**
	 * Gets the car hight from ground.
	 *
	 * @return the car hight from ground
	 */
	public double getCarHightFromGround() {
		return carHightFromGround;
	}
	
	/**
	 * Translate car.
	 *
	 * @param raceDuration the race duration
	 */
	public void translateCar(long raceDuration) {
		
	}
	
	/**
	 * Gets the speed list.
	 *
	 * @return the speed list
	 */
	public ArrayList<Integer> getSpeedList() {
		return radialMoveParams.getList();
	}
	
}
