package com.app.quantitymeasurement.model;

// Enum with conversion factor to base unit (inches)

import com.app.quantitymeasurement.model.IMeasurable;

public enum LengthUnit implements IMeasurable {
    FEET(12.0), // 1 feet = 12 inches
    INCHES(1.0), // 1 inch = 1 inch
    YARDS(36.0), // 1 yard = 36 inches
    CENTIMETERS(0.393701), // 1 cm = 0.393701 inch
    METERS(39.3701), // 1 meter = 39.3701 inches
    KILOMETERS(39370.1), // 1 km = 39370.1 inches
    MILLIMETERS(0.0393701), // 1 mm = 0.0393701 inch
    MILES(63360.0); // 1 mile = 63360 inches

    private final double conversionFactor;

    // Constructor
    LengthUnit(double conversionFactor){
        this.conversionFactor = conversionFactor;
    }

    // Getter method of conversionFactor
    @Override
    public double getConversionValue() {
        return conversionFactor;
    }

    // Convert value from this unit to base unit (inches)
    @Override
    public double convertToBaseUnit(double value){
        return value * conversionFactor;
    }

    // Convert value from base unit (inch) to this unit
    @Override
    public double convertFromBaseUnit(double baseValue){
        return baseValue/conversionFactor;
    }

    // Get unit name
    @Override
    public String getUnitName() {
        return this.name();
    }

    @Override
    public String getMeasurementType(){
        return this.getClass().getSimpleName();
    }

    @Override
    public IMeasurable getUnitInstance(String unitName){
        for(LengthUnit unit : LengthUnit.values()){
            if(unit.getUnitName().equalsIgnoreCase(unitName)){
                return unit;
            }
        }
        throw new IllegalArgumentException("Invalid length unit: " + unitName);
    }
}
