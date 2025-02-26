package com.nnk.springboot.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.sql.Timestamp;


@Data
@Entity
@Table(name = "trade")
public class Trade implements DomainModel<Trade> {
    // TODO: Map columns in data table TRADE with corresponding java fields

    // FIXME :  tinyint(4) : Par défaut signé. Les valeurs sont comprises entre -128 à 127.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TradeId")
    private Integer id;

    @NotNull
    @Column(length = 30, nullable = false)
    private String account;

    @NotNull
    @Column(length = 30, nullable = false)
    private String type;

    private Double buyQuantity;

    private Double sellQuantity;

    private Double buyPrice;

    private Double sellPrice;

    private Timestamp tradeDate;

    @Column(length = 125)
    private String security;

    @Column(length = 10)
    private String status;

    @Column(length = 125)
    private String trader;

    @Column(length = 125)
    private String benchmark;

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



//    public Integer getId(){
//        return getId();
//    }


    public Trade update(Trade trade){
        this.id = trade.getId();

        return this;
    }


}
