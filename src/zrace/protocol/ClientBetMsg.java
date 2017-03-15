package zrace.protocol;

import java.util.ArrayList;
import dbModels.*;

public class ClientBetMsg extends Message{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ArrayList<Bet> bets;
	
	
	public ClientBetMsg(ArrayList<Bet> bets) {
		this.bets = bets;
	}
	
	public ArrayList<Bet> getBets() {
		return bets;
	}
	public void setBets(ArrayList<Bet> bets) {
		this.bets = bets;
	}

}
