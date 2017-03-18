package zrace.server;


import java.util.ArrayList;
import dbModels.*;
import zrace.protocol.ClientBetMsg;
import zrace.protocol.ClientConnectMsg;
import zrace.protocol.ClientDisconnectMsg;
import zrace.protocol.Message;import zrace.protocol.UpdateRaceRunsMsg;
import zrace.protocol.UpdateRacesMsg;
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
			client.setUserFullName(((ClientConnectMsg) messageFromClient).getUserFullName());
			User user = client.getController().userLoginOrRegister(((ClientConnectMsg) messageFromClient).getUserId(), ((ClientConnectMsg) messageFromClient).getUserFullName());
			client.getController().addClientToActiveClients(client);
			client.getController().getLogger().logMessage(messageFromClient);
			
			new Thread(() -> {
				try {
//					ObjectOutputStream out = new ObjectOutputStream(client.getConnectToClient().getOutputStream());
//					out.flush();
					client.getStreamToClient().writeObject(new UserDetailsMsg(0, user));
					System.out.println("server sent user details " + user);
					ArrayList<Race> activeRaces = client.getController().getActiveRaces();
					client.getStreamToClient().flush();
					client.getStreamToClient().writeObject(new UpdateRacesMsg(100, activeRaces));
					System.out.println("server sent to client races " + activeRaces);
					client.getStreamToClient().writeObject(new UpdateRaceRunsMsg(101, client.getController().getRaceRuns()));
					client.getStreamToClient().flush();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}).start();
//			client.getController().getLogger().logMessage(messageFromClient);
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
			client.getController().getLogger().logMessage(messageFromClient);
			client.clientDisconnect(((ClientDisconnectMsg)messageFromClient).getUser());
		}

	
		
	}
}
