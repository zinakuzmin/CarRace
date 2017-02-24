package zrace.client.app.world;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Rotate;

public class Tribune  {

    private Group tribunes;
	private int numOfRows;
	private double radiusIncrement;
	static int initialRowRadius = 220;
	private double hight;
	private int increnebtHightOfColumn;

	public Tribune(Group all, int numOfRows, double radiusIncrement, double hight, int increnebtHightOfColumn) {
		this.tribunes = all;
		this.numOfRows = numOfRows;
		this.radiusIncrement = radiusIncrement;
		this.hight = hight;
		this.increnebtHightOfColumn = increnebtHightOfColumn;
	}
	
	public Group getTribunes() {
		return tribunes;
	}
	
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

	public int getTribuneCount() {
		return numOfRows;
	}

	public double getTribuneRadius(int numOfRow) {
		return (initialRowRadius + (numOfRow*radiusIncrement));
	}

	public double getHightOfRow(int numOfRow) {
		return (hight/2 + (numOfRow*increnebtHightOfColumn));
	}

	public double getRadiusIncrement() {
		return radiusIncrement;
	}

}