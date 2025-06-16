package com.nnk.springboot.service;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.repository.RuleNameRepository;
import org.springframework.stereotype.Service;

/**
 * Service implementation for managing {@link RuleName} entities.
 * <p>
 * This class extends {@link AbstractCrudService} to provide standard CRUD operations
 * (Create, Read, Update, Delete) for the {@link RuleName} entity.
 * It relies on a {@link RuleNameRepository} to perform persistence operations,
 * while the core business logic is inherited from the abstract superclass.
 * </p>
 * <p>
 * The {@link Service} annotation marks this class as a Spring component,
 * allowing it to be automatically detected and injected where needed.
 * </p>
 *
 * <p>Service methods may throw the following exceptions:</p>
 * <ul>
 *     <li>{@link com.nnk.springboot.exception.NotFoundIdException} – if an entity is not found for a given ID</li>
 *     <li>{@link com.nnk.springboot.exception.IdLimitReachedException} – if the insertion limit is exceeded</li>
 * </ul>
 *
 * @see AbstractCrudService
 * @see com.nnk.springboot.domain.RuleName
 * @see com.nnk.springboot.repository.RuleNameRepository
 */
@Service
public class RuleNameServiceImpl extends AbstractCrudService<RuleName> {

    /**
     * Constructs a {@code RuleNameServiceImpl} with the given repository.
     *
     * @param repository the JPA repository used to access {@link RuleName} data
     */
    public RuleNameServiceImpl(RuleNameRepository repository) {
        super(repository);
    }


}
