package zrace.protocol;

import java.util.ArrayList;
import dbModels.*;

public class ClientBetMsg extends Message{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ArrayList<Bet> bets;
	
	
	public ClientBetMsg(int messageId, ArrayList<Bet> bets) {
		super(messageId);
		this.bets = bets;
	}
	
	public ArrayList<Bet> getBets() {
		return bets;
	}
	public void setBets(ArrayList<Bet> bets) {
		this.bets = bets;
	}

	@Override
	public String toString() {
		return "ClientBetMsg [bets=" + bets + "]";
	}

	
	
}
