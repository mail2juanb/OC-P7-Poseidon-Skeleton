package com.nnk.springboot.domain;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import jakarta.persistence.*;
import java.sql.Timestamp;


@Slf4j
@Data
@Entity
@Table(name = "curvepoint")
public class CurvePoint implements DomainModel<CurvePoint> {

    // NOTE: tinyint(4) est signé par défaut, avec une plage de -128 à 127.
    //        Pas besoin de @Min/@Max tant que la valeur est générée automatiquement par la DB (@GeneratedValue).
    //        À envisager uniquement si l'id devient modifiable côté client.
    // FIXME : Oui mais que faire si la DB n'accepte pas au dela de 128, a cause du tinyint
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Integer id;

    // FIXME :  tinyint : Par défaut signé. Les valeurs sont comprises entre -128 à 127.
    @Min(-128)
    @Max(127)
    @Column(name = "curveid", columnDefinition = "TINYINT")
    private Integer curveId = 0;

    @Column(name = "asofdate")
    private Timestamp asOfDate = new Timestamp(System.currentTimeMillis());

    @NotNull(message = "Term can't be null")
    private Double term = 0.0;

    @NotNull(message = "Value can't be null")
    private Double value = 0.0;

    @Column(name = "creationdate")
    private Timestamp creationDate = new Timestamp(System.currentTimeMillis());



    @Override
    public CurvePoint update(CurvePoint curvePoint){
        this.id = curvePoint.getId();
        this.term = curvePoint.getTerm();
        this.value = curvePoint.getValue();

        return this;
    }

}
