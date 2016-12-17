package client;


/**
 * API compatible with ClientModel.
 * 
 * @author Moran Tenzer & Ben Nave
 *
 */
public class CannonGameModel {
	
	/**
	 * the client's controller listener.
	 */
	private CannonGameController contollerListener; 
	
	
	/**
	 * @return return the client's controller listener.
	 */
	public CannonGameController getControllerListener() {
		return contollerListener;
	}

	/**
	 * this method sets the client's controller listener.
	 * 
	 * @param contollerListener - the client's controller listener.
	 */
	public void setControllerListener(CannonGameController contollerListener) {
		this.contollerListener = contollerListener;
	}
}
