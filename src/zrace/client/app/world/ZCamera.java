package zrace.client.app.world;

import javafx.event.EventHandler;
import javafx.scene.Camera;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.PerspectiveCamera;
import javafx.scene.SubScene;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import zrace.client.app.Xform;

/**
 * This class sets the camera for the race.
 *
 * @author Zina K
 */
public class ZCamera {
	
	/**
	 * The camera.
	 */
	final PerspectiveCamera camera = new PerspectiveCamera(true);
	
	/** The camera xform. */
	final Xform cameraXform = new Xform();
	
	/**
	 * The camera xform 2.
	 */
	final Xform cameraXform2 = new Xform();
	
	/**
	 * The camera xform 3.
	 */
	final Xform cameraXform3 = new Xform();
	
	/**
	 * The camera distance.
	 */
	final double cameraDistance = 650;
	
	/**
	 * The mouse pos X.
	 */
	double mousePosX;
	
	/**
	 * The mouse pos Y.
	 */
	double mousePosY;
	
	/**
	 * The mouse old X.
	 */
	double mouseOldX;
	
	/**
	 * The mouse old Y.
	 */
	double mouseOldY;
	
	/**
	 * The mouse delta X.
	 */
	double mouseDeltaX;
	
	/**
	 * The mouse delta Y.
	 */
	double mouseDeltaY;

	/**
	 * Builds the camera.
	 *
	 * @param root the root
	 */
	public void buildCamera(Group root) {
		root.getChildren().add(cameraXform);
		cameraXform.getChildren().add(cameraXform2);
		cameraXform2.getChildren().add(cameraXform3);
		cameraXform3.getChildren().add(camera);
		cameraXform3.setRotateZ(180.0);

		camera.setNearClip(0.1);
		camera.setFarClip(10000.0);
		camera.setTranslateZ(-cameraDistance);
		cameraXform.ry.setAngle(320.0);
		cameraXform.rx.setAngle(35);
	}

	/**
	 * Handle mouse.
	 *
	 * @param subScene the sub scene
	 * @param root the root
	 */
	public void handleMouse(SubScene subScene, final Node root) {
		subScene.setOnScroll(new EventHandler<ScrollEvent>() {
			@Override
			public void handle(ScrollEvent event) {
				if (event.getDeltaY() != 0) {
					double z = camera.getTranslateZ();
					double newZ = z + event.getDeltaY() * 0.1 * 3;
					camera.setTranslateZ(newZ);
				}
			}
		});

		subScene.setOnMousePressed(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent me) {
				mousePosX = me.getSceneX();
				mousePosY = me.getSceneY();
				mouseOldX = me.getSceneX();
				mouseOldY = me.getSceneY();
			}
		});
		subScene.setOnMouseDragged(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent me) {
				mouseOldX = mousePosX;
				mouseOldY = mousePosY;
				mousePosX = me.getSceneX();
				mousePosY = me.getSceneY();
				mouseDeltaX = (mousePosX - mouseOldX);
				mouseDeltaY = (mousePosY - mouseOldY);

				double modifier = 1.0;
				double modifierFactor = 0.1;

				if (me.isControlDown()) {
					modifier = 0.1;
				}
				if (me.isShiftDown()) {
					modifier = 10.0;
				}
				if (me.isPrimaryButtonDown()) {
					cameraXform.ry.setAngle(cameraXform.ry.getAngle() - mouseDeltaX * modifierFactor * modifier * 2.0); // +
					cameraXform.rx.setAngle(cameraXform.rx.getAngle() + mouseDeltaY * modifierFactor * modifier * 2.0); // -
				} else if (me.isSecondaryButtonDown()) {
					// double z = camera.getTranslateZ();
					// double newZ = z + mouseDeltaX * modifierFactor *
					// modifier;
					// camera.setTranslateZ(newZ);
				} else if (me.isMiddleButtonDown()) {
					cameraXform2.t.setX(cameraXform2.t.getX() + mouseDeltaX * modifierFactor * modifier * 1.3); // -
					cameraXform2.t.setY(cameraXform2.t.getY() + mouseDeltaY * modifierFactor * modifier * 1.3); // -
				}
			}
		});
	}

	// public void handleKeyboard(SubScene subScene, final Node root,
	// ArrayList<Car> cars) {
	// subScene.setOnKeyPressed(new EventHandler<KeyEvent>() {
	// @Override
	// public void handle(KeyEvent event) {
	// switch (event.getCode()) {
	// case Z:
	// for (Car car : cars) {
	// car.startCar(new CarRadialMove());
	// }
	// break;
	// default:
	// break;
	// }
	// }
	// });
	// }

	/**
	 * Gets the camera.
	 *
	 * @return the camera
	 */
	public Camera getCamera() {
		return camera;
	}

}
