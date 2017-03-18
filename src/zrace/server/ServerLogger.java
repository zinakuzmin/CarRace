package zrace.server;

import javafx.scene.control.TextArea;
import zrace.protocol.Message;

public class ServerLogger {
	private TextArea logViewer;
	
	public ServerLogger(TextArea logViewer){
		this.logViewer = logViewer;
	}
	
	
	public void logMessage(Message msg){
		logViewer.appendText(msg.toString() + "\n");
	}
	
	public void logStringMessage(String msg){
		logViewer.appendText(msg + "\n");
	}

}
