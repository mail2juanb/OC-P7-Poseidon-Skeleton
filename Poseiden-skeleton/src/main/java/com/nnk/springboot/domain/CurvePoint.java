package com.nnk.springboot.domain;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import jakarta.persistence.*;
import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
@Entity
@Table(name = "curvepoint")
public class CurvePoint {
    // TODO: Map columns in data table CURVEPOINT with corresponding java fields

    // FIXME :  tinyint(4) : Par défaut signé. Les valeurs sont comprises entre -128 à 127.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    // FIXME :  tinyint : Par défaut signé. Les valeurs sont comprises entre -128 à 127.
    @Min(-128)
    @Max(127)
    @Column(columnDefinition = "TINYINT")
    private Integer curveId;

    private Timestamp asOfDate;

    private Double term;

    private Double value;

    private Timestamp creationDate;

}
