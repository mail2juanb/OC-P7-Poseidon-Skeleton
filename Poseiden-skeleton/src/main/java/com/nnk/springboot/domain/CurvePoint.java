package com.nnk.springboot.domain;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import jakarta.persistence.*;
import java.sql.Timestamp;

//@Getter
//@Setter
//@AllArgsConstructor
@Data
@Entity
@Table(name = "curvepoint")
public class CurvePoint implements DomainModel<CurvePoint> {

    // TODO: Map columns in data table CURVEPOINT with corresponding java fields

    // FIXME :  tinyint(4) : Par défaut signé. Les valeurs sont comprises entre -128 à 127.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    // FIXME :  tinyint : Par défaut signé. Les valeurs sont comprises entre -128 à 127.
    @Min(-128)
    @Max(127)
    @Column(name = "curveid", columnDefinition = "TINYINT")
    private Integer curveId;

    @Column(name = "asofdate")
    private Timestamp asOfDate;

    private Double term;

    private Double value;

    @Column(name = "creationdate")
    private Timestamp creationDate;



    @Override
    public CurvePoint update(CurvePoint curvePoint){
        this.id = curvePoint.getId();

        return this;
    }



    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public @Min(-128) @Max(127) Integer getCurveId() {
        return curveId;
    }

    public void setCurveId(@Min(-128) @Max(127) Integer curveId) {
        this.curveId = curveId;
    }

    public Timestamp getAsOfDate() {
        return asOfDate;
    }

    public void setAsOfDate(Timestamp asOfDate) {
        this.asOfDate = asOfDate;
    }

    public Double getTerm() {
        return term;
    }

    public void setTerm(Double term) {
        this.term = term;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public Timestamp getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Timestamp creationDate) {
        this.creationDate = creationDate;
    }


    @Override
    public String toString() {
        return "CurvePoint{" +
                "id=" + id +
                ", curveId=" + curveId +
                ", asOfDate=" + asOfDate +
                ", term=" + term +
                ", value=" + value +
                ", creationDate=" + creationDate +
                '}';
    }

}
