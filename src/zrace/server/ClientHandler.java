package zrace.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import dbModels.User;
import zrace.protocol.ClientDisconnectMsg;
import zrace.protocol.Message;
import javafx.application.Platform;

public class ClientHandler implements Runnable {
	private Socket connectToClient;
	private int userId;
	private String userFullName;
	private ServerController controller;
	private ObjectInputStream streamFromClient;
	private ObjectOutputStream streamToClient;

	public ClientHandler(Socket socket, ServerController controller) {
		this.connectToClient = socket;
		this.controller = controller;
		try {
			setStreamToClient(new ObjectOutputStream(
					connectToClient.getOutputStream()));
			getStreamToClient().flush();
			System.out.println("server out stream configured");
			setStreamFromClient(new ObjectInputStream(
					connectToClient.getInputStream()));
			System.out.println("server in stream configured");
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void run() {

		try {

			Message messageFromClient;
			do {
				System.out.println("Waiting for client message");
				messageFromClient = (Message) getStreamFromClient()
						.readObject();
				System.out.println("Server: read message " + messageFromClient);
				// Platform.runLater(new HandleMessage(messageFromClient,
				// this));
				new Thread(new HandleMessage(messageFromClient, this)).start();

			} while (!(messageFromClient instanceof ClientDisconnectMsg));

		} catch (IOException e) {
			System.err.println(e);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			try {
				// connectToClient.close();
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

	public Socket getConnectToClient() {
		return connectToClient;
	}

	public ObjectInputStream getStreamFromClient() {
		return streamFromClient;
	}

	public void clientDisconnect(User user) {
		try {
			if (connectToClient.isConnected())
				connectToClient.close();
			controller.disconnectClient(this);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void setStreamFromClient(ObjectInputStream streamFromClient) {
		this.streamFromClient = streamFromClient;
	}

	public ObjectOutputStream getStreamToClient() {
		return streamToClient;
	}

	public void setStreamToClient(ObjectOutputStream streamToClient) {
		this.streamToClient = streamToClient;
	}

	public String getUserFullName() {
		return userFullName;
	}

	public void setUserFullName(String userFullName) {
		this.userFullName = userFullName;
	}

}
