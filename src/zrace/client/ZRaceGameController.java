package zrace.client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Date;

import zrace.client.view.ClientView;
import zrace.protocol.ClientConnectMsg;
import zrace.server.ClientHandler;
import javafx.application.Platform;
import javafx.stage.Stage;

public class ZRaceGameController {
	private Socket socket;
	private ObjectInputStream in;
	private ObjectOutputStream out;

	public ZRaceGameController(Stage primaryStage) {
		
		
		new Thread(() -> {
			try {
				
				System.out.println("start game");
				socket = new Socket("localhost", 8000);
				System.out.println("Socket inited");
				out = new ObjectOutputStream(socket.getOutputStream());
				System.out.println("init out");
//				out.writeObject(new ClientConnectMsg(123245, "blabla"));
				in = new ObjectInputStream(socket.getInputStream());
				System.out.println("init in");
			
				
			} catch (IOException ex) {
				System.err.println(ex);
			}
		}).start();
		
		
		try {
			System.out.println("start GUI");
			ClientView clientView = new ClientView(this);
			clientView.start(new Stage());
			//thread
			

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	
	public synchronized void sendLoginOrRegisterMessage(int userId, String userFullName){
		ClientConnectMsg msg = new ClientConnectMsg(userId, userFullName);
		
		System.out.println("client: send login msg");
		System.out.println("output stream is " + out);
		try {
			out.writeObject(msg);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
