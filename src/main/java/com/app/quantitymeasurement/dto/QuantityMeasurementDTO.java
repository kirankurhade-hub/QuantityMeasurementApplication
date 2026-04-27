package com.app.quantitymeasurement.dto;

import com.app.quantitymeasurement.entity.QuantityMeasurementEntity;

import java.util.List;
import java.util.stream.Collectors;

/**
 * UC17 - API response DTO for quantity measurement operations.
 *
 * Decouples the REST layer from the JPA entity.
 * Static factory methods handle all entity <-> DTO conversions.
 */
public class QuantityMeasurementDTO {

    private double  thisValue;
    private String  thisUnit;
    private String  thisMeasurementType;

    private double  thatValue;
    private String  thatUnit;
    private String  thatMeasurementType;

    private String  operation;

    private String  resultString;
    private double  resultValue;
    private String  resultUnit;
    private String  resultMeasurementType;

    private String  errorMessage;
    private boolean error;

    public QuantityMeasurementDTO() {}

    // ── Static factory methods ───────────────────────────────────

    /** JPA entity → DTO */
    public static QuantityMeasurementDTO fromEntity(QuantityMeasurementEntity e) {
        QuantityMeasurementDTO dto = new QuantityMeasurementDTO();
        dto.thisValue             = e.getThisValue();
        dto.thisUnit              = e.getThisUnit();
        dto.thisMeasurementType   = e.getThisMeasurementType();
        dto.thatValue             = e.getThatValue();
        dto.thatUnit              = e.getThatUnit();
        dto.thatMeasurementType   = e.getThatMeasurementType();
        dto.operation             = e.getOperation();
        dto.resultString          = e.getResultString();
        dto.resultValue           = e.getResultValue();
        dto.resultUnit            = e.getResultUnit();
        dto.resultMeasurementType = e.getResultMeasurementType();
        dto.errorMessage          = e.getErrorMessage();
        dto.error                 = e.isError();
        return dto;
    }

    /** DTO → JPA entity */
    public QuantityMeasurementEntity toEntity() {
        QuantityMeasurementEntity e = new QuantityMeasurementEntity();
        e.setThisValue(thisValue);
        e.setThisUnit(thisUnit);
        e.setThisMeasurementType(thisMeasurementType);
        e.setThatValue(thatValue);
        e.setThatUnit(thatUnit);
        e.setThatMeasurementType(thatMeasurementType);
        e.setOperation(operation);
        e.setResultString(resultString);
        e.setResultValue(resultValue);
        e.setResultUnit(resultUnit);
        e.setResultMeasurementType(resultMeasurementType);
        e.setErrorMessage(errorMessage);
        e.setError(error);
        return e;
    }

    /** List of entities → list of DTOs */
    public static List<QuantityMeasurementDTO> fromEntityList(List<QuantityMeasurementEntity> entities) {
        return entities.stream()
                .map(QuantityMeasurementDTO::fromEntity)
                .collect(Collectors.toList());
    }

    // ── Getters & Setters ────────────────────────────────────────

    public double  getThisValue()                      { return thisValue; }
    public void    setThisValue(double v)              { this.thisValue = v; }
    public String  getThisUnit()                       { return thisUnit; }
    public void    setThisUnit(String v)               { this.thisUnit = v; }
    public String  getThisMeasurementType()            { return thisMeasurementType; }
    public void    setThisMeasurementType(String v)    { this.thisMeasurementType = v; }
    public double  getThatValue()                      { return thatValue; }
    public void    setThatValue(double v)              { this.thatValue = v; }
    public String  getThatUnit()                       { return thatUnit; }
    public void    setThatUnit(String v)               { this.thatUnit = v; }
    public String  getThatMeasurementType()            { return thatMeasurementType; }
    public void    setThatMeasurementType(String v)    { this.thatMeasurementType = v; }
    public String  getOperation()                      { return operation; }
    public void    setOperation(String v)              { this.operation = v; }
    public String  getResultString()                   { return resultString; }
    public void    setResultString(String v)           { this.resultString = v; }
    public double  getResultValue()                    { return resultValue; }
    public void    setResultValue(double v)            { this.resultValue = v; }
    public String  getResultUnit()                     { return resultUnit; }
    public void    setResultUnit(String v)             { this.resultUnit = v; }
    public String  getResultMeasurementType()          { return resultMeasurementType; }
    public void    setResultMeasurementType(String v)  { this.resultMeasurementType = v; }
    public String  getErrorMessage()                   { return errorMessage; }
    public void    setErrorMessage(String v)           { this.errorMessage = v; }
    public boolean isError()                           { return error; }
    public void    setError(boolean v)                 { this.error = v; }
}
