package zrace.server;


import java.io.ObjectOutputStream;



import java.util.ArrayList;

import dbModels.*;
import zrace.protocol.ClientBetMsg;
import zrace.protocol.ClientConnectMsg;
import zrace.protocol.ClientDisconnectMsg;
import zrace.protocol.Message;import zrace.protocol.UpdateRacesMsg;
import zrace.protocol.UserDetailsMsg;



public class HandleMessage implements Runnable{
	private Message messageFromClient;
	private ClientHandler client;
	
	public HandleMessage(Message messageFromClient, ClientHandler clientHandler) {
		this.messageFromClient = messageFromClient;
		this.client = clientHandler;
	}
	

	@Override
	public void run() {
		if (messageFromClient instanceof ClientConnectMsg){
			System.out.println("Server: got client connect message");
			client.setUserId(((ClientConnectMsg) messageFromClient).getUserId());
			User user = client.getController().userLoginOrRegister(((ClientConnectMsg) messageFromClient).getUserId(), ((ClientConnectMsg) messageFromClient).getUserFullName());
			client.getController().getLogger().logMessage(messageFromClient);
			
			new Thread(() -> {
				try {
//					ObjectOutputStream out = new ObjectOutputStream(client.getConnectToClient().getOutputStream());
//					out.flush();
					client.getStreamToClient().writeObject(new UserDetailsMsg(user));
					System.out.println("server sent user details " + user);
					ArrayList<Race> activeRaces = client.getController().getActiveRaces();
					client.getStreamToClient().flush();
					client.getStreamToClient().writeObject(new UpdateRacesMsg(activeRaces));
					System.out.println("server sent to client races " + activeRaces);
					client.getStreamToClient().flush();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}).start();
		}
		
		else if (messageFromClient instanceof ClientBetMsg){
			
			ArrayList<Bet> bets = ((ClientBetMsg)messageFromClient).getBets();
			System.out.println("Server: got client bets " + bets);
			for (Bet bet : bets) {
				client.getController().registerBet(bet);
				
			}
			client.getController().getLogger().logMessage(messageFromClient);
			
			
		}
		
		else if (messageFromClient instanceof ClientDisconnectMsg){
			
		}

	
		
	}
}
