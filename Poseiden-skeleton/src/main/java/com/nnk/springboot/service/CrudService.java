package com.nnk.springboot.service;

import java.util.List;

public interface CrudService<MODEL> {

    List<MODEL> getAll();
    MODEL getById(Integer id);
    void create( MODEL model);
    void update( MODEL model);
    void delete( Integer id);
}
