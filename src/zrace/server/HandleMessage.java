package zrace.server;


import zrace.protocol.ClientBetMsg;
import zrace.protocol.ClientConnectMsg;
import zrace.protocol.ClientDisconnectMsg;
import zrace.protocol.Message;


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
			client.getController().userLoginOrRegister(((ClientConnectMsg) messageFromClient).getUserId(), ((ClientConnectMsg) messageFromClient).getUserFullName());
			client.getController().getLogger().logMessage(messageFromClient);
		}
		
		else if (messageFromClient instanceof ClientBetMsg){
			
		}
		
		else if (messageFromClient instanceof ClientDisconnectMsg){
			
		}

	
		
	}
}
