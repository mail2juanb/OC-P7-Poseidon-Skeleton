package com.nnk.springboot.domain;

public interface DomainModel<MODEL> {

    Integer getId();

    MODEL update(MODEL update);
}

