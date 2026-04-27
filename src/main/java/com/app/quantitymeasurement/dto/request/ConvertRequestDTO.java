package com.app.quantitymeasurement.dto.request;

import com.app.quantitymeasurement.entity.QuantityDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class ConvertRequestDTO {

    @Valid @NotNull(message = "thisQuantity must not be null")
    private QuantityDTO thisQuantity;

    @NotBlank(message = "targetUnit must not be blank")
    private String targetUnit;

    public ConvertRequestDTO() {}

    public ConvertRequestDTO(QuantityDTO thisQuantity, String targetUnit) {
        this.thisQuantity = thisQuantity;
        this.targetUnit   = targetUnit;
    }

    public QuantityDTO getThisQuantity() { return thisQuantity; }
    public void setThisQuantity(QuantityDTO v) { this.thisQuantity = v; }
    public String getTargetUnit() { return targetUnit; }
    public void setTargetUnit(String v) { this.targetUnit = v; }
}
