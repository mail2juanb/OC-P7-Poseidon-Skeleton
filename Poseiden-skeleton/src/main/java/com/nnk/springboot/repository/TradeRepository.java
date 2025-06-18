package com.nnk.springboot.repository;

import com.nnk.springboot.domain.Trade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/**
 * Repository interface for {@link Trade} entities.
 * Provides CRUD operations and custom query methods for Trade management.
 */
@Repository
public interface TradeRepository extends JpaRepository<Trade, Integer> {
}
