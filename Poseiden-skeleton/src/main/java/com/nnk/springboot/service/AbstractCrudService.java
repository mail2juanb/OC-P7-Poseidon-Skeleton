package com.nnk.springboot.service;


import com.nnk.springboot.domain.DomainModel;
import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.repositories.TradeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;

public abstract class AbstractCrudService<MODEL extends DomainModel<MODEL>> implements CrudService<MODEL> {
    
    protected final JpaRepository<MODEL, Integer> repository;

    protected AbstractCrudService(JpaRepository<MODEL, Integer> repository) {
        this.repository = repository;
    }

    @Override
    public MODEL getById(Integer id){
        return repository.findById(id)
                .orElseThrow();
    }

    @Override
    public void create( MODEL model){
        if (model.getId() != null ){
            throw new RuntimeException("Save, not update !!!! ");
        }


        repository.save(model);
    }

    @Override
    public void update( MODEL model){
        MODEL updatedModel = getById(model.getId())
                .update(model);
        repository.save(updatedModel);
    }

    @Override
    public void delete( Integer id){
        repository.deleteById(id);
    }


}
