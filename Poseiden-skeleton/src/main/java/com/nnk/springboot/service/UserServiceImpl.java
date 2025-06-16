package com.nnk.springboot.service;

import  com.nnk.springboot.domain.User;
import com.nnk.springboot.repository.UserRepository;
import org.springframework.stereotype.Service;


/**
 * Service implementation for managing {@link User} entities.
 * <p>
 * This class extends {@link AbstractCrudService} to provide standard CRUD operations
 * (Create, Read, Update, Delete) for the {@link User} entity.
 * It leverages a {@link UserRepository} to perform persistence operations,
 * and inherits the core business logic from the abstract superclass.
 * </p>
 * <p>
 * The class is annotated with {@link Service}, marking it as a Spring component
 * for dependency injection and service management within the application context.
 * </p>
 *
 * <p>Service methods may throw the following exceptions:</p>
 * <ul>
 *     <li>{@link com.nnk.springboot.exception.NotFoundIdException} – when no entity is found for the provided ID</li>
 *     <li>{@link com.nnk.springboot.exception.IdLimitReachedException} – when the insertion limit is exceeded</li>
 * </ul>
 *
 * @see AbstractCrudService
 * @see com.nnk.springboot.domain.User
 * @see com.nnk.springboot.repository.UserRepository
 */
@Service
public class UserServiceImpl extends AbstractCrudService<User> {

    /**
     * Constructs a {@code UserServiceImpl} with the given repository.
     *
     * @param repository the JPA repository responsible for accessing {@link User} data
     */
    public UserServiceImpl(UserRepository repository) {
        super(repository);
    }


}
