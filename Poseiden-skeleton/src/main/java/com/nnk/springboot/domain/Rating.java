package com.nnk.springboot.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;


//@Getter
//@Setter
//@AllArgsConstructor
@Data
@Entity
@Table(name = "rating")
public class Rating implements DomainModel<Rating> {
    // TODO: Map columns in data table RATING with corresponding java fields

    // FIXME :  tinyint(4) : Par défaut signé. Les valeurs sont comprises entre -128 à 127.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "moodysrating", length = 125)
    private String moodysRating;

    @Column(name = "sandprating", length = 125)
    private String sandPRating;

    @Column(name ="fitchrating", length = 125)
    private String fitchRating;

    /* tinyint = Un entier de très petite taille.
     * La plage signée par défaut est comprise entre -128 et 127.
     * La plage non signée est comprise entre 0 et 255.
     */
    @Min(-128)
    @Max(127)
    @Column(name = "ordernumber", columnDefinition = "TINYINT")
    private Integer orderNumber;



    @Override
    public Rating update(Rating rating){
        this.id = rating.getId();

        return this;
    }



    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMoodysRating() {
        return moodysRating;
    }

    public void setMoodysRating(String moodysRating) {
        this.moodysRating = moodysRating;
    }

    public String getSandPRating() {
        return sandPRating;
    }

    public void setSandPRating(String sandPRating) {
        this.sandPRating = sandPRating;
    }

    public String getFitchRating() {
        return fitchRating;
    }

    public void setFitchRating(String fitchRating) {
        this.fitchRating = fitchRating;
    }

    public @Min(-128) @Max(127) Integer getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(@Min(-128) @Max(127) Integer orderNumber) {
        this.orderNumber = orderNumber;
    }

    @Override
    public String toString() {
        return "Rating{" +
                "id=" + id +
                ", moodysRating='" + moodysRating + '\'' +
                ", sandPRating='" + sandPRating + '\'' +
                ", fitchRating='" + fitchRating + '\'' +
                ", orderNumber=" + orderNumber +
                '}';
    }
}
