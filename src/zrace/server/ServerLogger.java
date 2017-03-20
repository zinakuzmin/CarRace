package zrace.server;

import com.sun.jmx.snmp.Timestamp;
import javafx.scene.control.TextArea;
import zrace.protocol.Message;

/**
 * This Class provides an API compatible with ServerLogger to control server activity log
 * @author Zina K
 *
 */
public class ServerLogger {
	private TextArea logViewer;
	
	/**
	 * Initialize logs area
	 * @param logViewer
	 */
	public ServerLogger(TextArea logViewer){
		this.logViewer = logViewer;
	}
	
	
	/**
	 * Add message to log area
	 * @param msg
	 */
	public void logMessage(Message msg){
		logViewer.appendText("[" + new Timestamp().getDate() + "] \n" + msg.toString() + "\n");
	}
	
	/**
	 * Add string to log area
	 * @param msg
	 */
	public void logStringMessage(String msg){
		logViewer.appendText("[" + new Timestamp().getDate()+ "] \n" + msg + "\n");
	}

}
