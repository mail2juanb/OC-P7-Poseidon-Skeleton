package com.nnk.springboot.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.Data;

import lombok.extern.slf4j.Slf4j;


@Slf4j
@Data
@Entity
@Table(name = "rating")
public class Rating implements DomainModel<Rating> {

    // NOTE: tinyint(4) est signé par défaut, avec une plage de -128 à 127.
    //        Pas besoin de @Min/@Max tant que la valeur est générée automatiquement par la DB (@GeneratedValue).
    //        À envisager uniquement si l'id devient modifiable côté client.
    // FIXME : Oui mais que faire si la DB n'accepte pas au dela de 128, a cause du tinyint
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false)
    private Integer id;

    @Size(max = 125, message = "moodysRating must not exceed 125 characters.")
    @Column(name = "moodysrating", length = 125)
    private String moodysRating = "";

    @Size(max = 125, message = "sandPRating must not exceed 125 characters.")
    @Column(name = "sandprating", length = 125)
    private String sandPRating = "";

    @Size(max = 125, message = "fitchRating must not exceed 125 characters.")
    @Column(name ="fitchrating", length = 125)
    private String fitchRating = "";

    /* tinyint = Un entier de très petite taille.
     * La plage signée par défaut est comprise entre -128 et 127.
     * La plage non signée est comprise entre 0 et 255.
     */
    @Min(value = -128, message = "orderNumber must be greater than or equal to -128")
    @Max(value = 127, message = "orderNumber must be less than or equal to 127")
    @Column(name = "ordernumber", columnDefinition = "TINYINT")
    private Integer orderNumber = 0;



    @Override
    public Rating update(Rating rating){
        this.id = rating.getId();
        this.moodysRating = rating.getMoodysRating();
        this.sandPRating = rating.getSandPRating();
        this.fitchRating = rating.getFitchRating();
        this.orderNumber = rating.getOrderNumber();

        return this;
    }

}
