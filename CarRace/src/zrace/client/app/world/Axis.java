package zrace.client.app.world;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import zrace.client.app.Xform;

public class Axis {
	
	 public static void buildAxes(Xform world) {
	    	final Group axisGroup = new Group();
	        final PhongMaterial redMaterial = new PhongMaterial();
	        redMaterial.setDiffuseColor(Color.DARKRED);
	        redMaterial.setSpecularColor(Color.RED);

	        final PhongMaterial greenMaterial = new PhongMaterial();
	        greenMaterial.setDiffuseColor(Color.DARKGREEN);
	        greenMaterial.setSpecularColor(Color.GREEN);

	        final PhongMaterial blueMaterial = new PhongMaterial();
	        blueMaterial.setDiffuseColor(Color.DARKBLUE);
	        blueMaterial.setSpecularColor(Color.BLUE);

	        final Box xAxis = new Box(240.0, 1, 1);
	        final Box yAxis = new Box(1, 240.0, 1);
	        final Box zAxis = new Box(1, 1, 240.0);

	        xAxis.setMaterial(redMaterial);
	        yAxis.setMaterial(greenMaterial);
	        zAxis.setMaterial(blueMaterial);

	        axisGroup.getChildren().addAll(xAxis, yAxis, zAxis);
	        world.getChildren().addAll(axisGroup);
	    }

}
