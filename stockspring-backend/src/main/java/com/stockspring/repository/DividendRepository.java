package com.stockspring.repository;

import com.stockspring.entity.Dividend;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DividendRepository extends JpaRepository<Dividend, Long> {
}
