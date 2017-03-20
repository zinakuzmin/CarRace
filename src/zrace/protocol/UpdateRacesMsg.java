package zrace.protocol;

import java.util.ArrayList;

import zrace.dbModels.Race;

/**
 * This class supplies API for {@link UpdateRacesMsg}
 * @author Zina K
 *
 */
public class UpdateRacesMsg extends Message{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ArrayList<Race> races;
	
	
	public UpdateRacesMsg(int messageId, ArrayList<Race> races) {
		super(messageId);
		this.races = new ArrayList<Race>();
		this.races.addAll(races);
		
	}
	
	public ArrayList<Race> getRaces() {
		return races;
	}
	public void setRaces(ArrayList<Race> races) {
		this.races = races;
	}

	@Override
	public String toString() {
		return  " getMessageId()= "
				+ getMessageId() + " UpdateRacesMsg [races=" + races + "]";
	}


	


	

}
