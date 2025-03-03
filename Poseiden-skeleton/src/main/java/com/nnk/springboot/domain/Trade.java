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

    // FIXME :  tinyint(4) : Par défaut signé. Les valeurs sont comprises entre -128 à 127. on ajoute un @Min et @%ax ?
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tradeid", updatable = false)
    private Integer id;

    @NotNull
    @Column(length = 30, nullable = false)
    private String account;

    @NotNull
    @Column(length = 30, nullable = false)
    private String type;

    @Column(name = "buyquantity")
    private Double buyQuantity;

    @Column(name = "sellquantity")
    private Double sellQuantity;

    @Column(name = "buyprice")
    private Double buyPrice;

    @Column(name = "sellprice")
    private Double sellPrice;

    @Column(name = "tradedate")
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

    @Column(name = "creationname", length = 125)
    private String creationName;

    @Column(name = "creationdate")
    private Timestamp creationDate;

    @Column(name = "revisionname", length = 125)
    private String revisionName;

    @Column(name = "revisiondate")
    private Timestamp revisionDate;

    @Column(name = "dealname", length = 125)
    private String dealName;

    @Column(name = "dealtype", length = 125)
    private String dealType;

    // FIXME : C'est quoi sourceListId dans une String ?
    @Column(name = "sourcelistid", length = 125)
    private String sourceListId;

    @Column(length = 125)
    private String side;



    public Integer getId(){
        return id;
    }


    public Trade update(Trade trade){
        this.id = trade.getId();

        return this;
    }


    @Override
    public String toString() {
        return "Trade{" +
                "id=" + id +
                ", account='" + account + '\'' +
                ", type='" + type + '\'' +
                ", buyQuantity=" + buyQuantity +
                ", sellQuantity=" + sellQuantity +
                ", buyPrice=" + buyPrice +
                ", sellPrice=" + sellPrice +
                ", tradeDate=" + tradeDate +
                ", security='" + security + '\'' +
                ", status='" + status + '\'' +
                ", trader='" + trader + '\'' +
                ", benchmark='" + benchmark + '\'' +
                ", book='" + book + '\'' +
                ", creationName='" + creationName + '\'' +
                ", creationDate=" + creationDate +
                ", revisionName='" + revisionName + '\'' +
                ", revisionDate=" + revisionDate +
                ", dealName='" + dealName + '\'' +
                ", dealType='" + dealType + '\'' +
                ", sourceListId='" + sourceListId + '\'' +
                ", side='" + side + '\'' +
                '}';
    }
}
