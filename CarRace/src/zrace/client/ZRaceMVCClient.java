package zrace.client;

import javafx.application.Application;
import javafx.stage.Stage;

public class ZRaceMVCClient extends Application{

	/**
	 * initialize ZRaceClientMVC controller and model.
	 * 
	 * @param primaryStage - the desired stage of the start.
	 */
	@Override
	public void start(Stage primaryStage) {
		ZRaceGameModel model = new ZRaceGameModel();
		ZRaceGameController controller = new ZRaceGameController(primaryStage);
//		controller.setModelListener(model);
//		model.setControllerListener(controller);
		primaryStage.setAlwaysOnTop(true);
	}
}
