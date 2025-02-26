package com.nnk.springboot.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@Entity
@Table(name = "rating")
public class Rating {
    // TODO: Map columns in data table RATING with corresponding java fields

    // FIXME :  tinyint(4) : Par défaut signé. Les valeurs sont comprises entre -128 à 127.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer Id;

    @Column(length = 125)
    private String moodysRating;

    @Column(length = 125)
    private String sandPRating;

    @Column(length = 125)
    private String fitchRating;

    /* tinyint = Un entier de très petite taille.
     * La plage signée par défaut est comprise entre -128 et 127.
     * La plage non signée est comprise entre 0 et 255.
     */
    @Min(-128)
    @Max(127)
    @Column(columnDefinition = "TINYINT")
    private Integer orderNumber;

}
