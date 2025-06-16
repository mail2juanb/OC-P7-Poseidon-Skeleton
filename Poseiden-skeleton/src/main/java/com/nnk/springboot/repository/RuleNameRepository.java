package com.nnk.springboot.repository;

import com.nnk.springboot.domain.RuleName;
import org.springframework.data.jpa.repository.JpaRepository;


/**
 * Repository interface for {@link RuleName} entities.
 * Provides CRUD operations and custom query methods for RuleName management.
 */
public interface RuleNameRepository extends JpaRepository<RuleName, Integer> {
}
