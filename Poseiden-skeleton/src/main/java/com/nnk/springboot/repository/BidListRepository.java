package com.nnk.springboot.repository;

import com.nnk.springboot.domain.BidList;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository interface for {@link BidList} entities.
 * Provides CRUD operations and custom query methods for BidList management.
 */
public interface BidListRepository extends JpaRepository<BidList, Integer> {

}
