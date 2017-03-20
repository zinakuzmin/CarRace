package zrace.client.app.world;

import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;
import javafx.scene.shape.ArcType;
import javafx.scene.shape.Shape;
import javafx.scene.shape.StrokeType;
import javafx.scene.transform.Rotate;

/**
 * This class sets the road for the race.
 *
 * @author Zina K
 */
public class Road {
	
	/**
	 * Create the lane from center.
	 *
	 * @param radius the radius
	 * @param width the width
	 * @param color the color
	 * @return the lane from center
	 */
	public static Shape getLaneFromCenter(double radius, double width, Color color) {
		return getLaneFromCenter(0, radius, width, color);
	}

	/**
	 * Create the lane from center.
	 *
	 * @param yPos the y pos
	 * @param radius the radius
	 * @param width the width
	 * @param color the color
	 * @return the lane from center
	 */
	public static Shape getLaneFromCenter(double yPos, double radius, double width, Color color) {
		Arc arc = new Arc(0, yPos, radius, radius, 0, 360);
	    arc.setType(ArcType.OPEN);
	    arc.setStrokeWidth(width);
		arc.setStroke(color);
	    arc.setStrokeType(StrokeType.CENTERED);
	    arc.setFill(null);
	    arc.setRotate(90);
    	arc.setRotationAxis(Rotate.X_AXIS);
    	
    	return arc;
	}
}
