package zrace.client.app.world;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;

public class Crowd {

	private Group humans;

	public Crowd(Group humans) {
		this.humans = humans;
	}

	public static Crowd generateCrowd(Tribune tribunes) {
		Group humans = new Group();
		int humansPerRow = 60; // not more than this - slows down
		
		for( int i=1 ; i < tribunes.getTribuneCount() ; i++) {
			for(int j=0 ; j < humansPerRow ; j++) {
				Group human = makeHuman();
				int humaneScale = 4;
				human.setScaleX(humaneScale);
				human.setScaleY(humaneScale);
				human.setScaleZ(humaneScale);

				human.setTranslateY(tribunes.getHightOfRow(i)-12);
				double radius = tribunes.getTribuneRadius(i) + tribunes.getRadiusIncrement()/humaneScale;
				setRadialOfHuman(human, radius, j, humansPerRow);
				
				setAnimationHuman(human, tribunes.getHightOfRow(i)-12);
	
	    		humans.getChildren().add(human);
	    	}
		}
		return new Crowd(humans);
	}

    private static void setAnimationHuman(Group human, double startFrom) {
    	TranslateTransition move = new TranslateTransition();
    	move.setNode(human);
    	move.setCycleCount(Timeline.INDEFINITE);
    	move.setAutoReverse(true);
    	move.setFromY(startFrom);
    	move.setToY(startFrom+new Random().nextInt(3));
    	move.setDuration(Duration.millis(400 + (int)(Math.random() * 800)));
    	
    	move.play();
	}

	public static void setRadialOfHuman(Group human, double orbitRadius, double movingStep, float numOfFaces) {
		int nextInt = new Random().nextInt(3);
		double degree = movingStep*(360/numOfFaces) + nextInt;//movingStep*(360f/80f) + nextInt; //double degree =  
		double angleAlpha = Math.toRadians(degree);

		// p(x) = x(0) + r * sin(a)
         // p(y) = y(0) - r * cos(a)
     	double moveX = -orbitRadius  * Math.cos(angleAlpha);
		double moveZ = orbitRadius * Math.sin(angleAlpha);
		double degCorrected = degree-90f;
		
		human.setRotate(degCorrected);
		human.setRotationAxis(Rotate.Y_AXIS);
		
		human.setTranslateX(moveX);
		human.setTranslateZ(moveZ);
    }
	
	private static Group makeHuman() {
		Group human = new Group();
		
		Group head = new Group();
    	Box face = new Box(0.7, 0.7, 0.7);
    	face.setTranslateY(1.3);
    	head.getChildren().add(face);
    	Box hair = new Box(0.8, 0.7, 0.7);
    	hair.setTranslateY(1.4);
    	hair.setTranslateZ(0.1);
    	hair.setMaterial(getRandomHairColor());
    	head.getChildren().add(hair);
    	
    	Box body = new Box(0.7, 1.4, 0.7);
    	body.setMaterial(getRandomColor());

    	Group lHand = new Group();
    	Box lHand1 = new Box(0.2, 0.7, 0.2);
    	lHand1.setTranslateX(-0.5);
    	lHand1.setTranslateY(0.35);
    	PhongMaterial handM = getRandomColor();
    	lHand1.setMaterial(handM);
    	Box lHand2 = new Box(0.2, 0.2, 0.2);
    	lHand2.setTranslateX(-0.5);
    	lHand2.setTranslateY(-0.2);
    	lHand.getChildren().add(lHand1);
    	lHand.getChildren().add(lHand2);
    	
    	
    	Group rHand = new Group();
    	Box rHand1 = new Box(0.2, 0.7, 0.2);
    	rHand1.setTranslateX(0.5);
    	rHand1.setTranslateY(0.35);
    	rHand1.setMaterial(handM);
    	Box rHand2 = new Box(0.2, 0.2, 0.2);
    	rHand2.setTranslateX(0.5);
    	rHand2.setTranslateY(-0.2);
    	rHand.getChildren().add(rHand1);
    	rHand.getChildren().add(rHand2);
    	
    	
    	Box lShow = new Box(0.2, 0.2, 0.2);
    	lShow.setTranslateX(-0.2);
    	lShow.setTranslateY(-1);
    	lShow.setMaterial(handM);
    	
    	Box rShow = new Box(0.2, 0.2, 0.2);
    	rShow.setTranslateX(0.2);
    	rShow.setTranslateY(-1);
    	rShow.setMaterial(handM);
    	
    	
    	human.getChildren().add(head);
    	human.getChildren().add(body);
    	human.getChildren().add(lHand);
    	human.getChildren().add(rHand);
    	human.getChildren().add(lShow);
    	human.getChildren().add(rShow);
		return human;
	}
	
	public Group getCrowdGroup() {
		return humans;
	}

	private static PhongMaterial getRandomHairColor() {
		Collections.shuffle(hairColors);
		
		return new PhongMaterial(hairColors.get(0));
	}
	
	private static PhongMaterial getRandomColor() {
		Collections.shuffle(otherColors);
		
		return new PhongMaterial(otherColors.get(0));
	}

	private static ArrayList<Color> hairColors;
	private static ArrayList<Color> otherColors;

	static {
		hairColors = new ArrayList<>();
				
		hairColors.add(Color.BLACK);
		hairColors.add(Color.BROWN);
		hairColors.add(Color.DARKCYAN);
		hairColors.add(Color.DARKGRAY);
		hairColors.add(Color.DARKMAGENTA);
		hairColors.add(Color.DEEPPINK);
		hairColors.add(Color.ORANGE);
	}

	static {
		otherColors = new ArrayList<>();
				
		otherColors.add(Color.AQUA);
		otherColors.add(Color.AQUAMARINE);
		otherColors.add(Color.BLUEVIOLET);
		otherColors.add(Color.CHARTREUSE);
		otherColors.add(Color.CORNFLOWERBLUE);
		otherColors.add(Color.DARKCYAN);
		otherColors.add(Color.DARKSEAGREEN);
		otherColors.add(Color.DARKMAGENTA);
		otherColors.add(Color.DARKSALMON);
		otherColors.add(Color.DARKSLATEBLUE);
		otherColors.add(Color.DEEPPINK);
		otherColors.add(Color.GREENYELLOW);
		otherColors.add(Color.INDIGO);
		otherColors.add(Color.LIGHTCORAL);
		otherColors.add(Color.LIGHTGREEN);
		otherColors.add(Color.LIGHTBLUE);
		otherColors.add(Color.MEDIUMPURPLE);
		otherColors.add(Color.MEDIUMSEAGREEN);
		otherColors.add(Color.MEDIUMAQUAMARINE);
		otherColors.add(Color.MEDIUMORCHID);
		otherColors.add(Color.MEDIUMSLATEBLUE);
		otherColors.add(Color.MEDIUMVIOLETRED);
		otherColors.add(Color.PLUM);
		otherColors.add(Color.VIOLET);
		otherColors.add(Color.SPRINGGREEN);
		otherColors.add(Color.THISTLE);
		otherColors.add(Color.SKYBLUE);
		otherColors.add(Color.PINK);
	}
}
