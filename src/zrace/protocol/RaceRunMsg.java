package zrace.protocol;

import dbModels.RaceRun;

public class RaceRunMsg extends Message{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private RaceRun raceRun;

	public RaceRunMsg(RaceRun raceRun) {
		this.raceRun = raceRun;
	}
	
	public RaceRun getRaceRun() {
		return raceRun;
	}
	public void setRaceRun(RaceRun raceRun) {
		this.raceRun = raceRun;
	}

	@Override
	public String toString() {
		return "RaceRunMsg [raceRun=" + raceRun + "]";
	}
	
	
	

}
