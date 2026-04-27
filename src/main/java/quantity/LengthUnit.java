package quantity;

public enum LengthUnit implements IMeasurable{
    FEET(12.0), // 1 feet = 12 inches
    INCHES(1.0), // 1 inch = 1 inch
    YARDS(36.0), // 1 yard = 36 inches
    CENTIMETERS(0.393701); // 1 cm = 0.393701 inch

    private final double conversionFactor;

    LengthUnit(double conversionFactor){
        this.conversionFactor = conversionFactor;
    }

    @Override
    public double getConversionValue() {
        return conversionFactor;
    }

    @Override
    public double convertToBaseUnit(double value){
        return value * conversionFactor;
    }

    @Override
    public double convertFromBaseUnit(double baseValue){return baseValue/conversionFactor;}

    @Override
    public String getUnitName() {return this.name();}
}
