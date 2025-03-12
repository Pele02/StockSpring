package com.stockspring.service;

import com.stockspring.dto.PortfolioDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PortfolioService {

    PortfolioDTO addPortfolio(String portfolioName);

    List<PortfolioDTO> getAllPortfolios();

    PortfolioDTO updatePortfolioName(Long portfolioId, String newPortfolioName);

    void deletePortfolio(Long portfolioId);

    boolean existsByName(String portfolioName);
}
