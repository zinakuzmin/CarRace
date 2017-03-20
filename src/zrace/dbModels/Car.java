package zrace.dbModels;

/**
 * The Class Car.
 * @author Zina K
 */
public class Car {
	
	/**
	 * The car id.
	 */
	private int carId;
	
	/**
	 * The car full name.
	 */
	private String carFullName;
	
	/**
	 * The car type.
	 */
	private CarTypeEnum carType;
	
	
	/**
	 * Instantiates a new car.
	 */
	public Car(){
		
	}
	
	/**
	 * Instantiates a new car.
	 *
	 * @param carId the car id
	 * @param carFullName the car full name
	 */
	public Car(int carId, String carFullName) {
		this.carId = carId;
		this.carFullName = carFullName;
	}
	
	/**
	 * Gets the car id.
	 *
	 * @return the car id
	 */
	public int getCarId() {
		return carId;
	}
	
	/**
	 * Sets the car id.
	 *
	 * @param carId the new car id
	 */
	public void setCarId(int carId) {
		this.carId = carId;
	}
	
	/**
	 * Gets the car full name.
	 *
	 * @return the car full name
	 */
	public String getCarFullName() {
		return carFullName;
	}
	
	/**
	 * Sets the car full name.
	 *
	 * @param carFullName the new car full name
	 */
	public void setCarFullName(String carFullName) {
		this.carFullName = carFullName;
	}
	
	/**
	 * Gets the car type.
	 *
	 * @return the car type
	 */
	public CarTypeEnum getCarType() {
		return carType;
	}
	
	/**
	 * Sets the car type.
	 *
	 * @param carType the new car type
	 */
	public void setCarType(CarTypeEnum carType) {
		this.carType = carType;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Car [carId=" + carId + ", carFullName=" + carFullName
				+ ", carType=" + carType + "]";
	}
	
	
	

}
