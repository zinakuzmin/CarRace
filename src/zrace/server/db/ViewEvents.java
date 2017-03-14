package zrace.server.db;
/**
 * This Interface is used by view's in order to notify to their controller that they are closed. 
 * 
 *
 */
public interface ViewEvents {
	
	/**
	 * called when a view is closed.
	 */
	public void viewClosed();

}
