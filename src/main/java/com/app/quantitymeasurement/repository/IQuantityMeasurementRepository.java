package com.app.quantitymeasurement.repository;

import com.app.quantitymeasurement.entity.QuantityMeasurementEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * UC17 - Spring Data JPA repository.
 * Replaces all JDBC boilerplate from UC16.
 * findAll(), save(), deleteAll(), count() are inherited from JpaRepository.
 */
@Repository
public interface IQuantityMeasurementRepository
        extends JpaRepository<QuantityMeasurementEntity, Long> {

    /** All records for a given operation type (COMPARE, ADD, etc.) */
    List<QuantityMeasurementEntity> findByOperation(String operation);

    /** All records for a given measurement type (LengthUnit, WeightUnit, etc.) */
    List<QuantityMeasurementEntity> findByThisMeasurementType(String type);

    /** All error records */
    List<QuantityMeasurementEntity> findByIsError(boolean isError);

    /** Count successful records for a given operation */
    long countByOperationAndIsErrorFalse(String operation);

    /** Custom JPQL: successful records for a given operation */
    @Query("SELECT e FROM QuantityMeasurementEntity e " +
           "WHERE e.operation = :operation AND e.isError = false " +
           "ORDER BY e.createdAt DESC")
    List<QuantityMeasurementEntity> findSuccessfulByOperation(@Param("operation") String operation);
}
