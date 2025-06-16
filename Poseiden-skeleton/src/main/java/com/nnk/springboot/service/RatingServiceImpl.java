package com.nnk.springboot.service;


import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.repository.RatingRepository;
import org.springframework.stereotype.Service;


/**
 * Service implementation for managing {@link Rating} entities.
 * <p>
 * This class extends {@link AbstractCrudService} to provide standard CRUD operations
 * (Create, Read, Update, Delete) for the {@link Rating} entity.
 * It uses a {@link RatingRepository} for data persistence and delegates
 * all business logic to the generic methods in the abstract superclass.
 * </p>
 * <p>
 * The class is annotated with {@link Service} to allow Spring to detect and
 * manage it as a component.
 * </p>
 *
 * <p>Possible exceptions thrown by the service methods include:</p>
 * <ul>
 *     <li>{@link com.nnk.springboot.exception.NotFoundIdException} – when no entity is found for a given ID</li>
 *     <li>{@link com.nnk.springboot.exception.IdLimitReachedException} – when the database limit for insertions is reached</li>
 * </ul>
 *
 * @see AbstractCrudService
 * @see com.nnk.springboot.domain.Rating
 * @see com.nnk.springboot.repository.RatingRepository
 */
@Service
public class RatingServiceImpl extends AbstractCrudService<Rating> {

    /**
     * Constructs a {@code RatingServiceImpl} with the specified repository.
     *
     * @param repository the JPA repository used for accessing {@link Rating} data
     */
    public RatingServiceImpl(RatingRepository repository) {
        super(repository);
    }

}
