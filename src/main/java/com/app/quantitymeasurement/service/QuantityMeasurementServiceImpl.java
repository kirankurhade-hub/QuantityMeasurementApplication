package com.app.quantitymeasurement.service;

import com.app.quantitymeasurement.core.Quantity;
import com.app.quantitymeasurement.dto.QuantityMeasurementDTO;
import com.app.quantitymeasurement.entity.QuantityDTO;
import com.app.quantitymeasurement.entity.QuantityMeasurementEntity;
import com.app.quantitymeasurement.entity.QuantityModel;
import com.app.quantitymeasurement.exeption.QuantityMeasurementException;
import com.app.quantitymeasurement.model.*;
import com.app.quantitymeasurement.repository.IQuantityMeasurementRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * UC17 - Spring Service implementation.
 *
 * Changes from UC16:
 *   - @Service registers this bean with Spring
 *   - Constructor injection of IQuantityMeasurementRepository (Spring Data JPA)
 *   - All operations return QuantityMeasurementDTO instead of raw types
 *   - Errors are saved to the repository for /history/errored endpoint
 *   - New methods: getMeasurementsByType, getErrorHistory, getOperationCount
 *   - No main() method — Spring Boot bootstraps the app
 *
 * Business logic (compare/convert/arithmetic) is unchanged from UC16.
 */
@Service
public class QuantityMeasurementServiceImpl implements IQuantityMeasurementService {

    private static final Logger logger =
            LoggerFactory.getLogger(QuantityMeasurementServiceImpl.class);

    private final IQuantityMeasurementRepository repository;

    public QuantityMeasurementServiceImpl(IQuantityMeasurementRepository repository) {
        this.repository = repository;
        logger.info("QuantityMeasurementServiceImpl initialized");
    }

    // ── Unit resolution ──────────────────────────────────────────

    private IMeasurable resolveUnit(String measurementType, String unitName) {
        return switch (measurementType) {
            case "LengthUnit"      -> LengthUnit.FEET.getUnitInstance(unitName);
            case "WeightUnit"      -> WeightUnit.KILOGRAM.getUnitInstance(unitName);
            case "VolumeUnit"      -> VolumeUnit.LITRE.getUnitInstance(unitName);
            case "TemperatureUnit" -> TemperatureUnit.CELSIUS.getUnitInstance(unitName);
            default -> throw new QuantityMeasurementException(
                    "Unknown measurement type: " + measurementType);
        };
    }

    private QuantityModel<IMeasurable> toModel(QuantityDTO dto) {
        if (dto == null) throw new QuantityMeasurementException("QuantityDTO cannot be null");
        return new QuantityModel<>(dto.getValue(), resolveUnit(dto.getMeasurementType(), dto.getUnit()));
    }

    // ── Entity builder helper ────────────────────────────────────

    private QuantityMeasurementEntity buildEntity(
            QuantityModel<IMeasurable> m1,
            QuantityModel<IMeasurable> m2,
            String operation,
            String resultString,
            QuantityModel<IMeasurable> resultModel,
            boolean isError,
            String errorMessage) {

        QuantityMeasurementEntity e = new QuantityMeasurementEntity();
        if (m1 != null) {
            e.setThisValue(m1.getValue());
            e.setThisUnit(m1.getUnit().getUnitName());
            e.setThisMeasurementType(m1.getUnit().getMeasurementType());
        }
        if (m2 != null) {
            e.setThatValue(m2.getValue());
            e.setThatUnit(m2.getUnit().getUnitName());
            e.setThatMeasurementType(m2.getUnit().getMeasurementType());
        }
        if (resultModel != null) {
            e.setResultValue(resultModel.getValue());
            e.setResultUnit(resultModel.getUnit().getUnitName());
            e.setResultMeasurementType(resultModel.getUnit().getMeasurementType());
        }
        e.setOperation(operation);
        e.setResultString(resultString);
        e.setError(isError);
        e.setErrorMessage(errorMessage);
        return e;
    }

    // ── Operations ───────────────────────────────────────────────

    @Override
    public QuantityMeasurementDTO compare(QuantityDTO dto1, QuantityDTO dto2) {
        QuantityModel<IMeasurable> m1 = toModel(dto1);
        QuantityModel<IMeasurable> m2 = toModel(dto2);
        try {
            if (!m1.getUnit().getClass().equals(m2.getUnit().getClass())) {
                QuantityMeasurementEntity e =
                        buildEntity(m1, m2, "COMPARE", "Not Equal", null, false, null);
                repository.save(e);
                return QuantityMeasurementDTO.fromEntity(e);
            }
            boolean equal = Math.abs(
                    m1.getUnit().convertToBaseUnit(m1.getValue()) -
                    m2.getUnit().convertToBaseUnit(m2.getValue())) <= 1e-5;
            QuantityMeasurementEntity e =
                    buildEntity(m1, m2, "COMPARE", equal ? "Equal" : "Not Equal", null, false, null);
            repository.save(e);
            return QuantityMeasurementDTO.fromEntity(e);
        } catch (Exception ex) {
            QuantityMeasurementEntity e =
                    buildEntity(m1, m2, "COMPARE", null, null, true, ex.getMessage());
            repository.save(e);
            return QuantityMeasurementDTO.fromEntity(e);
        }
    }

