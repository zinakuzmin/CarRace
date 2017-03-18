package zrace.client.app;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import com.interactivemesh.jfx.importer.ModelImporter;
import com.interactivemesh.jfx.importer.col.ColModelImporter;

import dbModels.RaceRun.CarInRace;
import dbModels.RaceRun.RaceStatus;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.SceneAntialiasing;
import javafx.scene.SubScene;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Duration;
import zrace.client.ZRaceGameController;
import zrace.client.app.world.Crowd;
import zrace.client.app.world.Road;
import zrace.client.app.world.Tribune;
import zrace.client.app.world.ZCamera;
import zrace.client.app.world.cars.CarResources;
import zrace.client.app.world.cars.objs.Songs;
import zrace.client.app.world.cars.objs.abstracts.Car;
import zrace.client.app.world.cars.objs.abstracts.CarRadialMove;

public class MainClientApp extends Application {

    private ArrayList<Car> cars = new ArrayList<>();
	private Pane pane;
	private ArrayList<CarInRace> carsInRace;
//	private long hipoteticalStartTime;
	private ZRaceGameController gameController;
	private int raceNumber;
    
    public MainClientApp(Pane racePane, ArrayList<CarInRace> carsInRace, ZRaceGameController gameController, int raceNumber) {
		this.pane = racePane;
		this.carsInRace = carsInRace;
		this.gameController = gameController;
		this.raceNumber = raceNumber;
	}

	private void buildAllCars(Xform world) throws InstantiationException, IllegalAccessException {
		for (CarInRace carInRace : carsInRace) {
    		Car car = CarResources.getCarByUid(carInRace);
			cars.add(car);
			world.getChildren().add(car.loadCar());

			car.getNode().setTranslateX(-carInRace.getRadius());
			car.getNode().setTranslateY(car.getCarHightFromGround()/2);
			car.addOrbit(carInRace.getRadius());
		}
//    	for( int i=0 ; i < 5 ; i++) {
//    		cars.add(CarResources.getCarByUid(carsInRace.get(i)));
//    	}
//
//    	for (int i = 0; i < cars.size(); i++) {
//			Car car = cars.get(i);
//			world.getChildren().add(car.loadCar());
//    		int startPositionPadding = Car.firstCarRadius+(i+1)*20;
//			car.getNode().setTranslateX(-startPositionPadding);
//			car.getNode().setTranslateY(car.getCarHightFromGround()/2);
//			car.addOrbit(startPositionPadding);
//		}
    }

    public void drawLanes(Xform world) throws FileNotFoundException {
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
//        cam.handleKeyboard(subScene, world, cars);
        cam.handleMouse(subScene, world);
        
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {

			@Override
			public void handle(WindowEvent arg0) {
//				long raceDurationInMillis2 = System.currentTimeMillis()-hipoteticalStartTime;
//				System.out.println("Race time in millis:" + raceDurationInMillis2);
//		        System.out.println("Cars driving mileage:");
//		        for (Car car : cars) {
//		        Car car = cars.get(0);
//		        	System.out.println(car.getCarName() + ":" + car.getTotalDrivingMileage());
//					Car.calculateTotalMilageOfCar(car, raceDurationInMillis2);
//		        }
//		        System.out.println("=============================");
//		        Car max = Collections.max(cars, Comparator.comparing(Car::getTotalDrivingMileage));
//		        System.out.println("Car won the race:" + max.getCarName());
//		        System.out.println("=============================");
		        closeApp();
			}
		});

        subScene.setCamera(cam.getCamera());
        pane.getChildren().add(subScene);
        
//        if (raceStarted) {
//        	System.out.println("Setting milis to 1");
//	        Car.STEP_DURATION_IN_MILLISECONDS = 1;
//	        
//        hipoteticalStartTime = System.currentTimeMillis();
        new Thread(new Runnable() {
			

			@Override
			public void run() {
				while(!isClosed()) {
					if (!gameController.getRaceRuns().get(raceNumber).getRaceStatus().equals(RaceStatus.in_progress)) {
						try {
							Thread.sleep(200);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						continue;
					}
					
					mediaPlayer.play();
					for (int i=0; i<cars.size() ; i++) {
						cars.get(i).startCar(new CarRadialMove(carsInRace.get(i).getSpeedList()));
					}
					carsStarted = true;
					return;
				}
			}
		}).start();
        
//	        
//	        
//	        System.out.println("Setting milis to 100");
//	        Car.STEP_DURATION_IN_MILLISECONDS = 100;
//	        
//        }
		
		mediaPlayer.setOnEndOfMedia(new Runnable() {
			
			@Override
			public void run() {
				System.out.println("Song ended");
			}
		});
    }
    MediaPlayer mediaPlayer ;
	private boolean raceStarted;
	private long raceDurationInMillis;
	private boolean isClosed = false;
	private boolean carsStarted;

	public void closeApp() {
		mediaPlayer.stop();
		
		if (carsStarted) {
			for (Car car : cars) {
				car.stopCar();
			}
		}
		
		pane.getChildren().clear();
		System.gc();
		isClosed = true;
	}
	
	public boolean isClosed() {
		return isClosed;
	}

	public void setMusic(Songs song, Duration seek) {
		Media songToPlay = new Media(new File(song.getSongName()).toURI().toString());
		mediaPlayer = new MediaPlayer(songToPlay);
		mediaPlayer.setStartTime(seek);
	}

	public void setIsRaceStarted(boolean raceStarted, long raceDuration) {
		this.raceStarted = raceStarted;
		this.raceDurationInMillis = raceDuration;
	}

	@SuppressWarnings("unused")
	private void buildCar(Xform world) {
//    	ModelImporter tdsImporter = new TdsModelImporter(); //3ds
    	ModelImporter tdsImporter = new ColModelImporter(); //dae, zae
//    	ModelImporter tdsImporter = new FxmlModelImporter(); //fxml
//    	ModelImporter tdsImporter = new ObjModelImporter(); //Obj, 
//    	ModelImporter tdsImporter = new X3dModelImporter(); //x3d, x3dz
    	
    	tdsImporter.read(new File("resources/models/McLaren/Car McLaren.dae"));
    	Node[] tdsMesh = (Node[]) tdsImporter.getImport();
    	
    	tdsImporter.close();
    	
    	Xform carForm = new Xform();
    	
    	carForm.getChildren().addAll(tdsMesh);
    	
    	carForm.setScaleX(0.02);
    	carForm.setScaleY(0.02);
    	carForm.setScaleZ(0.02);
    	
    	world.getChildren().addAll(carForm);
    }
}