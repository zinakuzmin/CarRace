package zrace.server;

import javafx.application.Application;
import javafx.stage.Stage;

public class ZRaceMVCServer extends Application {

	/**
	 * initialize ZRaceServerMVC controller and model.
	 * 
	 * @param primaryStage - the desired stage of the start.
	 */
	@Override
	public void start(Stage primaryStage) {
		ServerModel model 			= new ServerModel();
		ServerController controller = new ServerController(primaryStage);
//		controller.setModelListener(model);
//		model.setControllerListener(controller);
		primaryStage.setAlwaysOnTop(true);
	}
}