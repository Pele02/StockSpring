package com.stockspring.service;

import com.stockspring.entity.Portfolio;
import com.stockspring.repository.PortfolioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PortfolioServiceImpl implements PortfolioService {
    @Autowired
    private PortfolioRepository portfolioRepository;



    public void addPortfolio(String portfolioName) {
        Portfolio portfolio = new Portfolio();
        portfolio.setPortfolioName(portfolioName);
        portfolioRepository.save(portfolio);
    }

    /**
     * @param portfolioName
     * @return
     */
    @Override
    public boolean existsByName(String portfolioName) {
        return portfolioRepository.findByName(portfolioName).isPresent();
    }
}
