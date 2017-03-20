package zrace.protocol;

/**
 * Message type for protocol between client and server
 * @author Zina K
 *
 */
public class WinnerCarMsg extends Message{
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int raceId;
	private int carId;

	/**
	 * Initialize message
	 * @param messageId
	 * @param carId
	 * @param raceId
	 */
	public WinnerCarMsg(int messageId, int carId, int raceId) {
		super(messageId);
		this.carId = carId;
		this.raceId = raceId;
	}

	/**
	 * Get ID of race
	 * @return int
	 */
	public int getRaceId() {
		return raceId;
	}

	/**
	 * Set raceId
	 * @param raceId
	 */
	public void setRaceId(int raceId) {
		this.raceId = raceId;
	}

	/**
	 * Get ID of winner car
	 * @return int
	 */
	public int getCarId() {
		return carId;
	}

	/**
	 * @param carId
	 */
	public void setCarId(int carId) {
		this.carId = carId;
	}

	/* (non-Javadoc)
	 * @see zrace.protocol.Message#toString()
	 */
	@Override
	public String toString() {
		return "WinnerCarMsg [raceId=" + raceId + ", carId=" + carId
				+ ", getMessageId()=" + getMessageId() + "]";
	}

	
	
}
