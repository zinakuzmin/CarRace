package zrace.protocol;

import java.util.ArrayList;

import zrace.dbModels.*;

/**
 * This class supplies API for {@link ClientBetMsg}
 * @author Zina K
 *
 */
public class ClientBetMsg extends Message{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * List of bets
	 */
	private ArrayList<Bet> bets;
	
	
	/**
	 * Initialize {@link ClientBetMsg}
	 * @param messageId
	 * @param bets
	 */
	public ClientBetMsg(int messageId, ArrayList<Bet> bets) {
		super(messageId);
		this.bets = bets;
	}
	
	/**
	 * Return list of bets
	 * @return Arraylist
	 */
	public ArrayList<Bet> getBets() {
		return bets;
	}
	/**
	 * Set bets
	 * @param bets
	 */
	public void setBets(ArrayList<Bet> bets) {
		this.bets = bets;
	}

	/* (non-Javadoc)
	 * @see zrace.protocol.Message#toString()
	 */
	@Override
	public String toString() {
		return "ClientBetMsg [bets=" + bets + "]";
	}

	
	
}
