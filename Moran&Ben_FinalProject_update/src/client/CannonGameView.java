package client;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

import DBModels.Game;
import DBModels.GameEvent;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Cylinder;
import javafx.scene.shape.Sphere;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * API compatible with ClientView.
 * 
 * @author Moran Tenzer & Ben Nave
 *
 */
public class CannonGameView extends Stage {
	/**
	 * practice mode.
	 */
	public static final int PRACTICE_MODE = 0;
	/**
	 * game mode.
	 */
	public static final int GAME_MODE = 1;
	/**
	 * no game level yet.
	 */
	public static final double NO_GAME_LEVEL = 0;
	/**
	 * game level -> beginner 
	 */
	public static final double BEGINNER = 1;
	/**
	 * game level -> advanced
	 */
	public static final double ADVANCED = 1.5;
	/**
	 * game level -> expert
	 */
	public static final double EXPERT = 2;
	/**
	 * number of ballon images
	 */
	public static final int NUM_OF_BALLON_BACKGROUNDS = 5;
	/**
	 * the ball's radius.
	 */
	static int BALLOON_RADIUS = 25;
	
	/**
	 * the width of the client UI pane.
	 */
	final static int WIDTH = 300;
	/**
	 * the height of the client UI pane.
	 */
	final static int HEIGHT = 200;
	/**
	 * delay.
	 */
	final static float DELAY = (float) 0.5;
	
	/**
	 * the game's mode.(practice or official game).	 1->Beginner , 2->Advanced , 3->Expert
	 */
	private int gameMode; 
	/**
	 * counts the  hits.
	 */
	private int hitsAmount = 0;
	/**
	 * the game's pane.
	 */
	private Pane pane;
	/**
	 * the game's level.
	 */
	private double gameLevel; // if 0 -> Practice , 1->Beginner , 2->Advanced , 3->Expert
	/**
	 * the client's controller.
	 */
	private ViewControllerEvents controllerListener;
	/**
	 * the list of the events in the game(miss,hit events).
	 */
	private ArrayList<GameEvent> gameEvents = new ArrayList<GameEvent>();
	/**
	 * the ball's id.
	 */
	private static int ballID;
	/**
	 * the game's details.
	 */
	private Game game;
	/**
	 * timer.
	 */
	private Timeline timer;
	/**
	 * name of sound file
	 */
	private final String POP_NAME = "ballonHit.mp3";
	/**
	 * the game length in seconds
	 */
	private int GAME_TIME_LENGTH = 20;
	/**
	 * Builds the clients view main menu and initialized the client's game pane.
	 * 
	 * @param gameMode - the game pane.
	 * @param gameLevel - game's level.
	 */
	@SuppressWarnings("static-access")
	public CannonGameView(int gameMode, double gameLevel ,CannonGameController controller) {
		setControllerListener(controller);
		this.gameMode = gameMode;
		this.gameLevel = gameLevel;
		this.ballID = 1;
		setStageTitle();  // Set the stage title according to the mode
		
		pane = new Pane();
		Scene scene = new Scene(pane, 900, 700, true);
		final PerspectiveCamera camera = new PerspectiveCamera(false);
		scene.setCamera(camera);
		
		
		initView();
			
		this.setScene(scene);
		this.show();
		this.setAlwaysOnTop(true);
		this.setResizable(false);
		this.setOnCloseRequest( e-> {
			if(gameMode == GAME_MODE){
				gameEvents = null;
				controllerListener.sendGameEventsToServer(gameEvents , hitsAmount);
				timer.stop();
			}
			controllerListener.viewClosed();
		});
	}
	
