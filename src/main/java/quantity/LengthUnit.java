package quantity;

public enum LengthUnit {
	INCHES(1.0),
	FEET(12.0),
	YARDS(36.0),
	CENTIMETERS(0.393701);
	private final double toFeetFactor;
	LengthUnit(double toFeetFactor){
		this.toFeetFactor = toFeetFactor;
	}
	public double toFeet(double value) {
		return value*toFeetFactor;
	}
	public double fromFeet(double feetValue) {
		return feetValue/toFeetFactor;
	}
	public double getConversionFactor() {
		return toFeetFactor;
	}
}
