package com.nnk.springboot.domain;


/**
 * Generic interface representing an application domain model.
 *
 * @param <MODEL> the domain model type (ex: BidList, CurvePoint, Rating, etc.)
 */
public interface DomainModel<MODEL> {

    /**
     * Returns the unique identifier for the domain model.
     *
     * @return unique identifier.
     */
    Integer getId();


    /**
     * Updates the properties of the current entity with those of the model passed as a parameter.
     * This method is used to apply changes from one model to another,
     * particularly during update operations (PUT).
     *
     * @param model the model containing the new values
     * @return the current updated instance
     */
    MODEL update(MODEL model);
}

