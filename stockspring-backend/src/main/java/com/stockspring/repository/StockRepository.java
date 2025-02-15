package com.stockspring.repository;

import com.stockspring.entity.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository interface for managing {@link Stock} entities.
 * <p>
 * Provides CRUD operations and custom query methods for the {@code Stock} entity
 * by extending the {@link JpaRepository} interface.
 * </p>
 *
 * <p>
 * This repository is marked with {@link Repository} to indicate its role in the persistence layer.
 * </p>
 *
 * @version 1.0
 */
@Repository
public interface StockRepository extends JpaRepository<Stock, Long> {
    Optional<Stock> findBySymbol(String symbol);
}