package com.nnk.springboot.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Data
@Entity
@Table(name = "rulename")
public class RuleName implements DomainModel<RuleName> {

    // NOTE: tinyint(4) est signé par défaut, avec une plage de -128 à 127.
    //        Pas besoin de @Min/@Max tant que la valeur est générée automatiquement par la DB (@GeneratedValue).
    //        À envisager uniquement si l'id devient modifiable côté client.
    // FIXME : Oui mais que faire si la DB n'accepte pas au dela de 128, a cause du tinyint
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false)
    private Integer id;

    @Size(max = 125, message = "name must not exceed 125 characters.")
    @Column(length = 125)
    private String name;

    @Size(max = 125, message = "description must not exceed 125 characters.")
    @Column(length = 125)
    private String description;

    @Size(max = 125, message = "json must not exceed 125 characters.")
    @Column(length = 125)
    private String json;

    @Size(max = 512, message = "template must not exceed 512 characters.")
    @Column(length = 512)
    private String template;

    @Size(max = 125, message = "sqlStr must not exceed 125 characters.")
    @Column(name = "sqlstr", length = 125)
    private String sqlStr;

    @Size(max = 125, message = "sqlPart must not exceed 125 characters.")
    @Column(name = "sqlpart", length = 125)
    private String sqlPart;



    @Override
    public RuleName update(RuleName ruleName){
        //this.id = ruleName.getId();
        this.name = ruleName.getName();
        this.description = ruleName.getDescription();
        this.json = ruleName.getJson();
        this.template = ruleName.getTemplate();
        this.sqlStr = ruleName.getSqlStr();
        this.sqlPart = ruleName.getSqlPart();

        return this;
    }

}