    @Override
    public QuantityMeasurementDTO convert(QuantityDTO dto, String targetUnitName) {
        QuantityModel<IMeasurable> m = toModel(dto);
        try {
            IMeasurable targetUnit = resolveUnit(dto.getMeasurementType(), targetUnitName);
            Quantity<IMeasurable> converted =
                    new Quantity<>(m.getValue(), m.getUnit()).convertTo(targetUnit);
            QuantityModel<IMeasurable> resultModel =
                    new QuantityModel<>(converted.getValue(), converted.getUnit());
            QuantityMeasurementEntity e =
                    buildEntity(m, null, "CONVERT", null, resultModel, false, null);
            repository.save(e);
            return QuantityMeasurementDTO.fromEntity(e);
        } catch (Exception ex) {
            QuantityMeasurementEntity e =
                    buildEntity(m, null, "CONVERT", null, null, true, ex.getMessage());
            repository.save(e);
            return QuantityMeasurementDTO.fromEntity(e);
        }
    }

    @Override
    public QuantityMeasurementDTO add(QuantityDTO dto1, QuantityDTO dto2) {
        return add(dto1, dto2, dto1.getUnit());
    }

    @Override
    public QuantityMeasurementDTO add(QuantityDTO dto1, QuantityDTO dto2, String targetUnitName) {
        QuantityModel<IMeasurable> m1 = toModel(dto1);
        QuantityModel<IMeasurable> m2 = toModel(dto2);
        try {
            IMeasurable targetUnit = resolveUnit(dto1.getMeasurementType(), targetUnitName);
            Quantity<IMeasurable> result =
                    new Quantity<>(m1.getValue(), m1.getUnit())
                    .add(new Quantity<>(m2.getValue(), m2.getUnit()), targetUnit);
            QuantityModel<IMeasurable> resultModel =
                    new QuantityModel<>(result.getValue(), result.getUnit());
            QuantityMeasurementEntity e =
                    buildEntity(m1, m2, "ADD", null, resultModel, false, null);
            repository.save(e);
            return QuantityMeasurementDTO.fromEntity(e);
        } catch (Exception ex) {
            QuantityMeasurementEntity e =
                    buildEntity(m1, m2, "ADD", null, null, true, ex.getMessage());
            repository.save(e);
            return QuantityMeasurementDTO.fromEntity(e);
        }
    }

    @Override
    public QuantityMeasurementDTO subtract(QuantityDTO dto1, QuantityDTO dto2) {
        return subtract(dto1, dto2, dto1.getUnit());
    }

    @Override
    public QuantityMeasurementDTO subtract(QuantityDTO dto1, QuantityDTO dto2, String targetUnitName) {
        QuantityModel<IMeasurable> m1 = toModel(dto1);
        QuantityModel<IMeasurable> m2 = toModel(dto2);
        try {
            IMeasurable targetUnit = resolveUnit(dto1.getMeasurementType(), targetUnitName);
            Quantity<IMeasurable> result =
                    new Quantity<>(m1.getValue(), m1.getUnit())
                    .subtract(new Quantity<>(m2.getValue(), m2.getUnit()), targetUnit);
            QuantityModel<IMeasurable> resultModel =
                    new QuantityModel<>(result.getValue(), result.getUnit());
            QuantityMeasurementEntity e =
                    buildEntity(m1, m2, "SUBTRACT", null, resultModel, false, null);
            repository.save(e);
            return QuantityMeasurementDTO.fromEntity(e);
        } catch (Exception ex) {
            QuantityMeasurementEntity e =
                    buildEntity(m1, m2, "SUBTRACT", null, null, true, ex.getMessage());
            repository.save(e);
            return QuantityMeasurementDTO.fromEntity(e);
        }
    }

    @Override
    public QuantityMeasurementDTO divide(QuantityDTO dto1, QuantityDTO dto2) {
        QuantityModel<IMeasurable> m1 = toModel(dto1);
        QuantityModel<IMeasurable> m2 = toModel(dto2);
        try {
            double result = new Quantity<>(m1.getValue(), m1.getUnit())
                    .divide(new Quantity<>(m2.getValue(), m2.getUnit()));
            QuantityMeasurementEntity e =
                    buildEntity(m1, m2, "DIVIDE", String.valueOf(result), null, false, null);
            repository.save(e);
            return QuantityMeasurementDTO.fromEntity(e);
        } catch (Exception ex) {
            QuantityMeasurementEntity e =
                    buildEntity(m1, m2, "DIVIDE", null, null, true, ex.getMessage());
            repository.save(e);
            return QuantityMeasurementDTO.fromEntity(e);
        }
    }

    // ── History / reporting ──────────────────────────────────────

    @Override
    public List<QuantityMeasurementDTO> getAllMeasurements() {
        return QuantityMeasurementDTO.fromEntityList(repository.findAll());
    }

    @Override
    public List<QuantityMeasurementDTO> getMeasurementsByOperation(String operation) {
        return QuantityMeasurementDTO.fromEntityList(
                repository.findByOperation(operation.toUpperCase()));
    }

    @Override
    public List<QuantityMeasurementDTO> getMeasurementsByType(String measurementType) {
        return QuantityMeasurementDTO.fromEntityList(
                repository.findByThisMeasurementType(measurementType));
    }

    @Override
    public List<QuantityMeasurementDTO> getErrorHistory() {
        return QuantityMeasurementDTO.fromEntityList(
                repository.findByIsError(true));
    }

    @Override
    public long getOperationCount(String operation) {
        return repository.countByOperationAndIsErrorFalse(operation.toUpperCase());
    }
}
