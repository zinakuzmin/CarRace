package zrace.protocol;

public class WinnerCarMsg extends Message{
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int raceId;
	private int carId;

	public WinnerCarMsg(int messageId, int carId, int raceId) {
		super(messageId);
		this.carId = carId;
		this.raceId = raceId;
	}

	public int getRaceId() {
		return raceId;
	}

	public void setRaceId(int raceId) {
		this.raceId = raceId;
	}

	public int getCarId() {
		return carId;
	}

	public void setCarId(int carId) {
		this.carId = carId;
	}

	@Override
	public String toString() {
		return "WinnerCarMsg [raceId=" + raceId + ", carId=" + carId
				+ ", getMessageId()=" + getMessageId() + "]";
	}

	
	
}
