package com.nnk.springboot.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.sql.Timestamp;


@Slf4j
@Data
@Entity
@Table(name = "trade")
public class Trade implements DomainModel<Trade> {

    // NOTE: tinyint(4) est signé par défaut, avec une plage de -128 à 127.
    //        Pas besoin de @Min/@Max tant que la valeur est générée automatiquement par la DB (@GeneratedValue).
    //        À envisager uniquement si l'id devient modifiable côté client.
    // FIXME : Oui mais que faire si la DB n'accepte pas au dela de 128, a cause du tinyint
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tradeid", updatable = false)
    private Integer id;

    @NotBlank(message = "Account can't be null nor blank")
    @Column(length = 30, nullable = false)
    private String account;

    @NotBlank(message = "Type can't be null nor blank")
    @Column(length = 30, nullable = false)
    private String type;

    @Column(name = "buyquantity")
    private Double buyQuantity = 0.0;

    @Column(name = "sellquantity")
    private Double sellQuantity = 0.0;

    @Column(name = "buyprice")
    private Double buyPrice = 0.0;

    @Column(name = "sellprice")
    private Double sellPrice = 0.0;

    @Column(name = "tradedate")
    private Timestamp tradeDate = new Timestamp(System.currentTimeMillis());

    @Column(length = 125)
    private String security = "";

    @Column(length = 10)
    private String status = "";

    @Column(length = 125)
    private String trader = "";

    @Column(length = 125)
    private String benchmark = "";

    @Column(length = 125)
    private String book = "";

    @Column(name = "creationname", length = 125)
    private String creationName = "";

    @Column(name = "creationdate")
    private Timestamp creationDate = new Timestamp(System.currentTimeMillis());

    @Column(name = "revisionname", length = 125)
    private String revisionName = "";

    @Column(name = "revisiondate")
    private Timestamp revisionDate = new Timestamp(System.currentTimeMillis());

    @Column(name = "dealname", length = 125)
    private String dealName = "";

    @Column(name = "dealtype", length = 125)
    private String dealType = "";

    @Column(name = "sourcelistid", length = 125)
    private String sourceListId = "";

    @Column(length = 125)
    private String side = "";



    public Trade update(Trade trade){
        this.account = trade.getAccount();
        this.type = trade.getType();
        this.buyQuantity = trade.getBuyQuantity();

        return this;
    }

}
