package dbModels;

public class Car {
	
	private int carId;
	private String carFullName;
	private CarTypeEnum carType;
	
	
	public Car(){
		
	}
	public Car(int carId, String carFullName) {
		this.carId = carId;
		this.carFullName = carFullName;
	}
	public int getCarId() {
		return carId;
	}
	public void setCarId(int carId) {
		this.carId = carId;
	}
	public String getCarFullName() {
		return carFullName;
	}
	public void setCarFullName(String carFullName) {
		this.carFullName = carFullName;
	}
	public CarTypeEnum getCarType() {
		return carType;
	}
	public void setCarType(CarTypeEnum carType) {
		this.carType = carType;
	}
	@Override
	public String toString() {
		return "Car [carId=" + carId + ", carFullName=" + carFullName
				+ ", carType=" + carType + "]";
	}
	
	
	

}
