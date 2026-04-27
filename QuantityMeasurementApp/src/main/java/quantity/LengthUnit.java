package quantity;

public enum LengthUnit {
	INCHES(1.0),
	FEET(12.0),
	YARDS(36.0),
	CENTIMETERS(0.393701);
	private final double toInchesFactor;
	LengthUnit(double toInchesFactor){
		this.toInchesFactor = toInchesFactor;
	}
	public double toFeet(double value) {
		return value*toInchesFactor;
	}
}
