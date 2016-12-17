package server;

import db.DataBaseHandler;

/**
 * This Class provides an API compatible with ServerModel.
 * 
 * @author Moran Tenzer & Ben Nave
 *
 */
public class ServerModel {

	
	/**
	 * {@code controllerListener} - a controller listener.
	 */
	private ServerController controllerListener;
	/**
	 * {@code dataBaseHandler} - used to manage database information.
	 */
	private DataBaseHandler dataBaseHandler;
	
	/**
	 * This constructor create a Instance of ServerModel.
	 * 
	 */
	public ServerModel() {
		dataBaseHandler = new DataBaseHandler();
	}
	
	
	/**
	 * @return return the DataBaseHandler.
	 */
	public DataBaseHandler getDataBaseHandler() {
		return this.dataBaseHandler;
	}
	
	
	/**
	 * set a server controller listener to ServerModel.
	 * 
	 * @param serverController - a server controller listener.
	 */
	public void setControllerListener(ServerController serverController) {
		this.controllerListener = serverController;
	}
	
	/**
	 * @return the server controller listener that is binded to the current ServerModel.
	 */
	public ServerController getControllerListener() {
		return this.controllerListener;
	}
}