	/**
	 * this method initialize the game view pane according to the game's level.
	 */
	private void initView() {
		
		setBackGroundToPane();
		Text scoreText = new Text();
		scoreText.setFont(Font.font("Verdana", FontWeight.BOLD, 30));
		scoreText.setFill(Color.BLACK);
		scoreText.setX(0);
		scoreText.yProperty().bind(pane.heightProperty());
		
		if (gameLevel == NO_GAME_LEVEL)
			BALLOON_RADIUS = 55;
		
		if (gameLevel == BEGINNER)
			BALLOON_RADIUS = 55;
		
		if (gameLevel == ADVANCED)
			BALLOON_RADIUS = 45;
		
		if (gameLevel == EXPERT)
			BALLOON_RADIUS = 35;
		
		if (gameLevel != NO_GAME_LEVEL)
			
		
		pane.getChildren().add(0, new Ball(BALLOON_RADIUS, pane));
		pane.getChildren().add(1, scoreText);
		
		Gun gun = new Gun(pane);
		game();
		pane.getChildren().add(2,gun);
		
		if(gameMode == GAME_MODE) {
			Calendar calendar = new GregorianCalendar(TimeZone.getTimeZone("GMT"));
			int playerID = (controllerListener.getPlayer()).getplayerID();
			if (gameLevel == BEGINNER) {
				game = new Game(playerID, 0, calendar.getTime(), 0, "Beginner") ;
			}
			else if (gameLevel == ADVANCED) {
				game = new Game(playerID, 0, calendar.getTime(), 0, "Advanced") ;
			}
			else if (gameLevel == EXPERT) {
				game = new Game(playerID, 0, calendar.getTime(), 0, "Expert") ;
			}		
			controllerListener.startGame(game);

			int[] a = parseTime();
			Text timerText = new Text("Time Remaining: "+a[0]+":"+a[1]); // initial time
			timerText.setFont(Font.font("calibri", FontWeight.BOLD, 20));
			timerText.setFill(Color.BLACK);
			timer = new Timeline(new KeyFrame(Duration.millis(1000), e ->{
				int[] a1 = timeCounter(a);
				if (a[1] == 0 && a[0] ==0) {
					this.close();
					game.calcGameScore(gameEvents);			
					if (gameLevel == BEGINNER)
						controllerListener.sendGameEventsToServer(gameEvents , hitsAmount*100);
					if (gameLevel == ADVANCED)
						controllerListener.sendGameEventsToServer(gameEvents , hitsAmount*150);
					if (gameLevel == EXPERT)
						controllerListener.sendGameEventsToServer(gameEvents , hitsAmount*200);	
					controllerListener.viewClosed();
				}
				timerText.setText(String.format("Time Remaining: %02d:%02d", a1[0], a1[1]));
			}));
			
			timer.setCycleCount(Timeline.INDEFINITE);
		    timer.play(); // Start animation
			pane.getChildren().add(2, timerText);
			timerText.xProperty().bind(pane.widthProperty().add(-230));
			timerText.yProperty().bind(pane.heightProperty());
		}
		
		setAndRequestFocus();
		
		pane.setOnKeyPressed(e -> {
			if (e.getCode() == KeyCode.LEFT) {
				gun.rotateGunLeft();
				setAndRequestFocus();
			} else if (e.getCode() == KeyCode.RIGHT) {
				gun.rotateGunRight();
				setAndRequestFocus();
			} else if (e.getCode() == KeyCode.UP) {
				pane.getChildren().add(new Ball(ballID, Gun.GUN_HEIGHT,  gun.getAngle() ,Ball.SHOTTING_BALL_RADIUS, pane));
				if (gameLevel != NO_GAME_LEVEL) {
					Calendar calendar = new GregorianCalendar(TimeZone.getTimeZone("GMT"));
					Date eventTime = calendar.getTime();
					gameEvents.add(new GameEvent(ballID++, game.getPlayerID(), game.getGameID(), "Miss", eventTime));
				}
				else
					ballID++;
			}
		});
		
		EventHandler<ActionEvent> eventHandler = e -> {
			game();
			setAndRequestFocus();
		};
		
		
		Timeline animation = new Timeline(new KeyFrame(Duration.millis(DELAY), eventHandler));
		animation.setCycleCount(Timeline.INDEFINITE);
		animation.play();
		
		if (gameLevel != NO_GAME_LEVEL) {
			double time = 6000; // default -> Beginner level;
			EventHandler<ActionEvent> random = e -> {
				randomBallLoactions();
			};
			if (gameLevel == ADVANCED)
				time = 4000;
			else if (gameLevel == EXPERT)
				time = 3000;
			Timeline randomBall = new Timeline(new KeyFrame(Duration.millis(time), random));
			randomBall.setCycleCount(Timeline.INDEFINITE);
			randomBall.play();
		}
	}
	/**
	 * array for time -> {minutes , seconds}
	 */
	public int[] parseTime()
	{
		int[] a = new int[2];
		a[0] = GAME_TIME_LENGTH/60;
		//System.out.println("min:"+a[0]);
		a[1] = GAME_TIME_LENGTH%60;
		//System.out.println("sec:"+a[1]);
		
		return a;
	}
	/**
//	 * This method decrease 1 second from the current time of the timer.
//	 */
	public  int[] timeCounter(int[] timer) {
		timer[1] -= 1;
		if (timer[1] == -1) {
			timer[1] = 59;
			timer[0] -= 1;
			if (timer[0] == -1)
				timer[0] = 59;
		}
		return timer;
	}
	/**
	 * this method handles the game itself, by moving the balls randomly, and counting the amount of hits.
	 */
	private void game() {
		((Text) (pane.getChildren().get(1))).setText("Hits Amount: " + hitsAmount);
		
		for (int i = 0; i < pane.getChildren().size(); i++) {
			if (pane.getChildren().get(i) instanceof Ball) {
					Ball temp  = ((Ball) (pane.getChildren().get(i)));
				if (!temp.isOutOfPane()) {
					temp.addLength(1);
					temp.refresh();
				} else {
					pane.getChildren().remove(i);
				}
				if (!temp.getIsBallon() && ( temp ).overlaps( (Ball)pane.getChildren().get(0) ) ) {
					if (gameLevel != NO_GAME_LEVEL)
						gameEvents.get(temp.getBallID() - 1).setEventType("Hit");
					hit(i);
				}
			}
		}
	}
	private void setAndRequestFocus() {
		pane.setFocusTraversable(true);
		pane.requestFocus();
	}
	
