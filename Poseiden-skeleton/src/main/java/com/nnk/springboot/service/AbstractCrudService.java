package com.nnk.springboot.service;


import com.nnk.springboot.domain.DomainModel;
import com.nnk.springboot.exception.IdLimitReachedException;
import com.nnk.springboot.exception.NotFoundIdException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


/**
 * Generic abstract service for CRUD operations on data models.
 * This service is responsible for managing entities of type {@link MODEL}.
 * It provides methods for creating, reading, updating, and deleting entities.
 *
 * @param <MODEL> the model type on which this service operates {@link DomainModel}
 */
@Slf4j
public abstract class AbstractCrudService<MODEL extends DomainModel<MODEL>> implements CrudService<MODEL> {


    /**
     * Upper limit allowed for the number of entries, based on the SQL TINYINT type (127 signed, or 255 unsigned).
     * This is used to prevent insertion beyond what the database can handle if the ID type is constrained.
     */
    private static final int MAX_TINYINT_ID = 127; // ou 255 si UNSIGNED


    /**
     * Reference to the Spring Data JPA repository used to access database data.
     * Injected via the constructor into concrete classes.
     */
    protected final JpaRepository<MODEL, Integer> repository;


    /**
     * Constructor to initialize the repository used by the service.
     *
     * @param repository le repository JPA associé à l'entité
     */
    protected AbstractCrudService(JpaRepository<MODEL, Integer> repository) {
        this.repository = repository;
    }


    /**
     * Retrieves all persisted entities of this type.
     *
     * @return the list of all records in the database
     */
    @Override
    public List<MODEL> getAll() {
        return repository.findAll();
    }


    /**
     * Retrieves an entity from the database based on its identifier.
     *
     * @param id the identifier of the entity to be searched for
     * @return the corresponding entity
     * @throws NotFoundIdException if no record matches this ID
     */
    @Override
    public MODEL getById(Integer id){
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundIdException("id not found with : " + id));
    }


    /**
     * Creates a new entity in the database.
     * Checks that the ID is null (to avoid forcing a specific ID) and that the TINYINT limit has not been reached.
     *
     * @param model the entity to be registered
     * @throws NotFoundIdException if the ID is already defined (insertion not allowed with ID)
     * @throws IdLimitReachedException if the maximum number of entries is reached
     */
    @Override
    public void create(MODEL model){
        Integer modelId = model.getId();
        if (modelId != null ){
            throw new NotFoundIdException("You can't create with a " + model.getClass().getSimpleName() + "'s id set to " + modelId);
        }
        long count = repository.count();
        if (count >= MAX_TINYINT_ID) {
            throw new IdLimitReachedException("Maximum number of entries (" + MAX_TINYINT_ID + ") reached. Cannot insert new one.");
        }
        repository.save(model);
    }


    /**
     * Updates an existing entity with the provided data.
     * The entity is first retrieved from the database to ensure that it exists,
     * then updated using its {@code update()} method.
     *
     * @param model the entity containing the new values
     * @throws NotFoundIdException if the entity to be updated does not exist
     */
    @Override
    public void update(MODEL model){
        MODEL updatedModel = getById(model.getId())
                .update(model);
        repository.save(updatedModel);
    }


    /**
     * Removes an entity by its ID.
     *
     * @param id l'identifiant de l'entité à supprimer
     * @throws NotFoundIdException if no entity with this ID is found
     */
    @Override
    public void delete(Integer id){
        if (!repository.existsById(id)) {
            throw new NotFoundIdException("This id doesn't exist : " + id);
        }
        repository.deleteById(id);
    }


}
