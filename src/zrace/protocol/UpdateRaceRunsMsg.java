package zrace.protocol;

import java.util.ArrayList;

import dbModels.RaceRun;

public class UpdateRaceRunsMsg extends Message{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ArrayList<RaceRun> raceRuns;
	
	 
//	public UpdateRaceRunsMsg(ArrayList<RaceRun> raceRuns) {
//		this.raceRuns = raceRuns;
//	}
	
	public UpdateRaceRunsMsg(int messageId, ArrayList<RaceRun> raceRuns) {
		super(messageId);
		this.raceRuns = new ArrayList<RaceRun>();
		this.raceRuns.addAll(raceRuns);
//		this.raceRuns = raceRuns;
		
	}
	
	public ArrayList<RaceRun> getRaceRuns() {
		return raceRuns;
	}
	public void setRaceRuns(ArrayList<RaceRun> raceRuns) {
		this.raceRuns = raceRuns;
	}

	@Override
	public String toString() {
		return "getMessageId()= "
				+ getMessageId() + " UpdateRaceRunsMsg [raceRuns=" + raceRuns + "]";
	}


	
	
}