	/**
	 * this method removes the ball that got hit, and update the amount of hits. 
	 * @param i - index of the ball's location.
	 */
	public void hit(int i) {
		pane.getChildren().remove(i);
		randomBallLoactions();

		Media media = new Media(new File("src/imagesAndSound/" + POP_NAME).toURI().toString());
		MediaPlayer mediaPlayer = new MediaPlayer(media);
		mediaPlayer.play();

		this.hitsAmount++;
	}
	/**
	 * this method sets the title of the game pane according to the type of game.
	 */
	private void setStageTitle() {
		if(this.gameMode == PRACTICE_MODE)
			this.setTitle("Cannon Game -> Practice Mode");
		else if (this.gameLevel == BEGINNER)
			this.setTitle("Cannon Game -> Beginner Mode");
		else if (this.gameLevel == ADVANCED)
			this.setTitle("Cannon Game -> Advanced Mode");
		else if(this.gameMode == EXPERT)
			this.setTitle("Cannon Game -> Expert Mode");
	}
	/**
	 * this method move a ball randomly.
	 */
	private void randomBallLoactions() {
		pane.getChildren().remove(0);
		pane.getChildren().add(0, new Ball(BALLOON_RADIUS, pane));
	}
	/**
	 * set a picture on background.
	 */
	public void setBackGroundToPane() {
		pane.setStyle("-fx-background-image: url(\"/imagesAndSound/image1.jpg\");-fx-background-size: 1200;"
				+ "-fx-background-repeat: no-repeat;");
	}
 	/**
 	 * @return return the client's controller listener.
 	 */
 	public ViewControllerEvents getControllerListener() {
		return controllerListener;
	}
	/**
	 * this method sets the client's controller listener.
	 * 
	 * @param controllerListener - the client's controller listener.
	 */
	public void setControllerListener(ViewControllerEvents controllerListener) {
		this.controllerListener = controllerListener;
	}
	/**
	 * This class provides an API compatible with Ball.
	 */
	public class Ball extends Sphere {
		/**
		 * a final value of balloon radius  - used when the mode of the game is Practice Mode.
		 */
		public static final int PRACTICE_BALLON_RADIUS = 50;
		/**
		 * a final value of balloon radius  - used when the mode of the game is beginner Mode.
		 */
		public static final int BEGINNER_BALLON_RADIUS = 25;
		/**
		 * a final value of balloon radius  - used when the mode of the game is advanced Mode.
		 */
		public static final int MEDIUM_BALLON_RADIUS = 15;
		/**
		 * a final value of balloon radius  - used when the mode of the game is expert Mode.
		 */
		public static final int HARD_BALLON_RADIUS = 10;
		/**
		 * the radius value of a small ball.
		 */
		public static final int SHOTTING_BALL_RADIUS = 6;
		/**
		 * the number of ballon backGround
		 */
		private final int NUM_OF_BALLON_BACKGROUUNDS = 5;
		/**
		 * path of image files
		 */
		private final String FILES_FOLDER = "imagesAndSound/";
		/**
		 * {@code ballID} - the id of the ball.
		 */
		private int ballID;
		/**
		 * {@code length} - the length of the gun.
		 */
		private int length;
		/**
		 * {@code startedAngle} - the startedAngle of the gun who shot the ball.
		 */
		private int startedAngle;
		/**
		 * {@code pane}  - the small ball's container pane.
		 */
		private Pane pane;
		/**
		 * {@code isBallon}  - if the ball is present a balloon this value set to 'true'.
		 */
		private boolean isBallon;
		/**
		 * This Constructor create a new instance of a Ball -  a Small Gun Ball.
		 * 
		 * @param ballID - the ball's id.
		 * @param length - the gun's length.
		 * @param startedAngle - the gun's angle when the ball was created.
		 * @param ballRadius - the ball's radius.
		 * @param pane - the small ball's container pane.
		 */
		public Ball(int ballID, int length, int startedAngle,int ballRadius,Pane pane) {
			super(ballRadius);
			this.ballID = ballID;		
			this.isBallon = false;
			this.pane = pane;
			this.length = length;
			this.startedAngle = startedAngle;
			
			PhongMaterial m = new PhongMaterial(new Color(1, 1, 1, 1));
			setMaterial(m);
		}
		/**
		 * This Constructor create a new instance of Ball -  a Balloon.
		 * @param ballRadius - the balloon's radius.
		 * @param pane - the balloon's container pane.
		 */
		public Ball(int ballRaius,Pane pane) {
			super(ballRaius);
			this.isBallon = true;
			this.pane = pane;
			
			randomBallonImage();
			
			setTranslateX(getRand(ballRaius, (int) (pane.getWidth() - ballRaius)));
			setTranslateY(getRand(ballRaius, (int) (pane.getHeight() * 0.1)));
			
			translateXProperty().bind(pane.widthProperty().multiply(getTranslateX() / pane.getWidth()));
			translateYProperty().bind(pane.heightProperty().multiply(getTranslateY() / pane.getHeight()));	
		}
		/**
		 * @return random number in range.
		 */
		private int getRand(int from, int to) {
			return (int) (Math.random() * ((to - from) + 1) + from);
		}
		private void randomBallonImage()
		{
			PhongMaterial material = new PhongMaterial();
			int i = getRand(0 , NUM_OF_BALLON_BACKGROUUNDS-1);
			material.setDiffuseMap(new Image(FILES_FOLDER + "ball"+i+".jpg"));
			setMaterial(material);
		}
		/**
		 * @param value - added to the ball length for small gum balls.
		 */
		public void addLength(int value) {
			length += value;
		}
		/**
		 * sets the ball new position in the pane.
		 */
		public void refresh() {
			if(!isBallon) {
				setTranslateX((int) (length * 0.1 * Math.cos(Math.toRadians(startedAngle)) + (pane.getWidth() / 2)));
				setTranslateY((int) (pane.getHeight() - length * 0.1 * Math.sin(Math.toRadians(startedAngle))));
				setTranslateZ(0);
			}
		}
		/**
		 * @return return true if the ball position is out of pane border. else return false.
		 */
		public boolean isOutOfPane() {
			return (getTranslateX() > pane.getWidth() || getTranslateX() < 0 );
		}
		/**
		 * @param other - Ball
		 * @return return true if this ball and other ball collide.
		 */
		public boolean overlaps(Ball other){
			return Math.sqrt(Math.pow((getTranslateX() - other.getTranslateX()), 2)
					+ Math.pow((getTranslateY() - other.getTranslateY()), 2)) <= (getRadius() + other.getRadius());
		}
		/**
		 * @return return true if the object is a balloon.
		 */
		public boolean getIsBallon() {
			return isBallon;
		}
		/**
		 * @return return the ball's id.
		 */
		public int getBallID() {
			return ballID;
		}
		/**
		 * This method sets the ball's id.דדדד
		 * @param ballID - the ball's id.
		 */
		public void setBallID(int ballID) {
			this.ballID = ballID;
		}
	}
	
