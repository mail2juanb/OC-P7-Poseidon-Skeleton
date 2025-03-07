package com.nnk.springboot.service;


import com.nnk.springboot.domain.DomainModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

@Slf4j
public abstract class AbstractCrudService<MODEL extends DomainModel<MODEL>> implements CrudService<MODEL> {
    
    protected final JpaRepository<MODEL, Integer> repository;

    protected AbstractCrudService(JpaRepository<MODEL, Integer> repository) {
        this.repository = repository;
    }

    @Override
    public List<MODEL> getAll() {
        return repository.findAll();
    }

    @Override
    public MODEL getById(Integer id){
        return repository.findById(id)
                .orElseThrow();
    }

    @Override
    public void create(MODEL model){
        if (model.getId() != null ){
            throw new RuntimeException("Save, not update !!!! ");
        }
        log.debug("CREATE = {}", model);
        repository.save(model);
    }

    @Override
    public void update(MODEL model){
        MODEL updatedModel = getById(model.getId())
                .update(model);
        log.debug("UPDATE = {}", model);
        repository.save(updatedModel);
    }

    @Override
    public void delete(Integer id){
        log.debug("DELETE = {}", id);
        repository.deleteById(id);
    }


}
