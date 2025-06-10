package com.nnk.springboot.service;

import java.util.List;


/**
 * Generic interface defining basic CRUD operations (Create, Read, Update, Delete)
 * for a given domain model.
 * This interface is intended for use with services that manipulate business entities
 * such as BidList, CurvePoint, Rating, Trade, etc.
 *
 * @param <MODEL> the type of model on which the service operates
 */
public interface CrudService<MODEL> {


    /**
     * Retrieves all instances of the model from the database.
     *
     * @return a list containing all entities
     */
    List<MODEL> getAll();


    /**
     * Retrieves an entity by its unique identifier.
     *
     * @param id the identifier of the entity to retrieve
     * @return the corresponding entity
     * @throws com.nnk.springboot.exception.NotFoundIdException if no entity matches this ID
     */
    MODEL getById(Integer id);


    /**
     * Creates a new entity in the database.
     *
     * @param model the model to be created
     * @throws com.nnk.springboot.exception.NotFoundIdException if the ID is already defined (because it must be null at creation)
     * @throws com.nnk.springboot.exception.IdLimitReachedException if an insertion limit is reached
     */
    void create( MODEL model);


    /**
     * Updates an existing entity with the provided data.
     *
     * @param model the model containing the new values
     * @throws com.nnk.springboot.exception.NotFoundIdException if the entity to be updated does not exist
     */
    void update( MODEL model);


    /**
     * Deletes an entity by its ID.
     *
     * @param id the ID of the entity to be deleted
     * @throws com.nnk.springboot.exception.NotFoundIdException if no entity matches this ID
     */
    void delete( Integer id);
}