	public class Gun extends Cylinder {
		/**
		 * When the gun angle is changed. it always changes by increasing or decreasing that value.
		 */
		public final static int CHANGE_ANGLE = 5;
		/**
		 * The Gun Height.
		 */
		public final static int GUN_HEIGHT = 90;
		/**
		 * The Gun Width.
		 */
		public final static int GUN_WIDTH = 12;
		/**
		 * {@code angle} - Gun Started Angle. 
		 */
		private int angle = 0;
		/**
		 *{@code pane} - The Pane that contains the gun.
		 */
		private Pane pane;
		/**
		 * This Constructor create a new instance of a Gun and initialize it's value.
		 * @param pane - the gun's pane.
		 */
		public Gun(Pane pane) {
			super(GUN_WIDTH, GUN_HEIGHT);
			this.pane = pane;

			setMaterial(new PhongMaterial(Color.DARKGRAY));
			setRotate(angle);

			setTranslateX(this.pane.getWidth());
			setTranslateY(this.pane.getHeight());
			translateXProperty().bind(this.pane.widthProperty().divide(2));
			translateYProperty().bind(this.pane.heightProperty().add(-GUN_HEIGHT / 2));
		}
		/**
		 * This method rotates the gun to right.
		 */
		public void rotateGunRight() {
			if (angle < 90) {
				angle += CHANGE_ANGLE;
				getTransforms().add(
						new Rotate(CHANGE_ANGLE, getScaleX(), getScaleY() + GUN_HEIGHT / 2, getScaleZ(), Rotate.Z_AXIS));
			}
		}
		/**
		 * This method rotates the gun to left.
		 */
		public void rotateGunLeft() {
			if (angle > -90) {
				angle -= CHANGE_ANGLE;
				getTransforms().add(
						new Rotate(-CHANGE_ANGLE, getScaleX(), getScaleY() + GUN_HEIGHT / 2, getScaleZ(), Rotate.Z_AXIS));
			}
		}
		/**
		 * @return return the gun's angle.
		 */
		public int getAngle() {
			if (angle > 0)
				return 90 - angle;
			else
				return (angle * -1) + 90;
		}
	}
}
