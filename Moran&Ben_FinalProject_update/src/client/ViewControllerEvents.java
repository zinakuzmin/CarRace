package client;

import java.util.ArrayList;

import DBModels.Game;
import DBModels.GameEvent;
import DBModels.Player;

/**
 * This Interface the client's controller basic methods.
 * 
 * @author Moran Tenzer & Ben Nave
 *
 */
public interface ViewControllerEvents {

	/**
	 * called when the view is closed.
	 */
	public void viewClosed();
	
	/**
	 * this method connect the player to the server
	 * 
	 * @param name - player's name.
	 * @param iD - player's id.
	 */
	public void connect(String name , String iD);
	
	/**
	 * this method indicates the server that the player just started a game.
	 * 
	 * @param game - the game's details.
	 */
	public void startGame(Game game);
	
	/**
	 * this method sends all the game events to the server.
	 * 
	 * @param gameEvents -  list of game events.
	 */
	public void sendGameEventsToServer(ArrayList<GameEvent> gameEvents, double score);

	/**
	 * @return return the player's details.
	 */
	public Player getPlayer();
}
