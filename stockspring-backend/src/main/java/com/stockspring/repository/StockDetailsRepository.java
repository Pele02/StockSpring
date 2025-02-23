package com.stockspring.repository;

import com.stockspring.entity.StockDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StockDetailsRepository extends JpaRepository<StockDetails, Long> {
}
