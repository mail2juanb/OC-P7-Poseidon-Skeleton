package com.nnk.springboot.repository;

import com.nnk.springboot.domain.Rating;
import org.springframework.data.jpa.repository.JpaRepository;


/**
 * Repository interface for {@link Rating} entities.
 * Provides CRUD operations and custom query methods for Rating management.
 */
public interface RatingRepository extends JpaRepository<Rating, Integer> {

}
