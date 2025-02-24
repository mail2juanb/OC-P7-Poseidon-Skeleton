package com.nnk.springboot.domain;

//import javax.persistence.*;
import jakarta.persistence.*;
//import javax.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotBlank;
//import javax.validation.constraints.NotNull;
import jakarta.validation.constraints.NotNull;
import java.sql.Timestamp;

@Entity
@Table(name = "rating")
public class Rating {
    // TODO: Map columns in data table RATING with corresponding java fields

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer Id;

}
