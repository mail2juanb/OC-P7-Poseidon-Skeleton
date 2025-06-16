package com.nnk.springboot.repository;

import com.nnk.springboot.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


/**
 * Repository interface for {@link User} entities.
 * Provides CRUD operations and custom query methods for User management.
 */
public interface UserRepository extends JpaRepository<User, Integer> {

    /**
     * Finds a user by username, ignoring case.
     *
     * @param username The username to search for.
     * @return An Optional containing the found User or empty if not found.
     */
    Optional<User> findByUsernameIgnoreCase(String username);

}
