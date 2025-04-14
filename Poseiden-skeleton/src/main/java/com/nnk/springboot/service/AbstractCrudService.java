package com.nnk.springboot.service;


import com.nnk.springboot.domain.DomainModel;
import com.nnk.springboot.exception.NotFoundIdException;
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
                .orElseThrow(() -> new NotFoundIdException("id not found with : " + id));
    }

    @Override
    public void create(MODEL model){
        Integer modelId = model.getId();
        if (modelId != null ){
            throw new NotFoundIdException("You can't create with a " + model.getClass().getSimpleName() + "'s id set to " + modelId);
        }
        log.debug("CREATE = {}", model);
        repository.save(model);
    }

    // NOTE: Vérifier ce qu'il se passe lorsque l'ID n'est ps trouvé et donc = null --> Tests unitaires
    //       Il se passe que l'exception idNotFound est levée
    @Override
    public void update(MODEL model){
        MODEL updatedModel = getById(model.getId())
                .update(model);
        log.debug("UPDATE = {}", model);
        repository.save(updatedModel);
    }

    @Override
    public void delete(Integer id){
        if (!repository.existsById(id)) {
            throw new NotFoundIdException("This id doesn't exist : " + id);
        }
        log.debug("DELETE = {}", id);
        repository.deleteById(id);
    }


}
