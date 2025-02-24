package com.nnk.springboot.domain;

//import javax.persistence.*;
import jakarta.persistence.*;
//import javax.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotBlank;
import java.sql.Timestamp;

@Entity
@Table(name = "rulename")
public class RuleName {
    // TODO: Map columns in data table RULENAME with corresponding java fields

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer Id;

}
