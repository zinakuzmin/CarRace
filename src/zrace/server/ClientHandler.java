package zrace.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import zrace.dbModels.User;
import zrace.protocol.ClientDisconnectMsg;
import zrace.protocol.Message;

/**
 * This Class provides an API compatible with ClientHandler.
 * @author Zina K
 *
 */
/**
 * @author Zina K
 *
 */
/**
 * @author Zina K
 *
 */
/**
 * @author Zina K
 *
 */
/**
 * @author Zina K
 *
 */
public class ClientHandler implements Runnable {
	
	/**
	 * Network socket used for connection to client 
	 */
	private Socket connectToClient;
	
	/**
	 * User ID of connected client
	 */
	private int userId;
	
	
	/**
	 * User name of connected client
	 */
	private String userFullName;
	
	
	/**
	 * Server controller for controlling server activity
	 */
	private ServerController controller;
	
	
	/**
	 * Stream from client active till client disconnect
	 */
	private ObjectInputStream streamFromClient;
	
	/**
	 * Stream to client active till client disconnect
	 */
	private ObjectOutputStream streamToClient;

	/**
	 * Initialize client
	 * @param socket
	 * @param controller
	 */
	public ClientHandler(Socket socket, ServerController controller) {
		this.connectToClient = socket;
		this.controller = controller;
		try {
			setStreamToClient(new ObjectOutputStream(connectToClient.getOutputStream()));
			getStreamToClient().flush();
			setStreamFromClient(new ObjectInputStream(connectToClient.getInputStream()));

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 * Listener for client messages
	 */
	@Override
	public void run() {

		try {

			Message messageFromClient;
			do {
				//System.out.println("Waiting for client message");
				messageFromClient = (Message) getStreamFromClient().readObject();
				//System.out.println("Server: read message " + messageFromClient);
				new Thread(new HandleMessage(messageFromClient, this)).start();

			} while (!(messageFromClient instanceof ClientDisconnectMsg));

		} catch (IOException e) {
			System.err.println(e);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			try {
//				 connectToClient.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	/**
	 * Get id of client user
	 * @return
	 */
	public int getUserId() {
		return userId;
	}

	/**
	 * Set userID for client
	 * @param userId
	 */
	public void setUserId(int userId) {
		this.userId = userId;
	}

	/**
	 * Get server controller
	 * @return
	 */
	public ServerController getController() {
		return controller;
	}

	/**
	 * Set Server Controller
	 * @param controller
	 */
	public void setController(ServerController controller) {
		this.controller = controller;
	}

	/**
	 * Get client's socket 
	 * @return
	 */
	public Socket getConnectToClient() {
		return connectToClient;
	}

	/**
	 * Get output stream of relevant client
	 * @return
	 */
	public ObjectInputStream getStreamFromClient() {
		return streamFromClient;
	}

	/**
	 * Disconnect client socket 
	 * @param user
	 */
	public void clientDisconnect(User user) {
		try {
			if (connectToClient.isConnected())
				connectToClient.close();
			controller.disconnectClient(this);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Set input stream from client
	 * @param streamFromClient
	 */
	public void setStreamFromClient(ObjectInputStream streamFromClient) {
		this.streamFromClient = streamFromClient;
	}

	/**
	 * Return client output stream 
	 * @return
	 */
	public ObjectOutputStream getStreamToClient() {
		return streamToClient;
	}

	/**
	 * Set output stream to client
	 * @param streamToClient
	 */
	public void setStreamToClient(ObjectOutputStream streamToClient) {
		this.streamToClient = streamToClient;
	}

	/**
	 * Return user name 
	 * @return
	 */
	public String getUserFullName() {
		return userFullName;
	}

	/**
	 * Set user name
	 * @param userFullName
	 */
	public void setUserFullName(String userFullName) {
		this.userFullName = userFullName;
	}

}
