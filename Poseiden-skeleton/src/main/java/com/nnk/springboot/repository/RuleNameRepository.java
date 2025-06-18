package com.nnk.springboot.repository;

import com.nnk.springboot.domain.RuleName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/**
 * Repository interface for {@link RuleName} entities.
 * Provides CRUD operations and custom query methods for RuleName management.
 */
@Repository
public interface RuleNameRepository extends JpaRepository<RuleName, Integer> {
}
