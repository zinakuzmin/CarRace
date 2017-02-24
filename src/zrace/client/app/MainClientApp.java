package zrace.client.app;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

import com.interactivemesh.jfx.importer.ModelImporter;
import com.interactivemesh.jfx.importer.col.ColModelImporter;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import zrace.client.app.world.Crowd;
import zrace.client.app.world.Road;
import zrace.client.app.world.Tribune;
import zrace.client.app.world.ZCamera;
import zrace.client.app.world.cars.CarResources;
import zrace.client.app.world.cars.objs.abstracts.Car;

public class MainClientApp extends Application {

    private ArrayList<Car> cars = new ArrayList<>();
    
    private void buildAllCars(Xform world) throws InstantiationException, IllegalAccessException {
//    	cars.add(new AlfaRomeo());
//    	cars.add(new AudiTT());
//    	cars.add(new AstonMartinV12());
//    	cars.add(new Batmobile());
//    	cars.add(new ChryslerDodgeRam());
//    	cars.add(new MercedesCLKGTR());
//    	cars.add(new Nissan350Zcoupe());
//    	cars.add(new McLaren());

    	boolean shoudBePixelCar = true;
    	List<CarResources> carResources = shoudBePixelCar ? CarResources.getPixelCarResources() : CarResources.getModelCarResources();
    	
    	for( int i=0 ; i < 5 ; i++) {
    		int carI = new Random().nextInt(carResources.size());
    		cars.add(carResources.get(carI).getKlass().newInstance());
    	}

    	for (int i = 0; i < cars.size(); i++) {
			Car car = cars.get(i);
			world.getChildren().add(car.loadCar());
    		int startPositionPadding = Car.firstCarRadius+(i+1)*20;
			car.getNode().setTranslateX(-startPositionPadding);
			car.getNode().setTranslateY(car.getCarHightFromGround()/2);
			// need to add car hight/2 and and put it on the road not on center XXXXXXXXXX
			car.addOrbit(startPositionPadding);
		}
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
    public void start(Stage primaryStage) throws InstantiationException, IllegalAccessException, FileNotFoundException {
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
		
        Scene scene = new Scene(root, 1024, 768, true);
        scene.setFill(Color.FORESTGREEN);
        cam.handleKeyboard(scene, world, cars);
        cam.handleMouse(scene, world);

        primaryStage.setTitle("CarZ");
        primaryStage.setScene(scene);
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {

			@Override
			public void handle(WindowEvent arg0) {
		        System.out.println("Cars driving mileage:");
		        for (Car car : cars) {
		        	System.out.println(car.getCarName() + ":" + car.getTotalDrivingMileage());
		        }

		        System.out.println("=============================");
		        Car max = Collections.max(cars, Comparator.comparing(Car::getTotalDrivingMileage));
		        System.out.println("Car won the race:" + max.getCarName());
		        System.out.println("=============================");
			}
		});

        primaryStage.show();

        scene.setCamera(cam.getCamera());
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