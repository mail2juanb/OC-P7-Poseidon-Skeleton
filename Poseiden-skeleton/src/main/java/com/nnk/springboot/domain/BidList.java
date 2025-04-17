package com.nnk.springboot.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.sql.Timestamp;

@Slf4j
@Data
@Entity
@Table(name = "bidlist")
public class BidList implements DomainModel<BidList> {

    // NOTE: tinyint(4) est signé par défaut, avec une plage de -128 à 127.
    //        Pas besoin de @Min/@Max tant que la valeur est générée automatiquement par la DB (@GeneratedValue).
    //        À envisager uniquement si l'id devient modifiable côté client.
    // FIXME : Oui mais que faire si la DB n'accepte pas au dela de 128, a cause du tinyint
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bidlistid", updatable = false)
    private Integer id;

    @NotBlank(message = "Account can't be null nor blank")
    @Column(length = 30, nullable = false)
    private String account;

    @NotBlank(message = "Type can't be null nor blank")
    @Column(length = 30, nullable = false)
    private String type;

    @Column(name = "bidquantity")
    private Double bidQuantity = 0.0;

    @Column(name = "askquantity")
    private Double askQuantity = 0.0;

    private Double bid = 0.0;

    private Double ask = 0.0;

    @Column(length = 125)
    private String benchmark = "";

    @Column(name = "bidlistdate")
    private Timestamp bidListDate = new Timestamp(System.currentTimeMillis());

    @Column(length = 125)
    private String commentary = "";

    @Column(length = 125)
    private String security = "";

    @Column(length = 10)
    private String status = "";

    @Column(length = 125)
    private String trader = "";

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



    @Override
    public BidList update(BidList bidList){
        this.id = bidList.getId();
        this.account = bidList.getAccount();
        this.type = bidList.getType();
        this.bidQuantity = bidList.getBidQuantity();

        return this;
    }

}
