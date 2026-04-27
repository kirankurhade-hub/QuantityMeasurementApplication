package quantity;

public enum LengthUnit {

    INCHES(1.0 / 12.0),       // 1 inch = 1/12 feet
    FEET(1.0),                // 1 foot = 1 foot
    YARDS(3.0),               // 1 yard = 3 feet
    CENTIMETERS(1.0 / 30.48); // 1 cm = 1/30.48 feet

    private final double toFeetFactor;

    LengthUnit(double toFeetFactor) {
        this.toFeetFactor = toFeetFactor;
    }

    public double toFeet(double value) {
        return value * toFeetFactor;
    }

    public double fromFeet(double feetValue) {
        return feetValue / toFeetFactor;
    }
}