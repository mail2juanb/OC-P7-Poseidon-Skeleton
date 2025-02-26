package com.nnk.springboot.service;

public interface CrudService<MODEL> {

    MODEL getById(Integer id);
    void create( MODEL model);
    void update( MODEL model);
    void delete( Integer id);
}
