package com.app.quantitymeasurement.entity;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

interface IMeasurableUnit {
    String getUnitName();
    String getMeasurementType();
}

public class QuantityDTO {

    public enum LengthUnit implements IMeasurableUnit {
        FEET, INCHES, YARDS, CENTIMETERS;
        public String getUnitName()        { return this.name(); }
        public String getMeasurementType() { return this.getClass().getSimpleName(); }
    }

    public enum VolumeUnit implements IMeasurableUnit {
        LITRE, MILLILITRE, GALLON;
        public String getUnitName()        { return this.name(); }
        public String getMeasurementType() { return this.getClass().getSimpleName(); }
    }

    public enum WeightUnit implements IMeasurableUnit {
        MILLIGRAM, GRAM, KILOGRAM, POUND, TONNE;
        public String getUnitName()        { return this.name(); }
        public String getMeasurementType() { return this.getClass().getSimpleName(); }
    }

    public enum TemperatureUnit implements IMeasurableUnit {
        CELSIUS, FAHRENHEIT, KELVIN;
        public String getUnitName()        { return this.name(); }
        public String getMeasurementType() { return this.getClass().getSimpleName(); }
    }

    @NotNull(message = "Value must not be null")
    public Double value;

    @NotBlank(message = "Unit must not be blank")
    public String unit;

    @NotBlank(message = "Measurement type must not be blank")
    @Pattern(
        regexp = "LengthUnit|WeightUnit|VolumeUnit|TemperatureUnit",
        message = "Measurement type must be one of: LengthUnit, WeightUnit, VolumeUnit, TemperatureUnit"
    )
    public String measurementType;

    public QuantityDTO() {}

    public QuantityDTO(double value, IMeasurableUnit unit) {
        this.value           = value;
        this.unit            = unit.getUnitName();
        this.measurementType = unit.getMeasurementType();
    }

    public QuantityDTO(double value, String unit, String measurementType) {
        this.value           = value;
        this.unit            = unit;
        this.measurementType = measurementType;
    }

    public double getValue()           { return value; }
    public String getUnit()            { return unit; }
    public String getMeasurementType() { return measurementType; }

    @Override
    public String toString() { return String.format("%.2f %s", value, unit); }
}
