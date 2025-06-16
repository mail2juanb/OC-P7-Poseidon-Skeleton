package com.nnk.springboot.service;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.repository.CurvePointRepository;
import org.springframework.stereotype.Service;


/**
 * Service implementation for managing {@link CurvePoint} entities.
 * <p>
 * This class extends {@link AbstractCrudService} to provide standard CRUD operations
 * (Create, Read, Update, Delete) for the {@link CurvePoint} entity.
 * It delegates all business logic to the generic methods provided by the superclass
 * and uses a {@link CurvePointRepository} for data access.
 * </p>
 * <p>
 * This service is marked with the {@link Service} annotation to be registered
 * as a Spring component.
 * </p>
 *
 * <p>Exceptions that may be thrown by service methods include:</p>
 * <ul>
 *     <li>{@link com.nnk.springboot.exception.NotFoundIdException} – when an entity is not found by ID</li>
 *     <li>{@link com.nnk.springboot.exception.IdLimitReachedException} – when the insertion limit is exceeded</li>
 * </ul>
 *
 * @see AbstractCrudService
 * @see com.nnk.springboot.domain.CurvePoint
 * @see com.nnk.springboot.repository.CurvePointRepository
 */
@Service
public class CurvePointServiceImpl extends AbstractCrudService<CurvePoint> {

    /**
     * Constructs a {@code CurvePointServiceImpl} with the given repository.
     *
     * @param repository the JPA repository used for accessing {@link CurvePoint} data
     */
    public CurvePointServiceImpl(CurvePointRepository repository) {
        super(repository);
    }

}
