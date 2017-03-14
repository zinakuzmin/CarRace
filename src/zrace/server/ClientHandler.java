package zrace.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import zrace.protocol.ClientDisconnectMsg;
import zrace.protocol.Message;
import javafx.application.Platform;
public class ClientHandler implements Runnable{
	private Socket connectToClient;
	private int userId;
	private ServerController controller;

	public ClientHandler(Socket socket, ServerController controller) {
		this.connectToClient = socket;
		this.controller = controller;
		
	}
	
	

	@Override
	public void run() {

		try {
			System.out.println("server: configure in out stream");
//			ObjectInputStream streamFromClient = new ObjectInputStream(
//					connectToClient.getInputStream());
//			System.out.println("in stream configured");
//			ObjectOutputStream streamToClient = new ObjectOutputStream(
//					connectToClient.getOutputStream());
//			System.out.println("out stream configured");
			
			Message messageFromClient;
			do{
				System.out.println("Waiting for client message");
				messageFromClient = (Message) new ObjectInputStream(
						connectToClient.getInputStream()).readObject();
				System.out.println("Server: read message " + messageFromClient);
				Platform.runLater(new HandleMessage(messageFromClient, this));
//				new Thread(new HandleMessage(messageFromClient, logger)).start();
				
			}
			while (!(messageFromClient instanceof ClientDisconnectMsg));


		} catch (IOException e) {
			System.err.println(e);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			try {
//				connectToClient.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	
		
	}



	public int getUserId() {
		return userId;
	}



	public void setUserId(int userId) {
		this.userId = userId;
	}



	public ServerController getController() {
		return controller;
	}



	public void setController(ServerController controller) {
		this.controller = controller;
	}
	
	
	

}
