package com.nnk.springboot.domain;

import jakarta.persistence.*;
import lombok.Data;



@Data
@Entity
@Table(name = "rulename")
public class RuleName implements DomainModel<RuleName> {
    // TODO: Map columns in data table RULENAME with corresponding java fields

    // FIXME :  tinyint(4) : Par défaut signé. Les valeurs sont comprises entre -128 à 127.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 125)
    private String name;

    @Column(length = 125)
    private String description;

    @Column(length = 125)
    private String json;

    @Column(length = 512)
    private String template;

    @Column(name = "sqlstr", length = 125)
    private String sqlStr;

    @Column(name = "sqlpart", length = 125)
    private String sqlPart;



    @Override
    public RuleName update(RuleName ruleName){
        this.id = ruleName.getId();

        return this;
    }

}
