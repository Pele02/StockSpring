package com.stockspring.repository;

import com.stockspring.entity.Portfolio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PortfolioRepository extends JpaRepository<Portfolio, Long> {

    Optional<Portfolio> findByPortfolioName(String portfolioName);

    List<Portfolio> findByUserPortfolioId(Long userId);
}
