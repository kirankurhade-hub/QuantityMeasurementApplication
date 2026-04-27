package quantity;
public enum LengthUnit {

    FEET(1.0),
    INCHES(1.0 / 12.0),
    YARDS(3.0),
    CENTIMETERS(1.0 / 30.48);

    private final double conversionFactor;

    LengthUnit(double conversionFactor) {
        this.conversionFactor = conversionFactor;
    }

    public double getConversionFactor() {
        return conversionFactor;
    }
   
    public double convertToBaseUnit(double value) {
        if (Double.isNaN(value) || Double.isInfinite(value)) {
            throw new IllegalArgumentException("Invalid value");
        }
        return round(value * conversionFactor);
    }

    public double convertFromBaseUnit(double baseValue) {
       
        return round(baseValue / conversionFactor);
    }

    private double round(double value) {
        return Math.round(value * 100.0) / 100.0;
    }
}