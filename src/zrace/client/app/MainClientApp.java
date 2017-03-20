package zrace.client.app;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.SceneAntialiasing;
import javafx.scene.SubScene;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import zrace.client.ZRaceGameController;
import zrace.client.app.world.Crowd;
import zrace.client.app.world.Road;
import zrace.client.app.world.Tribune;
import zrace.client.app.world.ZCamera;
import zrace.client.app.world.cars.CarResources;
import zrace.client.app.world.cars.objs.Songs;
import zrace.client.app.world.cars.objs.abstracts.Car;
import zrace.dbModels.RaceRun.CarInRace;

/**
 * This class supplies API for {@link MainClientApp}
 * @author Zina K
 *
 */
public class MainClientApp extends Application {

    /**
     * Array of cars in race
     */
    private ArrayList<Car> cars = new ArrayList<>();
    
	/**
	 * Container of race
	 */
	private Pane pane;
	
	/**
	 * Array of car info
	 */
	private ArrayList<CarInRace> carsInRace;
	
	/**
	 * Control client behavior 
	 */
	private ZRaceGameController gameController;
	
	/**
	 * Race id
	 */
	private int raceNumber;
	
	/**
	 * Monitor race events
	 */
	private RaceMonitor raceThread;
    
    
    /**
     * Media player
     */
    MediaPlayer mediaPlayer ;
    /**
     * is app closed
     */
	private boolean isClosed = false;

	
	
    /**
     * Main constructor 
     * @param racePane
     * @param carsInRace
     * @param gameController
     * @param raceNumber
     */
    public MainClientApp(Pane racePane, ArrayList<CarInRace> carsInRace, ZRaceGameController gameController, int raceNumber) {
		this.pane = racePane;
		this.carsInRace = carsInRace;
		this.gameController = gameController;
		this.raceNumber = raceNumber;
	}

	/**
	 * @param world
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	private void buildAllCars(Xform world) throws InstantiationException, IllegalAccessException {
		for (CarInRace carInRace : carsInRace) {
    		Car car = CarResources.getCarByUid(carInRace);
			cars.add(car);
			world.getChildren().add(car.loadCar());

			car.getNode().setTranslateX(-carInRace.getRadius());
			car.getNode().setTranslateY(car.getCarHightFromGround()/2);
			car.addOrbit(carInRace.getRadius());
		}
    }

    /**
     * @param world
     * @throws FileNotFoundException
     */
    private void drawLanes(Xform world) throws FileNotFoundException {
    	Color gray = Color.gray(0.2);
    	Color black = Color.BLACK;
    	int widthOfRoad = 16;
    	int widthOfLaneDevider = 10;
    	double paddingOfLaneDivider = widthOfLaneDevider/2;
    	double laneDividerSkewYPos = -0.5;
    	
		world.getChildren().add(Road.getLaneFromCenter(100, widthOfRoad, gray));
		world.getChildren().add(Road.getLaneFromCenter(laneDividerSkewYPos, 110+paddingOfLaneDivider, widthOfLaneDevider, black));
    	world.getChildren().add(Road.getLaneFromCenter(120, widthOfRoad, gray));
    	world.getChildren().add(Road.getLaneFromCenter(laneDividerSkewYPos, 130+paddingOfLaneDivider, widthOfLaneDevider, black));
    	world.getChildren().add(Road.getLaneFromCenter(140, widthOfRoad, gray));
    	world.getChildren().add(Road.getLaneFromCenter(laneDividerSkewYPos, 150+paddingOfLaneDivider, widthOfLaneDevider, black));
    	world.getChildren().add(Road.getLaneFromCenter(160, widthOfRoad, gray));
    	world.getChildren().add(Road.getLaneFromCenter(laneDividerSkewYPos, 170+paddingOfLaneDivider, widthOfLaneDevider, black));
    	world.getChildren().add(Road.getLaneFromCenter(180, widthOfRoad, gray));
    }
    
    /* (non-Javadoc)
     * @see javafx.application.Application#start(javafx.stage.Stage)
     */
    @Override
    public void start(Stage primaryStage) throws InstantiationException, IllegalAccessException, FileNotFoundException, InterruptedException {
        Group root = new Group();
        Xform world = new Xform();
//        System.setProperty("javafx.pulseLogger","true");
        root.getChildren().add(world);
        ZCamera cam = new ZCamera();
        cam.buildCamera(root);
//        Axis.buildAxes(world);
        buildAllCars(world);
        drawLanes(world);

        Tribune tribunes = Tribune.generateAllSeatsAndColumns(20, 10, 10);
		world.getChildren().add(tribunes.getTribunes());
		world.getChildren().add(Crowd.generateCrowd(tribunes).getCrowdGroup());
		
		SubScene subScene = new SubScene(root, 750, 550, true, SceneAntialiasing.BALANCED);
		
        subScene.setFill(Color.FORESTGREEN);
        cam.handleMouse(subScene, world);
        
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {

			@Override
			public void handle(WindowEvent arg0) {
		        closeApp();
			}
		});

        subScene.setCamera(cam.getCamera());
        pane.getChildren().add(subScene);
        
        raceThread = new RaceMonitor(gameController, raceNumber, mediaPlayer, cars, carsInRace);
        new Thread(raceThread).start();
        
		mediaPlayer.setOnEndOfMedia(new Runnable() {
			
			@Override
			public void run() {
				System.out.println("Song ended");
				try {
					Thread.sleep(2_000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				if (raceThread.isCarsStarted()) {
					for (Car car : cars) {
						car.stopCar();
					}
				}
				
			}
		});
    }

	/**
	 * Closing race - stop threads and music
	 */
	public void closeApp() {
		System.out.println("Closing Race view:" + raceNumber);
		raceThread.stopThread();
		mediaPlayer.stop();
		
		if (raceThread.isCarsStarted()) {
			for (Car car : cars) {
				car.stopCar();
			}
		}
		
		pane.getChildren().clear();
		System.gc();
		isClosed = true;
	}
	
	/**
	 * @return
	 */
	public boolean isClosed() {
		return isClosed;
	}

	/**
	 * Set song for race
	 * @param song to play
	 */
	public void setMusic(Songs song) {
		Media songToPlay = new Media(new File(song.getSongName()).toURI().toString());
		mediaPlayer = new MediaPlayer(songToPlay);
	}
}