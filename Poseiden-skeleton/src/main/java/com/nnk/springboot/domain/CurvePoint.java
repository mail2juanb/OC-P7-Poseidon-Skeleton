package com.nnk.springboot.domain;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import jakarta.persistence.*;
import java.sql.Timestamp;

/**
 * Represents a point on a financial curve used in risk modeling or trading systems.
 * <p>
 * This entity is mapped to the "curvepoint" table in the database.
 * It captures key data such as the curve ID, term, and value at a specific point in time.
 * </p>
 *
 * <p>Validation constraints are applied to ensure the integrity of the {@code curveId} field.</p>
 */
@Slf4j
@Data
@Entity
@Table(name = "curvepoint")
public class CurvePoint implements DomainModel<CurvePoint> {

    /**
     * Primary identifier for the curve point.
     * Auto-generated by the persistence provider.
     * <p>Constrained between -128 and 127, stored as a TINYINT in the database.</p>
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Integer id;

    /**
     * Identifier of the curve to which this point belongs.
     * Must be between -128 and 127 (inclusive).
     * Required and persisted as a TINYINT in the database.
     */
    @Min(value = -128, message = "curveId must be greater than or equal to -128")
    @Max(value = 127, message = "curveId must be less than or equal to 127")
    @NotNull(message = "CurveId can't be null")
    @Column(name = "curveid", nullable = false, columnDefinition = "TINYINT")
    private Integer curveId = 0;

    /**
     * The timestamp representing when this curve point is applicable ("as of" date).
     * Initialized with the current system timestamp by default.
     */
    @Column(name = "asofdate")
    private Timestamp asOfDate = new Timestamp(System.currentTimeMillis());

    /**
     * The term associated with the curve point.
     * Default is 0.0.
     */
    private Double term = 0.0;

    /**
     * The value at the given term for the curve.
     * Default is 0.0.
     */
    private Double value = 0.0;

    /**
     * Timestamp recording when the curve point was created.
     * Initialized to the current time when the entity is instantiated.
     */
    @Column(name = "creationdate")
    private Timestamp creationDate = new Timestamp(System.currentTimeMillis());


    /**
     * Updates the current {@link CurvePoint} instance with selected fields from another instance.
     *
     * <p>The following fields are updated:</p>
     * <ul>
     *     <li>{@code curveId}</li>
     *     <li>{@code term}</li>
     *     <li>{@code value}</li>
     * </ul>
     *
     * @param curvePoint the {@link CurvePoint} instance containing the new data
     * @return the updated {@link CurvePoint} instance
     */
    @Override
    public CurvePoint update(CurvePoint curvePoint){
        this.curveId = curvePoint.getCurveId();
        this.term = curvePoint.getTerm();
        this.value = curvePoint.getValue();

        return this;
    }

}
