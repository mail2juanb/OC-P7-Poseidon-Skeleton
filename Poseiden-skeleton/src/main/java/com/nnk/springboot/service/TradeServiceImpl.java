package com.nnk.springboot.service;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.repository.TradeRepository;
import org.springframework.stereotype.Service;


/**
 * Service implementation for managing {@link Trade} entities.
 * <p>
 * This class extends {@link AbstractCrudService} to provide standard CRUD operations
 * (Create, Read, Update, Delete) for the {@link Trade} entity.
 * It delegates persistence operations to a {@link TradeRepository},
 * and inherits all business logic from the generic superclass.
 * </p>
 * <p>
 * The class is annotated with {@link Service} to indicate that it is a Spring-managed
 * component eligible for dependency injection and service discovery.
 * </p>
 *
 * <p>Service methods may throw the following exceptions:</p>
 * <ul>
 *     <li>{@link com.nnk.springboot.exception.NotFoundIdException} – if no entity is found for a given ID</li>
 *     <li>{@link com.nnk.springboot.exception.IdLimitReachedException} – if the number of entities exceeds the allowed limit</li>
 * </ul>
 *
 * @see AbstractCrudService
 * @see com.nnk.springboot.domain.Trade
 * @see com.nnk.springboot.repository.TradeRepository
 */
@Service
public class TradeServiceImpl  extends AbstractCrudService<Trade> {

    /**
     * Constructs a {@code TradeServiceImpl} using the given repository.
     *
     * @param repository the JPA repository responsible for accessing {@link Trade} data
     */
    public TradeServiceImpl(TradeRepository repository) {
        super(repository);
    }
}