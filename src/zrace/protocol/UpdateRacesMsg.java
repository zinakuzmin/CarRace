package zrace.protocol;

import java.util.ArrayList;

import dbModels.Race;

public class UpdateRacesMsg extends Message{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ArrayList<Race> races;
	
	public UpdateRacesMsg(ArrayList<Race> races) {
		this.races = races;
		
	}
	
	
	public ArrayList<Race> getRaces() {
		return races;
	}
	public void setRaces(ArrayList<Race> races) {
		this.races = races;
	}

}
