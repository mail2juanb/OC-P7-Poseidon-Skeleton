package com.nnk.springboot.domain;

import org.hibernate.validator.constraints.Length;

//import javax.persistence.*;
import jakarta.persistence.*;
//import javax.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotBlank;
//import javax.validation.constraints.NotNull;
import jakarta.validation.constraints.NotNull;
import java.sql.Timestamp;


@Entity
@Table(name = "curvepoint")
public class CurvePoint {
    // TODO: Map columns in data table CURVEPOINT with corresponding java fields
}
