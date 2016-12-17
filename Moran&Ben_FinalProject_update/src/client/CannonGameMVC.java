package client;

import javafx.application.Application;
import javafx.stage.Stage;

/**
 * @author Moran Tenzer & Ben Nave
 *
 */
public class CannonGameMVC extends Application {

	/**
	 * initialize CannonGameMVC controller and model.
	 * 
	 * @param primaryStage - the desired stage of the start.
	 */
	@Override
	public void start(Stage primaryStage) {
		CannonGameModel model = new CannonGameModel();
		CannonGameController controller = new CannonGameController(primaryStage);
		controller.setModelListener(model);
		model.setControllerListener(controller);
		primaryStage.setAlwaysOnTop(true);
	}

}
