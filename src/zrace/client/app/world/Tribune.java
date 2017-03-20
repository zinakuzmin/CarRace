package zrace.client.app.world;

/**
 * This class sets the tribune in the race.
 *
 * @author Zina K
 */
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Rotate;

/**
 * The Class Tribune.
 */
public class Tribune  {

    /**
     * The tribunes.
     */
    private Group tribunes;
	
	/**
	 * The number of rows.
	 */
	private int numOfRows;
	
	/**
	 * The radius increment.
	 */
	private double radiusIncrement;
	
	/**
	 * The initial row radius.
	 */
	static int initialRowRadius = 220;
	
	/**
	 * The height.
	 */
	private double hight;
	
	/**
	 * The increnebt hight of column.
	 */
	private int increnebtHightOfColumn;

	/**
	 * Instantiates a new tribune.
	 *
	 * @param all the all
	 * @param numOfRows the num of rows
	 * @param radiusIncrement the radius increment
	 * @param hight the hight
	 * @param increnebtHightOfColumn the increnebt hight of column
	 */
	public Tribune(Group all, int numOfRows, double radiusIncrement, double hight, int increnebtHightOfColumn) {
		this.tribunes = all;
		this.numOfRows = numOfRows;
		this.radiusIncrement = radiusIncrement;
		this.hight = hight;
		this.increnebtHightOfColumn = increnebtHightOfColumn;
	}
	
	/**
	 * Gets the tribunes.
	 *
	 * @return the tribunes
	 */
	public Group getTribunes() {
		return tribunes;
	}
	
	/**
	 * Generate all seats and columns.
	 *
	 * @param hight the hight
	 * @param radiusIncrement the radius increment
	 * @param numOfRows the num of rows
	 * @return the tribune
	 */
	public static Tribune generateAllSeatsAndColumns(double hight, double radiusIncrement, int numOfRows) {
		Group all = new Group();
    	int increnebtHightOfColumn = 5;
    	double initialRowRadius = Tribune.initialRowRadius;
    	
    	for(int i=0 ; i < numOfRows ; i++) {
			hight += increnebtHightOfColumn;
			initialRowRadius += radiusIncrement;
			
			Group createTube = createTube(360, initialRowRadius, hight);
    		createTube.setTranslateY(hight/2);
    		
    		all.getChildren().add(createTube);
    		all.getChildren().add(Road.getLaneFromCenter(hight-2, initialRowRadius+(radiusIncrement/2), radiusIncrement, Color.GHOSTWHITE));//Color.ANTIQUEWHITE		
    	}
    	return new Tribune(all, numOfRows, radiusIncrement, hight, increnebtHightOfColumn);
	}

	/**
	 * Creates the tube.
	 *
	 * @param numOfFaces the num of faces
	 * @param radius the radius
	 * @param hight the hight
	 * @return the group
	 */
	private static Group createTube(int numOfFaces, double radius, double hight) {
    	Group group = new Group();
    	
		double width = 2*Math.PI*radius/numOfFaces;
		for(int i=0 ; i < numOfFaces ; i++) {
			Rectangle rect = new Rectangle(width, hight);
    		rect.setFill(Color.CADETBLUE);//Color.CORAL
    		rect.setTranslateX(-width/2);
    		rect.setTranslateY(-hight/2);
    		
    		setRadialOfRect(rect, radius, i, numOfFaces);
    		group.getChildren().add(rect);
    	}
    	
    	return group;
	}
    
    /**
     * Sets the radial of rect.
     *
     * @param rec the rec
     * @param orbitRadius the orbit radius
     * @param movingStep the moving step
     * @param numOfFaces the num of faces
     */
    public static void setRadialOfRect(Rectangle rec, double orbitRadius, double movingStep, int numOfFaces) {
		double degree = movingStep*(360/numOfFaces);
		double angleAlpha = Math.toRadians(degree);

		// p(x) = x(0) + r * sin(a)
         // p(y) = y(0) - r * cos(a)
     	double moveX = -orbitRadius  * Math.cos(angleAlpha);
		double moveZ = orbitRadius * Math.sin(angleAlpha);
		double degCorrected = degree+90f;
		
		rec.setRotate(degCorrected);
		rec.setRotationAxis(Rotate.Y_AXIS);
		
		rec.setTranslateX(moveX);
		rec.setTranslateZ(moveZ);
    }

	/**
	 * Gets the tribune count.
	 *
	 * @return the tribune count
	 */
	public int getTribuneCount() {
		return numOfRows;
	}

	/**
	 * Gets the tribune radius.
	 *
	 * @param numOfRow the num of row
	 * @return the tribune radius
	 */
	public double getTribuneRadius(int numOfRow) {
		return (initialRowRadius + (numOfRow*radiusIncrement));
	}

	/**
	 * Gets the hight of row.
	 *
	 * @param numOfRow the num of row
	 * @return the hight of row
	 */
	public double getHightOfRow(int numOfRow) {
		return (hight/2 + (numOfRow*increnebtHightOfColumn));
	}

	/**
	 * Gets the radius increment.
	 *
	 * @return the radius increment
	 */
	public double getRadiusIncrement() {
		return radiusIncrement;
	}

}