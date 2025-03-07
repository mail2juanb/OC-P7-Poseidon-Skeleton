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
                // TODO : Ajouter ici une NotFoundException plutot
                .orElseThrow();
    }

    @Override
    public void create(MODEL model){
        if (model.getId() != null ){
            // TODO: Ecrire une levée d'erreur plus précise dans son message. cette méthode est utilisé par toutes les tables
            throw new RuntimeException("Save, not update !!!! ");
        }
        log.debug("CREATE = {}", model);
        repository.save(model);
    }

    // TODO: Vérifier ce qu'il se passe lorsque l'ID n'est ps trouvé et donc = null
    @Override
    public void update(MODEL model){
        MODEL updatedModel = getById(model.getId())
                .update(model);
        log.debug("UPDATE = {}", model);
        repository.save(updatedModel);
    }

    @Override
    public void delete(Integer id){
        // TODO: Vérifier que l'id existe et si elle n'existe pas, donc null, alors levée d'exception
        log.debug("DELETE = {}", id);
        repository.deleteById(id);
    }


}
