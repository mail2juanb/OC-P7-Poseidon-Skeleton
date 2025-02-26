package com.nnk.springboot.domain;

//import org.springframework.beans.factory.annotation.Required;

import jakarta.persistence.*;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
@Entity
@Table(name = "bidlist")
public class BidList {
    // TODO: Map columns in data table BIDLIST with corresponding java fields

    // FIXME :  tinyint(4) : Par défaut signé. Les valeurs sont comprises entre -128 à 127.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer BidListId;

    @NotNull
    @Column(length = 30, nullable = false)
    private String account;

    @NotNull
    @Column(length = 30, nullable = false)
    private String type;

    // FIXME : On peut annoter avec @Digits(integer = 10, fraction = 2) pour contraindre par exemple.
    //  Mais je ne sais pas ce que la variable doit représenter...
    private Double bidQuantity;

    private Double askQuantity;

    private Double bid;

    private Double ask;

    @Column(length = 125)
    private String benchmark;

    private Timestamp bidListDate;

    @Column(length = 125)
    private String commentary;

    @Column(length = 125)
    private String security;

    @Column(length = 10)
    private String status;

    @Column(length = 125)
    private String trader;

    @Column(length = 125)
    private String book;

    @Column(length = 125)
    private String creationName;

    private Timestamp creationDate;

    @Column(length = 125)
    private String revisionName;

    private Timestamp revisionDate;

    @Column(length = 125)
    private String dealName;

    @Column(length = 125)
    private String dealType;

    // FIXME : C'est quoi sourceListId dans une String ?
    @Column(length = 125)
    private String sourceListId;

    @Column(length = 125)
    private String side;

}
